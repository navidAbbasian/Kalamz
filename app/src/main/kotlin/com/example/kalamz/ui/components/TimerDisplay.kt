package com.example.kalamz.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
import com.example.kalamz.ui.theme.RedPrimary
import com.example.kalamz.ui.theme.YellowAccent

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

    val progressColor = if (isUrgent) RedPrimary else MaterialTheme.colorScheme.primary
    val bgColor = Color.LightGray.copy(alpha = 0.3f)

    Box(modifier = modifier.size(120.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(120.dp)) {
            // Background circle
            drawArc(
                color = bgColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
            // Progress arc
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = "$seconds",
            fontSize = if (isUrgent) 40.sp else 36.sp,
            fontWeight = FontWeight.Bold,
            color = if (isUrgent) RedPrimary else MaterialTheme.colorScheme.primary
        )
    }
}

