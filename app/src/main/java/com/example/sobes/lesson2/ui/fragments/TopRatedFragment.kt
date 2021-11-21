package com.example.sobes.lesson2.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sobes.R
import com.example.sobes.databinding.FragmentTopRatedBinding
import com.example.sobes.lesson2.FilmApp
import com.example.sobes.lesson2.data.entity.uimodels.MovieModel
import com.example.sobes.lesson2.data.services.IImageLoader
import com.example.sobes.lesson2.di.ViewModelFactory
import com.example.sobes.lesson2.ui.BaseState
import com.example.sobes.lesson2.ui.adapters.TopMoviePagingAdapter
import com.example.sobes.lesson2.ui.viewmodels.TopRatedViewModel
import com.example.sobes.searchMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DESCRIPTION_REQUEST_KEY = "movie"
const val DESCRIPTION_BUNDLE_KEY = "movie_id"

class TopRatedFragment: BaseFragment<FragmentTopRatedBinding, TopRatedViewModel>() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    private var adapter: TopMoviePagingAdapter? = null
    private val clickListener: ((Int) -> Unit) = { pos ->
        adapter?.getMovieItem(pos)?.let { item ->
            setFragmentResult(
                DESCRIPTION_REQUEST_KEY,
                bundleOf(DESCRIPTION_BUNDLE_KEY to item.idMovie)
            )

            findNavController().navigate(R.id.action_topmovie_to_movie)
        }
    }

    override fun getViewModelClass() = TopRatedViewModel::class.java
    override fun getViewBinding() = FragmentTopRatedBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[getViewModelClass()]
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun initListeners() {
        binding?.searchMovie
            ?.searchMovie()
            ?.filter { str ->
                !str.isNullOrBlank() && (str.length >= 3)
            }
            ?.debounce(300)
            ?.distinctUntilChanged()
            ?.mapLatest { it }
            ?.onEach {
                it?.let { movieName ->
                    viewModel.getMoviesByName(movieName)
                    binding?.textTopRated?.text = getString(R.string.find_movies_text)
                }

            }
            ?.launchIn(lifecycleScope)

        binding?.searchMovie?.setOnCloseListener {
            viewModel.getTopMovies()
            binding?.textTopRated?.text = getString(R.string.top_movies_text)
            false
        }
    }

    private fun initRecyclerView() {
        binding?.let {
            it.rvTopRatedFragment.layoutManager = GridLayoutManager(context, 2)
            adapter = TopMoviePagingAdapter(imageLoader, clickListener)
            it.rvTopRatedFragment.adapter = adapter
        }
    }

    override fun setupViews() {
        initRecyclerView()
        initListeners()
        isProgressVisible(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FilmApp.filmComponent.inject(this)
    }

    override fun observeData() {
        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it)
            } ?: renderData(BaseState.Error(Error()))
        }

        viewModel.getTopMovies()
    }

    override fun renderData(data: BaseState) {
        when(data) {
            is BaseState.Success<*> -> {
                isProgressVisible(false)
                (data.resultData as PagingData<MovieModel>).also {
                    launch(Dispatchers.Main) {
                        adapter?.submitData(it)
                    }
                }
            }
            is BaseState.Error -> {
                isProgressVisible(false)
                Toast.makeText(requireContext(), "ERROR!", Toast.LENGTH_LONG).show()
            }
            BaseState.Loading -> {
                isProgressVisible(true)
            }
        }
    }

    override fun isProgressVisible(isVisible: Boolean) {
        binding?.let {
            if (isVisible) {
                it.rvTopRatedFragment.visibility = View.GONE
                it.pbTopRated.visibility = View.VISIBLE
            } else {
                it.rvTopRatedFragment.visibility = View.VISIBLE
                it.pbTopRated.visibility = View.GONE
            }
        }
    }
}