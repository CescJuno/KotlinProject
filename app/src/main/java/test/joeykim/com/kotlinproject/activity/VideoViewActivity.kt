package test.joeykim.com.kotlinproject.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.MediaController
import test.joeykim.com.kotlinproject.R
import test.joeykim.com.kotlinproject.databinding.ActivityVideoBinding
import kotlinx.android.synthetic.main.activity_video.*
import test.joeykim.com.kotlinproject.User

class VideoViewActivity : AppCompatActivity() {

    var mContext: Activity? = null
    private var playbackPosition = 0
    //private val rtspUrl = "http://polaris.infraware.net/bullseye/share/content/YTN%EB%89%B4%EC%8A%A4.mp4"
    private val rtspUrl = "http://211.45.65.186:8080/ocr/mvc/vod/?metaId=MM0001222689"
    //private val rtspUrl = "http://1.255.144.33:8080/media/010101_201807231304_030356553/pa2My2mO0gQ5tAFoQVPvCFWfyKxCDou%2FyEwtklzlFe59q3IY8sFEc43j1g6fQ0PlkS%2BCPT1%2FqJO8TnQOPV%2BBqWPc951iR8hjE3ARslKILJW3HOM9zy3YhNQFzuOovJUx9sbhR3qkwDExjndTCnOKzbZbm8mrz98oPoT0MfBhtmAlqfcDd%2BMt9nPDLajXLTjkVgA%2BgTG6bTc%2F%2BoplVZP1TGFfg2zUXSFx%2BnWCB6SLGffAyitTqX%2B27RyOlCyabqt3EqXHERMTt%2BpvhVO%2B3Gpz%2FNgYSxMLmCHdHcOfPM4MlHTnx4sDGYhe1XUl2eCEU5dzCMKfogclWrahGkPyixvrKRVu5RN%2FZUaOSgePE69UvuNak7OMyKxbOFIJuggXGjRMtorDFpJ39ajGZJkGnRRoGw%3D%3D/MM302374_1_HD_141215_ABR.m3u8"

    private lateinit var mediaController: MediaController


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Action Bar Hidden
        supportActionBar?.hide()
        var binding: ActivityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video)

        binding.executePendingBindings()

        mContext = this

        mediaController = MediaController(mContext)

       videoView.setOnPreparedListener {
            mediaController.setAnchorView(binding.videoContainer)
            videoView.setMediaController(mediaController)
            videoView.seekTo(playbackPosition)
            videoView!!.start()
        }


        videoView.setOnInfoListener { player, what, extras ->
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                progressBar.visibility = View.INVISIBLE
            true
        }

    }
    override fun onStart() {
        super.onStart()

        val uri = Uri.parse(rtspUrl)
        videoView.setVideoURI(uri)
        progressBar.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()

        videoView.pause()
        playbackPosition = videoView.currentPosition
    }

    override fun onStop() {
        videoView.stopPlayback()

        super.onStop()
    }

    companion object {

        private val INTENT_USER_ID = "user_id"

        fun newIntent(context: Context?, user: User?): Intent {
            val intent = Intent(context, VideoViewActivity::class.java)
            intent.putExtra(INTENT_USER_ID, user?.name)
            return intent
        }
    }
}