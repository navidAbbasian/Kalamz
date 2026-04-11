package com.example.kalamz.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Groups2
import androidx.compose.material.icons.filled.Groups3
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.ui.theme.*

private data class PlayerOption(val count: Int, val icon: ImageVector, val color: androidx.compose.ui.graphics.Color)

@Composable
fun PlayerCountScreen(onPlayerCountSelected: (Int) -> Unit) {
    val options = listOf(
        PlayerOption(4,  Icons.Default.People,    teamColors[1]),
        PlayerOption(6,  Icons.Default.Groups,    teamColors[2]),
        PlayerOption(8,  Icons.Default.Groups2,   teamColors[3]),
        PlayerOption(10, Icons.Default.Groups3,   teamColors[4]),
        PlayerOption(12, Icons.Default.Groups,    teamColors[5]),
        PlayerOption(14, Icons.Default.Whatshot,  teamColors[6]),
        PlayerOption(16, Icons.Default.PersonAdd, teamColors[7]),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(RedMid, RedDark)))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            Icon(Icons.Default.Groups, contentDescription = null, tint = White, modifier = Modifier.size(52.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text("چند نفرید؟", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = White, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(6.dp))
            Text("تعداد بازیکنان رو انتخاب کنید", fontSize = 15.sp, color = White.copy(alpha = 0.7f), textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(28.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(options) { option ->
                    Card(
                        onClick = { onPlayerCountSelected(option.count) },
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.12f)),
                        border = BorderStroke(1.dp, option.color.copy(alpha = 0.45f)),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(option.icon, contentDescription = null, tint = option.color, modifier = Modifier.size(30.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("${option.count} نفر", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = White)
                            Text("${option.count / 2} تیم", fontSize = 12.sp, color = White.copy(alpha = 0.6f))
                        }
                    }
                }
            }
        }
    }
}
