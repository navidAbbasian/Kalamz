package com.example.kalamz.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.kalamz.model.RoundType
import com.example.kalamz.model.Team
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.components.TeamScoreCard
import com.example.kalamz.ui.theme.*

@Composable
fun ResultsScreen(teams: List<Team>, onPlayAgain: () -> Unit) {
    val sound = LocalSoundManager.current
    LaunchedEffect(Unit) { sound?.playGameOver() }

    val sortedTeams = teams.sortedByDescending { it.totalScore }
    val winnerScore = sortedTeams.firstOrNull()?.totalScore ?: 0

    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(RedMid, RedDark)))) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(52.dp))

            Icon(Icons.Outlined.EmojiEvents, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(64.dp).align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(8.dp))
            Text("نتایج نهایی", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = White, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("برنده: ${sortedTeams.firstOrNull()?.name?.ifBlank { "تیم ۱" } ?: ""}",
                    fontSize = 18.sp, color = GoldAccent, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                sortedTeams.forEachIndexed { index, team ->
                    val isWinner = team.totalScore == winnerScore
                    TeamScoreCard(team = team, rank = index + 1, isWinner = isWinner)

                    var expanded by remember { mutableStateOf(false) }
                    Card(modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }, shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.08f)),
                        border = BorderStroke(1.dp, White.copy(alpha = 0.15f)), elevation = CardDefaults.cardElevation(0.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                Icon(if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null, tint = White.copy(alpha = 0.55f), modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("جزئیات", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = White.copy(alpha = 0.55f))
                            }
                            if (expanded) {
                                Spacer(modifier = Modifier.height(8.dp))
                                val rounds = listOf(RoundType.DESCRIBE, RoundType.ONE_WORD, RoundType.PANTOMIME)
                                rounds.forEachIndexed { rIdx, round ->
                                    val score = team.scoresPerRound.getOrElse(rIdx) { 0 }
                                    val words = team.correctWordsPerRound.getOrElse(rIdx) { emptyList() }
                                    val roundAccent = when (round) { RoundType.DESCRIBE -> GoldAccent; RoundType.ONE_WORD -> BlueAccent; else -> PurpleAccent }
                                    if (rIdx > 0) HorizontalDivider(color = White.copy(alpha = 0.08f), thickness = 0.5.dp, modifier = Modifier.padding(vertical = 6.dp))
                                    Text("${round.persianTitle.substringBefore(":")}: $score کلمه", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = roundAccent, modifier = Modifier.padding(top = 4.dp))
                                    if (words.isNotEmpty()) {
                                        words.forEach { word ->
                                            Row(modifier = Modifier.padding(start = 8.dp, top = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                                                Icon(Icons.Default.Check, contentDescription = null, tint = GreenAccent, modifier = Modifier.size(14.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(word, fontSize = 13.sp, color = White.copy(alpha = 0.65f))
                                            }
                                        }
                                    } else {
                                        Text("  —", fontSize = 13.sp, color = White.copy(alpha = 0.4f), modifier = Modifier.padding(start = 8.dp))
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            KalamzButton(text = "بازی دوباره", onClick = onPlayAgain, containerColor = White.copy(alpha = 0.92f), contentColor = RedPrimary)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
