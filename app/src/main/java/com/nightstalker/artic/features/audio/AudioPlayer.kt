package com.nightstalker.artic.features.audio

import android.media.AudioManager
import android.media.MediaPlayer

/**
 * Аудио плеер
 *
 * @author Tamerlan Mamukhov on 2022-11-02
 */
class AudioPlayer : MediaPlayer() {
    var audioUrl: String? = null
    override fun setAudioStreamType(streamtype: Int) {
        super.setAudioStreamType(AudioManager.STREAM_MUSIC)
    }

    fun startPlaying() {
        if (audioUrl != null) {
            try {
                with(this) {
                    setDataSource(audioUrl)
                    prepare()
                    start()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun stopPlaying() {
        with(this) {
            if (isPlaying) {
                stop()
                reset()
                release()
            }
        }
    }
}