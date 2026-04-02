package com.example.kalamz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.model.GamePhase
import com.example.kalamz.model.GameUiState
import com.example.kalamz.model.Team
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
        is GamePhase.TurnReady -> TurnReadyContent(
            playerName = phase.playerName,
            team = state.teams[phase.teamId],
            onStart = onStartTurn
        )
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

@Composable
private fun TurnReadyContent(
    playerName: String,
    team: Team,
    onStart: () -> Unit
) {
    val teamColor = teamColors.getOrElse(team.id) { teamColors[0] }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(teamColor, teamColor.copy(alpha = 0.7f))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "📱",
                fontSize = 56.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "گوشی رو بده به",
                fontSize = 20.sp,
                color = White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = playerName,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = team.name.ifBlank { "تیم ${team.id + 1}" },
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = YellowAccent,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            KalamzButton(
                text = "شروع ⏱️",
                onClick = onStart,
                containerColor = White,
                contentColor = teamColor,
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }
    }
}

@Composable
private fun TurnActiveContent(
    state: GameUiState,
    onCorrect: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onPauseTimer: () -> Unit,
    onResumeTimer: () -> Unit
) {
    val teamColor = teamColors.getOrElse(state.currentTeamIndex) { teamColors[0] }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(teamColor.copy(alpha = 0.1f), White)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Pause button at top center
            KalamzButton(
                text = if (state.isTimerPaused) "▶️" else "⏸️",
                onClick = if (state.isTimerPaused) onResumeTimer else onPauseTimer,
                containerColor = if (state.isTimerPaused) GreenAccent else OrangeAccent,
                contentColor = White,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Round indicator & score
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = state.currentRound.persianTitle.split(":").first(),
                        fontSize = 14.sp,
                        color = MediumGray
                    )
                    Text(
                        text = state.teams[state.currentTeamIndex].name.ifBlank { 
                            "تیم ${state.currentTeamIndex + 1}" 
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = teamColor
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${state.turnCorrectCount}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = GreenAccent
                    )
                    Text(
                        text = "درست",
                        fontSize = 12.sp,
                        color = MediumGray
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${state.remainingWords.size}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = OrangeAccent
                    )
                    Text(
                        text = "باقیمانده",
                        fontSize = 12.sp,
                        color = MediumGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Timer (without pause button)
            TimerDisplay(
                timeLeftMillis = state.timeLeftMillis,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Word Card
            state.currentWord?.let { word ->
                WordCard(
                    word = word.text,
                    onPrevious = onPrevious,
                    onNext = onNext,
                    canGoBack = state.canGoToPrevious,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Action button
            KalamzButton(
                text = "درسته ✅",
                onClick = onCorrect,
                containerColor = GreenAccent,
                contentColor = White,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isTimerPaused
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Pause overlay
        if (state.isTimerPaused) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "⏸️",
                        fontSize = 64.sp
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "بازی متوقف شده",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "برای ادامه روی دکمه پخش کلیک کن",
                        fontSize = 16.sp,
                        color = White.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun TurnEndContent(
    correctCount: Int,
    correctWords: List<String>,
    onProceed: () -> Unit,
    onRemoveWord: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(RedPrimary, RedDark)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = if (correctCount > 0) "🎉" else "😅",
                fontSize = 56.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "تایم تموم شد!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$correctCount کلمه درست",
                fontSize = 22.sp,
                color = YellowAccent,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "اگه کلمه‌ای اشتباه زده شده، روی ❌ بزن تا حذف بشه",
                fontSize = 13.sp,
                color = White.copy(alpha = 0.65f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (correctWords.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.15f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "کلمات درست:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = White.copy(alpha = 0.8f),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        correctWords.forEach { word ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "✅ $word",
                                    fontSize = 18.sp,
                                    color = White,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 8.dp),
                                    textAlign = TextAlign.Start
                                )
                                IconButton(
                                    onClick = { onRemoveWord(word) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "حذف کلمه",
                                        tint = Color.Red.copy(alpha = 0.85f),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            KalamzButton(
                text = "بعدی ➡️",
                onClick = onProceed,
                containerColor = YellowAccent,
                contentColor = DarkText
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

