package com.nightstalker.artic.features.audio.player

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.util.NotificationUtil
import com.nightstalker.artic.R
import com.nightstalker.artic.features.audio.domain.model.AudioFileModel

/**
 * Аудио плеер на основе [ExoPlayer]
 *
 * @author Tamerlan Mamukhov on 2022-11-02
 */
class AudioPlayerService : Service() {

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var playerNotificationManager: PlayerNotificationManager
    private var fileModel: AudioFileModel? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    override fun onCreate() {
        super.onCreate()
        setupNotificationManager()
        initPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return AudioServiceBinder()
    }

    inner class AudioServiceBinder : Binder() {
        fun getExoPlayerInstance() = exoPlayer
    }

    fun setAudio(audio: AudioFileModel) {
        fileModel = audio
    }

    private fun initPlayer() {
        exoPlayer = ExoPlayer.Builder(this)
            .build()
            .also {
                val mediaItem =
                    MediaItem.fromUri(Uri.parse("https://www.artic.edu/mobile/audio/150s.mp3"))
                it.setMediaItem(mediaItem)
                it.playWhenReady = playWhenReady
                it.seekTo(currentItem, playbackPosition)
                it.prepare()
            }
    }

    private fun setupNotificationManager() {
        NotificationUtil.createNotificationChannel(
            this,
            FOREGROUND_CHANNEL_ID,
            R.string.tour_audio_channel_name,
            R.string.audio_channel_descr,
            NotificationUtil.IMPORTANCE_DEFAULT
        )

        playerNotificationManager = PlayerNotificationManager.Builder(
            this, NOTIFICATION_ID, FOREGROUND_CHANNEL_ID,
            object : PlayerNotificationManager.MediaDescriptionAdapter {
                override fun getCurrentContentTitle(player: Player): CharSequence {
                    return fileModel?.title.orEmpty()
                }

                override fun createCurrentContentIntent(player: Player): PendingIntent? {
                    return null
                }

                override fun getCurrentContentText(player: Player): CharSequence? {
                    return null
                }

                override fun getCurrentLargeIcon(
                    player: Player,
                    callback: PlayerNotificationManager.BitmapCallback
                ): Bitmap? {
                    return null
                }

            }
        ).setNotificationListener(
            object : PlayerNotificationManager.NotificationListener {
                override fun onNotificationCancelled(
                    notificationId: Int,
                    dismissedByUser: Boolean
                ) {
                    super.onNotificationCancelled(notificationId, dismissedByUser)
                }

                override fun onNotificationPosted(
                    notificationId: Int,
                    notification: Notification,
                    ongoing: Boolean
                ) {
                    super.onNotificationPosted(notificationId, notification, ongoing)
                }
            }
        ).build()
    }

    companion object {
        private const val TAG = "AudioPlayerService"
        private const val FOREGROUND_CHANNEL_ID = "foreground_channel_id"
        private const val NOTIFICATION_ID = 200

    }


}