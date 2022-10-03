package com.project.propositionsquizapp

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.facebook.ads.*
import com.google.android.gms.ads.AdRequest

import com.google.android.gms.ads.MobileAds
import com.google.android.gms.common.internal.Objects
import com.google.android.material.card.MaterialCardView
import org.w3c.dom.Text

class QuestionsResult : AppCompatActivity() {

    var i = 0
    lateinit var mAdView2 : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_result)

        val layout: LinearLayout = findViewById(R.id.banner_wrapper)
        AudienceNetworkAds.initialize(this)
        //mAdView2 = findViewById(R.id.adView2)
        //val adRequest = AdRequest.Builder().build()
        val adView = AdView(this@QuestionsResult,"806384263883552_806390543882924", AdSize.BANNER_HEIGHT_50)
        layout.addView(adView)
        //mAdView2.loadAd()
        val config = adView.buildLoadAdConfig().withAdListener(object : AdListener {
            override fun onError(p0: Ad?, p1: AdError?) {
                Toast.makeText(this@QuestionsResult,"Banner ${p1?.errorMessage}",Toast.LENGTH_LONG).show()
            }

            override fun onAdLoaded(p0: Ad?) {
                TODO("Not yet implemented")
            }

            override fun onAdClicked(p0: Ad?) {
                TODO("Not yet implemented")
            }

            override fun onLoggingImpression(p0: Ad?) {
                TODO("Not yet implemented")
            }

        }).build()
        adView.loadAd(config)

        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(width, height)
        layoutParams.setMargins(60,30,60,30)
        val layoutParamsText: LinearLayout.LayoutParams = LinearLayout.LayoutParams(width, height)
        layoutParamsText.setMargins(100,15,100,15)
        val layoutParamsForQuestion: LinearLayout.LayoutParams = LinearLayout.LayoutParams(width, height)
        layoutParamsForQuestion.setMargins(5,15,5,15)

        val linearLayout: LinearLayout = findViewById(R.id.linearLayout)
        val shape = GradientDrawable()
        shape.setStroke(5,Color.parseColor("#CC1010"))
        shape.cornerRadius = 50f
        shape.setColor(Color.parseColor("#D7D7D7"))

        while(i < Constants.totalQuestions) {
            val cardView: MaterialCardView = MaterialCardView(this)


            cardView.layoutParams = layoutParams
            cardView.background = shape

            val linearLayoutCard: LinearLayout = LinearLayout(this)
            linearLayoutCard.layoutParams = layoutParams
            linearLayoutCard.gravity = Gravity.CENTER_VERTICAL
            linearLayoutCard.orientation = LinearLayout.VERTICAL



            for (x in 0..4) {
                val name = TextView(this)
                when(x){
                    0 -> name.text = Constants.questionList[i]
                    1 -> name.text = Constants.optionOneList[i]
                    2 -> name.text = Constants.optionTwoList[i]
                    3 -> name.text = Constants.optionThreeList[i]
                    4 -> name.text = Constants.optionFourList[i]
                }

                if(x==0){
                    name.layoutParams = layoutParamsForQuestion
                    name.textSize = 18f
                }
                else {
                    name.layoutParams = layoutParamsText
                    name.textSize = 15f
                }
                name.background = ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)

                if(Constants.selectedOptionList[i] == x){
                    name.background = ContextCompat.getDrawable(this, R.drawable.false_answer_border)
                }
                if(Constants.correctAnswersList[i] == x) {
                    name.background = ContextCompat.getDrawable(this, R.drawable.correct_answer_border)
                }

                name.gravity = Gravity.CENTER
                name.setPadding(15,15,15,15)
                name.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
                name.setTextColor(Color.parseColor("#000000"))
                linearLayoutCard.addView(name)

            }



            cardView.addView(linearLayoutCard)
            linearLayout.addView(cardView)
            i++

        }



    }
    fun returnBack(view: View){
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun returnMenu(view: View)
    {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}