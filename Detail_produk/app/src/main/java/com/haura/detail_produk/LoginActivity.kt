package com.haura.detail_produk

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.haura.detail_produk.api.ApiClient
import com.haura.detail_produk.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnlogin)
        progressBar = findViewById(R.id.progressBar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnLogin.setOnClickListener{
            prosesLogin()
        }
    }

    private fun prosesLogin(){
        progressBar.visibility = View.VISIBLE
        ApiClient.apiService.login(
            etUsername.text.toString(),
            etPassword.text.toString()
        ).enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    if (response.body()!!.succes){
                        //Arahkan ke halaman dashboard
                        startActivity(Intent(this@LoginActivity,DashboardActivity::class.java))
                    } else {
                        Toast.makeText(this@LoginActivity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity,t.message,Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
            }
        })

    }

}
