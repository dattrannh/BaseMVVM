package com.vn.basemvvm.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.video.VideoListener
import com.vn.basemvvm.di.network.NetworkModule.userAgent

/**
 * Created by DatTran on 22/12/2017.
 */
class MediaPlayMobile (private val activity: Context,
                       private var surfaceView: PlayerView,
                       private val progressBar: ProgressBar,
                       private val imgHQ: ImageView) {

    private val timeUpdate = 200
    private val handler = Handler()
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            handler.postDelayed(this, timeUpdate.toLong())
        }
    }

    fun setProgressUpdating(enable: Boolean) {
        handler.removeCallbacks(runnable)
        if (enable) {
            handler.post(runnable)
        }
    }

    private lateinit var mPlayer: SimpleExoPlayer
    private var mTrackSelector: DefaultTrackSelector? = null
    private lateinit var mediaDataSourceFactory: DataSource.Factory
//    private val bandwidthMeter = DefaultBandwidthMeter()
    private lateinit var dataSourceFactory: DataSource.Factory
    private lateinit var videoTrackSelectionFactory: TrackSelection.Factory
    private var listener: ExoplayerListener? = null

    private fun setupVideo() {
        videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()
        mTrackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        mPlayer = ExoPlayerFactory.newSimpleInstance(activity, mTrackSelector)
        listener = ExoplayerListener()
        surfaceView.player = mPlayer
        mPlayer.addListener(listener)
        mPlayer.addVideoListener(listener)
        //        String userAgent = Util.getUserAgent(activity, "VideoPlayerGlue");
//        dataSourceFactory = new DefaultDataSourceFactory(activity, bandwidthMeter,
//                new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter, 15 * 1000, 15 * 1000, true));//hls
    }

    var isHls = false
    var isRunning = false
    fun playVideo(url: String, cookie: String? = null) {
        isRunning = true
        val httpDataSourceFactory = DefaultHttpDataSourceFactory(userAgent, 15 * 1000, 15 * 1000, true)
        if (cookie != null) {
            httpDataSourceFactory.defaultRequestProperties.set("Cookie", cookie)
        }
        dataSourceFactory = DefaultDataSourceFactory(activity, httpDataSourceFactory)
        val mediaSource: MediaSource
        if (url.contains(".m3u8")) {
            isHls = true
            mediaSource = HlsMediaSource(Uri.parse(url), dataSourceFactory, null, null)
        } else {
            isHls = false
            mediaSource = ExtractorMediaSource(
                Uri.parse(url), dataSourceFactory,
                DefaultExtractorsFactory(), null, null
            )
        }

        mPlayer.prepare(mediaSource)
        mPlayer.playWhenReady = true
    }

    private var isBuffering = false
    private var initialized = false
    fun showProgressBar(enable: Boolean) {
        if (enable) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }


    val isPlaying: Boolean
        get() = initialized && mPlayer.playWhenReady

    fun seekTo(positionMs: Long) {
        mPlayer.seekTo(positionMs)
    }

    fun stop() {
        if (initialized) {
            initialized = false
            setProgressUpdating(false)
            mPlayer.stop()
        }
    }

    fun release() {
        isRunning = false
        initialized = false
        mPlayer.release()
        mPlayer.removeListener(listener)
        mTrackSelector = null
        setProgressUpdating(false)
    }

    fun start() {
        mPlayer.playWhenReady = true
    }

    fun pause() {
        if (initialized) {
            initialized = false
            setProgressUpdating(false)
            mPlayer.playWhenReady = false
        }
    }

    val duration: Long
        get() = mPlayer.duration

    val currentPosition: Long
        get() = mPlayer.currentPosition

    val bufferedPosition: Long
        get() = mPlayer.bufferedPosition

    private inner class ExoplayerListener : Player.EventListener, VideoListener {
        
        fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {}
        override fun onTracksChanged(
            trackGroups: TrackGroupArray,
            trackSelections: TrackSelectionArray
        ) {
        }

        override fun onLoadingChanged(isLoading: Boolean) {}
        override fun onPlayerStateChanged(
            playWhenReady: Boolean,
            playbackState: Int
        ) {
//            INFO.logs("play="+playWhenReady,"state="+playbackState);
            initialized = playbackState != Player.STATE_IDLE
            isBuffering = playbackState == Player.STATE_BUFFERING
            showProgressBar(isBuffering || !initialized)
            val hasEnded = playbackState == Player.STATE_ENDED
            if (hasEnded) {
                setProgressUpdating(false)
                isRunning = false
                if (onListenerMedia != null) {
                    onListenerMedia?.hasFinishedPlaying()
                }
            }
        }

        override fun onRepeatModeChanged(repeatMode: Int) {}
        override fun onPlayerError(exception: ExoPlaybackException) {
            isRunning = false
            if (onListenerMedia != null) {
                onListenerMedia?.mediaError(exception.type)
            }
        }


        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
        override fun onVideoSizeChanged(
            width: Int,
            height: Int,
            unappliedRotationDegrees: Int,
            pixelWidthHeightRatio: Float
        ) {
            imgHQ.isActivated = height >= 720 || width >= 1280
        }

        override fun onRenderedFirstFrame() {
            print("onRenderedFirstFrame")
        }
    }

    private var onListenerMedia: OnListenerMedia? = null
    fun setOnListenerMedia(media: OnListenerMedia?) {
        onListenerMedia = media
    }

    interface OnListenerMedia {
        fun mediaError(type: Int)
        fun hasFinishedPlaying()
    }

    companion object {
        private const val bandwidth = 104857600
        private const val increase_decrease = 0

    }

    init {
        setupVideo()
        //        setSeekBarListener();
    }
}