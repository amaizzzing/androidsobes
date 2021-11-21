package com.example.sobes.lesson2.ui.fragments

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sobes.R
import com.example.sobes.databinding.FragmentMovieBinding
import com.example.sobes.lesson2.FilmApp
import com.example.sobes.lesson2.MovieActivity
import com.example.sobes.lesson2.data.entity.uimodels.ActorsItemModel
import com.example.sobes.lesson2.data.entity.uimodels.MovieInfoModel
import com.example.sobes.lesson2.data.services.IImageLoader
import com.example.sobes.lesson2.di.ViewModelFactory
import com.example.sobes.lesson2.ui.BaseState
import com.example.sobes.lesson2.ui.adapters.ActorsAdapter
import com.example.sobes.lesson2.ui.viewmodels.MovieViewModel
import com.example.sobes.setGone
import com.example.sobes.setVisible
import javax.inject.Inject

class MovieFragment : BaseFragment<FragmentMovieBinding, MovieViewModel>() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    override fun getViewModelClass() = MovieViewModel::class.java
    override fun getViewBinding() = FragmentMovieBinding.inflate(layoutInflater)

    private var movieId: Int = -1
    private var adapter: ActorsAdapter? = null
    private val backdropColorList: List<Long> = listOf(
        0x662C3539,
        0x6625383C,
        0x664C4646,
        0x66000080,
        0x66348781,
        0x66254117,
        0x668A4117
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FilmApp.filmComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[getViewModelClass()]
    }

    override fun setupViews() {
        initRecyclerView()
        binding?.backdropTransparentColor?.setBackgroundColor(backdropColorList.random().toInt())
        (requireActivity() as MovieActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun initRecyclerView() {
        binding?.let {
            it.rvActors.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ActorsAdapter(imageLoader, listOf())
            it.rvActors.adapter = adapter
        }
    }

    override fun observeData() {
        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it)
            } ?: renderData(BaseState.Error(Error()))
        }
        setFragmentResultListener(DESCRIPTION_REQUEST_KEY) { _, bundle ->
            movieId = bundle.getInt(DESCRIPTION_BUNDLE_KEY)
            if (movieId != -1) {
                viewModel.getMovieInfo(movieId)
                viewModel.getActors(movieId)
            }
        }
    }

    override fun renderData(data: BaseState) {
        when(data) {
            is BaseState.Success<*> -> {
                isProgressVisible(false)

                when(data.resultData) {
                    is MovieInfoModel -> {
                        data.resultData.also { movieInfoModel ->
                            binding?.let {
                                imageLoader.loadIntoWithRoundCorners(movieInfoModel.posterImage, it.imagePosterMovie, 16)
                                imageLoader.loadInto(movieInfoModel.backdropImage, it.imageBackdropMovie)
                                it.titleMovie.text = movieInfoModel.title
                                it.releasedDate.text = getString(R.string.released_text, movieInfoModel.released_date)
                                it.durationMovie.text = getString(R.string.duration_text, movieInfoModel.duration)
                                it.genresMovie.text = movieInfoModel.genres
                                it.overviewMovie.text = movieInfoModel.description
                            }
                        }
                    }
                    else -> {
                        (data.resultData as List<ActorsItemModel>).also { actors ->
                            adapter?.let {
                                it.actorsList = actors
                                it.notifyDataSetChanged()
                            }
                        }
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
                it.movieContainer.setGone()
                it.pbMovie.setVisible()
            } else {
                it.movieContainer.setVisible()
                it.pbMovie.setGone()
            }
        }
    }
}
