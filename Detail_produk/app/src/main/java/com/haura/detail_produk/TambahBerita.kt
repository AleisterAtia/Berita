package com.haura.detail_produk

import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.haura.detail_produk.api.ApiClient
import com.haura.detail_produk.model.TambahResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class TambahBerita : AppCompatActivity() {

    private lateinit var etJudul: EditText
    private lateinit var etIsi: EditText
    private lateinit var btnGambar: Button
    private lateinit var btnTambah: Button
    private lateinit var imgGambar: ImageView
    private var imageFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_berita)

        btnGambar = findViewById(R.id.btnGambar)
        imgGambar = findViewById(R.id.imgGambar)
        etIsi = findViewById(R.id.etIsiBerita)
        btnTambah = findViewById(R.id.btnTambah)
        etJudul = findViewById(R.id.etJudul)


        btnGambar.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        btnTambah.setOnClickListener {
            imageFile?.let {
                    file ->
                tambahBerita(etJudul.text.toString(), etIsi.text.toString(), file)
            }
        }

    }

    //menampilkan gambar
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data!!
            imageFile = File(uri.path!!)
            imgGambar.visibility = View.VISIBLE
            imgGambar.setImageURI(uri)

        }
    }


    //proses tambah berita
    private fun tambahBerita(judul: String, isi_berita: String, fileGambar: File) {
        val requestBody = fileGambar.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val partFileGambar =
            MultipartBody.Part.createFormData("fileGambar", fileGambar.name, requestBody)
        val title = judul.toRequestBody("text/plain".toMediaTypeOrNull())
        val deskripsiBerita = isi_berita.toRequestBody("text/plain".toMediaTypeOrNull())

        ApiClient.apiService.addBerita(title, deskripsiBerita, partFileGambar)
            .enqueue(object : Callback<TambahResponse> {
                override fun onResponse(
                    call: Call<TambahResponse>,
                    response: Response<TambahResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.succes) {
                            startActivity(
                                Intent(
                                    this@TambahBerita,
                                    DashboardActivity::class.java
                                )
                            )

                        } else {
                            Toast.makeText(
                                this@TambahBerita,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    } else {
                        Toast.makeText(
                            this@TambahBerita,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<TambahResponse>, t: Throwable) {
                    Toast.makeText(
                        this@TambahBerita,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                })
        }
}



















