package com.mess.service_co

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val button3: Button = findViewById(R.id.workerbtn)
        button3.setOnClickListener {
            val intent = Intent(Welcome@ this,Worker::class.java)
            startActivity(intent)
        }

        val button4: Button = findViewById(R.id.userbtn)
        button4.setOnClickListener {
            val intent = Intent(Welcome@ this,Home::class.java)
            startActivity(intent)
        }
    }
}