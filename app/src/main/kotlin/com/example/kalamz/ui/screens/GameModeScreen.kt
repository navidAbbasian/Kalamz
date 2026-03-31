package com.example.kalamz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.kalamz.model.GameMode
import com.example.kalamz.ui.theme.*

@Composable
fun GameModeScreen(
    onModeSelected: (GameMode) -> Unit
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
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = "⚡",
                fontSize = 56.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "حالت بازی",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Quick Mode
            Card(
                onClick = { onModeSelected(GameMode.QUICK) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = YellowAccent),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "⚡ سریع",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "هر نفر ۴ کلمه می‌نویسه",
                        fontSize = 16.sp,
                        color = DarkText.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "۱۵ ثانیه زمان",
                        fontSize = 14.sp,
                        color = DarkText.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Pro Mode
            Card(
                onClick = { onModeSelected(GameMode.PRO) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = GreenAccent),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🏆 حرفه‌ای",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "هر نفر ۸ کلمه می‌نویسه",
                        fontSize = 16.sp,
                        color = White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = "۴۵ ثانیه زمان",
                        fontSize = 14.sp,
                        color = White.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

