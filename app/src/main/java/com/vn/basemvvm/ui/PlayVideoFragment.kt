package com.vn.basemvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.exoplayer2.ui.PlayerView
import com.vn.basemvvm.R
import com.vn.basemvvm.extension.bind
import com.vn.basemvvm.ui.base.fragment.BaseFragment
import com.vn.basemvvm.utils.MediaPlayMobile
import java.util.regex.Pattern


class PlayVideoFragment: Fragment() {

    private val playerView: PlayerView by bind(R.id.exoplayer_view)
    private val progressBar: ProgressBar by bind(R.id.progress_bar)
    private val imgHQ: ImageView by bind(R.id.mobile_hq)

    private lateinit var media: MediaPlayMobile
    private val endPoints = arrayOf("=m18", "=m37", "=m22")

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val binding: ViewDataBinding = FragmentBlank2Binding.inflate(inflater, container, false)
//
//        return binding.root
//    }

//    override val layoutId: Int = R.layout.fragment_play_video

//    override fun init(view: View) {
//        val context = this.context ?: return
//        media = MediaPlayMobile(context, playerView, progressBar, imgHQ)
//    }
//
//    override fun initDelayed() {
//        media.setOnListenerMedia(object : MediaPlayMobile.OnListenerMedia {
//            override fun mediaError(type: Int) {
//                Log.d("myInfo", "${type}")
//            }
//
//            override fun hasFinishedPlaying() {
//
//            }
//        })
//        val url = "https://file-examples.com/wp-content/uploads/2017/04/file_example_MP4_480_1_5MG.mp4"
//        media.playVideo(url)
////        NetworkModule.get("https://photos.app.goo.gl/4x42AMh4D2eY2NNW6", {res->
////            find(res)
////        })
//    }
//
//    override fun onBackPressed() : Boolean {
//        media.release()
//        return true
//    }

    fun find(string: String?) {
        val match = Pattern.compile("og:video.+?content=\"(.+?)\">").matcher(string ?: "")
        if (match.find()) {
            val str = match.group(1)?.split("=")?.first() ?: return
            endPoints.forEach {
                val url = str + it
//                NetworkModule.get(url, {
//                    if (!media.isRunning) {
//                        media.playVideo(url)
//                    }
//                }) {failure ->
//                    Log.d("myInfo", "$failure")
//                }
            }
        }
    }
}