package com.nightstalker.artic.features.audio.player

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
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
const val FOREGROUND_CHANNEL_ID = "foreground_channel_id"
const val NOTIFICATION_ID = 200

const val CANCEL_ACTION = "Cancel_Notification"

fun getLaunchIntent(context: Context): Intent {
    return Intent(context, AudioPlayerService::class.java)
}

class AudioPlayerService(
    val context: Context,
    private val fileModel: AudioFileModel,
    private var playerView: PlayerView,
) : DefaultLifecycleObserver {

    init {
        setUpNotif()
    }

    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    private var player: ExoPlayer? = null

    private lateinit var playerNotificationManager: PlayerNotificationManager

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        initPlayer()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        releasePlayer()
    }

    private fun setUpNotif() {
        NotificationUtil.createNotificationChannel(context,
            FOREGROUND_CHANNEL_ID,
            R.string.tour_audio_channel_name,
            R.string.audio_channel_descr,
            IMPORTANCE_DEFAULT)


        playerNotificationManager = PlayerNotificationManager.Builder(
            context,
            NOTIFICATION_ID, FOREGROUND_CHANNEL_ID)
            .setMediaDescriptionAdapter(
                object : PlayerNotificationManager.MediaDescriptionAdapter {
                    override fun getCurrentContentTitle(player: Player): CharSequence {
                        return fileModel.title.toString()
                    }

                    override fun createCurrentContentIntent(player: Player): PendingIntent? {
                        val notificationIntent = "com.night.artic".asDeepLinkIntent()
                        return PendingIntent.getActivity(context,
                            0,
                            notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT)
                    }

                    override fun getCurrentContentText(player: Player): CharSequence? {
                        return null
                    }

                    override fun getCurrentLargeIcon(
                        player: Player,
                        callback: PlayerNotificationManager.BitmapCallback,
                    ): Bitmap? {
                        return null
                    }
                }
            )
            .setNotificationListener(
                object : PlayerNotificationManager.NotificationListener {
                    override fun onNotificationPosted(
                        notificationId: Int,
                        notification: Notification,
                        ongoing: Boolean,
                    ) {
                        super.onNotificationPosted(notificationId, notification, ongoing)
                    }

                    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
                        super.onNotificationCancelled(notificationId, dismissedByUser)
                    }
                }).build()

        playerNotificationManager.setPlayer(player)
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(context)
            .build()
            .also {
                playerView.player = it
                val mediaItem = MediaItem.fromUri(Uri.parse(fileModel.url))
                it.setMediaItem(mediaItem)
                it.playWhenReady = playWhenReady
                it.seekTo(currentItem, playbackPosition)
                it.prepare()
            }

        playerNotificationManager.setPlayer(player)
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
}

sealed class PlayBackAction {

    /**
     * Play the track associated with the given [Playable].
     *
     * Currently, we only support tracks defined in [ArticObject.audioCommentary].
     *
     * @see [ExoPlayer.prepare]
     * @see [Player.setPlayWhenReady]
     */
    class Play(val audioModel: AudioFileModel) : PlayBackAction()

    /**
     * Pause the current track.
     *
     * You can continue where you left off with [Resume].
     *
     * @see [Player.setPlayWhenReady]
     */
    class Pause : PlayBackAction()

    /**
     * Resume the current track.
     *
     * You can also resume (without resetting the stream) by sending a subsequent [Play]
     * action backed by the same [ArticObject].
     *
     * @see [Player.setPlayWhenReady]
     */
    class Resume : PlayBackAction()

    /**
     * Stop playback, seek back to the start of the file, and move this service into the background.
     *
     * @see [Player.stop]
     */
    class Stop : PlayBackAction()

    /**
     * Move playback to a specific temporal position.
     *
     * @param time number of milliseconds from the start of the track
     */
    class Seek(val time: Long) : PlayBackAction()
}

sealed class PlayBackState(val audio: AudioFileModel) {
    class Playing(audio: AudioFileModel) : PlayBackState(audio)
    class Paused(audio: AudioFileModel) : PlayBackState(audio)
    class Stopped(audio: AudioFileModel) : PlayBackState(audio)
}

fun String.asDeepLinkIntent(action: String = Intent.ACTION_VIEW, schema: String = "artic"): Intent {
    return Intent(action, Uri.parse("$schema://${this}"))
}