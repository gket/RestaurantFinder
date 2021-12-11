package com.gketdev.restaurantfinder.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.gketdev.restaurantfinder.databinding.FragmentDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailFragment : BottomSheetDialogFragment() {

    private var binding: FragmentDetailBinding? = null
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initUi() {
        val restaurant = args.restaurantDetail
        binding?.textViewName?.text = restaurant.name
        binding?.textViewAddress?.text = restaurant.location.address
        binding?.textViewStreet?.text = restaurant.location.street
        binding?.textViewRegion?.text = restaurant.location.region
    }

}
