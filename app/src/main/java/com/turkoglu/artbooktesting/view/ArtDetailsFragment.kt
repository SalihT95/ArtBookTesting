package com.turkoglu.artbooktesting.view

import android.os.Bundle
import android.view.PixelCopy.Request
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.turkoglu.artbooktesting.R
import com.turkoglu.artbooktesting.Util.Status
import com.turkoglu.artbooktesting.databinding.FragmentArtDetailsBinding
import com.turkoglu.artbooktesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide : RequestManager
): Fragment(R.layout.fragment_art_details) {

    lateinit var viewModel : ArtViewModel

    private var fragementBinding : FragmentArtDetailsBinding?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentArtDetailsBinding.bind(view)

        fragementBinding=binding
        subscribeToObservers()

        binding.artImageView.setOnClickListener {
            findNavController().navigate(
                ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())

        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setSelectedImage("")
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener {
            viewModel.makeArt(binding.nameText.text.toString(),
                binding.artistText.text.toString(),
                binding.yearText.text.toString())
        }

    }

    private fun subscribeToObservers(){

        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer {url->
            fragementBinding?.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message?:"Error",Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {

                }
            }
        })

    }

    override fun onDestroyView() {
        fragementBinding=null
        super.onDestroyView()
    }
}