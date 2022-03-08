package com.furkan.beinConnectMovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.furkan.beinConnectMovies.databinding.ActivityDetailBinding
import com.furkan.beinConnectMovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideTopMenu()

    }

    override fun onResume() {
        super.onResume()
        hideTopMenu()
    }

    override fun onStart() {
        super.onStart()
        hideTopMenu()

    }

    override fun onRestart() {
        super.onRestart()
        hideTopMenu()
    }

    override fun onPause() {
        super.onPause()
        hideTopMenu()
    }

    override fun onStop() {
        super.onStop()
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

}