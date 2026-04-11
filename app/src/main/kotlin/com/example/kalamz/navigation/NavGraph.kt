package com.example.kalamz.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kalamz.LocalSoundManager
import com.example.kalamz.model.*
import com.example.kalamz.ui.screens.*
import com.example.kalamz.ui.theme.*
import com.example.kalamz.util.MusicTrack
import com.example.kalamz.viewmodel.GameViewModel

enum class MainTab { SETTINGS, HOME, HOW_TO_PLAY }

@Composable
fun KalamzApp(viewModel: GameViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()
    var showHome by remember { mutableStateOf(true) }
    var currentTab by remember { mutableStateOf(MainTab.HOME) }
    val sound = LocalSoundManager.current

    // ── سوییچ موسیقی بر اساس فاز بازی ──────────────────────────────────────
    LaunchedEffect(state.phase, state.currentRound) {
        val track = when (state.phase) {
            // صفحه اصلی + مراحل تنظیم → موزیک منو
            is GamePhase.Setup,
            is GamePhase.TeamSetup,
            is GamePhase.CustomSettings,
            is GamePhase.WordEntry -> MusicTrack.MENU
            // هر فاز درون بازی → موزیک بر اساس راند جاری
            else -> when (state.currentRound) {
                RoundType.DESCRIBE  -> MusicTrack.ROUND_1
                RoundType.ONE_WORD  -> MusicTrack.ROUND_2
                RoundType.PANTOMIME -> MusicTrack.ROUND_3
            }
        }
        sound?.switchMusic(track)
    }

    LaunchedEffect(state.resetCount) {
        showHome = true
    }

    when (val phase = state.phase) {
        is GamePhase.Setup -> {
            if (showHome) {
                // Main screen with bottom navigation
                Box(modifier = Modifier.fillMaxSize()) {
                    when (currentTab) {
                        MainTab.HOME -> HomeScreen(onStartGame = { showHome = false })
                        MainTab.HOW_TO_PLAY -> HowToPlayScreen()
                        MainTab.SETTINGS -> SettingsScreen()
                    }
                    KalamzBottomNav(
                        currentTab = currentTab,
                        onTabSelected = { currentTab = it },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            } else {
                BackHandler { showHome = true }
                PlayerCountScreen(
                    onPlayerCountSelected = { count -> viewModel.setPlayerCount(count) }
                )
            }
        }

        is GamePhase.TeamSetup -> {
            BackHandler { viewModel.navigateBack() }
            TeamSetupScreen(
                teams = state.teams,
                onPlayerNameChanged = { playerId, name -> viewModel.updatePlayerName(playerId, name) },
                onTeamNameChanged = { teamId, name -> viewModel.updateTeamName(teamId, name) },
                onConfirm = { viewModel.confirmTeams() }
            )
        }

        is GamePhase.CustomSettings -> {
            BackHandler { viewModel.navigateBack() }
            GameSettingsScreen(
                onConfirmSettings = { wordsPerPlayer, timerDurationMillis ->
                    viewModel.setGameSettings(wordsPerPlayer, timerDurationMillis)
                }
            )
        }

        is GamePhase.WordEntry -> {
            BackHandler { viewModel.navigateBack() }
            val playerIndex = phase.currentPlayerIndex
            val player = state.allPlayers[playerIndex]
            WordEntryScreen(
                player = player,
                wordsPerPlayer = state.wordsPerPlayer,
                currentPlayerIndex = playerIndex,
                totalPlayers = state.allPlayers.size,
                onSubmitWords = { idx, words -> viewModel.submitWordsForPlayer(idx, words) }
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
                onResumeTimer = { viewModel.resumeTimer() },
                onRemoveWord = { word -> viewModel.removeCorrectWord(word) }
            )
        }

        is GamePhase.RoundEnd -> {
            val roundIndex = phase.round.roundNumber - 1
            val teamScores = state.teams.map { team -> Pair(team.id, team.scoresPerRound[roundIndex]) }
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

@Composable
fun KalamzBottomNav(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color(0xCC000000))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Glass pill container
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 10.dp)
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(White.copy(alpha = 0.19f)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Default.Settings,
                label = "تنظیمات",
                selected = currentTab == MainTab.SETTINGS,
                onClick = { onTabSelected(MainTab.SETTINGS) }
            )
            BottomNavHomeItem(
                selected = currentTab == MainTab.HOME,
                onClick = { onTabSelected(MainTab.HOME) }
            )
            BottomNavItem(
                icon = Icons.Default.Info,
                label = "آموزش",
                selected = currentTab == MainTab.HOW_TO_PLAY,
                onClick = { onTabSelected(MainTab.HOW_TO_PLAY) }
            )
        }
    }
}

@Composable
private fun RowScope.BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) White else White.copy(alpha = 0.55f),
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) White else White.copy(alpha = 0.55f)
        )
    }
}

@Composable
private fun RowScope.BottomNavHomeItem(selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    if (selected) White else White.copy(alpha = 0.18f),
                    CircleShape
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "خانه",
                tint = if (selected) RedPrimary else White.copy(alpha = 0.7f),
                modifier = Modifier.size(26.dp)
            )
        }
    }
}
