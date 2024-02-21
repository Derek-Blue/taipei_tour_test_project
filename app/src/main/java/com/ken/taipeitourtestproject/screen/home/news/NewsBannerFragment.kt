package com.ken.taipeitourtestproject.screen.home.news

import android.os.Bundle
import android.view.View
import com.ken.taipeitourtestproject.R
import com.ken.taipeitourtestproject.base.BaseFragment
import com.ken.taipeitourtestproject.databinding.FragmentNewsBannerBinding
import com.ken.taipeitourtestproject.screen.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsBannerFragment: BaseFragment(R.layout.fragment_news_banner) {

    private var _binding: FragmentNewsBannerBinding? = null
    private val binding get() = _binding!!

    private val parentViewModel by viewModel<HomeViewModel>(
        ownerProducer = { requireParentFragment() }
    )

    companion object {
        private const val ARG_DATA = "ARG_DATA"
        fun newInstance(data: NewsShowData) : NewsBannerFragment {
            return NewsBannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_DATA, data)
                }
            }
        }
    }

    private val showData by lazy {
        arguments?.getParcelable(ARG_DATA) as? NewsShowData
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBannerBinding.bind(view)

        initView()
    }

    private fun initView() {
        binding.root.setOnClickDebounce {
            showData?.url?.let {
                parentViewModel.showNewsWebView(it)
            }
        }

        binding.titleTextView.text = showData?.title ?: ""
        binding.descriptionTextView.text = showData?.description ?: ""
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}