package com.nightstalker.artic.features.audio.player

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.NotificationUtil
import com.google.android.exoplayer2.util.NotificationUtil.IMPORTANCE_DEFAULT
import com.nightstalker.artic.R
import com.nightstalker.artic.features.audio.domain.model.AudioFileModel

/**
 * Аудио плеер на основе [ExoPlayer]
 *
 * @author Tamerlan Mamukhov on 2022-11-02
 */
class AudioPlayerService(
    val context: Context,
) {

    lateinit var fileModel: AudioFileModel

    fun setAudio(audio: AudioFileModel) {
        fileModel = audio
    }

    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    private var player: ExoPlayer? = null
    val exPlayer get() = player

    private fun initPlayer() {
        player = ExoPlayer.Builder(context)
            .build()
            .also {
                val mediaItem = MediaItem.fromUri(Uri.parse(fileModel.url))
                it.setMediaItem(mediaItem)
                it.playWhenReady = playWhenReady
                it.seekTo(currentItem, playbackPosition)
                it.prepare()
            }
    }

    private fun releasePlayer() {
        player?.let {
            playbackPosition = it.currentPosition
            currentItem = it.currentMediaItemIndex
            playWhenReady = it.playWhenReady
            it.release()
        }
        player = null
    }

    fun play() {
        initPlayer()
        player?.play()
    }

    fun pause() {
        player?.pause()
    }

    private fun close() {
        player?.release()
    }

    companion object {
        const val TAG = "AudioPlayerService"
    }
}