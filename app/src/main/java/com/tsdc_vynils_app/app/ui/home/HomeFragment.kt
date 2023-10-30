package com.tsdc_vynils_app.app.ui.home

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        root.setBackgroundColor(resources.getColor(R.color.black))

        //cargando los albumes

        val parentLinearLayout: LinearLayout = binding.parentLinearLayout

        val childLinearLayout = LinearLayout(requireContext())
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



        val imageWidth = 100 // Valor en dp
        val imageSizeInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            imageWidth.toFloat(),
            resources.displayMetrics
        ).toInt()

        val imageUrl = "https://img.discogs.com/BrMMi9F_5nW8acbz9YiSAfgjoGw=/fit-in/600x527/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-15276011-1589049135-3433.jpeg.jpg"

        for(i in 1..3){
            val cardLinearLayout = LinearLayout(requireContext())

            val layoutParamsCard = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            val marginlefcardtindp = 5 // Valor en dp
            val marginlefcardInPixels = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                marginlefcardtindp.toFloat(),
                resources.displayMetrics
            ).toInt()

            layoutParamsCard.setMargins(marginlefcardInPixels, 0, marginlefcardInPixels, 0)
            cardLinearLayout.layoutParams = layoutParamsCard
            cardLinearLayout.orientation = LinearLayout.VERTICAL


            val albumImageView = ImageView(requireContext())
            albumImageView.layoutParams = ViewGroup.LayoutParams(
                imageSizeInPixels,
                imageSizeInPixels
            )
            albumImageView.setOnClickListener {
                val action = R.id.action_navigation_home_to_navigate_albumDetails
                albumImageView.findNavController().navigate(action)
            }
            Picasso.get().load(imageUrl).into(albumImageView)
            cardLinearLayout.addView(albumImageView)

            val textViewAlbum = TextView(requireContext())
            textViewAlbum.text = "Poeta del pueblo"
            textViewAlbum.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            val fontSize = 8 // Valor en dp
            val fontSizeInPixels = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                fontSize.toFloat(),
                resources.displayMetrics
            ).toInt()
            textViewAlbum.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSizeInPixels.toFloat())
            cardLinearLayout.addView(textViewAlbum)



            childLinearLayout.addView(cardLinearLayout)


        }


        parentLinearLayout.addView(childLinearLayout)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}