package com.example.kalamz.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.LocalSoundManager
import com.example.kalamz.model.Player
import com.example.kalamz.model.RoundType
import com.example.kalamz.model.Team
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.theme.*

@Composable
fun TurnIntroScreen(
    currentPlayer: Player,
    team: Team,
    round: RoundType,
    onStartTurn: () -> Unit
) {
    val sound = LocalSoundManager.current
    LaunchedEffect(Unit) { sound?.playTurnStart() }

    val infiniteTransition = rememberInfiniteTransition(label = "bg")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.12f,
        animationSpec = infiniteRepeatable(tween(900, easing = EaseInOutSine), RepeatMode.Reverse), label = "p"
    )

    val teamColor = teamColors.getOrElse(team.id) { teamColors[0] }
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
            modifier = Modifier.padding(horizontal = 28.dp)
        ) {
            // Team badge
            Box(modifier = Modifier.size(100.dp).scale(pulse).background(teamColor.copy(alpha = 0.22f), CircleShape), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.size(76.dp).background(teamColor.copy(alpha = 0.35f), CircleShape), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Groups, contentDescription = null, tint = teamColor, modifier = Modifier.size(38.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Team name
            Surface(shape = RoundedCornerShape(20.dp), color = teamColor.copy(alpha = 0.25f)) {
                Text("تیم ${team.name}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = teamColor,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("نوبت", fontSize = 22.sp, color = White.copy(alpha = 0.7f), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))
            Text(currentPlayer.name, fontSize = 38.sp, fontWeight = FontWeight.Bold, color = GoldAccent, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(28.dp))

            // Round info card
            Card(shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = roundAccent.copy(alpha = 0.15f)),
                elevation = CardDefaults.cardElevation(0.dp)) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("راند ${round.roundNumber}", fontSize = 13.sp, color = roundAccent.copy(alpha = 0.8f))
                    Text(round.persianTitle, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = roundAccent)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(round.persianDescription, fontSize = 13.sp, color = White.copy(alpha = 0.7f), textAlign = TextAlign.Center, lineHeight = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Scores summary
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(18.dp).align(Alignment.CenterVertically))
                Text("امتیاز کل تیم: ${team.totalScore}", fontSize = 15.sp, color = White.copy(alpha = 0.75f))
            }

            Spacer(modifier = Modifier.height(44.dp))

            KalamzButton(
                text = "شروع نوبت",
                onClick = onStartTurn,
                containerColor = roundAccent.copy(alpha = 0.85f),
                contentColor = White,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


