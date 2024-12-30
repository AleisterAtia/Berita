package com.haura.detail_produk

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
import com.haura.detail_produk.LoginActivity
import com.haura.detail_produk.MainActivity
import com.haura.detail_produk.R
import com.haura.detail_produk.api.ApiClient
import com.haura.detail_produk.model.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var etUsername : EditText
    private lateinit var etPassword : EditText
    private lateinit var etFullname : EditText
    private lateinit var etEmail : EditText
    private lateinit var btnSignUp : Button
    private lateinit var progressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etFullname = findViewById(R.id.etFullname)
        etEmail = findViewById(R.id.etEmail)
        btnSignUp = findViewById(R.id.btnSignUp)
        progressBar = findViewById(R.id.progressBar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnSignUp.setOnClickListener {
            prosesRegister()
        }
    }

    //Method Proses Register
    fun prosesRegister(){
        progressBar.visibility = View.VISIBLE
        ApiClient.apiService.register(
            etUsername.text.toString(),
            etPassword.text.toString(),
            etFullname.text.toString(),
            etEmail.text.toString()
        ).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(response.isSuccessful){
                    //jika berhasil menambahkan data user
                    //kondisi true succsess
                    if (response.body()!!.success){
                        //arahkan ke  loginActivity
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        Toast.makeText(
                            this@RegisterActivity,
                            response.body()!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    } //kondisi false => success

                    else {
                        Toast.makeText(
                            this@RegisterActivity,
                            response.body()!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
                //response berhasil
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                //response gagal
                Toast.makeText(this@RegisterActivity,t.message,
                    Toast.LENGTH_SHORT).show()

                progressBar.visibility = View.GONE
            }
            })
        }

}
