package com.example.kalamz.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.ui.theme.*

@Composable
fun TimerDisplay(
    timeLeftMillis: Long,
    totalTimeMillis: Long = 60_000L,
    modifier: Modifier = Modifier
) {
    val progress by animateFloatAsState(
        targetValue = timeLeftMillis.toFloat() / totalTimeMillis.toFloat(),
        animationSpec = tween(durationMillis = 100),
        label = "timer_progress"
    )

    val seconds = (timeLeftMillis / 1000).toInt()
    val isUrgent = seconds <= 10

    val progressColor = if (isUrgent) Color(0xFFFF5252) else GoldAccent
    val trackColor = White.copy(alpha = 0.15f)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = GlassWhite),
        border = BorderStroke(1.dp, GlassBorder),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Circular timer
            Box(modifier = Modifier.size(72.dp), contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(72.dp)) {
                    drawArc(
                        color = trackColor,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = progressColor,
                        startAngle = -90f,
                        sweepAngle = 360f * progress,
                        useCenter = false,
                        style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text(
                    text = "$seconds",
                    fontSize = if (isUrgent) 26.sp else 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = progressColor
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "⏱️",
                    fontSize = 28.sp
                )
                Text(
                    text = "زمان باقی‌مانده",
                    fontSize = 12.sp,
                    color = White.copy(alpha = 0.7f)
                )
            }
        }
    }
}
