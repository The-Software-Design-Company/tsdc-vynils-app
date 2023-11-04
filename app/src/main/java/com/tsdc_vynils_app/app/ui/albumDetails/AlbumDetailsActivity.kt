package com.tsdc_vynils_app.app.ui.albumDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsdc_vynils_app.app.databinding.ActivityMainBinding
import com.tsdc_vynils_app.app.ui.components.carousel.adapter.CarouselAdapter
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.ActivityAlbumDetailsBinding


class AlbumDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlbumDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumDetailsBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_album_details)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val titleTextView = toolbar.findViewById<TextView>(R.id.toolbar_title)
        titleTextView.text = getResources().getString(R.string.title_activity_album_details)
        val iconImageView = findViewById<AppCompatImageView>(R.id.toolbar_icon)
        iconImageView.setImageDrawable(getDrawable(R.drawable.arrow_left))
        iconImageView.setOnClickListener {
            this.onSupportNavigateUp()
        }
        val titleAlbum = this.findViewById<TextView>(R.id.album_details_title)
        titleAlbum.text = "...And justice for all"
        val albumDescription = this.findViewById<TextView>(R.id.album_description)
        albumDescription.text = "Bacon ipsum dolor amet turkey turducken chuck salami, alcatra frankfurter picanha ground round pork chop biltong jowl venison brisket kevin tri-tip. Beef ribs alcatra ham hock chuck pancetta flank rump biltong chicken doner jowl. Flank spare ribs shoulder salami. Venison bacon picanha pork loin tail pork belly turkey kielbasa. Leberkas picanha swine ground round cow meatball strip steak pork loin turkey corned beef. Kielbasa shoulder sirloin chislic pork chop ribeye. T-bone shankle rump venison cow.\n" +
                "\n" +
                "Sirloin shank brisket pig rump pork beef ribs spare ribs drumstick. Ham hock pork belly bacon pork pork chop kevin tri-tip sirloin pastrami turkey. Meatloaf leberkas prosciutto, beef pastrami turkey salami. Ham ball tip meatball ground round. Cow pork chop chuck bresaola capicola."

        val images = listOf(R.drawable.and_justice_for_all, R.drawable.and_justice_for_all, R.drawable.and_justice_for_all)
        val adapter = CarouselAdapter(images)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Handle the Up button click event
        return true
    }


}