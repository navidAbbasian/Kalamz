package com.example.kalamz.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.LocalSoundManager
import com.example.kalamz.R
import com.example.kalamz.ui.components.GlassCard
import com.example.kalamz.ui.theme.*

@Composable
fun HomeScreen(onStartGame: () -> Unit) {
    val sound = LocalSoundManager.current
    val infiniteTransition = rememberInfiniteTransition(label = "home")

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
    val floatY by infiniteTransition.animateFloat(
        initialValue = -7f, targetValue = 7f,
        animationSpec = infiniteRepeatable(tween(2400, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "float"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(RedMid, RedDark)))
    ) {
        // ── Decorative background blobs ──────────────────────────────────────
        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.TopStart)
                .offset(x = (-110).dp, y = (-70).dp)
                .background(White.copy(alpha = 0.055f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 90.dp, y = 70.dp)
                .background(White.copy(alpha = 0.045f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(130.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-16).dp, y = 170.dp)
                .background(GoldAccent.copy(alpha = 0.08f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomStart)
                .offset(x = 30.dp, y = (-200).dp)
                .background(BlueAccent.copy(alpha = 0.07f), CircleShape)
        )

        // ── Floating decorative icons ─────────────────────────────────────────
        Icon(
            Icons.Default.Star, contentDescription = null,
            tint = GoldAccent.copy(alpha = 0.5f),
            modifier = Modifier
                .size(22.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-38).dp, y = (88 + floatY).dp)
        )
        Icon(
            Icons.Default.Star, contentDescription = null,
            tint = White.copy(alpha = 0.22f),
            modifier = Modifier
                .size(14.dp)
                .align(Alignment.TopStart)
                .offset(x = 46.dp, y = (140 - floatY).dp)
        )
        Icon(
            Icons.Outlined.EmojiEvents, contentDescription = null,
            tint = GoldAccent.copy(alpha = 0.32f),
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.BottomStart)
                .offset(x = 26.dp, y = (-138 + floatY).dp)
        )
        Icon(
            Icons.Default.AutoAwesome, contentDescription = null,
            tint = White.copy(alpha = 0.18f),
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-52).dp, y = (-168 - floatY).dp)
        )
        Icon(
            Icons.Default.AutoAwesome, contentDescription = null,
            tint = GoldAccent.copy(alpha = 0.25f),
            modifier = Modifier
                .size(14.dp)
                .align(Alignment.TopStart)
                .offset(x = 22.dp, y = (260 + floatY).dp)
        )

        // ── Main content ──────────────────────────────────────────────────────
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 48.dp, bottom = 96.dp)
        ) {
            // ─── Title ───────────────────────────────────────────────────────
            Text(
                text = "کَلَمز",
                fontSize = 65.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // ─── Subtitle glass badge ─────────────────────────────────────────
            GlassCard {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Outlined.EmojiEvents, null, tint = GoldAccent, modifier = Modifier.size(17.dp))
                    Text(
                        "بازی گروهی خانوادگی کلمات",
                        fontSize = 14.sp,
                        color = White.copy(alpha = 0.85f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(0.dp))

            // ─── Character image ──────────────────────────────────────────────
            Image(
                painter = painterResource(id = R.drawable.kalamz_no_bg),
                contentDescription = "کاراکتر کلمز",
                modifier = Modifier
                    .height(162.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ─── Game round feature cards ──────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RoundFeatureCard(
                    icon = Icons.Default.RecordVoiceOver,
                    title = "توضیح",
                    subtitle = "راند اول",
                    accentColor = BlueAccent,
                    modifier = Modifier.weight(1f)
                )
                RoundFeatureCard(
                    icon = Icons.Default.Filter1,
                    title = "یک کلمه",
                    subtitle = "راند دوم",
                    accentColor = OrangeAccent,
                    modifier = Modifier.weight(1f)
                )
                RoundFeatureCard(
                    icon = Icons.Default.PanToolAlt,
                    title = "پانتومیم",
                    subtitle = "راند سوم",
                    accentColor = GreenAccent,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ─── Info strip ────────────────────────────────────────────────────
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 14.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InfoItem(icon = Icons.Default.Groups, line1 = "۲ تا ۱۶", line2 = "بازیکن")
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(38.dp)
                            .background(White.copy(alpha = 0.3f))
                    )
                    InfoItem(icon = Icons.Default.Layers, line1 = "سه راند", line2 = "متفاوت")
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(38.dp)
                            .background(White.copy(alpha = 0.3f))
                    )
                    InfoItem(icon = Icons.Default.Edit, line1 = "کلمات", line2 = "خودتون")
                }
            }

            // ─── Glowing play button ────────────────────────────────────────────
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
            Box(modifier = Modifier.size(124.dp), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(106.dp)
                        .scale(rippleScale1)
                        .graphicsLayer { alpha = rippleAlpha1 }
                        .background(White.copy(alpha = 0.22f), CircleShape)
                )
                Box(
                    modifier = Modifier
                        .size(106.dp)
                        .scale(rippleScale2)
                        .graphicsLayer { alpha = rippleAlpha2 }
                        .background(White.copy(alpha = 0.22f), CircleShape)
                )
                Box(
                    modifier = Modifier
                        .size(91.dp)
                        .scale(buttonScale)
                        .background(
                            Brush.radialGradient(listOf(White, White.copy(alpha = 0.88f))),
                            CircleShape
                        )
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
                        modifier = Modifier.size(42.dp)
                    )
                }
            }
            } // end weighted Box
        }
    }
}

// ── Private helper composables ─────────────────────────────────────────────────

@Composable
private fun RoundFeatureCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    GlassCard(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 14.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(accentColor.copy(alpha = 0.22f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center
            )
            Text(
                subtitle,
                fontSize = 11.sp,
                color = White.copy(alpha = 0.55f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun InfoItem(icon: ImageVector, line1: String, line2: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(icon, contentDescription = null, tint = White.copy(alpha = 0.8f), modifier = Modifier.size(22.dp))
        Text(
            line1,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = White.copy(alpha = 0.85f),
            textAlign = TextAlign.Center
        )
        Text(
            line2,
            fontSize = 11.sp,
            color = White.copy(alpha = 0.55f),
            textAlign = TextAlign.Center
        )
    }
}
