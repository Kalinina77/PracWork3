package com.example.pracwork3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val item = intent.getParcelableExtra<ItemOfList>("OBJECT_INTENT")

        Picasso.with(this).load(item?.poster_path).fit().centerInside()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground).fit().into(Image)

        Title.text = item?.title
        Description.text = item?.overview
        Author.text = item?.vote_average.toString()
        Date.text = item?.release_date
    }

}