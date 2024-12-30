package com.haura.detail_produk

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.haura.detail_produk.api.ApiClient
import com.haura.detail_produk.model.RegisterRequest
import com.haura.detail_produk.model.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Tag
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private val storagePermission = 101

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnRegister = findViewById(R.id.btnSignUp)
        btnLogin = findViewById(R.id.btnLogin)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkStoragePermission()

        btnRegister.setOnClickListener{
            startActivity(Intent(this,  RegisterActivity::class.java))
        }
        btnLogin.setOnClickListener{
            startActivity(Intent(this,  LoginActivity::class.java))
        }
        }

    private fun checkStoragePermission(){
        if (ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                storagePermission
            )

        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }
    }
}













