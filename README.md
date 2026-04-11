# کَلَمز (Kalamz)

بازی خانوادگی کلمات — ساخته‌شده با Jetpack Compose

---

## 📋 فهرست محتوا

- [تصاویر مورد نیاز](#-تصاویر-مورد-نیاز)
- [صداها و موسیقی مورد نیاز](#-صداها-و-موسیقی-مورد-نیاز)
- [ساختار پروژه](#-ساختار-پروژه)

---

## 🖼️ تصاویر مورد نیاز

همه تصاویر باید در مسیر زیر قرار بگیرند:
```
app/src/main/res/drawable/
```

> **فرمت پیشنهادی:** فایل‌های XML (Vector Drawable) برای کیفیت بهتر در همه اندازه‌ها
> در صورت استفاده از PNG، نسخه‌های `hdpi`, `xhdpi`, `xxhdpi`, `xxxhdpi` باید تهیه شوند.

---

### تصاویر صفحات بازی

| نام فایل | مسیر قرارگیری | توضیح | ابعاد پیشنهادی | صفحه‌ای که استفاده می‌شه |
|---|---|---|---|---|
| `img_round_describe.xml` | `drawable/` | تصویر نماد راند اول — یک نفر در حال توضیح دادن با دهان باز و انگشت اشاره | ۱۲۰×۱۲۰ dp | `RoundIntroScreen` (راند توضیح) |
| `img_round_one_word.xml` | `drawable/` | تصویر نماد راند دوم — یک انگشت بالا یا حباب گفتگو با عدد ۱ | ۱۲۰×۱۲۰ dp | `RoundIntroScreen` (راند یک کلمه) |
| `img_round_pantomime.xml` | `drawable/` | تصویر نماد راند سوم — آدم‌واره در حال اداکردن با دست‌های باز | ۱۲۰×۱۲۰ dp | `RoundIntroScreen` (راند پانتومیم) |
| `img_secret_player.xml` | `drawable/` | تصویر نفری که پشتش به بقیه هست یا با چشم‌بند — نشان‌دهنده ورود مخفیانه کلمات | ۱۴۰×۱۴۰ dp | `WordEntryScreen` |
| `img_phone_handoff.xml` | `drawable/` | دو دست در حال پاس دادن گوشی به هم — نشان‌دهنده تعویض نوبت | ۱۴۰×۱۴۰ dp | `TurnIntroScreen` |
| `img_celebration.xml` | `drawable/` | ستاره‌ها و کنفتی در حال ریختن — جشن پیروزی | ۱۶۰×۱۶۰ dp | `ResultsScreen` |
| `img_trophy.xml` | `drawable/` | جام قهرمانی طلایی — نماد برنده شدن | ۱۴۰×۱۴۰ dp | `ResultsScreen` |
| `img_no_correct.xml` | `drawable/` | آدم‌واره با علامت ضربدر یا دست خالی — نشان‌دهنده صفر بودن امتیاز | ۱۲۰×۱۲۰ dp | `RoundEndScreen` (امتیاز صفر) |

---

### آیکون‌های ناوبری (Bottom Navigation)

| نام فایل | مسیر قرارگیری | توضیح | ابعاد پیشنهادی | محل استفاده |
|---|---|---|---|---|
| `ic_nav_home.xml` | `drawable/` | آیکون خانه — طرح مدرن خانه با خطوط تمیز | ۲۴×۲۴ dp | Bottom Navigation — تب خانه |
| `ic_nav_home_filled.xml` | `drawable/` | آیکون خانه پُر (حالت انتخاب‌شده) | ۲۴×۲۴ dp | Bottom Navigation — تب خانه (فعال) |
| `ic_nav_how_to_play.xml` | `drawable/` | آیکون کتاب باز یا علامت سوال — راهنمای بازی | ۲۴×۲۴ dp | Bottom Navigation — تب آموزش |
| `ic_nav_how_to_play_filled.xml` | `drawable/` | آیکون کتاب باز پُر (حالت انتخاب‌شده) | ۲۴×۲۴ dp | Bottom Navigation — تب آموزش (فعال) |
| `ic_nav_settings.xml` | `drawable/` | آیکون چرخ‌دنده — تنظیمات | ۲۴×۲۴ dp | Bottom Navigation — تب تنظیمات |
| `ic_nav_settings_filled.xml` | `drawable/` | آیکون چرخ‌دنده پُر (حالت انتخاب‌شده) | ۲۴×۲۴ dp | Bottom Navigation — تب تنظیمات (فعال) |

---

### آیکون‌های صفحات داخلی

| نام فایل | مسیر قرارگیری | توضیح | ابعاد پیشنهادی | محل استفاده |
|---|---|---|---|---|
| `ic_players_2.xml` | `drawable/` | آیکون ۲ آدم‌واره کنار هم | ۴۸×۴۸ dp | `PlayerCountScreen` — کارت انتخاب ۲ نفره |
| `ic_players_4.xml` | `drawable/` | آیکون ۴ آدم‌واره | ۴۸×۴۸ dp | `PlayerCountScreen` — کارت انتخاب ۴ نفره |
| `ic_players_6.xml` | `drawable/` | آیکون ۶ آدم‌واره | ۴۸×۴۸ dp | `PlayerCountScreen` — کارت انتخاب ۶ نفره |
| `ic_players_8.xml` | `drawable/` | آیکون ۸ آدم‌واره | ۴۸×۴۸ dp | `PlayerCountScreen` — کارت انتخاب ۸ نفره |
| `ic_players_10.xml` | `drawable/` | آیکون گروه ۱۰ نفره | ۴۸×۴۸ dp | `PlayerCountScreen` — کارت انتخاب ۱۰ نفره |
| `ic_players_12.xml` | `drawable/` | آیکون گروه ۱۲ نفره | ۴۸×۴۸ dp | `PlayerCountScreen` — کارت انتخاب ۱۲ نفره |
| `ic_players_14.xml` | `drawable/` | آیکون گروه ۱۴ نفره | ۴۸×۴۸ dp | `PlayerCountScreen` — کارت انتخاب ۱۴ نفره |
| `ic_players_16.xml` | `drawable/` | آیکون گروه ۱۶ نفره | ۴۸×۴۸ dp | `PlayerCountScreen` — کارت انتخاب ۱۶ نفره |
| `ic_timer.xml` | `drawable/` | آیکون ساعت‌شنی یا تایمر | ۳۲×۳۲ dp | `GameSettingsScreen`, `TurnScreen` |
| `ic_word.xml` | `drawable/` | آیکون کاغذ با خطوط نوشته | ۳۲×۳۲ dp | `GameSettingsScreen`, `WordEntryScreen` |
| `ic_check_circle.xml` | `drawable/` | آیکون دایره با تیک سبز | ۲۴×۲۴ dp | `RoundEndScreen` — کلمات درست |
| `ic_delete_word.xml` | `drawable/` | آیکون سطل آشغال یا ضربدر قرمز | ۲۴×۲۴ dp | `RoundEndScreen` — حذف کلمه از لیست |
| `ic_crown.xml` | `drawable/` | تاج طلایی — نشان‌دهنده تیم اول | ۲۸×۲۸ dp | `ResultsScreen` — رتبه اول |
| `ic_medal_silver.xml` | `drawable/` | مدال نقره‌ای — رتبه دوم | ۲۴×۲۴ dp | `ResultsScreen` — رتبه دوم |
| `ic_medal_bronze.xml` | `drawable/` | مدال برنزی — رتبه سوم | ۲۴×۲۴ dp | `ResultsScreen` — رتبه سوم |
| `ic_skip.xml` | `drawable/` | آیکون فلش به جلو یا علامت skip | ۲۴×۲۴ dp | `TurnScreen` — دکمه رد کردن کلمه |
| `ic_correct.xml` | `drawable/` | آیکون تیک بزرگ سبز | ۲۴×۲۴ dp | `TurnScreen` — دکمه تأیید کلمه درست |
| `ic_undo.xml` | `drawable/` | آیکون فلش برگشت — بازگشت به کلمه قبل | ۲۴×۲۴ dp | `TurnScreen` — دکمه برگشت |
| `ic_pause.xml` | `drawable/` | آیکون مکث (دو خط عمودی) | ۲۴×۲۴ dp | `TurnScreen` — دکمه توقف تایمر |
| `ic_sound_on.xml` | `drawable/` | آیکون بلندگو با موج‌های صدا | ۲۴×۲۴ dp | `SettingsScreen` — صدا فعال |
| `ic_sound_off.xml` | `drawable/` | آیکون بلندگو با خط ضربدر | ۲۴×۲۴ dp | `SettingsScreen` — صدا غیرفعال |
| `ic_music_on.xml` | `drawable/` | آیکون نت موسیقی | ۲۴×۲۴ dp | `SettingsScreen` — موسیقی فعال |
| `ic_music_off.xml` | `drawable/` | آیکون نت موسیقی با خط ضربدر | ۲۴×۲۴ dp | `SettingsScreen` — موسیقی غیرفعال |

---

## 🔊 صداها و موسیقی مورد نیاز

همه فایل‌های صوتی باید در مسیر زیر قرار بگیرند:
```
app/src/main/res/raw/
```

> **فرمت پیشنهادی:** `.ogg` (کوچکترین حجم، بهترین کیفیت برای Android) یا `.mp3`
> فایل‌های WAV حجم بالایی دارند — در صورت استفاده از WAV، نرخ نمونه‌برداری ۴۴۱۰۰ Hz پیشنهاد می‌شود.

---

### 🎵 موسیقی پس‌زمینه

> هر بخش از بازی موسیقی مستقل خود را دارد. سوییچ بین تِرَک‌ها به صورت خودکار توسط `SoundManager.switchMusic()` انجام می‌شود.

| نام فایل | توضیح | مدت زمان | خصوصیات | محل استفاده |
|---|---|---|---|---|
| `music_menu` | موسیقی صفحه اصلی و مراحل تنظیم | ۲–۳ دقیقه (loop) | سازهای زهی ملایم یا پیانو؛ آرام و دعوت‌کننده؛ تمپو: ۷۰–۸۵ BPM؛ seamless loop | صفحه خانه، انتخاب بازیکنان، تنظیمات، ورود کلمات |
| `music_round1` | موسیقی راند اول — توضیح | ۲–۳ دقیقه (loop) | ریتم متوسط و شاد؛ احساس گفتگو و تعامل؛ تمپو: ۹۰–۱۱۰ BPM؛ seamless loop | از شروع راند اول تا پایان آن (RoundIntro → TurnReady → TurnActive → TurnEnd → RoundEnd) |
| `music_round2` | موسیقی راند دوم — یک کلمه | ۲–۳ دقیقه (loop) | ریتم تندتر و هیجان‌انگیزتر از راند اول؛ احساس چالش و تمرکز؛ تمپو: ۱۱۰–۱۳۰ BPM؛ seamless loop | از شروع راند دوم تا پایان آن |
| `music_round3` | موسیقی راند سوم — پانتومیم | ۲–۳ دقیقه (loop) | پرانرژی‌ترین تِرَک؛ موسیقی طنزآمیز و شاد با ریتم سریع؛ تمپو: ۱۳۰–۱۵۰ BPM؛ seamless loop | از شروع راند سوم تا پایان بازی (نتایج) |

---

### 🔔 صداهای رابط کاربری (UI Sounds)

| نام فایل | توضیح | مدت زمان | خصوصیات | محل استفاده |
|---|---|---|---|---|
| `sound_button_click` | صدای کلیک دکمه‌های معمولی | ۰.۰۵–۰.۱ ثانیه | تیک یا پاپ کوتاه و تمیز؛ فرکانس متوسط (۸۰۰–۱۲۰۰ Hz)؛ بدون اکو | تمام دکمه‌های بازی در همه صفحات |

---

### ✅ صداهای گیم‌پلی

| نام فایل | توضیح | مدت زمان | خصوصیات | محل استفاده |
|---|---|---|---|---|
| `sound_correct_word` | وقتی بازیکن کلمه‌ای رو درست می‌گه | ۰.۳–۰.۵ ثانیه | دو نت پشت سر هم صعودی (ding-ding)؛ تُن شاد و مثبت؛ فرکانس بالا (۱۰۰۰–۱۵۰۰ Hz) | `TurnScreen` — دکمه تأیید کلمه درست |
| `sound_word_skip` | وقتی بازیکن کلمه‌ای رو رد می‌کنه | ۰.۲–۰.۳ ثانیه | یک نت کوتاه نزولی یا صدای whoosh؛ تُن خنثی (نه منفی)؛ فرکانس متوسط | `TurnScreen` — دکمه رد کردن کلمه |

---

### ⏱️ صداهای تایمر

| نام فایل | توضیح | مدت زمان | خصوصیات | محل استفاده |
|---|---|---|---|---|
| `sound_timer_tick` | تیک‌تاک تایمر در حین بازی (در ثانیه‌های عادی) | ۰.۰۸–۰.۱۲ ثانیه | صدای ساعت مکانیکی؛ ملایم و غیرمزاحم؛ Volume پایین (۳۵٪) | `TurnScreen` — هر ثانیه تایمر در بالای ۱۰ ثانیه |
| `sound_timer_warning` | هشدار تایمر در ۱۰ ثانیه آخر | ۰.۱۵–۰.۲ ثانیه | تیک سریع‌تر و با ارتفاع بیشتر؛ کمی اضطراب‌انگیز؛ فرکانس بالاتر از tick معمولی | `TurnScreen` — هر ثانیه در ۱۰ ثانیه آخر |
| `sound_timer_end` | پایان زمان نوبت | ۰.۸–۱.۲ ثانیه | بوق ممتد یا چند نت نزولی پیاپی؛ واضح و بلند؛ نشان‌دهنده اتمام وقت | `TurnScreen` — وقتی تایمر به صفر می‌رسه |

---

### 🎮 صداهای شروع نوبت و راند

| نام فایل | توضیح | مدت زمان | خصوصیات | محل استفاده |
|---|---|---|---|---|
| `sound_turn_start` | شروع نوبت یک بازیکن | ۰.۵–۰.۸ ثانیه | سه نت صعودی کوتاه (مثل fanfare کوچک)؛ هیجان‌انگیز و آماده‌باش | `TurnIntroScreen` — وقتی نوبت شروع می‌شه |
| `sound_round_start` | شروع یک راند جدید | ۱.۰–۱.۵ ثانیه | fanfare پرانرژی‌تر از turn_start؛ موسیقال و جذاب؛ احساس شروع ماجراجویی | `RoundIntroScreen` — وقتی راند جدید شروع می‌شه |
| `sound_round_end` | پایان یک راند | ۱.۰–۱.۵ ثانیه | صدای applause کوتاه یا fanfare پایانی؛ احساس رضایت و اتمام | `RoundEndScreen` — وقتی راند تموم می‌شه |
| `sound_game_over` | پایان کل بازی | ۲.۰–۳.۰ ثانیه | fanfare پیروزی با ارکستر کوچک؛ پرانرژی و جشن‌وار؛ ماندگار در ذهن | `ResultsScreen` — وقتی نتایج نهایی نشون داده می‌شه |

---

## 📁 ساختار پروژه

```
app/src/main/
├── kotlin/com/example/kalamz/
│   ├── MainActivity.kt
│   ├── LocalSoundManager.kt
│   ├── model/
│   │   └── GameModels.kt
│   ├── navigation/
│   │   └── NavGraph.kt
│   ├── ui/
│   │   ├── components/
│   │   │   ├── GlassCard.kt
│   │   │   ├── KalamzButton.kt
│   │   │   ├── TeamScoreCard.kt
│   │   │   ├── TimerDisplay.kt
│   │   │   └── WordCard.kt
│   │   ├── screens/
│   │   │   ├── HomeScreen.kt
│   │   │   ├── PlayerCountScreen.kt
│   │   │   ├── GameSettingsScreen.kt
│   │   │   ├── TeamSetupScreen.kt
│   │   │   ├── WordEntryScreen.kt
│   │   │   ├── RoundIntroScreen.kt
│   │   │   ├── TurnIntroScreen.kt
│   │   │   ├── TurnScreen.kt
│   │   │   ├── RoundEndScreen.kt
│   │   │   ├── ResultsScreen.kt
│   │   │   ├── HowToPlayScreen.kt
│   │   │   ├── SettingsScreen.kt
│   │   │   └── GameModeScreen.kt
│   │   └── theme/
│   │       ├── Color.kt
│   │       ├── Theme.kt
│   │       └── Type.kt
│   ├── util/
│   │   └── SoundManager.kt
│   └── viewmodel/
│       └── GameViewModel.kt
└── res/
    ├── drawable/        ← تصاویر و آیکون‌ها اینجا قرار می‌گیرن
    └── raw/             ← فایل‌های صوتی اینجا قرار می‌گیرن
```

---

## ✅ چک‌لیست دارایی‌ها

### تصاویر (Drawables)
- [ ] `img_round_describe.xml`
- [ ] `img_round_one_word.xml`
- [ ] `img_round_pantomime.xml`
- [ ] `img_secret_player.xml`
- [ ] `img_phone_handoff.xml`
- [ ] `img_celebration.xml`
- [ ] `img_trophy.xml`
- [ ] `img_no_correct.xml`
- [ ] `ic_nav_home.xml` + `ic_nav_home_filled.xml`
- [ ] `ic_nav_how_to_play.xml` + `ic_nav_how_to_play_filled.xml`
- [ ] `ic_nav_settings.xml` + `ic_nav_settings_filled.xml`
- [ ] `ic_players_2.xml` تا `ic_players_16.xml` (مجموعاً ۸ فایل)
- [ ] `ic_timer.xml`
- [ ] `ic_word.xml`
- [ ] `ic_check_circle.xml`
- [ ] `ic_delete_word.xml`
- [ ] `ic_crown.xml`
- [ ] `ic_medal_silver.xml`
- [ ] `ic_medal_bronze.xml`
- [ ] `ic_skip.xml`
- [ ] `ic_correct.xml`
- [ ] `ic_undo.xml`
- [ ] `ic_pause.xml`
- [ ] `ic_sound_on.xml` + `ic_sound_off.xml`
- [ ] `ic_music_on.xml` + `ic_music_off.xml`

### صداها (Raw)
- [ ] `music_menu.ogg` (یا `.mp3`) — موسیقی صفحه اصلی و تنظیمات
- [ ] `music_round1.ogg` — موسیقی راند اول (توضیح)
- [ ] `music_round2.ogg` — موسیقی راند دوم (یک کلمه)
- [ ] `music_round3.ogg` — موسیقی راند سوم (پانتومیم)
- [ ] `sound_button_click.ogg`
- [ ] `sound_correct_word.ogg`
- [ ] `sound_word_skip.ogg`
- [ ] `sound_timer_tick.ogg`
- [ ] `sound_timer_warning.ogg`
- [ ] `sound_timer_end.ogg`
- [ ] `sound_turn_start.ogg`
- [ ] `sound_round_start.ogg`
- [ ] `sound_round_end.ogg`
- [ ] `sound_game_over.ogg`

---

> **نکته:** فایل‌های صوتی WAV موجود در پروژه placeholder هستند و باید با فایل‌های واقعی جایگزین شوند. فایل‌های `music_menu.wav`، `music_round1.wav`، `music_round2.wav` و `music_round3.wav` هر کدام باید با موسیقی متناسب با حال‌وهوای آن بخش جایگزین شوند. برای تبدیل فرمت می‌توانید از ابزار [Audacity](https://www.audacityteam.org/) یا [ffmpeg](https://ffmpeg.org/) استفاده کنید.

> **نکته:** تصاویر drawable موجود در پروژه به‌صورت Vector Drawable (XML) پیاده‌سازی شده‌اند. برای طراحی آیکون‌ها می‌توانید از ابزار [Android Studio Asset Studio](https://developer.android.com/studio/write/create-app-icons) یا [Figma](https://figma.com) + [SVG to VectorDrawable](https://svg2vector.com) استفاده کنید.
