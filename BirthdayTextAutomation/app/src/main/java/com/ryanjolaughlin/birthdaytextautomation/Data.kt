package com.ryanjolaughlin.birthdaytextautomation

import android.content.ContentResolver
import com.ryanjolaughlin.birthdaytextautomation.model.Contact
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Data {
  lateinit var allContacts: ArrayList<Contact>
  var upcomingBirthdays = ArrayList<Contact>()

  fun loadContacts(cr: ContentResolver) {
    // query contact contract
    allContacts = Contacts.getAllContacts(cr)

    allContacts = ArrayList(allContacts.sortedWith(compareBy{ it.birthday }))

    allContacts.forEach{
      if (it.birthday.isNotEmpty()) {
        val tokens = it.birthday.split("-")
        it.birthday = tokens[tokens.size - 2] + '/' + tokens[tokens.size - 1]
      }
    }

    getUpcomingBirthdays()
  }

  fun getUpcomingBirthdays() {
    upcomingBirthdays.clear()
    val sdf = SimpleDateFormat("MM/dd")
    val currentDate = sdf.format(Date())

    var nextIndex = allContacts.indexOfFirst { it.birthday >= currentDate }

    if (nextIndex == -1) {
      nextIndex = allContacts.indexOfFirst { it.birthday >= "01/01"}
    }

    if (allContacts.size < 4) {
      upcomingBirthdays = allContacts
    }

    for (i in 0 until 4) {
      upcomingBirthdays.add(allContacts[nextIndex % allContacts.size])
      nextIndex++
    }
  }
}