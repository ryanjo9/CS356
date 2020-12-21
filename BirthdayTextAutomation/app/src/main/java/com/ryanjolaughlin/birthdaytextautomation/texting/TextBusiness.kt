package com.ryanjolaughlin.birthdaytextautomation.texting

import android.content.ContentResolver
import android.telephony.SmsManager
import com.ryanjolaughlin.birthdaytextautomation.Data
import com.ryanjolaughlin.birthdaytextautomation.model.Contact
import java.text.SimpleDateFormat
import java.util.*


class TextBusiness(cr : ContentResolver) {

  val cr = cr
  val smsManager =  SmsManager.getDefault()


  fun run() {
    Data.loadContacts(cr)

    val contacts = Data.contactsMap
    val sdf = SimpleDateFormat("MM/dd")
    val currentDate = sdf.format(Date())

    val birthdays = contacts.values.filter { it.enabled && (it.birthday.equals(currentDate)) }
    for (contact in birthdays) {
      val number = getPhoneNumber(contact)
      print(contact.firstName + " " + contact.lastName + ": " + number)
      val message = "Happy Birthday, " + contact.firstName + " " + contact.lastName + "!"
      if (number != null) {
        smsManager.sendTextMessage("+1-571-290-1731", null, message, null, null)
      }
    }
  }

  fun getPhoneNumber(contact : Contact) : String? {
    if (contact.mobile != null) {
      return contact.mobile
    }

    if (contact.other != null) {
      return contact.other
    }

    if (contact.home != null) {
      return contact.home
    }

    if (contact.work != null) {
      return contact.work
    }

    return null
  }
}