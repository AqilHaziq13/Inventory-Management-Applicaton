package com.example.inventorymanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity2 : AppCompatActivity() {

    lateinit var btnA : Button
    lateinit var btnV : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btnA = findViewById<Button>(R.id.button2)
        btnV = findViewById<Button>(R.id.button3)

        btnA.setOnClickListener {
            val i = Intent(this, MainActivity3::class.java)
            startActivity(i)
        }

        btnV.setOnClickListener {
            val i = Intent(this, MainActivity4::class.java)
            startActivity(i)
        }

    }
}