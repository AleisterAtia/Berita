package com.haura.detail_produk

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.haura.detail_produk.R
import com.haura.detail_produk.api.ApiClient
import com.haura.detail_produk.model.BeritaResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BeritaAdapter(
    val dataBerita: ArrayList<BeritaResponse.ListItem>

) : RecyclerView.Adapter<BeritaAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgBerita = view.findViewById<ImageView>(R.id.imgBerita)
        val tvJudul = view.findViewById<TextView>(R.id.tvJudul)
        val tvTglBerita = view.findViewById<TextView>(R.id.tvTglBerita)
        val tvRating = view.findViewById<TextView>(R.id.tvrating)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_news,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataBerita.size
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val hasilResponse = dataBerita[position]
        Picasso.get().load(hasilResponse.gambar).into(holder.imgBerita)
        holder.tvJudul.text = hasilResponse.judul
        holder.tvTglBerita.text = hasilResponse.tgl_indonesia_berita
        holder.tvRating.text = "${hasilResponse.rating}"
        holder.ratingBar.rating = hasilResponse.rating

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,DetailBeritaActivity::class.java).apply {
                putExtra("gambar",hasilResponse.gambar)
                putExtra("judul",hasilResponse.judul)
                putExtra("tgl_berita",hasilResponse.tgl_indonesia_berita)
                putExtra("rating",hasilResponse.rating)
                putExtra("isi_berita",hasilResponse.isi)

            }
            holder.imgBerita.context.startActivity(intent)
        }
        //remove item
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Konfirmasi")
                setMessage("Apakah anda ingin melanjutkan?")
                setIcon(R.drawable.ic_delete)

                setPositiveButton("Yakin") { dialogInterface, i ->
                    ApiClient.apiService.delBerita(hasilResponse.id)
                        .enqueue(object : Callback<BeritaResponse> {
                            override fun onResponse(
                                call: Call<BeritaResponse>,
                                response: Response<BeritaResponse>
                            ) {
                                if (response.body()!!.succes) {
                                    removeItem(position)
                                } else {
                                    Toast.makeText(
                                        holder.itemView.context,
                                        response.body()!!.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }

                            override fun onFailure(call: Call<BeritaResponse>, t: Throwable) {
                                Toast.makeText(
                                    holder.itemView.context,
                                    "Ada Kesalahan Server",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        })
                    dialogInterface.dismiss()
                }

                setNegativeButton("Batal") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
            }.show()

            true
        }

    }

    fun removeItem(position: Int) {
        dataBerita.removeAt(position)
        notifyItemRemoved(position) // Notify the position of the removed item
        notifyItemRangeChanged(position, dataBerita.size - position) // Optional: Adjust for index shifts



    }

    fun setData(data: List<BeritaResponse.ListItem>){
        dataBerita.clear()
        dataBerita.addAll(data)
    }
}