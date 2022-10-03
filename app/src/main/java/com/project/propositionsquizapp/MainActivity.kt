package com.project.propositionsquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val buttonStart : Button = findViewById(R.id.buttonStart)
        val textName : EditText = findViewById(R.id.textName)



        // MobileAds.initialize(this) {}
        // mAdView = findViewById(R.id.adView)
        // val adRequest = AdRequest.Builder().build()
        // mAdView.loadAd(adRequest)

        buttonStart.setOnClickListener {
            if(textName.text.isEmpty()){
                Toast.makeText(this,"Please enter your name", Toast.LENGTH_LONG).show()
            }else{
                Constants.userName = textName.text.toString()
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}