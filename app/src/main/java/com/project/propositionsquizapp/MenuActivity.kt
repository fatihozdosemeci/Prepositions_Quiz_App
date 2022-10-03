package com.project.propositionsquizapp

import android.content.Intent
import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MenuActivity : AppCompatActivity() {

    var seekBarProcess: Int = 5
    var seekBarProcess2 : Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val imageView: ImageView = findViewById(R.id.imageView4)
        imageView.setImageResource(R.drawable.start_quiz)

        Constants.totalQuestions = 5
        Constants.time = 10

    }

    fun startQuiz(view: View){
        val intent = Intent(this, QuizQuestionsActivity::class.java)
        startActivity(intent)
        finish()
    }
}