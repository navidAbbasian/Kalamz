package com.example.kalamz.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.LocalSoundManager
import com.example.kalamz.R
import com.example.kalamz.model.RoundType
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.theme.*

@Composable
fun RoundIntroScreen(round: RoundType, onStartRound: () -> Unit) {
    val sound = LocalSoundManager.current
    LaunchedEffect(Unit) { sound?.playRoundStart() }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.1f,
        animationSpec = infiniteRepeatable(tween(700, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "s"
    )

    val roundImage = when (round) {
        RoundType.DESCRIBE   -> R.drawable.img_round_describe
        RoundType.ONE_WORD   -> R.drawable.img_round_one_word
        RoundType.PANTOMIME  -> R.drawable.img_round_pantomime
    }
    val roundAccent = when (round) {
        RoundType.DESCRIBE  -> GoldAccent
        RoundType.ONE_WORD  -> BlueAccent
        RoundType.PANTOMIME -> PurpleAccent
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(RedMid, RedDark))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Animated placeholder image for each round
            Box(
                modifier = Modifier.size(140.dp).scale(scale).background(roundAccent.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.size(110.dp).background(roundAccent.copy(alpha = 0.12f), CircleShape), contentAlignment = Alignment.Center) {
                    Image(painter = painterResource(roundImage), contentDescription = round.persianTitle, modifier = Modifier.size(80.dp))
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = roundAccent.copy(alpha = 0.2f)),
                border = BorderStroke(1.dp, roundAccent.copy(alpha = 0.5f)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Text(
                    text = "راند ${round.roundNumber} از ۳",
                    fontSize = 14.sp, fontWeight = FontWeight.Bold, color = roundAccent,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(round.persianTitle.substringAfter(": ").ifBlank { round.persianTitle },
                fontSize = 34.sp, fontWeight = FontWeight.Bold, color = White, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.1f)),
                border = BorderStroke(1.dp, White.copy(alpha = 0.22f)), elevation = CardDefaults.cardElevation(0.dp)) {
                Text(round.persianDescription, fontSize = 17.sp, color = White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center, lineHeight = 28.sp, modifier = Modifier.padding(16.dp))
            }

            Spacer(modifier = Modifier.height(48.dp))

            KalamzButton(
                text = "شروع", onClick = onStartRound,
                containerColor = roundAccent.copy(alpha = 0.85f), contentColor = White,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
        }
    }
}
