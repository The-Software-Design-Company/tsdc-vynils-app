package com.tsdc_vynils_app.app.ui.collectors

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.FragmentCollectorsBinding
import com.tsdc_vynils_app.app.models.Collector
import com.tsdc_vynils_app.app.ui.adapters.CollectorsAdapter
import com.tsdc_vynils_app.app.viewModels.CollectorViewModel
import com.tsdc_vynils_app.app.viewModels.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import java.util.stream.Collectors

/**
 * A simple [Fragment] subclass.
 * Use the [CollectorsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CollectorsFragment : Fragment() {

    private var _binding: FragmentCollectorsBinding? = null
    private lateinit var recyclerView: RecyclerView

    private val binding get() = _binding!!
    private lateinit var collectorViewModel:CollectorViewModel
    private lateinit var collectorsAdapter: CollectorsAdapter
    private var collectors = mutableListOf<Collector>()
    private var collectorsList: List<Collector> = collectors.toList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        ViewModelProvider(this).get(HomeViewModel::class.java)

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        _binding = FragmentCollectorsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        root.setBackgroundColor(resources.getColor(R.color.black))

        recyclerView = _binding!!.recyclerViewCollectors
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        collectorsAdapter = CollectorsAdapter(collectorsList)
        recyclerView.adapter = collectorsAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Aquí verificamos si el RecyclerView ha sido desplazado al principio
                if (!recyclerView.canScrollVertically(-1) && dy < 0) {
                    // Llama a la función para obtener todos los álbumes cuando se llega al inicio del scroll
                    getAllCollectors()
                }
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!collectorViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Error de conexión", Toast.LENGTH_LONG).show()
            showConnectionError()
            collectorViewModel.onNetworkErrorShown()
        }
    }

    private fun showConnectionError(){
        val parentLinearLayout: LinearLayout = binding.parentLinearLayout
        val WifiImage = ImageView(requireContext())
        val imageWidth = 100
        val imageSizeInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            imageWidth.toFloat(),
            resources.displayMetrics).toInt()
        val layoutParams = LinearLayout.LayoutParams(
            imageSizeInPixels,
            imageSizeInPixels
        )
        layoutParams.gravity = Gravity.CENTER
        WifiImage.layoutParams = layoutParams
        Picasso.get().load(R.drawable.ic_nowifi).into(WifiImage)

        parentLinearLayout.addView(WifiImage)

        val textMessage = TextView(requireContext())
        textMessage.text = "No tienes conexión a internet"
        textMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorTextSelected))
        val fontSize = 20
        val fontSizeInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            fontSize.toFloat(),
            resources.displayMetrics).toInt()
        textMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSizeInPixels.toFloat())
        val textLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textLayoutParams.gravity = Gravity.CENTER

        textMessage.layoutParams = textLayoutParams

        parentLinearLayout.addView(textMessage)

    }

    fun getAllCollectors() {
        collectorViewModel = ViewModelProvider(this).get(CollectorViewModel::class.java)

        try {
            collectorViewModel.collectors.observe(viewLifecycleOwner, Observer<List<Collector>> { collectors ->
                collectors?.let {
                    collectorsList = it // Asigna la lista observada a una variable Collectors (asegúrate de tener Collectors definido en tu clase)
                    GlobalScope.launch(Dispatchers.Main) {
                        updateUI(collectorsList) // Actualiza la UI con la lista Collectors
                    }
                }
            })
            collectorViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
        } catch (e: Exception) {
            Log.d("error", e.toString())
            onNetworkError()
        }
    }

    fun updateUI(collectors: List<Collector>) {
        val parentLinearLayout: LinearLayout = binding.parentLinearLayout
        parentLinearLayout.removeAllViews()

        var childLinearLayout = LinearLayout(requireContext())

        var cont=1

        collectors.forEach{ col->
            //cargando los Coleccionistas
            if(cont==4 || cont==1){
                cont=1
                childLinearLayout = LinearLayout(requireContext())
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, // Ancho
                    LinearLayout.LayoutParams.WRAP_CONTENT // Altura
                )

                val marginTopInDp = 10 // Valor en dp
                val marginTopInPixels = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    marginTopInDp.toFloat(),
                    resources.displayMetrics
                ).toInt()
                val marginLeftInDp = 20 // Valor en dp
                val marginLeftPixels = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    marginLeftInDp.toFloat(),
                    resources.displayMetrics
                ).toInt()

                layoutParams.setMargins(marginLeftPixels, marginTopInPixels, 0, 0)
                childLinearLayout.layoutParams = layoutParams
                childLinearLayout.orientation = LinearLayout.HORIZONTAL
                parentLinearLayout.addView(childLinearLayout)
            }
            val imageWidth = 100 // Valor en dp
            val imageSizeInPixels = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                imageWidth.toFloat(),
                resources.displayMetrics).toInt()

            val imageUrl = col.imagenResId


            val cardLinearLayout = LinearLayout(requireContext())

            val layoutParamsCard = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            val marginlefcardtindp = 5 // Valor en dp
            val marginlefcardInPixels = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                marginlefcardtindp.toFloat(),
                resources.displayMetrics).toInt()

            layoutParamsCard.setMargins(marginlefcardInPixels, 0, marginlefcardInPixels, 0)
            cardLinearLayout.layoutParams = layoutParamsCard
            cardLinearLayout.orientation = LinearLayout.VERTICAL

            val collectorImageView = ImageView(requireContext())
            collectorImageView.layoutParams = ViewGroup.LayoutParams(
                imageSizeInPixels,
                imageSizeInPixels
            )

            Picasso.get()
                .load(imageUrl)
                .into(collectorImageView)

            cardLinearLayout.addView(collectorImageView)

            val textViewCollector = TextView(requireContext())
            textViewCollector.text = col.name
            textViewCollector.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            val fontSize = 8 // Valor en dp
            val fontSizeInPixels = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                fontSize.toFloat(),
                resources.displayMetrics).toInt()
            textViewCollector.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSizeInPixels.toFloat())
            cardLinearLayout.addView(textViewCollector)

            childLinearLayout.addView(cardLinearLayout)
            cont++
        }
    }
}