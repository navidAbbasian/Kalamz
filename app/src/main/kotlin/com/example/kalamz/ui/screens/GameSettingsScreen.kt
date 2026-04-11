package com.example.kalamz.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
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
            .background(Brush.verticalGradient(listOf(RedMid, RedDark)))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Icon(Icons.Default.Settings, contentDescription = null, tint = White, modifier = Modifier.size(52.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text("تنظیمات بازی", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = White, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))
            Text("بازی رو سفارشی‌سازی کنید", fontSize = 15.sp, color = White.copy(alpha = 0.7f), textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(40.dp))

            GlassSettingCard(
                icon = Icons.Default.Edit,
                title = "کلمات هر بازیکن",
                value = "$wordsPerPlayer کلمه",
                onDecrease = { if (wordsPerPlayer > 2) wordsPerPlayer-- },
                onIncrease = { if (wordsPerPlayer < 15) wordsPerPlayer++ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            GlassSettingCard(
                icon = Icons.Default.Timer,
                title = "زمان هر نوبت",
                value = "$turnSeconds ثانیه",
                onDecrease = { if (turnSeconds > 15) turnSeconds -= 5 },
                onIncrease = { if (turnSeconds < 180) turnSeconds += 5 }
            )

            Spacer(modifier = Modifier.weight(1f))

            KalamzButton(
                text = "شروع بازی",
                onClick = { onConfirmSettings(wordsPerPlayer, turnSeconds * 1000L) },
                containerColor = White.copy(alpha = 0.92f),
                contentColor = RedPrimary,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun GlassSettingCard(
    icon: ImageVector,
    title: String,
    value: String,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.1f)),
        border = BorderStroke(1.dp, White.copy(alpha = 0.28f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(icon, contentDescription = null, tint = White.copy(alpha = 0.85f), modifier = Modifier.size(20.dp))
                Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = White)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                FilledIconButton(
                    onClick = onDecrease, modifier = Modifier.size(48.dp), shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = White.copy(alpha = 0.2f), contentColor = White)
                ) { Icon(Icons.Default.Remove, contentDescription = "کم کن", modifier = Modifier.size(22.dp)) }

                Text(value, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = GoldAccent,
                    textAlign = TextAlign.Center, modifier = Modifier.widthIn(min = 110.dp))

                FilledIconButton(
                    onClick = onIncrease, modifier = Modifier.size(48.dp), shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = White.copy(alpha = 0.2f), contentColor = White)
                ) { Text("+", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = White) }
            }
        }
    }
}
