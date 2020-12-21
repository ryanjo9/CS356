package com.ryanjolaughlin.birthdaytextautomation.texting

import android.app.job.JobParameters
import android.app.job.JobService
import kotlinx.coroutines.*

class TextService : JobService() {
  override fun onStartJob(p0: JobParameters?): Boolean {
    runBackGroundWork(p0)
    return false
  }

  private fun runBackGroundWork(p0: JobParameters?) {
    Thread(Runnable {
      TextBusiness(contentResolver).run()
    }).start()
  }

  override fun onStopJob(p0: JobParameters?): Boolean {
    return false
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}