package com.example.kalamz.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kalamz.model.*
import com.example.kalamz.ui.screens.*
import com.example.kalamz.viewmodel.GameViewModel

@Composable
fun KalamzApp(
    viewModel: GameViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showHome by remember { mutableStateOf(true) }

    // Reset showHome when game resets
    LaunchedEffect(state.phase) {
        if (state.phase is GamePhase.Setup) {
            showHome = true
        }
    }

    when (val phase = state.phase) {
        is GamePhase.Setup -> {
            if (showHome) {
                HomeScreen(
                    onStartGame = { showHome = false }
                )
            } else {
                PlayerCountScreen(
                    onPlayerCountSelected = { count ->
                        viewModel.setPlayerCount(count)
                    }
                )
            }
        }

        is GamePhase.TeamSetup -> {
            TeamSetupScreen(
                teams = state.teams,
                onPlayerNameChanged = { playerId, name ->
                    viewModel.updatePlayerName(playerId, name)
                },
                onTeamNameChanged = { teamId, name ->
                    viewModel.updateTeamName(teamId, name)
                },
                onConfirm = { viewModel.confirmTeams() }
            )
        }

        is GamePhase.ModeSelection -> {
            GameModeScreen(
                onModeSelected = { mode ->
                    viewModel.setGameMode(mode)
                }
            )
        }

        is GamePhase.WordEntry -> {
            val playerIndex = phase.currentPlayerIndex
            val player = state.allPlayers[playerIndex]
            WordEntryScreen(
                player = player,
                gameMode = state.gameMode,
                currentPlayerIndex = playerIndex,
                totalPlayers = state.allPlayers.size,
                onSubmitWords = { idx, words ->
                    viewModel.submitWordsForPlayer(idx, words)
                }
            )
        }

        is GamePhase.RoundIntro -> {
            RoundIntroScreen(
                round = phase.round,
                onStartRound = { viewModel.startRound() }
            )
        }

        is GamePhase.TurnReady, is GamePhase.TurnActive, is GamePhase.TurnEnd -> {
            TurnScreen(
                state = state,
                onStartTurn = { viewModel.startTurn() },
                onCorrect = { viewModel.markCorrect() },
                onPrevious = { viewModel.previousWord() },
                onNext = { viewModel.nextWord() },
                onProceed = { viewModel.proceedAfterTurn() },
                onPauseTimer = { viewModel.pauseTimer() },
                onResumeTimer = { viewModel.resumeTimer() }
            )
        }

        is GamePhase.RoundEnd -> {
            val roundIndex = phase.round.roundNumber - 1
            val teamScores = state.teams.map { team ->
                Pair(team.id, team.scoresPerRound[roundIndex])
            }
            RoundEndScreen(
                round = phase.round,
                teams = state.teams,
                teamScores = teamScores,
                onProceed = { viewModel.proceedToNextRound() }
            )
        }

        is GamePhase.GameOver -> {
            ResultsScreen(
                teams = state.teams,
                onPlayAgain = { viewModel.resetGame() }
            )
        }
    }
}


