package com.project.propositionsquizapp

import android.content.SharedPreferences

object Constants {
    var userName : String = ""
    var totalQuestions: Int = 0
    var totalCorrectAnswer: Int = 0
    var questionList: ArrayList<String> = ArrayList()
    var optionOneList: ArrayList<String> = ArrayList()
    var optionTwoList: ArrayList<String> = ArrayList()
    var optionThreeList: ArrayList<String> = ArrayList()
    var optionFourList: ArrayList<String> = ArrayList()
    var correctAnswersList: ArrayList<Int> = ArrayList()
    var selectedOptionList: ArrayList<Int> = ArrayList()
    var adsCounter : Int = 0
    var time : Int = 10
}