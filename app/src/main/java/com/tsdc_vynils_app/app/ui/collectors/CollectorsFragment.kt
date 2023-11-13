package com.tsdc_vynils_app.app.ui.collectors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.FragmentCollectorsBinding
import com.tsdc_vynils_app.app.models.Collector
import com.tsdc_vynils_app.app.ui.adapters.CollectorsAdapter
import com.tsdc_vynils_app.app.ui.dashboard.DashboardViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CollectorsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CollectorsFragment : Fragment() {

    private var _binding: FragmentCollectorsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCollectorsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listaElementos = mutableListOf(
            Collector(1,"Miguel Vega","3216437263","mttm774@gmail.com",R.drawable.ellipse2),
            Collector(2,"Pedro Capriles","3216437263","mttm774@gmail.com",R.drawable.ellipse3),
            Collector(3,"Fabio Castagnola","3216437263","mttm774@gmail.com",R.drawable.ellipse4),
            Collector(4,"Miguel Tovar","3216437263","mttm774@gmail.com",R.drawable.ellipse5)
        )

        val recyclerView: RecyclerView = _binding!!.recyclerViewCollectors
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CollectorsAdapter(listaElementos)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}