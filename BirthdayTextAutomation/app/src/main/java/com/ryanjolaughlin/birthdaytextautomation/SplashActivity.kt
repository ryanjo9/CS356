package com.ryanjolaughlin.birthdaytextautomation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.ryanjolaughlin.birthdaytextautomation.dao.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    CoroutineScope(IO).launch{
      Data.db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "enabled"
      ).build()

      Data.loadContacts(contentResolver)

      startMain()
    }

  }

  private suspend fun startMain() {
    withContext(Main) {
      startActivity(Intent(applicationContext,MainActivity::class.java))
      finish()
    }
  }
}
