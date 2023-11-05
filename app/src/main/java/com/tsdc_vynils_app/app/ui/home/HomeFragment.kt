package com.tsdc_vynils_app.app.ui.home

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.FragmentHomeBinding
import com.tsdc_vynils_app.app.viewModels.HomeViewModel
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentViewHolder
import com.squareup.picasso.Transformation
import com.tsdc_vynils_app.app.models.Album
import java.net.ConnectException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel:HomeViewModel
    private lateinit var Albums: List<Album>


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this).get(HomeViewModel::class.java)

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        root.setBackgroundColor(resources.getColor(R.color.black))




        val scrollView = binding.contentAlbums

        scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == 0) {

                getAllAlbums()
            }
        }


        return root
    }

    fun getAllAlbums(){
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

            try {

                homeViewModel.albums.observe(viewLifecycleOwner, Observer<List<Album>> {
                    it.apply {
                        Albums=it
                        GlobalScope.launch(Dispatchers.IO) {
                            withContext(Dispatchers.Main) {
                                updateUI(Albums)
                                binding.swipeRefreshLayout.isRefreshing = false
                            }
                        }
                    }



                })
                homeViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
                    if (isNetworkError) onNetworkError()
                })





            } catch (e: Exception) {
                Log.d("error",e.toString())

                    onNetworkError()

            }

    }

    fun updateUI(albums: List<Album>) {
        val parentLinearLayout: LinearLayout = binding.parentContentLinearLayout
        parentLinearLayout.removeAllViews()

        var childLinearLayout = LinearLayout(requireContext())


        var cont=1

        albums.forEach{ al->
            //cargando los albumes
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

            val imageUrl = al.cover


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


            val albumImageView = ImageView(requireContext())
            albumImageView.layoutParams = ViewGroup.LayoutParams(
                imageSizeInPixels,
                imageSizeInPixels
            )
            albumImageView.setOnClickListener {
                val bundle = bundleOf("albumId" to al.id)
                albumImageView.findNavController().navigate(R.id.actionHomeFragmentToAlbumDetailsActivity, bundle)
            }



            Picasso.get()
                .load(imageUrl)
                .into(albumImageView)






            cardLinearLayout.addView(albumImageView)

            val textViewAlbum = TextView(requireContext())
            textViewAlbum.text = al.name
            textViewAlbum.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            val fontSize = 8 // Valor en dp
            val fontSizeInPixels = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                fontSize.toFloat(),
                resources.displayMetrics).toInt()
            textViewAlbum.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSizeInPixels.toFloat())
            cardLinearLayout.addView(textViewAlbum)

            childLinearLayout.addView(cardLinearLayout)
            cont++



        }


    }


    fun fillDataInLayout(){
        val parentLinearLayout: LinearLayout = binding.parentContentLinearLayout
        parentLinearLayout.removeAllViews()

        var childLinearLayout = LinearLayout(requireContext())


        var cont=1

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.albums.observe(viewLifecycleOwner, Observer<List<Album>> {
            it.apply {
                it.forEach{ al->
                    //cargando los albumes
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

                    val imageUrl = al.cover


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


                    val albumImageView = ImageView(requireContext())
                    albumImageView.layoutParams = ViewGroup.LayoutParams(
                        imageSizeInPixels,
                        imageSizeInPixels
                    )
                    albumImageView.setOnClickListener {
                        val bundle = bundleOf("albumId" to al.id)
                        albumImageView.findNavController().navigate(R.id.actionHomeFragmentToAlbumDetailsActivity, bundle)
                    }

                    Picasso.get()
                        .load(imageUrl)
                        .into(albumImageView)

                    cardLinearLayout.addView(albumImageView)

                    val textViewAlbum = TextView(requireContext())
                    textViewAlbum.text = al.name
                    textViewAlbum.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    val fontSize = 8 // Valor en dp
                    val fontSizeInPixels = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        fontSize.toFloat(),
                        resources.displayMetrics).toInt()
                    textViewAlbum.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSizeInPixels.toFloat())
                    cardLinearLayout.addView(textViewAlbum)

                    childLinearLayout.addView(cardLinearLayout)
                    cont++



                }
            }
        })
        homeViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllAlbums()


    }

    private fun onNetworkError() {
        if(!homeViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Error de conexión", Toast.LENGTH_LONG).show()
            showConnectionError()
            homeViewModel.onNetworkErrorShown()
        }
    }

    private fun showConnectionError(){
        val parentLinearLayout: LinearLayout = binding.parentContentLinearLayout
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class RoundedCornersTransform(private val radiusInPx: Float) : Transformation {
    override fun key(): String {
        return "rounded_corners"
    }

    override fun transform(source: Bitmap): Bitmap {
        val bitmap = Bitmap.createBitmap(source.width, source.height, source.config)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rectF, radiusInPx, radiusInPx, paint)
        source.recycle()
        return bitmap
    }
}
