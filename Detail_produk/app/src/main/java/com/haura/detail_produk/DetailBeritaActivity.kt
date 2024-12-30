package com.haura.detail_produk

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class DetailBeritaActivity : AppCompatActivity() {

    private lateinit var imgBeritaDetail : ImageView
    private lateinit var tvJudulDetail : TextView
    private lateinit var tvTglDetailBerita : TextView
    private lateinit var tvDetailRating : TextView
    private lateinit var DetailRatingBar : RatingBar
    private lateinit var tvDetailIsiBerita : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_berita)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgBeritaDetail = findViewById(R.id.imgBeritaDetail)
        tvJudulDetail = findViewById(R.id.tvJudulDetail)
        tvTglDetailBerita = findViewById(R.id.tvTglDetailBerita)
        tvDetailRating = findViewById(R.id.tvDetailRating)
        DetailRatingBar = findViewById(R.id.DetailRatingBar)
        tvDetailIsiBerita = findViewById(R.id.tvDetailIsiBerita)

        Picasso.get().load(intent.getStringExtra("gambar")).into(imgBeritaDetail)
        tvJudulDetail.text = intent.getStringExtra("judul")
        tvTglDetailBerita.text = intent.getStringExtra("tgl_berita")
        tvDetailRating.text = "${intent.getDoubleExtra("rating",0.0)}"
        DetailRatingBar.rating = intent.getDoubleExtra("judul", 0.0).toFloat()
        tvDetailIsiBerita.text = intent.getStringExtra("isi")
    }
}