package com.tsdc_vynils_app.app.ui.albumDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.tsdc_vynils_app.app.R

class AlbumDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = AlbumDetailsFragment()
    }

    private lateinit var viewModel: AlbumDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlbumDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}