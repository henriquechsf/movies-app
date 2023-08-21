package com.example.movieapp.presenter.main.moviedetails.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.presenter.main.moviedetails.similar.SimilarFragment
import com.example.movieapp.presenter.main.moviedetails.trailers.TrailersFragment
import com.example.movieapp.presenter.main.moviedetails.adapter.CastAdapter
import com.example.movieapp.presenter.main.moviedetails.adapter.ViewPagerAdapter
import com.example.movieapp.presenter.main.moviedetails.comments.CommentsFragment
import com.example.movieapp.util.StateView
import com.example.movieapp.util.ViewPager2ViewHeightAnimator
import com.example.movieapp.util.initToolbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val movieDetailsViewModel: MovieDetailsViewModel by activityViewModels()

    private lateinit var castAdapter: CastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar, lightIcon = true)

        getMovieDetails()
        initRecyclerCredits()
        configTabLayout()
    }

    private fun initRecyclerCredits() {
        castAdapter = CastAdapter()

        with(binding.rvCast) {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }
    }

    private fun getMovieDetails() {
        movieDetailsViewModel.getMovieDetails(args.movieId)
            .observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {

                    }
                    is StateView.Success -> {
                        configData(stateView.data)
                        getCredits()
                    }
                    is StateView.Error -> {

                    }
                }
            }
    }

    private fun getCredits() {
        movieDetailsViewModel.getCredits(args.movieId)
            .observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {

                    }
                    is StateView.Success -> {
                        castAdapter.submitList(stateView.data?.cast)
                    }
                    is StateView.Error -> {

                    }
                }
            }
    }

    private fun configData(movie: Movie?) = with(binding) {
        Glide
            .with(requireContext())
            .load("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
            .into(imgMoviePoster)

        tvMovieTitle.text = movie?.originalTitle
        tvMovieAverage.text = String.format("%.1f", movie?.voteAverage)
        tvProductionCountry.text = movie?.productionCountries?.get(0)?.name ?: ""
        tvMovieRelease.text = movie?.releaseDate
        val genres = movie?.genres?.map { it.name }?.joinToString(", ")
        tvGenres.text = getString(R.string.text_all_genres_movie_details_fragment, genres)
        tvDescription.text = movie?.overview
    }

    private fun configTabLayout() {
        movieDetailsViewModel.setMovieId(args.movieId)

        val adapter = ViewPagerAdapter(requireActivity())
        val mViewPager = ViewPager2ViewHeightAnimator()
        mViewPager.viewPager2 = binding.viewPager
        mViewPager.viewPager2?.adapter = adapter
        //binding.viewPager.adapter = adapter

        adapter.addFragment(
            fragment = TrailersFragment(),
            title = R.string.title_trailers_tab_layout
        )
        adapter.addFragment(
            fragment = SimilarFragment(),
            title = R.string.title_similar_tab_layout
        )
        adapter.addFragment(
            fragment = CommentsFragment(),
            title = R.string.title_comments_tab_layout
        )

        binding.viewPager.offscreenPageLimit = adapter.itemCount

        mViewPager.viewPager2?.let {
            TabLayoutMediator(binding.tabLayout, it) { tab, position ->
                tab.text = getString(adapter.getTitle(position))
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}