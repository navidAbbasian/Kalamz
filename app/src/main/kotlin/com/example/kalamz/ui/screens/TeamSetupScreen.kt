package com.example.kalamz.ui.screens

import androidx.compose.foundation.BorderStroke
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
    onTeamNameChanged: (teamId: Int, name: String) -> Unit,
    onConfirm: () -> Unit
) {
    val allNamesFilled = teams.all {
        it.player1.name.isNotBlank() && it.player2.name.isNotBlank() && it.name.isNotBlank()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(RedMid, RedDark)))
    ) {
        // imePadding روی ستون بیرونی → وقتی کیبورد باز می‌شه فضای کل ستون کوچک می‌شه
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()                       // ← کلید حل مشکل
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(52.dp))

            Text(
                text = "تیم‌بندی",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "اسم تیم‌ها و بازیکن‌ها رو وارد کنید",
                fontSize = 14.sp,
                color = White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ناحیه اسکرول — کارت‌های تیم + دکمه ادامه (داخل اسکرول)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                teams.forEach { team ->
                    val teamColor = teamColors.getOrElse(team.id) { teamColors[0] }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.1f)),
                        border = BorderStroke(1.dp, teamColor.copy(alpha = 0.45f)),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Team number label
                            Text(
                                text = "تیم ${team.id + 1}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = teamColor,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            GlassTextField(
                                value = team.name,
                                onValueChange = { onTeamNameChanged(team.id, it) },
                                label = "نام تیم",
                                accentColor = teamColor
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            GlassTextField(
                                value = team.player1.name,
                                onValueChange = { onPlayerNameChanged(team.player1.id, it) },
                                label = "بازیکن ۱",
                                accentColor = teamColor
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            GlassTextField(
                                value = team.player2.name,
                                onValueChange = { onPlayerNameChanged(team.player2.id, it) },
                                label = "بازیکن ۲",
                                accentColor = teamColor
                            )
                        }
                    }
                }

                // دکمه داخل اسکرول → همیشه بعد از آخرین کارت قابل اسکرول است
                Spacer(modifier = Modifier.height(4.dp))
                KalamzButton(
                    text = "ادامه",
                    onClick = onConfirm,
                    enabled = allNamesFilled,
                    containerColor = if (allNamesFilled) White.copy(alpha = 0.92f) else White.copy(alpha = 0.25f),
                    contentColor = if (allNamesFilled) RedPrimary else White.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun GlassTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    accentColor: Color = White
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 13.sp) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = accentColor,
            unfocusedBorderColor = White.copy(alpha = 0.3f),
            focusedLabelColor = accentColor,
            unfocusedLabelColor = White.copy(alpha = 0.5f),
            cursorColor = accentColor,
            focusedTextColor = White,
            unfocusedTextColor = White,
            focusedContainerColor = White.copy(alpha = 0.08f),
            unfocusedContainerColor = White.copy(alpha = 0.04f),
        )
    )
}
