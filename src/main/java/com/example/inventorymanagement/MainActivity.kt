package com.example.inventorymanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btnN : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnN = findViewById<Button>(R.id.button)

        btnN.setOnClickListener{
            val i = Intent (this, MainActivity2::class.java)
            startActivity(i)
        }
    }
}