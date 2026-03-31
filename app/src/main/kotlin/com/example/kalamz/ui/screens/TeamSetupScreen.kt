package com.example.kalamz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.model.Team
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.theme.*

@Composable
fun TeamSetupScreen(
    teams: List<Team>,
    onPlayerNameChanged: (playerId: Int, name: String) -> Unit,
    onConfirm: () -> Unit
) {
    val allNamesFilled = teams.all { it.player1.name.isNotBlank() && it.player2.name.isNotBlank() }

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
                text = "✏️ تیم‌بندی",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "اسم بازیکن‌ها رو وارد کنید",
                fontSize = 16.sp,
                color = White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                teams.forEach { team ->
                    val teamColor = teamColors.getOrElse(team.id) { teamColors[0] }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "تیم ${team.id + 1}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = teamColor,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = team.player1.name,
                                onValueChange = { onPlayerNameChanged(team.player1.id, it) },
                                label = { Text("بازیکن ۱") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = teamColor,
                                    unfocusedBorderColor = Color.LightGray,
                                    cursorColor = teamColor
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = team.player2.name,
                                onValueChange = { onPlayerNameChanged(team.player2.id, it) },
                                label = { Text("بازیکن ۲") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = teamColor,
                                    unfocusedBorderColor = Color.LightGray,
                                    cursorColor = teamColor
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            KalamzButton(
                text = "ادامه ➡️",
                onClick = onConfirm,
                enabled = allNamesFilled,
                containerColor = YellowAccent,
                contentColor = DarkText
            )
        }
    }
}

