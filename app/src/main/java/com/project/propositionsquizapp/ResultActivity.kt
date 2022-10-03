package com.project.propositionsquizapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView


class ResultActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val textName: TextView = findViewById(R.id.textFinal)
        val score: TextView = findViewById(R.id.textScore)
        val menuButton: Button = findViewById(R.id.menuButton)
        val resultsButton : Button = findViewById(R.id.resultButton)

        textName.text = "Congratulations " + Constants.userName
        val totalQuestions = Constants.totalQuestions.toString()
        val correctAnswers = Constants.totalCorrectAnswer

        score.text = "Your Score Is $correctAnswers out of $totalQuestions"

        menuButton.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
        resultsButton.setOnClickListener{
            val intent = Intent(this, QuestionsResult::class.java)
            startActivity(intent)
            finish()
        }



    }
}