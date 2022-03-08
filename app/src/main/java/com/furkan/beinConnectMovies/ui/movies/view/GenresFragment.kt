package com.furkan.beinConnectMovies.ui.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.furkan.beinConnectMovies.R
import com.furkan.beinConnectMovies.data.remote.model.GenreObject
import com.furkan.beinConnectMovies.databinding.FragmentGenresBinding
import com.furkan.beinConnectMovies.ui.movies.adapter.GenresPagerAdapter
import com.furkan.beinConnectMovies.ui.movies.viewmodels.GenresViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*


class GenresFragment : Fragment() {

    private lateinit var binding: FragmentGenresBinding
    private val viewModel: GenresViewModel by viewModels()
    lateinit var adapter: GenresPagerAdapter
    var list: List<GenreObject>? = null
    private var genreId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_genres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentGenresBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        adapter = GenresPagerAdapter(childFragmentManager)
        genreId = "28"

        getData()
    }


    private fun getData() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getGenreList()
        }

        viewModel.getGenre.observe(viewLifecycleOwner, {

            list = it.data

            if (!list.isNullOrEmpty()) {
                adapter.setItems(list!!)
                setupViewPager()
            }

        })
    }

    private fun setupViewPager() {
        binding.apply {
            fragmenGenresViewpager.adapter = adapter

            val genres = adapter.genres
            var position = 0

            tvHeader.text = genres[position].name
            fragmentGenresTablayout.setupWithViewPager(fragmenGenresViewpager)
            fragmenGenresViewpager.currentItem = position
            fragmenGenresViewpager.addOnPageChangeListener(
                TabLayout.TabLayoutOnPageChangeListener(
                    fragmentGenresTablayout
                )
            )
        }

        binding.fragmenGenresViewpager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.tvHeader.text = list?.get(position)?.name
            }
        })
    }


}