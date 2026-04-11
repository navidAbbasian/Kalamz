package com.example.kalamz.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(RedMid, RedDark)))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 56.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null, tint = White, modifier = Modifier.size(52.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "تنظیمات",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                SettingsSection("درباره بازی") {
                    SettingsRow("نام بازی", "کَلَمز", Icons.Default.SportsEsports)
                    SettingsDivider()
                    SettingsRow("نسخه", "1.0.0", Icons.Default.Info)
                    SettingsDivider()
                    SettingsRow("تعداد راندها", "۳ راند", Icons.Default.Repeat)
                    SettingsDivider()
                    SettingsRow("حداکثر تیم", "۸ تیم (۱۶ نفر)", Icons.Default.Groups)
                }
            }

            item {
                SettingsSection("راهنمای تنظیمات بازی") {
                    SettingsInfoRow(Icons.Default.Edit, "کلمات هر بازیکن", "۲ تا ۱۵ کلمه")
                    SettingsDivider()
                    SettingsInfoRow(Icons.Default.Timer, "زمان هر نوبت", "۱۵ تا ۱۸۰ ثانیه")
                    SettingsDivider()
                    SettingsInfoRow(Icons.Default.Groups, "تعداد بازیکنان", "از ۴ تا ۱۶ نفر")
                }
            }

            item {
                SettingsSection("راندها") {
                    SettingsInfoRow(Icons.Default.RecordVoiceOver, "راند اول", "توضیح دادن")
                    SettingsDivider()
                    SettingsInfoRow(Icons.Default.Filter1, "راند دوم", "یک کلمه")
                    SettingsDivider()
                    SettingsInfoRow(Icons.Default.PanToolAlt, "راند سوم", "پانتومیم")
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.08f)),
                    border = BorderStroke(1.dp, White.copy(alpha = 0.2f)),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Favorite, contentDescription = null, tint = RedLight, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "ساخته شده با عشق برای خانواده‌های ایرانی",
                            fontSize = 14.sp,
                            color = White.copy(alpha = 0.65f),
                            textAlign = TextAlign.Center,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = White.copy(alpha = 0.55f),
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.1f)),
            border = BorderStroke(1.dp, White.copy(alpha = 0.25f)),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                content()
            }
        }
    }
}

@Composable
private fun ColumnScope.SettingsRow(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = White.copy(alpha = 0.6f), modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, fontSize = 15.sp, color = White)
        }
        Text(
            text = value, fontSize = 15.sp,
            color = White.copy(alpha = 0.6f),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ColumnScope.SettingsInfoRow(icon: ImageVector, label: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = White.copy(alpha = 0.7f), modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, fontSize = 15.sp, color = White, fontWeight = FontWeight.Medium)
            Text(text = desc, fontSize = 12.sp, color = White.copy(alpha = 0.6f))
        }
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = White.copy(alpha = 0.1f),
        thickness = 0.5.dp
    )
}
