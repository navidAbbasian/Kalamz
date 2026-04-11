package com.example.kalamz.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalamz.R
import com.example.kalamz.ui.theme.*

@Composable
fun HowToPlayScreen() {
    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(RedMid, RedDark)))) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 56.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.AutoStories, contentDescription = null, tint = White, modifier = Modifier.size(52.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("آموزش بازی", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = White, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("کَلَمز چطور بازی می‌شه؟", fontSize = 16.sp, color = White.copy(alpha = 0.7f), textAlign = TextAlign.Center)
                }
            }
            item { RuleCard("۱", Icons.Default.Groups, "تیم‌بندی", "بازیکنان به تیم‌های ۲ نفره تقسیم می‌شن. هر تیم یک اسم انتخاب می‌کنه.") }
            item { RuleCard("۲", Icons.Default.Edit, "نوشتن کلمات", "هر بازیکن به تنهایی تعداد مشخصی کلمه وارد می‌کنه. این کلمات به بانک مشترک بازی اضافه می‌شن.") }
            item { RuleCard("۳", Icons.Default.RecordVoiceOver, "راند اول: توضیح بده!", "هر نفر نوبتی داره. باید کلمات رو با توضیح دادن به هم‌تیمی‌اش بفهمونه. نمی‌تونی خود کلمه رو بگی!") }
            item { RuleCard("۴", Icons.Default.Filter1, "راند دوم: یک کلمه!", "فقط با یک کلمه باید کلمه هدف رو به هم‌تیمی بفهمونی!") }
            item { RuleCard("۵", Icons.Default.PanToolAlt, "راند سوم: پانتومیم!", "بدون هیچ حرفی! فقط با حرکات بدنی و ادا و اشاره.") }
            item { RuleCard("۶", Icons.Outlined.EmojiEvents, "امتیازدهی", "هر کلمه درست = ۱ امتیاز. در پایان سه راند، تیمی که بیشترین امتیاز داره برنده‌ست!") }
            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = GoldAccent.copy(alpha = 0.18f)),
                    border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.5f)), elevation = CardDefaults.cardElevation(0.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("نکات مهم", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        TipText("بعد از هر نوبت، لیست کلمات درست نشون داده می‌شه و می‌تونی کلمات اشتباه رو حذف کنی.")
                        TipText("تایمر قابل توقف (pause) هست در صورت نیاز.")
                        TipText("در هر نوبت می‌تونی به کلمه قبلی برگردی.")
                        TipText("اگه کلمات بانک تموم بشه، راند همونجا تموم می‌شه.")
                    }
                }
            }
        }
    }
}

@Composable
private fun RuleCard(step: String, icon: ImageVector, title: String, desc: String) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.1f)),
        border = BorderStroke(1.dp, White.copy(alpha = 0.25f)), elevation = CardDefaults.cardElevation(0.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(modifier = Modifier.size(44.dp).background(White.copy(alpha = 0.2f), CircleShape), contentAlignment = Alignment.Center) {
                Text(step, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = White)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon, contentDescription = null, tint = White.copy(alpha = 0.85f), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = White)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(desc, fontSize = 14.sp, color = White.copy(alpha = 0.75f), lineHeight = 22.sp)
            }
        }
    }
}

@Composable
private fun TipText(text: String) {
    Text("• $text", fontSize = 13.sp, color = White.copy(alpha = 0.8f), lineHeight = 20.sp, modifier = Modifier.padding(vertical = 3.dp))
}
