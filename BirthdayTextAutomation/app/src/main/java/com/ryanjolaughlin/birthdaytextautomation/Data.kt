package com.ryanjolaughlin.birthdaytextautomation

import android.content.ContentResolver
import com.ryanjolaughlin.birthdaytextautomation.device.Contacts
import com.ryanjolaughlin.birthdaytextautomation.model.Contact
import com.ryanjolaughlin.birthdaytextautomation.model.Enabled
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min
import com.ryanjolaughlin.birthdaytextautomation.dao.AppDatabase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.collections.HashMap

object Data {
  var contactsMap : MutableMap<String, Contact> = HashMap()
  var nameSorted = ArrayList<String>()
  var upcomingBirthdays = ArrayList<String>()
  var numEnabled = 0
  var lastUpdated = Date()
  var contactsRecyclerPosition = 0
  lateinit var db : AppDatabase

  private fun fetchContactData(cr: ContentResolver): HashMap<String, Contact> = runBlocking {
    val contactsMap: Deferred<HashMap<String, Contact>> = async {
      Contacts.getAllContacts(cr)
    }

    val enabledIds: Deferred<List<Enabled>> = async {
      db.enabledDao().loadAll()
    }

    mergeData(contactsMap.await(), enabledIds.await())
  }

  private fun mergeData(map: HashMap<String, Contact>, enabledIds: List<Enabled>): HashMap<String, Contact> {
    enabledIds.forEach { it ->
      map[it.id]!!.enabled = true
    }
    return map
  }

  fun loadContacts(cr: ContentResolver) {
    // query contact contract
    numEnabled = 0
    contactsMap = fetchContactData(cr)

    contactsMap.values.sortedWith(compareBy{ it.birthday }).map{
      if (it.birthday.isNotEmpty()) {
        val tokens = it.birthday.split("-")
        it.birthday = tokens[tokens.size - 2] + '/' + tokens[tokens.size - 1]

        if (it.enabled) numEnabled += 1
      } else {
        contactsMap[it.id]!!.enabled = false
      }
    }

    nameSorted = ArrayList(contactsMap.values.sortedWith(compareBy{ it.firstName }).map{ it.id })

    getUpcomingBirthdays()

    lastUpdated = Date()
    contactsRecyclerPosition = min(contactsRecyclerPosition, contactsMap.size-1)
  }

  private fun getUpcomingBirthdays() {
    upcomingBirthdays.clear()
    val sdf = SimpleDateFormat("MM/dd")
    val currentDate = sdf.format(Date())

    val sortedBirthdays: ArrayList<Contact> = ArrayList(contactsMap.values.sortedWith(compareBy{ it.birthday }))
    var nextIndex = sortedBirthdays.indexOfFirst { it.birthday >= currentDate }

    if (nextIndex == -1) {
      nextIndex = sortedBirthdays.indexOfFirst { it.birthday >= "01/01"}
    }

    var i = 0
    while (i < 4 && i < contactsMap.size) {
      upcomingBirthdays.add(sortedBirthdays[nextIndex % contactsMap.size].id)
      nextIndex++
      i++
    }
  }

}