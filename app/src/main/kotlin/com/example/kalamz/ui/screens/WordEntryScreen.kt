package com.example.kalamz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.model.GameMode
import com.example.kalamz.model.Player
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.theme.*

@Composable
fun WordEntryScreen(
    player: Player,
    gameMode: GameMode,
    currentPlayerIndex: Int,
    totalPlayers: Int,
    onSubmitWords: (playerIndex: Int, words: List<String>) -> Unit
) {
    val wordCount = gameMode.wordsPerPlayer
    var words by remember(player.id) {
        mutableStateOf(List(wordCount) { "" })
    }
    var showWords by remember(player.id) { mutableStateOf(false) }

    val allWordsFilled = words.all { it.isNotBlank() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(RedPrimary, RedDark)
                )
            )
    ) {
        if (!showWords) {
            // Privacy screen - hand the phone to the player
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🤫",
                    fontSize = 64.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "گوشی رو بده به",
                    fontSize = 20.sp,
                    color = White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = player.name,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = YellowAccent,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "نفر ${currentPlayerIndex + 1} از $totalPlayers",
                    fontSize = 16.sp,
                    color = White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(48.dp))

                KalamzButton(
                    text = "آماده‌ام! ✍️",
                    onClick = { showWords = true },
                    containerColor = YellowAccent,
                    contentColor = DarkText,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            }
        } else {
            // Word entry form
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "✍️ ${player.name}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$wordCount کلمه بنویس!",
                    fontSize = 16.sp,
                    color = White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    words.forEachIndexed { index, word ->
                        OutlinedTextField(
                            value = word,
                            onValueChange = { newVal ->
                                words = words.toMutableList().also { it[index] = newVal }
                            },
                            label = { Text("کلمه ${index + 1}") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = YellowAccent,
                                unfocusedBorderColor = White.copy(alpha = 0.5f),
                                focusedLabelColor = YellowAccent,
                                unfocusedLabelColor = White.copy(alpha = 0.7f),
                                cursorColor = YellowAccent,
                                focusedTextColor = White,
                                unfocusedTextColor = White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                KalamzButton(
                    text = "ثبت کلمات ✅",
                    onClick = { onSubmitWords(currentPlayerIndex, words) },
                    enabled = allWordsFilled,
                    containerColor = YellowAccent,
                    contentColor = DarkText
                )
            }
        }
    }
}

