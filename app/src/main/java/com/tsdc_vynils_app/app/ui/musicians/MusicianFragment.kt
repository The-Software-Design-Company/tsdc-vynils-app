package com.tsdc_vynils_app.app.ui.musicians

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.FragmentCollectorsBinding
import com.tsdc_vynils_app.app.models.Collector
import com.tsdc_vynils_app.app.ui.adapters.CollectorsAdapter
import com.tsdc_vynils_app.app.viewModels.MusicianViewModel
import com.tsdc_vynils_app.app.databinding.FragmentMusicianBinding
import com.tsdc_vynils_app.app.models.Musician
import com.tsdc_vynils_app.app.ui.adapters.MusiciansAdapter
import java.time.LocalDate
import java.util.Date

class MusicianFragment : Fragment() {

    companion object {
        fun newInstance() = MusicianFragment()
    }

    private var _binding: FragmentMusicianBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MusicianViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMusicianBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val elementList = mutableListOf(
            Musician(1,"Victor Manuelle","Salsero", Date(),R.drawable.victor_manuelle),
            Musician(2,"Martin Garrix","Dance", Date(),R.drawable.martin),
            Musician(3,"Alejandro Fernandez","Ranchera", Date(),R.drawable.alejandro),
            Musician(4,"Avril Lavigne","Punk", Date(),R.drawable.avril)

        )

        val recyclerView: RecyclerView = _binding!!.recyclerViewMusicians
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MusiciansAdapter(elementList)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MusicianViewModel::class.java)
        // TODO: Use the ViewModel
    }

}