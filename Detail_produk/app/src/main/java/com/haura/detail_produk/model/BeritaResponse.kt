package com.haura.detail_produk.model

import android.graphics.pdf.models.ListItem

data class BeritaResponse(
    val succes: Boolean,
    val message: String,
    val data: ArrayList<ListItem>
){
    data class ListItem(
        val id: String,
        val judul: String,
        val isi: String,
        val tgl_indonesia_berita: String,
        val gambar: String,
        val rating: Float,
    )
}
