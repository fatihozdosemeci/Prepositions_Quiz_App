package com.project.propositionsquizapp

import android.os.Looper
import android.os.Handler
import java.lang.reflect.Executable
import java.util.concurrent.Executors

class ThreadUtil {
    companion object{
        private val executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        private var handler = Handler(Looper.getMainLooper())

        fun startThread(runnable: Runnable){
            executorService.submit(runnable)
        }
        fun startUIThread(delayMillis: Int,runnable: Runnable)
        {
            handler.postDelayed(runnable,delayMillis.toLong())
        }
    }
}