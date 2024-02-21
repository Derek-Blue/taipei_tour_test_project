package com.ken.taipeitourtestproject.screen.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ken.taipeitourtestproject.R
import com.ken.taipeitourtestproject.base.BaseFragment
import com.ken.taipeitourtestproject.databinding.FragmentHomeBinding
import com.ken.taipeitourtestproject.module.ext.dpToPx
import com.ken.taipeitourtestproject.module.ext.navigateScreen
import com.ken.taipeitourtestproject.screen.MainViewModel
import com.ken.taipeitourtestproject.screen.attractioninfo.AttractionInfoFragment
import com.ken.taipeitourtestproject.screen.attractionweb.AttractionWebFragment
import com.ken.taipeitourtestproject.screen.home.adapter.AttractionsAdapter
import com.ken.taipeitourtestproject.screen.home.data.AttractionShowData
import com.ken.taipeitourtestproject.screen.home.data.LanguageType
import com.ken.taipeitourtestproject.screen.home.news.NewsBannerAdapter
import com.ken.taipeitourtestproject.screen.home.news.NewsShowData
import com.ken.taipeitourtestproject.tools.recyclerview.LoadMoreRecyclerViewScrollListener
import com.kyc.application.module.recyclerview.linearspacedecoration.LinearSpaceDecoration
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()
    private val activityViewModel by activityViewModel<MainViewModel>()

    companion object {
        private const val ITEM_SPACE = 12
    }

    private val attractionsAdapter by lazy {
        AttractionsAdapter { item ->
            if (item is AttractionShowData.Item && isClickSafe()) {
                val bundle = Bundle().apply {
                    putParcelable(AttractionInfoFragment.ARG_INFO_ITEM_KEY, item)
                }
                navigateScreen(R.id.nav_attraction_info, bundle)
            }
        }
    }

    private val newsAdapter by lazy {
        NewsBannerAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        initView()
        observer()
    }

    private fun initView() {
        binding.languageImageView.setOnClickDebounce {
            showSelectLanguageDialog()
        }
        binding.attractionsRecyclerView.apply {
            val layoutManager = LinearLayoutManager(requireContext())
            adapter = attractionsAdapter
            this.layoutManager = layoutManager
            addOnScrollListener(object : LoadMoreRecyclerViewScrollListener(layoutManager, 5) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.onLoadMore()
                }
            })
            addItemDecoration(
                LinearSpaceDecoration(
                    topBorderSpace = ITEM_SPACE,
                    itemsSpace = ITEM_SPACE,
                    bottomBorderSpace = ITEM_SPACE
                )
            )
        }

        binding.bannerViewPager.apply {
            if (adapter == null) {
                adapter = newsAdapter
                offscreenPageLimit = 1
                val nextItemVisiblePx = 9.dpToPx() //左右要突出的距離
                val currentItemHorizontalMarginPx = 21.dpToPx() //目前主View的邊距
                val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
                val pageTransformer =
                    ViewPager2.PageTransformer { page: View, position: Float ->
                        page.translationX = -pageTranslationX * position
                        page.scaleY =
                            1 - (0.25f * kotlin.math.abs(position)) //左右預覽的高度
                    }
                setPageTransformer(pageTransformer)
            }
        }
    }

    private fun showSelectLanguageDialog() {
        AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setItems(LanguageType.SELECT_ITEMS) { _, index ->
                LanguageType.SELECT_ITEMS.getOrNull(index)?.let { select ->
                    activityViewModel.onChangeLanguage(LanguageType.fromShowText(select))
                }
            }
            .show()
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.viewState.map {
                        it.isProgress
                    }.distinctUntilChanged().collectLatest {
                        binding.loadingProgress.isVisible = it
                    }
                }

                launch {
                    viewModel.viewState.map {
                        it.showItems
                    }.distinctUntilChanged().collectLatest {
                        if (it.isNotEmpty()) {
                            attractionsAdapter.submitList(it)
                        }
                    }
                }

                launch {
                    viewModel.viewState.map {
                        it.newsShowData
                    }.distinctUntilChanged().collectLatest {
                        binding.bannerViewPager.isVisible = it.isNotEmpty()
                        binding.newsTitleTextView.isVisible = it.isNotEmpty()
                        if (it.isNotEmpty()) {
                            newsAdapter.submitList(it)
                        }
                    }
                }

                launch {
                    viewModel.showUrlFlow.collectLatest { url ->
                        val bundle = Bundle().apply {
                            putString(AttractionWebFragment.ARG_URL_KEY, url)
                        }
                        navigateScreen(R.id.nav_attraction_web, bundle)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}