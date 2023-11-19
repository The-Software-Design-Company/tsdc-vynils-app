package com.tsdc_vynils_app.app.ui.collectors

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import com.tsdc_vynils_app.app.databinding.FragmentCollectorsBinding
import com.tsdc_vynils_app.app.models.Collector
import com.tsdc_vynils_app.app.viewModels.CollectorViewModel
import  com.tsdc_vynils_app.app.ui.adapters.CollectorsAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [CollectorsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CollectorsFragment : Fragment() {

    companion object {
        fun newInstance() = CollectorsFragment()
    }

    private var _binding: FragmentCollectorsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CollectorViewModel
    private var viewModelAdapter: CollectorsAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectorsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModelAdapter = CollectorsAdapter()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerViewCollectors
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, CollectorViewModel.Factory(activity.application)).get(
            CollectorViewModel::class.java
        )
        viewModel.collectors.observe(viewLifecycleOwner, Observer<List<Collector>> {
            it.apply {
                viewModelAdapter!!.elementList = this
            }
            val searchText = binding.editTextSearch

            searchText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModelAdapter?.filter?.filter(s)
                }
            })
        })
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Error de conexión", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}