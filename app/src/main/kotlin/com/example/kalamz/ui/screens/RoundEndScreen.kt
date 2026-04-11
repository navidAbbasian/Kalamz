package com.example.kalamz.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.LocalSoundManager
import com.example.kalamz.model.RoundType
import com.example.kalamz.model.Team
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.theme.*

@Composable
fun RoundEndScreen(
    round: RoundType,
    teams: List<Team>,
    teamScores: List<Pair<Int, Int>>,
    onProceed: () -> Unit
) {
    val sound = LocalSoundManager.current
    LaunchedEffect(Unit) { sound?.playRoundEnd() }

    val roundAccent = when (round) {
        RoundType.DESCRIBE  -> GoldAccent
        RoundType.ONE_WORD  -> BlueAccent
        RoundType.PANTOMIME -> PurpleAccent
    }

    val medals: List<ImageVector> = listOf(Icons.Outlined.EmojiEvents, Icons.Default.Star, Icons.Default.StarOutline)

    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(RedMid, RedDark)))) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(52.dp))

            Icon(Icons.Default.Flag, contentDescription = null, tint = roundAccent, modifier = Modifier.size(56.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text("${round.persianTitle.substringBefore(":")} تموم شد!", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = White, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(24.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.1f)),
                border = BorderStroke(1.dp, roundAccent.copy(alpha = 0.35f)), elevation = CardDefaults.cardElevation(0.dp)) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp).verticalScroll(rememberScrollState())) {
                    Text("امتیازات این راند", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = roundAccent,
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(12.dp))

                    teamScores.sortedByDescending { it.second }.forEachIndexed { index, (teamId, score) ->
                        val team = teams.find { it.id == teamId } ?: return@forEachIndexed
                        val teamColor = teamColors.getOrElse(teamId) { teamColors[0] }
                        val medalIcon = medals.getOrElse(index) { Icons.Default.Circle }
                        val medalColor = when (index) {
                            0 -> Color(0xFFFFD700)
                            1 -> Color(0xFFC0C0C0)
                            else -> Color(0xFFCD7F32)
                        }
                        if (index > 0) HorizontalDivider(color = White.copy(alpha = 0.08f), thickness = 0.5.dp)
                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(medalIcon, contentDescription = null, tint = medalColor, modifier = Modifier.size(22.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(team.name.ifBlank { "تیم ${teamId + 1}" }, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = teamColor)
                            }
                            Text("$score کلمه", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            val buttonText = when (round) {
                RoundType.DESCRIBE  -> "راند دوم"
                RoundType.ONE_WORD  -> "راند سوم"
                RoundType.PANTOMIME -> "نتایج نهایی"
            }

            KalamzButton(text = buttonText, onClick = onProceed, containerColor = roundAccent.copy(alpha = 0.85f), contentColor = White)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
