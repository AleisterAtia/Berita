package com.haura.detail_produk

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.haura.detail_produk.api.ApiClient
import com.haura.detail_produk.model.BeritaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {
    private lateinit var svJudul: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var rvBerita: RecyclerView
    private lateinit var floatBtnTambah: FloatingActionButton
    private lateinit var beritaAdapter: BeritaAdapter
    private lateinit var imgNotFound: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi View
        svJudul = findViewById(R.id.svJudul)
        progressBar = findViewById(R.id.progressBar)
        rvBerita = findViewById(R.id.rvBerita)
        floatBtnTambah = findViewById(R.id.floatBtnTambah)
        imgNotFound = findViewById(R.id.imgNotFound)

        // Inisialisasi Adapter
        beritaAdapter = BeritaAdapter(arrayListOf())
        rvBerita.adapter = beritaAdapter
        rvBerita.layoutManager = LinearLayoutManager(this)

        // Panggil method getBerita
        getBerita("")

        // SearchView Listener
        svJudul.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(pencarian: String?): Boolean {
                getBerita(pencarian.orEmpty())
                return false
            }
        })

        floatBtnTambah.setOnClickListener{
            startActivity(Intent(this@DashboardActivity,TambahBerita::class.java))
        }
    }

    private fun getBerita(judul: String) {
        progressBar.visibility = View.VISIBLE

        ApiClient.apiService.getListBerita(judul).enqueue(object : Callback<BeritaResponse> {
            override fun onResponse(
                call: Call<BeritaResponse>,
                response: Response<BeritaResponse>
            ) {
                if (response.isSuccessful) {
                    val beritaResponse = response.body()
                    if (beritaResponse != null && beritaResponse.succes) {
                        // Jika data ditemukan
                        beritaAdapter.setData(beritaResponse.data)
                        imgNotFound.visibility = View.GONE
                    } else {
                        // Jika data tidak ditemukan
                        beritaAdapter.setData(emptyList())
                        imgNotFound.visibility = View.VISIBLE
                    }
                } else {
                    Toast.makeText(
                        this@DashboardActivity,
                        "Error: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<BeritaResponse>, t: Throwable) {
                Toast.makeText(
                    this@DashboardActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
                progressBar.visibility = View.GONE
            }
        })
    }
}
