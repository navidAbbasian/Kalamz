package com.example.kalamz.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.R
import com.example.kalamz.model.Player
import com.example.kalamz.ui.components.KalamzButton
import com.example.kalamz.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordEntryScreen(
    player: Player,
    wordsPerPlayer: Int,
    currentPlayerIndex: Int,
    totalPlayers: Int,
    onSubmitWords: (playerIndex: Int, words: List<String>) -> Unit
) {
    var words by remember(player.id) { mutableStateOf(List(wordsPerPlayer) { "" }) }
    var showWords by remember(player.id) { mutableStateOf(false) }
    val allWordsFilled = words.all { it.isNotBlank() }

    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(RedMid, RedDark)))) {
        if (!showWords) {
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Placeholder image: person shushing
                Image(
                    painter = painterResource(R.drawable.img_secret_player),
                    contentDescription = "گوشی رو بده",
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(28.dp))
                Text("گوشی رو بده به", fontSize = 20.sp, color = White.copy(alpha = 0.8f), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
                Text(player.name, fontSize = 40.sp, fontWeight = FontWeight.Bold, color = GoldAccent, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
                Text("نفر ${currentPlayerIndex + 1} از $totalPlayers", fontSize = 15.sp, color = White.copy(alpha = 0.6f), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(48.dp))
                KalamzButton(
                    text = "آماده‌ام",
                    onClick = { showWords = true },
                    containerColor = White.copy(alpha = 0.92f),
                    contentColor = RedPrimary,
                    modifier = Modifier.fillMaxWidth(0.75f)
                )
            }
        } else {
            val listState = rememberLazyListState()
            val focusManager = LocalFocusManager.current
            val scope = rememberCoroutineScope()

            // imePadding روی LazyColumn → لیست کوتاه می‌شه تا کیبورد روی آیتم‌ها نیفته
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding(),                  // ← کلید حل مشکل
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(player.name, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "$wordsPerPlayer کلمه بنویس!",
                        fontSize = 15.sp,
                        color = White.copy(alpha = 0.75f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                itemsIndexed(words) { index, word ->
                    // BringIntoViewRequester → وقتی فیلد فوکوس می‌گیره خودش رو از زیر کیبورد می‌کشه بیرون
                    val bringIntoViewRequester = remember { BringIntoViewRequester() }

                    OutlinedTextField(
                        value = word,
                        onValueChange = { newVal ->
                            words = words.toMutableList().also { it[index] = newVal }
                        },
                        label = { Text("کلمه ${index + 1}", fontSize = 13.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .bringIntoViewRequester(bringIntoViewRequester)
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    scope.launch {
                                        // کمی صبر می‌کنیم تا کیبورد کامل باز بشه
                                        delay(300)
                                        bringIntoViewRequester.bringIntoView()
                                    }
                                }
                            },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldAccent,
                            unfocusedBorderColor = White.copy(alpha = 0.35f),
                            focusedLabelColor = GoldAccent,
                            unfocusedLabelColor = White.copy(alpha = 0.55f),
                            cursorColor = GoldAccent,
                            focusedTextColor = White,
                            unfocusedTextColor = White,
                            focusedContainerColor = White.copy(alpha = 0.1f),
                            unfocusedContainerColor = White.copy(alpha = 0.06f),
                        )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    KalamzButton(
                        text = "ثبت کلمات",
                        onClick = { focusManager.clearFocus(); onSubmitWords(currentPlayerIndex, words) },
                        enabled = allWordsFilled,
                        containerColor = if (allWordsFilled) White.copy(alpha = 0.92f) else White.copy(alpha = 0.25f),
                        contentColor = if (allWordsFilled) RedPrimary else White.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
