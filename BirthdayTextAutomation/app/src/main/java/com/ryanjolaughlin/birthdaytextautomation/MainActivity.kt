package com.ryanjolaughlin.birthdaytextautomation

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.room.Room
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ryanjolaughlin.birthdaytextautomation.dao.AppDatabase



class MainActivity : AppCompatActivity() {
  private val EDIT_REQUEST_CODE = 4

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

//    val address = DebugDB.getAddressLog()
//    println("Address " + address)

    val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

    val viewPager = findViewById<ViewPager2>(R.id.pager)
    viewPager.adapter = object : FragmentStateAdapter(this) {
      override fun createFragment(position: Int): Fragment {
        TabLayoutMediator(tabLayout, viewPager) { tab, i ->
          tab.text = if (i == 0) "Home" else "Contacts"
//          tab.setIcon(if (i == 0) R.drawable.ic_schedule_white_24dp else R.drawable.ic_contacts_white_24dp)
        }.attach()
        return if (position == 0) TimeFragment.newInstance() else ContactsFragment.newInstance()
      }

      override fun getItemCount(): Int = 2
    }

    tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
      override fun onTabSelected(tab: TabLayout.Tab) {
        viewPager!!.currentItem = tab.position
      }
      override fun onTabUnselected(tab: TabLayout.Tab) {

      }
      override fun onTabReselected(tab: TabLayout.Tab) {
        viewPager!!.currentItem = tab.position
      }
    })

    while (ContextCompat.checkSelfPermission(this,
        Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {

      ActivityCompat.requestPermissions(this,
        arrayOf(Manifest.permission.READ_CONTACTS),
        1)
      println("\n\n\nPermission not granted\n\n\n")
    }

    Data.db = Room.databaseBuilder(
      this.applicationContext,
      AppDatabase::class.java, "enabled"
    ).allowMainThreadQueries().build()

    val enabledIds = Data.db.enabledDao().loadAll()

    Data.loadContacts(contentResolver, enabledIds)
  }

  public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
        EDIT_REQUEST_CODE->{
          val enabledIds = Data.db.enabledDao().loadAll()
          Data.loadContacts(contentResolver, enabledIds)
//          supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.main_content, ContactsFragment.newInstance(), "contacts")
//            .commit()
        }
    }

  }
}
