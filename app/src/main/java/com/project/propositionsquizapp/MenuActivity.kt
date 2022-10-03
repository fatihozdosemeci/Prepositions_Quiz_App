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

        Constants.totalQuestions = seekBarProcess
        Constants.time = seekBarProcess2

    }

    fun startQuiz(view: View){
        val intent = Intent(this, QuizQuestionsActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun alertSetting(view: View){
        var yeni = LayoutInflater.from(this).inflate(R.layout.activity_setting,null)
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setView(yeni)
        alertDialog.show()

        var seekBarText: TextView = yeni.findViewById(R.id.seekBarText)
        var seekBar2Text: TextView = yeni.findViewById(R.id.seekBar2Text)
        val volumeSeekBar: SeekBar = yeni.findViewById(R.id.seekBar)
        val volumeSeekBar2: SeekBar = yeni.findViewById(R.id.seekBar2)
        volumeSeekBar.progressDrawable.setColorFilter(Color.RED,android.graphics.PorterDuff.Mode.SRC_IN)
        volumeSeekBar2.progressDrawable.setColorFilter(Color.RED,android.graphics.PorterDuff.Mode.SRC_IN)

        volumeSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBarText.text = volumeSeekBar.progress.toString()
                Constants.totalQuestions =  volumeSeekBar.progress
                seekBarProcess = volumeSeekBar.progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                if(volumeSeekBar!= null){
                }
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                if(volumeSeekBar!= null){
                }

            }
        })
        volumeSeekBar2.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar2Text.text = volumeSeekBar2.progress.toString()
                Constants.time =  volumeSeekBar2.progress
                seekBarProcess2 = volumeSeekBar2.progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                if(volumeSeekBar!= null){
                }
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                if(volumeSeekBar!= null){
                }

            }
        })
    }
}