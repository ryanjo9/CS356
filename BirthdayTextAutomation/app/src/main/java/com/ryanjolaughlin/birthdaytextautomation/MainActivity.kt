package com.ryanjolaughlin.birthdaytextautomation

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      supportFragmentManager
        .beginTransaction()
        .add(R.id.main_content, TimeFragment.newInstance(), "time")
        .commit()
    }

    val navView : BottomNavigationView = findViewById(R.id.bottom_navigation)

    navView.selectedItemId = R.id.navigation_time

    navView.setOnNavigationItemSelectedListener {
      when (it.itemId) {
        R.id.navigation_time -> {
          supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_content, TimeFragment.newInstance(), "time")
            .commit()
          true
        }
        R.id.navigation_contacts -> {
          supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_content, ContactsFragment.newInstance(), "contacts")
            .commit()
          true
        }
        R.id.navigation_messages -> {
          true
        }
        else -> false
      }
    }

    while (ContextCompat.checkSelfPermission(this,
        Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {

      ActivityCompat.requestPermissions(this,
        arrayOf(Manifest.permission.READ_CONTACTS),
        1)
      println("\n\n\nPermission not granted\n\n\n")
    }

    Data.loadContacts(contentResolver)
  }
}
