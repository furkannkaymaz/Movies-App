package com.furkan.beinConnectMovies.ui.detail.view

import android.app.PictureInPictureParams
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.furkan.beinConnectMovies.MainActivity
import com.furkan.beinConnectMovies.data.remote.model.MoviesResult
import com.furkan.beinConnectMovies.databinding.ActivityDetailBinding
import com.furkan.beinConnectMovies.utils.Sources.VIDEO_URL
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.util.MimeTypes


class DetailActivity : AppCompatActivity(), Player.Listener {

    companion object {
        var moviesInfo: MoviesResult? = null
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var simpleExoPlayer: ExoPlayer
    private var isInPipMode: Boolean = false
    private var isPIPModeEnabled: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideTopMenu()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    }

    override fun onStart() {
        super.onStart()
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        binding.player.player = simpleExoPlayer


        val media: MediaItem = MediaItem.Builder()
            .setUri(Uri.parse(VIDEO_URL))
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .build()


        simpleExoPlayer.addMediaItem(media)
        simpleExoPlayer.prepare()
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.addListener(this@DetailActivity)

        binding.titleTrailers.text = moviesInfo?.title
        binding.player.setControllerVisibilityListener { visibility ->
            if (visibility == View.VISIBLE) {
                binding.titleTrailers.visibility = View.VISIBLE
            } else {
                binding.titleTrailers.visibility = View.GONE
            }
        }

        hideTopMenu()

    }

    override fun onPause() {
        super.onPause()
        hideTopMenu()
    }

    private fun hideTopMenu() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.container).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    override fun onDestroy() {
        simpleExoPlayer.stop()
        simpleExoPlayer.clearMediaItems()
        moviesInfo = null
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
        hideTopMenu()
    }

    override fun onStop() {
        super.onStop()
        hideTopMenu()
        simpleExoPlayer.release()

    }

    override fun onResume() {
        super.onResume()
        binding.player.useController = true
        hideTopMenu()
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        if (newConfig != null) {
            isInPipMode = !isInPictureInPictureMode

        }
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        enterPIPMode()
    }

    private fun enterPIPMode() {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        ) {
            binding.player.useController = false
            val params = PictureInPictureParams.Builder()
            this.enterPictureInPictureMode(params.build())
            checkPIPPermission()

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun checkPIPPermission() {
        isPIPModeEnabled = isInPictureInPictureMode
        if (!isInPictureInPictureMode) {
            onBackPressed()
        }
    }


}