package com.ken.taipeitourtestproject.screen.attractioninfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.ken.taipeitourtestproject.R
import com.ken.taipeitourtestproject.base.BaseFragment
import com.ken.taipeitourtestproject.databinding.FragmentAttractionInfoBinding
import com.ken.taipeitourtestproject.module.ext.finishScreen
import com.ken.taipeitourtestproject.module.ext.navigateScreen
import com.ken.taipeitourtestproject.screen.attractioninfo.imagebanner.ImageBannerData
import com.ken.taipeitourtestproject.screen.attractioninfo.imagebanner.ImageBannerViewPagerAdapter
import com.ken.taipeitourtestproject.screen.attractionweb.AttractionWebFragment.Companion.ARG_URL_KEY
import com.ken.taipeitourtestproject.screen.home.data.AttractionShowData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AttractionInfoFragment: BaseFragment(R.layout.fragment_attraction_info) {

    companion object {
        const val ARG_INFO_ITEM_KEY = "ARG_INFO_ITEM_KEY"
    }

    private var _binding: FragmentAttractionInfoBinding? = null
    private val binding get() = _binding!!

    private var autoScrollJob: Job? = null

    private val showItem by lazy {
        arguments?.getParcelable(ARG_INFO_ITEM_KEY) as? AttractionShowData
    }

    private lateinit var viewPagerAdapter : ImageBannerViewPagerAdapter
    private lateinit var showImages: List<String>

    private val viewModel by viewModel<AttractionInfoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAttractionInfoBinding.bind(view)

        initView()
        observer()
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.viewState.map {
                        it.currentIndex
                    }.distinctUntilChanged().collectLatest {
                        if (it != binding.imageViewPager.currentItem) {
                            binding.imageViewPager.currentItem = it
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        if (showItem == null) finishScreen()
        val item = showItem!!
        viewPagerAdapter = ImageBannerViewPagerAdapter(this)
        showImages = convertShowImages(item.images)

        binding.titleTextView.text = item.name

        binding.backImageView.setOnClickDebounce {
            finishScreen()
        }

        val isInfinityScroll = showImages.size >= 2
        binding.imageViewPager.apply {
            offscreenPageLimit = 1
            adapter = viewPagerAdapter
            isUserInputEnabled = isInfinityScroll
            if (isInfinityScroll) {
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        when (position) {
                            0 -> {
                                binding.imageViewPager.setCurrentItem(showImages.lastIndex - 1, false)
                            }
                            showImages.lastIndex -> {
                                binding.imageViewPager.setCurrentItem(1, false)
                            }
                            else -> {
                                viewModel.setCurrentIndex(position)
                                val realPosition = position - 1
                                val tab = binding.dotTabLayout.getTabAt(realPosition)
                                tab?.select()
                            }
                        }
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        super.onPageScrollStateChanged(state)
                        if (state == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                            autoScrollImage()
                        }
                    }
                })
            }
        }

        binding.dotTabLayout.isVisible = isInfinityScroll
        if (isInfinityScroll) {
            for (i in 0 until item.images.size) {
                binding.dotTabLayout.addTab(binding.dotTabLayout.newTab())
            }
            // disable tab click
            val tabStrip = binding.dotTabLayout.getChildAt(0) as LinearLayout
            for (i in 0 until tabStrip.childCount) {
                tabStrip.getChildAt(i).setOnTouchListener { _, _ -> true }
            }
        }
        setImageViewPager(showImages)

        binding.infoTextView.text = getInfoStringBuilder(item)
        binding.infoTextView.movementMethod = LinkMovementMethod.getInstance()
        autoScrollImage()
    }

    private fun convertShowImages(images: List<String>): List<String> {
        return if (images.size <= 1) {
            images
        } else {
            val first = images.first()
            val last = images.last()
            listOf(last) + images + listOf(first)
        }
    }

    private fun setImageViewPager(images: List<String>) {
        val showItems = when {
            images.isEmpty() -> listOf(ImageBannerData(0, ""))
            images.size == 1 -> listOf(ImageBannerData(0, images.first()))
            else -> {
                images.mapIndexed { index, url ->
                    ImageBannerData(index, url)
                }
            }
        }
        viewPagerAdapter.submitList(showItems) {
            if (images.size >= 2) {
                val stateIndex = viewModel.getCurrentIndex()
                binding.imageViewPager.setCurrentItem(stateIndex, false)
            }
        }
    }

    private fun autoScrollImage() {
        autoScrollJob?.cancel()
        autoScrollJob = lifecycleScope.launch {
            delay(3000)
            val current = _binding?.imageViewPager?.currentItem ?: 1
            if (current + 1 >= showImages.lastIndex) {
                _binding?.imageViewPager?.let {
                    it.setCurrentItem(1, false)
                    autoScrollImage()
                }
            } else {
                _binding?.imageViewPager?.currentItem = current + 1
            }
        }
    }

    private fun getInfoStringBuilder(item: AttractionShowData): SpannedString {
        return buildSpannedString {
            append(item.name)
            setSpan(RelativeSizeSpan(1.5f),
                0, item.name.length, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(
                ContextCompat.getColor(requireContext(), R.color.primary_text)
            ), 0, item.name.length, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)

            append("\n\n")
            append(item.introduction)
            append("\n\n")
            if (item.address.isNotEmpty()) {
                append("Address:\n")
                append(item.address)
                append("\n\n")
            }
            if (item.lastUpdateTime.isNotEmpty()) {
                append("Last Update Time\n")
                append(item.lastUpdateTime)
                append("\n\n")
            }
            if (item.url.isNotEmpty()) {
                append(item.url, object : ClickableSpan(){
                    override fun onClick(widget: View) {
                        val bundle = Bundle().apply {
                            putString(ARG_URL_KEY, item.url)
                        }
                        navigateScreen(R.id.nav_attraction_web, bundle)
                    }
                }, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
                append("\n\n")
            }
        }
    }

    override fun onPause() {
        autoScrollJob?.cancel()
        autoScrollJob = null
        super.onPause()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}