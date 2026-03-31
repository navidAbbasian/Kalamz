package com.example.kalamz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.model.RoundType
import com.example.kalamz.model.Team
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.components.TeamScoreCard
import com.example.kalamz.ui.theme.*

@Composable
fun ResultsScreen(
    teams: List<Team>,
    onPlayAgain: () -> Unit
) {
    val sortedTeams = teams.sortedByDescending { it.totalScore }
    val winnerScore = sortedTeams.firstOrNull()?.totalScore ?: 0

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
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "🏆",
                fontSize = 56.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "نتایج نهایی",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                sortedTeams.forEachIndexed { index, team ->
                    val isWinner = team.totalScore == winnerScore

                    TeamScoreCard(
                        team = team,
                        rank = index + 1,
                        isWinner = isWinner
                    )

                    // Expandable details
                    var expanded by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.9f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = if (expanded) "جزئیات ▲" else "جزئیات ▼",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MediumGray,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )

                            if (expanded) {
                                Spacer(modifier = Modifier.height(8.dp))

                                val rounds = listOf(
                                    RoundType.DESCRIBE,
                                    RoundType.ONE_WORD,
                                    RoundType.PANTOMIME
                                )
                                rounds.forEachIndexed { rIdx, round ->
                                    val score = team.scoresPerRound.getOrElse(rIdx) { 0 }
                                    val words = team.correctWordsPerRound.getOrElse(rIdx) { emptyList() }

                                    Text(
                                        text = "${round.persianTitle.split(":").first()}: $score کلمه",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = RedPrimary,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )

                                    if (words.isNotEmpty()) {
                                        words.forEach { word ->
                                            Text(
                                                text = "  ✅ $word",
                                                fontSize = 14.sp,
                                                color = DarkText.copy(alpha = 0.7f),
                                                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                                            )
                                        }
                                    } else {
                                        Text(
                                            text = "  —",
                                            fontSize = 14.sp,
                                            color = MediumGray,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            KalamzButton(
                text = "بازی دوباره 🔄",
                onClick = onPlayAgain,
                containerColor = YellowAccent,
                contentColor = DarkText
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

