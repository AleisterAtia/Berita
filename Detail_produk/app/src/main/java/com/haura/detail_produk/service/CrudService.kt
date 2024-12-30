package com.haura.detail_produk.service

import com.haura.detail_produk.respon.ResponseBerita
import retrofit2.Call
import retrofit2.http.GET


interface CrudService {
    @GET("getBerita.php")
    fun getAllBerita(): Call<ResponseBerita>
}