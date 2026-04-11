package com.example.kalamz.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.LocalSoundManager
import com.example.kalamz.model.GamePhase
import com.example.kalamz.model.GameUiState
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.components.TimerDisplay
import com.example.kalamz.ui.components.WordCard
import com.example.kalamz.ui.theme.*

@Composable
fun TurnScreen(
    state: GameUiState,
    onStartTurn: () -> Unit,
    onCorrect: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onProceed: () -> Unit,
    onPauseTimer: () -> Unit,
    onResumeTimer: () -> Unit,
    onRemoveWord: (String) -> Unit
) {
    when (val phase = state.phase) {
        is GamePhase.TurnReady -> {
            val team = state.teams[phase.teamId]
            val currentPlayer = if (state.currentPlayerSlot == 1) team.player1 else team.player2
            TurnIntroScreen(
                currentPlayer = currentPlayer,
                team = team,
                round = state.currentRound,
                onStartTurn = onStartTurn
            )
        }
        is GamePhase.TurnActive -> TurnActiveContent(
            state = state,
            onCorrect = onCorrect,
            onPrevious = onPrevious,
            onNext = onNext,
            onPauseTimer = onPauseTimer,
            onResumeTimer = onResumeTimer
        )
        is GamePhase.TurnEnd -> TurnEndContent(
            correctCount = phase.correctCount,
            correctWords = phase.correctWords,
            onProceed = onProceed,
            onRemoveWord = onRemoveWord
        )
        else -> {}
    }
}

// ────────── TURN ACTIVE ──────────

@Composable
private fun TurnActiveContent(
    state: GameUiState,
    onCorrect: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onPauseTimer: () -> Unit,
    onResumeTimer: () -> Unit
) {
    val sound = LocalSoundManager.current
    val teamColor = teamColors.getOrElse(state.currentTeamIndex) { teamColors[0] }

    // Timer-tick and warning sounds
    // تیک‌تاک روی تمام مدت نوبت پخش می‌شه؛ در ۵ ثانیه آخر به هشدار تبدیل می‌شه
    val timeLeft = state.timeLeftMillis
    LaunchedEffect(timeLeft / 1000) {
        if (!state.isTimerPaused && timeLeft > 0) {
            when {
                timeLeft <= 5_000 -> sound?.playTimerWarning()
                else              -> sound?.playTimerTick()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(RedMid, RedDark)))) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(52.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = teamColor.copy(alpha = 0.2f)),
                    border = BorderStroke(1.dp, teamColor.copy(alpha = 0.4f)), elevation = CardDefaults.cardElevation(0.dp)) {
                    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(state.teams[state.currentTeamIndex].name.ifBlank { "تیم ${state.currentTeamIndex + 1}" }, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = teamColor)
                        Text(state.currentRound.persianTitle.substringBefore(":"), fontSize = 10.sp, color = White.copy(alpha = 0.5f))
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatBadge("${state.turnCorrectCount}", "درست", GreenAccent)
                    StatBadge("${state.remainingWords.size}", "باقی", GoldAccent)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TimerDisplay(timeLeftMillis = state.timeLeftMillis, totalTimeMillis = state.timerDurationMillis, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            state.currentWord?.let { word ->
                WordCard(word = word.text, onPrevious = onPrevious, onNext = onNext, canGoBack = state.canGoToPrevious, modifier = Modifier.fillMaxWidth())
            }

            Spacer(modifier = Modifier.weight(1f))

            // Pause / Resume button
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Box(
                    modifier = Modifier.size(52.dp).background(White.copy(alpha = 0.15f), CircleShape)
                        .clickable(indication = null, interactionSource = remember { MutableInteractionSource() },
                            onClick = if (state.isTimerPaused) { { sound?.playButtonClick(); onResumeTimer() } } else { { sound?.playButtonClick(); onPauseTimer() } }),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (state.isTimerPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                        contentDescription = if (state.isTimerPaused) "ادامه" else "توقف",
                        tint = White, modifier = Modifier.size(26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            KalamzButton(
                text = "درسته!",
                onClick = { sound?.playCorrectWord(); onCorrect() },
                containerColor = GreenAccent.copy(alpha = 0.85f),
                contentColor = White,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isTimerPaused
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Pause overlay
        if (state.isTimerPaused) {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.75f)), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Pause, contentDescription = null, tint = White, modifier = Modifier.size(72.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("متوقف شده", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("برای ادامه دکمه پلی بزن", fontSize = 15.sp, color = White.copy(alpha = 0.7f))
                }
            }
        }
    }
}

@Composable
private fun StatBadge(value: String, label: String, color: Color) {
    Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.18f)),
        border = BorderStroke(1.dp, color.copy(alpha = 0.4f)), elevation = CardDefaults.cardElevation(0.dp)) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
            Text(text = label, fontSize = 10.sp, color = White.copy(alpha = 0.6f))
        }
    }
}

// ────────── TURN END ──────────

@Composable
private fun TurnEndContent(correctCount: Int, correctWords: List<String>, onProceed: () -> Unit, onRemoveWord: (String) -> Unit) {
    val sound = LocalSoundManager.current
    LaunchedEffect(Unit) { sound?.playTimerEnd() }

    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(RedMid, RedDark)))) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(52.dp))

            Icon(
                imageVector = if (correctCount > 0) Icons.Outlined.EmojiEvents else Icons.Default.SentimentNeutral,
                contentDescription = null, tint = if (correctCount > 0) GoldAccent else White.copy(alpha = 0.6f),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("تایم تموم شد!", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = White, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(6.dp))

            Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = GoldAccent.copy(alpha = 0.2f)),
                border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.5f)), elevation = CardDefaults.cardElevation(0.dp)) {
                Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("$correctCount کلمه درست", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("برای حذف کلمه اشتباه روی ایکس بزن", fontSize = 12.sp, color = White.copy(alpha = 0.55f), textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(16.dp))

            if (correctWords.isNotEmpty()) {
                Card(modifier = Modifier.fillMaxWidth().weight(1f), shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.1f)),
                    border = BorderStroke(1.dp, White.copy(alpha = 0.25f)), elevation = CardDefaults.cardElevation(0.dp)) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp).verticalScroll(rememberScrollState())) {
                        Text("کلمات درست:", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = White.copy(alpha = 0.7f),
                            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(10.dp))
                        correctWords.forEach { word ->
                            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f).padding(start = 4.dp)) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = GreenAccent, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(word, fontSize = 17.sp, color = White)
                                }
                                IconButton(onClick = { sound?.playWordSkip(); onRemoveWord(word) }, modifier = Modifier.size(36.dp)) {
                                    Icon(Icons.Default.Close, contentDescription = "حذف", tint = Color(0xFFFF5252), modifier = Modifier.size(20.dp))
                                }
                            }
                            HorizontalDivider(color = White.copy(alpha = 0.08f), thickness = 0.5.dp)
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            KalamzButton(
                text = "نفر بعدی",
                onClick = onProceed,
                containerColor = White.copy(alpha = 0.92f),
                contentColor = RedPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

