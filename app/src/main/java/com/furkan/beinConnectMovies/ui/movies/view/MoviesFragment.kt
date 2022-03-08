package com.furkan.beinConnectMovies.ui.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkan.beinConnectMovies.R
import com.furkan.beinConnectMovies.data.remote.model.GenreObject
import com.furkan.beinConnectMovies.data.remote.model.MoviesResult
import com.furkan.beinConnectMovies.databinding.FragmentMoviesBinding
import com.furkan.beinConnectMovies.ui.movies.adapter.MoviesAdapter
import com.furkan.beinConnectMovies.ui.movies.viewmodels.MoviesViewModel
import kotlinx.coroutines.*

class MoviesFragment(private val genreObject: GenreObject?) : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()
    private var movieAdapter: MoviesAdapter? = null
    private var list: List<MoviesResult>? = null
    private var searchItem: List<MoviesResult>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMoviesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        getData()

    }

    override fun onResume() {
        super.onResume()
        binding.progress.visibility = View.VISIBLE
        getData()
        getSearchItem()

    }

    private fun getData() {

        CoroutineScope(Dispatchers.Main).launch {
            genreObject?.id?.toInt()?.let {
                viewModel.getMovieList(it)
            }

        }

        viewModel.getMovies.observe(viewLifecycleOwner, {

            searchItem = it.results

            list = it.results

            GlobalScope.launch(Dispatchers.Main) {
                showProgress(false)
            }

            binding.moviesRv.layoutManager =
                GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

            movieAdapter = it.results?.let { it1 -> MoviesAdapter(it1) }

            binding.moviesRv.adapter = movieAdapter
        })
    }

    private fun getSearchItem() {

        binding.svSearch.callbackDidTextChange = Callback@{ text ->

            if (text.isEmpty()) {
                movieAdapter = list?.let { MoviesAdapter(it) }
            }

            CoroutineScope(Dispatchers.IO).launch {
                context?.let { viewModel.getFilterText(text, searchItem) }
            }

            viewModel.filterText.observe(viewLifecycleOwner, {
                movieAdapter = MoviesAdapter(it)
                binding.moviesRv.adapter = movieAdapter

            })
        }

    }

    private suspend fun showProgress(show: Boolean) {
        delay(1)
        if (show) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }

}