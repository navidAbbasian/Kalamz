package com.example.kalamz.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.LocalSoundManager
import com.example.kalamz.R
import com.example.kalamz.ui.theme.*

@Composable
fun HomeScreen(onStartGame: () -> Unit) {
    val sound = LocalSoundManager.current
    val infiniteTransition = rememberInfiniteTransition(label = "glow")

    val rippleScale1 by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.8f,
        animationSpec = infiniteRepeatable(tween(1800, easing = LinearEasing), RepeatMode.Restart),
        label = "r1"
    )
    val rippleAlpha1 by infiniteTransition.animateFloat(
        initialValue = 0.5f, targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(1800, easing = LinearEasing), RepeatMode.Restart),
        label = "a1"
    )
    val rippleScale2 by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.8f,
        animationSpec = infiniteRepeatable(tween(1800, delayMillis = 900, easing = LinearEasing), RepeatMode.Restart),
        label = "r2"
    )
    val rippleAlpha2 by infiniteTransition.animateFloat(
        initialValue = 0.5f, targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(1800, delayMillis = 900, easing = LinearEasing), RepeatMode.Restart),
        label = "a2"
    )
    val buttonScale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.06f,
        animationSpec = infiniteRepeatable(tween(900, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "bs"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(RedMid, RedDark))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            // Subtitle and icons — moved above the title
            Text(
                text = "بازی خانوادگی کلمات",
                fontSize = 18.sp,
                color = White.copy(alpha = 0.75f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Icon row
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Edit, contentDescription = null, tint = White.copy(alpha = 0.6f), modifier = Modifier.size(26.dp))
                Icon(Icons.Default.Groups, contentDescription = null, tint = White.copy(alpha = 0.6f), modifier = Modifier.size(28.dp))
                Icon(Icons.Default.Timer, contentDescription = null, tint = White.copy(alpha = 0.6f), modifier = Modifier.size(26.dp))
                Icon(Icons.Outlined.EmojiEvents, contentDescription = null, tint = GoldAccent.copy(alpha = 0.8f), modifier = Modifier.size(28.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main title
            Text(
                text = "کَلَمز",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Character image between title and play button
            Image(
                painter = painterResource(id = R.drawable.kalamz_no_bg),
                contentDescription = "کاراکتر کلمز",
                modifier = Modifier
                    .offset(y = (-20).dp)
                    .height(210.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Glowing play button + label shifted 15dp upward
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.offset(y = (-15).dp)
            ) {
                Box(modifier = Modifier.size(160.dp), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.size(140.dp).scale(rippleScale1).graphicsLayer { alpha = rippleAlpha1 }.background(White.copy(alpha = 0.22f), CircleShape))
                    Box(modifier = Modifier.size(140.dp).scale(rippleScale2).graphicsLayer { alpha = rippleAlpha2 }.background(White.copy(alpha = 0.22f), CircleShape))
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .scale(buttonScale)
                            .background(Brush.radialGradient(listOf(White, White.copy(alpha = 0.88f))), CircleShape)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { sound?.playButtonClick(); onStartGame() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "شروع بازی",
                            tint = RedPrimary,
                            modifier = Modifier.size(52.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text("شروع بازی", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = White.copy(alpha = 0.85f))
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
