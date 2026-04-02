package com.example.kalamz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.theme.*

@Composable
fun GameSettingsScreen(
    onConfirmSettings: (wordsPerPlayer: Int, timerDurationMillis: Long) -> Unit
) {
    var wordsPerPlayer by remember { mutableIntStateOf(5) }
    var turnSeconds by remember { mutableIntStateOf(45) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = listOf(RedPrimary, RedDark))
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            Text(
                text = "⚙️",
                fontSize = 56.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "تنظیمات بازی",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Words per player card
            SettingCard(
                emoji = "✍️",
                title = "کلمات هر بازیکن",
                value = "$wordsPerPlayer کلمه",
                onDecrease = { if (wordsPerPlayer > 2) wordsPerPlayer-- },
                onIncrease = { if (wordsPerPlayer < 15) wordsPerPlayer++ }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Turn duration card
            SettingCard(
                emoji = "⏱️",
                title = "زمان هر نوبت",
                value = "$turnSeconds ثانیه",
                onDecrease = { if (turnSeconds > 15) turnSeconds -= 5 },
                onIncrease = { if (turnSeconds < 180) turnSeconds += 5 }
            )

            Spacer(modifier = Modifier.weight(1f))

            KalamzButton(
                text = "شروع بازی 🎮",
                onClick = {
                    onConfirmSettings(wordsPerPlayer, turnSeconds * 1000L)
                },
                containerColor = YellowAccent,
                contentColor = DarkText,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SettingCard(
    emoji: String,
    title: String,
    value: String,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$emoji $title",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                KalamzButton(
                    text = "−",
                    onClick = onDecrease,
                    containerColor = White.copy(alpha = 0.25f),
                    contentColor = White,
                    modifier = Modifier.size(52.dp)
                )

                Text(
                    text = value,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = YellowAccent,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(min = 100.dp)
                )

                KalamzButton(
                    text = "+",
                    onClick = onIncrease,
                    containerColor = White.copy(alpha = 0.25f),
                    contentColor = White,
                    modifier = Modifier.size(52.dp)
                )
            }
        }
    }
}

