package com.tsdc_vynils_app.app.ui.musicians

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.viewModels.MusicianViewModel
import com.tsdc_vynils_app.app.databinding.FragmentMusicianBinding
import com.tsdc_vynils_app.app.models.Musician
import com.tsdc_vynils_app.app.ui.adapters.MusiciansAdapter
import java.util.Date

class MusicianFragment : Fragment() {

    companion object {
        fun newInstance() = MusicianFragment()
    }

    private var _binding: FragmentMusicianBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MusicianViewModel
    private var viewModelAdapter: MusiciansAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMusicianBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModelAdapter=MusiciansAdapter()




        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerViewMusicians
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = viewModelAdapter


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, MusicianViewModel.Factory(activity.application)).get(MusicianViewModel::class.java)
        viewModel.musicians.observe(viewLifecycleOwner, Observer<List<Musician>> {
            it.apply {
                viewModelAdapter!!.elementList = this
            }

            val searchText = binding.editTextSearchMusician
            searchText.setText("")

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
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })



    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Error de conexión", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

}