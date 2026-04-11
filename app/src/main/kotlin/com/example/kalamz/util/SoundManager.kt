package com.example.kalamz.util

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import com.example.kalamz.R

/** چهار تِرَک موسیقی مستقل برای بخش‌های مختلف بازی */
enum class MusicTrack {
    /** صفحه اصلی + مراحل تنظیم (قبل از شروع راند اول) */
    MENU,
    /** موسیقی راند اول — توضیح */
    ROUND_1,
    /** موسیقی راند دوم — یک کلمه */
    ROUND_2,
    /** موسیقی راند سوم — پانتومیم */
    ROUND_3
}

class SoundManager(context: Context) {

    private val appContext = context.applicationContext

    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(8)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()

    private var mediaPlayer: MediaPlayer? = null
    private var currentTrack: MusicTrack? = null

    // Sound IDs
    private var idButtonClick = 0
    private var idCorrectWord = 0
    private var idWordSkip = 0
    private var idTimerTick = 0
    private var idTimerWarning = 0
    private var idTimerEnd = 0
    private var idTurnStart = 0
    private var idRoundStart = 0
    private var idRoundEnd = 0
    private var idGameOver = 0

    var isSoundEnabled = true
    var isMusicEnabled = true

    init {
        loadSounds()
    }

    private fun loadSounds() {
        idButtonClick  = load(R.raw.sound_button_click)
        idCorrectWord  = load(R.raw.sound_correct_word)
        idWordSkip     = load(R.raw.sound_word_skip)
        idTimerTick    = load(R.raw.sound_timer_tick)
        idTimerWarning = load(R.raw.sound_timer_warning)
        idTimerEnd     = load(R.raw.sound_timer_end)
        idTurnStart    = load(R.raw.sound_turn_start)
        idRoundStart   = load(R.raw.sound_round_start)
        idRoundEnd     = load(R.raw.sound_round_end)
        idGameOver     = load(R.raw.sound_game_over)
    }

    private fun load(resId: Int): Int =
        try { soundPool.load(appContext, resId, 1) } catch (e: Exception) { 0 }

    fun playButtonClick()  = play(idButtonClick, 0.8f)
    fun playCorrectWord()  = play(idCorrectWord, 0.9f, rate = 1.1f)
    fun playWordSkip()     = play(idWordSkip, 0.65f)
    fun playTimerTick()    = play(idTimerTick, 0.35f)
    fun playTimerWarning() = play(idTimerWarning, 1f, rate = 0.95f)
    fun playTimerEnd()     = play(idTimerEnd, 1f)
    fun playTurnStart()    = play(idTurnStart, 0.9f)
    fun playRoundStart()   = play(idRoundStart, 1f)
    fun playRoundEnd()     = play(idRoundEnd, 1f)
    fun playGameOver()     = play(idGameOver, 1f)

    private fun play(id: Int, volume: Float = 1f, rate: Float = 1f) {
        if (!isSoundEnabled || id == 0) return
        try { soundPool.play(id, volume, volume, 1, 0, rate) } catch (_: Exception) {}
    }

    /** شروع موسیقی پس‌زمینه با تِرَک مشخص — اگر همان تِرَک در حال پخش باشد تغییری نمی‌دهد */
    fun switchMusic(track: MusicTrack) {
        if (currentTrack == track && mediaPlayer?.isPlaying == true) return
        currentTrack = track
        startBackgroundMusic(track)
    }

    /** شروع/ری‌استارت موسیقی با تِرَک مشخص */
    fun startBackgroundMusic(track: MusicTrack = MusicTrack.MENU) {
        currentTrack = track
        if (!isMusicEnabled) return
        val resId = when (track) {
            MusicTrack.MENU    -> R.raw.music_menu
            MusicTrack.ROUND_1 -> R.raw.music_round1
            MusicTrack.ROUND_2 -> R.raw.music_round2
            MusicTrack.ROUND_3 -> R.raw.music_round3
        }
        try {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(appContext, resId)?.apply {
                isLooping = true
                setVolume(0.22f, 0.22f)
                start()
            }
        } catch (_: Exception) {}
    }

    fun pauseBackgroundMusic() {
        try { if (mediaPlayer?.isPlaying == true) mediaPlayer?.pause() } catch (_: Exception) {}
    }

    fun resumeBackgroundMusic() {
        if (!isMusicEnabled) return
        try { if (mediaPlayer?.isPlaying == false) mediaPlayer?.start() } catch (_: Exception) {}
    }

    fun release() {
        try { soundPool.release() } catch (_: Exception) {}
        try { mediaPlayer?.release() } catch (_: Exception) {}
        mediaPlayer = null
    }
}

