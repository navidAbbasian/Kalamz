package com.example.kalamz.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.model.RoundType
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.theme.*

@Composable
fun RoundIntroScreen(
    round: RoundType,
    onStartRound: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "round_pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "round_icon_pulse"
    )

    val emoji = when (round) {
        RoundType.DESCRIBE -> "🗣️"
        RoundType.ONE_WORD -> "☝️"
        RoundType.PANTOMIME -> "🎭"
    }

    val bgColors = when (round) {
        RoundType.DESCRIBE -> listOf(RedPrimary, RedDark)
        RoundType.ONE_WORD -> listOf(BlueAccent, BlueAccent.copy(alpha = 0.7f))
        RoundType.PANTOMIME -> listOf(PurpleAccent, PurpleAccent.copy(alpha = 0.7f))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = bgColors)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .scale(scale)
                    .background(White.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emoji,
                    fontSize = 64.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = round.persianTitle,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = round.persianDescription,
                fontSize = 18.sp,
                color = White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            KalamzButton(
                text = "شروع! 🚀",
                onClick = onStartRound,
                containerColor = YellowAccent,
                contentColor = DarkText,
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }
    }
}

