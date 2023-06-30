package com.example.newsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.ServiceBuilder
import com.example.newsapp.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        getNews()
    }
    private fun getNews() {
        val apiEndpoint = ServiceBuilder.buildService(EndPoints::class.java)
        val call: Call<List<App>> = apiEndpoint.getNews()
        call.enqueue(object : Callback<List<App>> {
            @SuppressLint("DiscouragedApi")
            override fun onResponse(
                call: Call<List<App>>,
                response: Response<List<App>>
            ) {
                if(response.isSuccessful){
                    val news = response.body()!!.take(9)
                    Log.e("List of App", news.toString())
                    if(news != null){
                        for (new in news){
                            val resources = getResources()
//                            val itemTitle = "item_title" // Replace with your actual string value
//
//                            val titleId =
//                            val titleTV = findViewById<TextView>(titleId)

                            val titleTV = findViewById<TextView>(resources.getIdentifier("item_title${news.indexOf(new)}", "id", packageName))
                            val summaryTV = findViewById<TextView>(resources.getIdentifier("item_review_summary${news.indexOf(new)}", "id", packageName))
                            val dateTV = findViewById<TextView>(resources.getIdentifier("item_date${news.indexOf(new)}", "id", packageName))
                            val priceTV = findViewById<TextView>(resources.getIdentifier("item_price${news.indexOf(new)}", "id", packageName))
                            val playBtn = findViewById<TextView>(resources.getIdentifier("item_play${news.indexOf(new)}", "id", packageName))
                            val itemImage = findViewById<ImageView>(resources.getIdentifier("item_image${news.indexOf(new)}", "id", packageName))

                            titleTV.text = new.title
                            summaryTV.text = new.reviewSummary.replace("<br>", "\n")
                            dateTV.text = new.released
                            priceTV.text = new.price
                            Picasso.with(this@MainActivity).load(new.imgUrl).into(itemImage)
                            itemImage.scaleType = ImageView.ScaleType.CENTER_CROP
                            val param = itemImage.layoutParams as ViewGroup.MarginLayoutParams
                            param.setMargins(10,10,10,10)
                            itemImage.layoutParams = param
                            playBtn.setOnClickListener {
                                val openURL = Intent(Intent.ACTION_VIEW)
                                openURL.data = Uri.parse(new.url)
                                startActivity(openURL)
                            }
                            playBtn.text = "get  "

                        }
                        val employeeRV = binding!!.gamesList
                        val employeesAdapter = AppsAdapter(this@MainActivity, news)
                        employeeRV.adapter = employeesAdapter
                        employeeRV.layoutManager = LinearLayoutManager(this@MainActivity)
                        Log.e("list not null", "updating UI")
                    }
                } else {
                    Log.e("response error", "Ooops! There was an error in getting your response ${response.errorBody()
                        ?.string()}")
                }
            }

            override fun onFailure(call: Call<List<App>>, t: Throwable) {
                Log.e("error", "Ooops! there is an error $t")
            }

        })

    }
}