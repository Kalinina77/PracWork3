package com.example.pracwork3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var UPCOMING_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=df74eb704c0f9b7a3e8c2617fe6e5ce4&page=1"
    var TOP_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=df74eb704c0f9b7a3e8c2617fe6e5ce4&page=1"

    var okHttpClient: OkHttpClient = OkHttpClient()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        recyclerView = findViewById<RecyclerView>(R.id.RecycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        getRequest(recyclerView, UPCOMING_URL )

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_upcoming -> getRequest(recyclerView, UPCOMING_URL)
                R.id.ic_top -> getRequest(recyclerView, TOP_URL)

            }
            true
        }

        navigation.setOnNavigationItemReselectedListener {  }
    }
    /*
    fun getRequest_old(recyclerView: RecyclerView){
        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                val json = JSONObject(response!!.body!!.string())
                runOnUiThread {
                    val jsonArray = json.getJSONArray("articles")
                    var imageList: ArrayList<ItemOfList> = ArrayList<ItemOfList>()

                    for (i in 0..jsonArray.length()-1){
                        var jsonArticle = jsonArray.optJSONObject(i)

                        var author: String = jsonArticle.getString("author")
                        var title: String = jsonArticle.getString("title")
                        var urlToImage: String = jsonArticle.getString("urlToImage")
                        var description: String = jsonArticle.getString("description")
                        var publishedAt: String = jsonArticle.getString("publishedAt")

                        imageList.add(ItemOfList(author, title, urlToImage, description, publishedAt))
                    }

                    recyclerView.adapter = ItemAdapter(this@MainActivity, imageList){
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra("OBJECT_INTENT", it)
                        startActivity(intent)
                    }
                }
            }
        })
    }
*/
    fun getRequest(recyclerView: RecyclerView, URL: String){

        animateContent(true)

        //var URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=df74eb704c0f9b7a3e8c2617fe6e5ce4&page=1"
        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                val json = JSONObject(response!!.body!!.string())
                runOnUiThread {
                    val jsonArray = json.getJSONArray("results")
                    var imageList: ArrayList<ItemOfList> = ArrayList<ItemOfList>()

                    for (i in 0..jsonArray.length()-1){
                        var jsonArticle = jsonArray.optJSONObject(i)

                        var vote_average: Double = jsonArticle.getDouble("vote_average")
                        var title: String = jsonArticle.getString("title")
                        var poster_path: String = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + jsonArticle.getString("poster_path")
                        var overview: String = jsonArticle.getString("overview")
                        var release_date: String = jsonArticle.getString("release_date")

                        imageList.add(ItemOfList(title, poster_path, overview, release_date, vote_average))
                    }

                    recyclerView.adapter = ItemAdapter(this@MainActivity, imageList){
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra("OBJECT_INTENT", it)
                        startActivity(intent)
                    }
                }
            }
        })

        Handler().postDelayed({
            animateContent(false)
        }, 1000)

    }

    fun animateContent(start: Boolean) {
        if (start) {
            progressBar.visibility = View.VISIBLE
            recyclerView.animate()
                .translationY(recyclerView.height.toFloat())
                .alpha(0.0f)
                .setDuration(300)
            //Handler().postDelayed({
                recyclerView.clearAnimation()
                recyclerView.visibility = View.GONE
            //}, 300)

        }
        else {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            recyclerView.animate()
                .translationY(0.0f)
                .alpha(1.0f)
                .setDuration(300)
            recyclerView.clearAnimation()
        }
    }

}