package com.example.kalamz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.model.Player
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.theme.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import kotlinx.coroutines.launch

@Composable
fun WordEntryScreen(
    player: Player,
    wordsPerPlayer: Int,
    currentPlayerIndex: Int,
    totalPlayers: Int,
    onSubmitWords: (playerIndex: Int, words: List<String>) -> Unit
) {
    val wordCount = wordsPerPlayer
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
            val listState = rememberLazyListState()
            val focusManager = LocalFocusManager.current
            val coroutineScope = rememberCoroutineScope()

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
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
                }

                // Word input fields
                itemsIndexed(words) { index, word ->
                    OutlinedTextField(
                        value = word,
                        onValueChange = { newVal ->
                            words = words.toMutableList().also { it[index] = newVal }
                            // Auto-scroll to keep focused field visible
                            coroutineScope.launch {
                                listState.animateScrollToItem(index + 1) // +1 because of header item
                            }
                        },
                        label = { Text("کلمه ${index + 1}") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
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

                item {
                    Spacer(modifier = Modifier.height(24.dp))

                    KalamzButton(
                        text = "ثبت کلمات ✅",
                        onClick = {
                            focusManager.clearFocus()
                            onSubmitWords(currentPlayerIndex, words)
                        },
                        enabled = allWordsFilled,
                        containerColor = YellowAccent,
                        contentColor = DarkText,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Extra space at bottom to ensure button is always visible above keyboard
                    Spacer(modifier = Modifier.height(120.dp))
                }
            }
        }
    }
}

