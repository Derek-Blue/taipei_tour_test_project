package com.ken.taipeitourtestproject.screen.attractioninfo.imagebanner

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.ken.taipeitourtestproject.R
import com.ken.taipeitourtestproject.base.BaseFragment
import com.ken.taipeitourtestproject.databinding.FragmentImageBannerBinding

class ImageBannerFragment: BaseFragment(R.layout.fragment_image_banner) {

    companion object {
        private const val ARG_ITEM = "arg_item"

        fun newInstance(item: ImageBannerData): ImageBannerFragment {
            return ImageBannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ITEM, item)
                }
            }
        }
    }

    private var _binding: FragmentImageBannerBinding? = null
    private val binding get() = _binding!!

    private val imageUrl by lazy {
        arguments?.getParcelable<ImageBannerData>(ARG_ITEM)?.url ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentImageBannerBinding.bind(view)
        initView()
    }

    private fun initView() {
        binding.contentImageView.isVisible = imageUrl.isNotEmpty()
        if (imageUrl.isNotEmpty()) {
            Glide.with(requireContext())
                .load(imageUrl)
                .into(binding.contentImageView)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}