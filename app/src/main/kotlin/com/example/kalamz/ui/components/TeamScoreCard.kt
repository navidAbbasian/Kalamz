package com.example.kalamz.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.model.Team
import com.example.kalamz.ui.theme.White
import com.example.kalamz.ui.theme.teamColors

@Composable
fun TeamScoreCard(
    team: Team,
    rank: Int,
    isWinner: Boolean = false,
    modifier: Modifier = Modifier
) {
    val teamColor = teamColors.getOrElse(team.id) { teamColors[0] }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isWinner) teamColor.copy(alpha = 0.15f) else White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isWinner) 8.dp else 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Rank
                Text(
                    text = if (isWinner) "🏆" else "$rank",
                    fontSize = if (isWinner) 28.sp else 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = teamColor,
                    modifier = Modifier.width(40.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = team.name.ifBlank { "تیم ${team.id + 1}" },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = teamColor
                    )
                    Text(
                        text = "${team.player1.name} و ${team.player2.name}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${team.totalScore}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = teamColor
                )
                Text(
                    text = "امتیاز",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

