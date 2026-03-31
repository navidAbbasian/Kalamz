package com.example.kalamz.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.example.kalamz.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var timer: CountDownTimer? = null

    // Words collected from all players
    private var allWords: List<Word> = emptyList()

    // Track the play order: list of (teamIndex, playerSlot)
    private var playOrder: List<Pair<Int, Int>> = emptyList()

    // ---- SETUP ----

    fun setPlayerCount(count: Int) {
        val teamCount = count / 2
        val players = mutableListOf<Player>()
        val teams = mutableListOf<Team>()

        var playerId = 0
        for (i in 0 until teamCount) {
            val p1 = Player(id = playerId++, name = "", teamId = i)
            val p2 = Player(id = playerId++, name = "", teamId = i)
            players.add(p1)
            players.add(p2)
            teams.add(Team(id = i, player1 = p1, player2 = p2))
        }

        _uiState.update {
            it.copy(
                playerCount = count,
                allPlayers = players,
                teams = teams,
                phase = GamePhase.TeamSetup
            )
        }
    }

    fun updatePlayerName(playerId: Int, name: String) {
        _uiState.update { state ->
            val updatedPlayers = state.allPlayers.map { p ->
                if (p.id == playerId) p.copy(name = name) else p
            }
            val updatedTeams = state.teams.map { team ->
                val p1 = updatedPlayers.find { it.id == team.player1.id } ?: team.player1
                val p2 = updatedPlayers.find { it.id == team.player2.id } ?: team.player2
                team.copy(player1 = p1, player2 = p2)
            }
            state.copy(allPlayers = updatedPlayers, teams = updatedTeams)
        }
    }

    fun confirmTeams() {
        _uiState.update { it.copy(phase = GamePhase.ModeSelection) }
    }

    fun setGameMode(mode: GameMode) {
        _uiState.update {
            it.copy(
                gameMode = mode,
                phase = GamePhase.WordEntry(currentPlayerIndex = 0)
            )
        }
    }

    // ---- WORD ENTRY ----

    fun submitWordsForPlayer(playerIndex: Int, words: List<String>) {
        val player = _uiState.value.allPlayers[playerIndex]
        val startId = allWords.size
        val newWords = words.mapIndexed { i, text ->
            Word(id = startId + i, text = text.trim(), submittedByPlayerId = player.id)
        }
        allWords = allWords + newWords

        val nextIndex = playerIndex + 1
        if (nextIndex < _uiState.value.allPlayers.size) {
            _uiState.update {
                it.copy(phase = GamePhase.WordEntry(currentPlayerIndex = nextIndex))
            }
        } else {
            // All players have entered words, build the play order and start round 1
            buildPlayOrder()
            _uiState.update {
                it.copy(
                    wordBank = allWords,
                    remainingWords = allWords.shuffled(),
                    currentRound = RoundType.DESCRIBE,
                    playOrderIndex = 0,
                    phase = GamePhase.RoundIntro(RoundType.DESCRIBE)
                )
            }
        }
    }

    private fun buildPlayOrder() {
        val teamCount = _uiState.value.teams.size
        val order = mutableListOf<Pair<Int, Int>>()
        // First all Player 1s, then all Player 2s
        for (slot in 1..2) {
            for (teamIdx in 0 until teamCount) {
                order.add(Pair(teamIdx, slot))
            }
        }
        playOrder = order
    }

    // ---- ROUND FLOW ----

    fun startRound() {
        val state = _uiState.value
        val (teamIdx, slot) = playOrder[state.playOrderIndex]
        val team = state.teams[teamIdx]
        val playerName = if (slot == 1) team.player1.name else team.player2.name

        _uiState.update {
            it.copy(
                phase = GamePhase.TurnReady(playerName = playerName, teamId = teamIdx),
                currentTeamIndex = teamIdx,
                currentPlayerSlot = slot
            )
        }
    }

    fun startTurn() {
        val shuffled = _uiState.value.remainingWords.shuffled()
        _uiState.update {
            it.copy(
                phase = GamePhase.TurnActive,
                remainingWords = shuffled,
                currentWord = shuffled.firstOrNull(),
                timeLeftMillis = 60_000L,
                turnCorrectWords = emptyList(),
                turnCorrectCount = 0
            )
        }
        startTimer()
    }

    fun markCorrect() {
        val state = _uiState.value
        val word = state.currentWord ?: return

        val updatedRemaining = state.remainingWords.filter { it.id != word.id }
        val updatedTurnWords = state.turnCorrectWords + word.text
        val updatedCount = state.turnCorrectCount + 1

        // Update team score
        val roundIndex = state.currentRound.roundNumber - 1
        val updatedTeams = state.teams.map { team ->
            if (team.id == state.currentTeamIndex) {
                val newScores = team.scoresPerRound.toMutableList()
                newScores[roundIndex] = newScores[roundIndex] + 1
                val newCorrectWords = team.correctWordsPerRound.toMutableList()
                newCorrectWords[roundIndex] = newCorrectWords[roundIndex] + word.text
                team.copy(scoresPerRound = newScores, correctWordsPerRound = newCorrectWords)
            } else team
        }

        if (updatedRemaining.isEmpty()) {
            // Bank is empty - end round
            timer?.cancel()
            _uiState.update {
                it.copy(
                    remainingWords = updatedRemaining,
                    currentWord = null,
                    turnCorrectWords = updatedTurnWords,
                    turnCorrectCount = updatedCount,
                    teams = updatedTeams,
                    phase = GamePhase.TurnEnd(
                        correctCount = updatedCount,
                        correctWords = updatedTurnWords
                    )
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    remainingWords = updatedRemaining,
                    currentWord = updatedRemaining.firstOrNull(),
                    turnCorrectWords = updatedTurnWords,
                    turnCorrectCount = updatedCount,
                    teams = updatedTeams
                )
            }
        }
    }

    fun passWord() {
        val state = _uiState.value
        val word = state.currentWord ?: return

        // Move current word to end of remaining
        val withoutCurrent = state.remainingWords.filter { it.id != word.id }
        val updatedRemaining = withoutCurrent + word

        _uiState.update {
            it.copy(
                remainingWords = updatedRemaining,
                currentWord = updatedRemaining.firstOrNull()
            )
        }
    }

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(60_000L, 100L) {
            override fun onTick(millisUntilFinished: Long) {
                _uiState.update { it.copy(timeLeftMillis = millisUntilFinished) }
            }

            override fun onFinish() {
                onTimerFinished()
            }
        }.start()
    }

    private fun onTimerFinished() {
        val state = _uiState.value
        _uiState.update {
            it.copy(
                timeLeftMillis = 0L,
                phase = GamePhase.TurnEnd(
                    correctCount = state.turnCorrectCount,
                    correctWords = state.turnCorrectWords
                )
            )
        }
    }

    fun proceedAfterTurn() {
        val state = _uiState.value

        // Check if word bank is empty (round ended)
        if (state.remainingWords.isEmpty()) {
            handleRoundEnd()
            return
        }

        // Move to next player in play order
        val nextOrderIndex = state.playOrderIndex + 1
        if (nextOrderIndex < playOrder.size) {
            val (teamIdx, slot) = playOrder[nextOrderIndex]
            val team = state.teams[teamIdx]
            val playerName = if (slot == 1) team.player1.name else team.player2.name

            _uiState.update {
                it.copy(
                    playOrderIndex = nextOrderIndex,
                    currentTeamIndex = teamIdx,
                    currentPlayerSlot = slot,
                    phase = GamePhase.TurnReady(playerName = playerName, teamId = teamIdx)
                )
            }
        } else {
            // All players have played, loop back to first
            _uiState.update {
                it.copy(
                    playOrderIndex = 0,
                    phase = GamePhase.TurnReady(
                        playerName = state.teams[playOrder[0].first].let { t ->
                            if (playOrder[0].second == 1) t.player1.name else t.player2.name
                        },
                        teamId = playOrder[0].first
                    )
                )
            }
        }
    }

    private fun handleRoundEnd() {
        val state = _uiState.value
        _uiState.update {
            it.copy(phase = GamePhase.RoundEnd(state.currentRound))
        }
    }

    fun proceedToNextRound() {
        val state = _uiState.value
        val nextRound = when (state.currentRound) {
            RoundType.DESCRIBE -> RoundType.ONE_WORD
            RoundType.ONE_WORD -> RoundType.PANTOMIME
            RoundType.PANTOMIME -> null
        }

        if (nextRound != null) {
            _uiState.update {
                it.copy(
                    currentRound = nextRound,
                    remainingWords = allWords.shuffled(),
                    playOrderIndex = 0,
                    phase = GamePhase.RoundIntro(nextRound)
                )
            }
        } else {
            // Game over!
            _uiState.update {
                it.copy(phase = GamePhase.GameOver)
            }
        }
    }

    fun resetGame() {
        timer?.cancel()
        allWords = emptyList()
        playOrder = emptyList()
        _uiState.value = GameUiState()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}

