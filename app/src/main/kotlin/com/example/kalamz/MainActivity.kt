package com.example.kalamz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.kalamz.navigation.KalamzApp
import com.example.kalamz.ui.theme.KalamzTheme
import com.example.kalamz.util.MusicTrack
import com.example.kalamz.util.SoundManager

class MainActivity : ComponentActivity() {

    private lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        soundManager = SoundManager(this)
        enableEdgeToEdge()
        setContent {
            KalamzTheme {
                // اعمال جهت راست‌به‌چپ برای تمام اپ
                CompositionLocalProvider(
                    LocalSoundManager provides soundManager,
                    LocalLayoutDirection provides LayoutDirection.Rtl
                ) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        KalamzApp()
                    }
                }
            }
        }
        soundManager.startBackgroundMusic(MusicTrack.MENU)
    }

    override fun onResume() {
        super.onResume()
        soundManager.resumeBackgroundMusic()
    }

    override fun onPause() {
        super.onPause()
        soundManager.pauseBackgroundMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager.release()
    }
}
