package com.example.kalamz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.model.RoundType
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.theme.*

@Composable
fun RoundEndScreen(
    round: RoundType,
    teamScores: List<Pair<Int, Int>>, // teamId to score this round
    onProceed: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(RedPrimary, RedDark)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "🏁",
                fontSize = 56.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${round.persianTitle.split(":").first()} تموم شد!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.15f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "امتیازات این راند:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    teamScores.sortedByDescending { it.second }.forEach { (teamId, score) ->
                        val teamColor = teamColors.getOrElse(teamId) { teamColors[0] }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "تیم ${teamId + 1}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = teamColor
                            )
                            Text(
                                text = "$score کلمه",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = YellowAccent
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            val buttonText = when (round) {
                RoundType.DESCRIBE -> "راند دوم ➡️"
                RoundType.ONE_WORD -> "راند سوم ➡️"
                RoundType.PANTOMIME -> "نتایج نهایی 🏆"
            }

            KalamzButton(
                text = buttonText,
                onClick = onProceed,
                containerColor = YellowAccent,
                contentColor = DarkText
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

