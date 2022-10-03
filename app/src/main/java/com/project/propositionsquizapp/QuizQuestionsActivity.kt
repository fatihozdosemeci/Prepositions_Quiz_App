package com.project.propositionsquizapp

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Handler
import kotlin.collections.ArrayList

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 0
    private var questionList: ArrayList<Int> = ArrayList()


    private var mSelectedOptionPosition : Int = 0
    private var userName: String = ""
    private var totalCorrectAnswer: Int = 0

    var randomForQuestion = 1
    val context : Context = this


    val questions: ArrayList<Question> = ArrayList()

    private var progressBar : ProgressBar? = null
    private var progressText : TextView? = null
    private var questionText: TextView? = null
    private var optionOne: TextView? = null
    private var optionTwo: TextView? = null
    private var optionThree: TextView? = null
    private var optionFour: TextView? = null
    private var correctOption : TextView? = null
    private var numOfQuestion = 5

    lateinit var mAdView : AdView
    var mInterstitialAd: InterstitialAd? = null
    var TAG = "QuizQuestionActivity"

    //var sharedPreferences: SharedPreferences? = null

    var mCountDownTimer: CountDownTimer? = null

    /*var mCountDownTimer: CountDownTimer = object : CountDownTimer(10800, 25){
        override fun onTick(p0: Long) {
            TODO("Not yet implemented")
        }

        override fun onFinish() {
            TODO("Not yet implemented")
        }
    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)


        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progress)
        questionText = findViewById(R.id.questionText)
        optionOne = findViewById(R.id.optionOne)
        optionTwo = findViewById(R.id.optionTwo)
        optionThree = findViewById(R.id.optionThree)
        optionFour = findViewById(R.id.optionFour)

        optionOne?.setOnClickListener(this)
        optionTwo?.setOnClickListener(this)
        optionThree?.setOnClickListener(this)
        optionFour?.setOnClickListener(this)

        numOfQuestion = Constants.totalQuestions
        progressBar?.max = Constants.totalQuestions

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        var adRequest2 = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-6441311043049397/1909993521", adRequest2, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(ContentValues.TAG, adError?.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(ContentValues.TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })

        val sharedPreferences: SharedPreferences = getSharedPreferences("adsCounter",Context.MODE_PRIVATE)
        Constants.adsCounter = sharedPreferences.getInt("adsCounter",0)

        try {

            val obj = JSONObject(getJSONFromAssets())
            val questionArray = obj.getJSONArray("questions")

            for (x in 0 until questionArray.length()) {
                val user = questionArray.getJSONObject(x)
                val question = user.getString("question")
                val answer = user.getString("answer")

                val userDetails = Question(question, answer)

                questions.add(userDetails)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }



        setQuestion()

    }


    private fun selectedOptionView(tv:TextView, context : Context){

        mCountDownTimer?.cancel()
        if(tv == correctOption){

            tv.typeface = Typeface.DEFAULT
            tv.background = ContextCompat.getDrawable(context,R.drawable.correct_answer_border)
            totalCorrectAnswer++
        }else{

            tv.typeface = Typeface.DEFAULT
            tv.background = ContextCompat.getDrawable(this, R.drawable.false_answer_border)
            this.correctOption?.typeface = Typeface.DEFAULT
            this.correctOption?.background = ContextCompat.getDrawable(this, R.drawable.correct_answer_border)
        }
        disableOnClick()
        ThreadUtil.startThread{
            Thread.sleep(1500)
            ThreadUtil.startUIThread(0){

                if( mCurrentPosition < numOfQuestion ) {
                    setQuestion()
                }
                else {
                    val sharedPreferences = this.getSharedPreferences("adsCounter",Context.MODE_PRIVATE)
                    Constants.adsCounter++
                    val editor = sharedPreferences.edit()
                    editor.putInt("adsCounter",Constants.adsCounter)
                    editor.apply()
                    val intent = Intent(this, ResultActivity::class.java)
                    //intent.putExtra(Constants.userName, userName)
                    Constants.totalCorrectAnswer = this.totalCorrectAnswer
                    Constants.totalQuestions= numOfQuestion
                    startActivity(intent)
                    if(Constants.adsCounter == 3){
                    if (mInterstitialAd != null) {
                        mInterstitialAd?.show(this)
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.")
                    }
                    Constants.adsCounter = 0
                    editor.putInt("adsCounter",Constants.adsCounter)
                    editor.apply()
                    }
                    finish()
                }

            }}

    }



    private fun setQuestion() {
        defaultOptionsView()


        mCurrentPosition++
        progressBar?.progress = mCurrentPosition
        progressText?.text = "$mCurrentPosition/${progressBar?.max}"




        val questionListSize = questions.size - 1
        var option: TextView? = null
        Constants.totalQuestions = questions.size



        while(true){
            randomForQuestion = (0..questionListSize).random()
            if(!(questionList.contains(randomForQuestion)))
            {
                questionList.add(randomForQuestion)
                Constants.questionList.add(questions[randomForQuestion].question)
                break
            }

        }
        val optionArray = listOf(questions[randomForQuestion].answer).toMutableList()
        var i: Int = 1

        while (i < 5) {
            var randomForOption = (0..questionListSize).random()
            if (!optionArray.contains(questions[randomForOption].answer)) {
                optionArray.add(questions[randomForOption].answer)

                when (i) {
                    1 -> { option = optionOne
                        Constants.optionOneList.add(questions[randomForOption].answer)
                        }
                    2 -> { option = optionTwo
                        Constants.optionTwoList.add(questions[randomForOption].answer)
                        }
                    3 -> { option = optionThree
                        Constants.optionThreeList.add(questions[randomForOption].answer)
                        }
                    4 -> { option = optionFour
                        Constants.optionFourList.add(questions[randomForOption].answer)
                        }
                }
                option?.text = questions[randomForOption].answer
                i++
            }
        }


        var randomForAnswer = (1..4).random()
        when (randomForAnswer) {
            1 -> correctOption = optionOne
            2 -> correctOption = optionTwo
            3 -> correctOption = optionThree
            4 -> correctOption = optionFour
        }
        when (randomForAnswer){
            1 -> {
                Constants.optionOneList.removeAt(mCurrentPosition-1)
                Constants.optionOneList.add(questions[randomForQuestion].answer)
                Constants.correctAnswersList.add(1)
            }
            2 ->{
                Constants.optionTwoList.removeAt(mCurrentPosition-1)
                Constants.optionTwoList.add(questions[randomForQuestion].answer)
                Constants.correctAnswersList.add(2)
            }
            3 ->{
                Constants.optionThreeList.removeAt(mCurrentPosition-1)
                Constants.optionThreeList.add(questions[randomForQuestion].answer)
                Constants.correctAnswersList.add(3)
            }
            4 ->{
                Constants.optionFourList.removeAt(mCurrentPosition-1)
                Constants.optionFourList.add(questions[randomForQuestion].answer)
                Constants.correctAnswersList.add(4)
            }
        }

        questionText?.text = questions[randomForQuestion].question
        correctOption?.text = questions[randomForQuestion].answer


        var progress: Int = 0

        val mProgressBar: ProgressBar = findViewById(R.id.progressBarTime)
        mProgressBar.progressDrawable.setColorFilter(Color.RED,android.graphics.PorterDuff.Mode.SRC_IN)
        progressBar?.progressDrawable?.setColorFilter(Color.RED,android.graphics.PorterDuff.Mode.SRC_IN)
        mProgressBar.progress = progress
        val time = (Constants.time * 1000 ) + 700
         mCountDownTimer = object : CountDownTimer(time.toLong(), (Constants.time* 2.5).toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                progress++
                mProgressBar.progress = progress // as Int * 100 / (5000 / 50)
            }

            override fun onFinish() {
                Constants.selectedOptionList.add(0)
                correctOption.let {
                    if (it != null) {
                        selectedOptionView(it,context)
                        totalCorrectAnswer--
                    }
                }
            }
        }
        mCountDownTimer?.start()

    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        optionOne?.let {
            options.add(0,it)
        }
        optionTwo?.let {
            options.add(1,it)
        }
        optionThree?.let {
            options.add(2,it)
        }
        optionFour?.let {
            options.add(3,it)
        }
        for(option in options){
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)

        }
        enableOnClick()


    }

    fun getJSONFromAssets(): String? {

        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val myUsersJSONFile = assets.open("prepositions.json")
            val size = myUsersJSONFile.available()
            val buffer = ByteArray(size)
            myUsersJSONFile.read(buffer)
            myUsersJSONFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


    override fun onClick(view: View?) {
        when(view?.id){
            R.id.optionOne -> {
                optionOne?.let {
                    Constants.selectedOptionList.add(1)
                    selectedOptionView(it,this)
                }
            }
            R.id.optionTwo -> {
                optionTwo?.let {
                    Constants.selectedOptionList.add(2)
                    selectedOptionView(it,this)
                }
            }
            R.id.optionThree -> {
                optionThree?.let {
                    Constants.selectedOptionList.add(3)
                    selectedOptionView(it,this)
                }
            }
            R.id.optionFour -> {
                optionFour?.let {
                    Constants.selectedOptionList.add(4)
                    selectedOptionView(it,this)
                }
            }
        }

    }

    fun enableOnClick(){
        optionOne?.isEnabled = true
        optionTwo?.isEnabled = true
        optionThree?.isEnabled = true
        optionFour?.isEnabled = true
    }

    fun disableOnClick(){
        optionOne?.isEnabled = false
        optionTwo?.isEnabled = false
        optionThree?.isEnabled =false
        optionFour?.isEnabled = false
    }


}