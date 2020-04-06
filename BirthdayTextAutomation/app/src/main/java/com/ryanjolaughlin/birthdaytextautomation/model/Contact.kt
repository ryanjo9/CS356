package com.ryanjolaughlin.birthdaytextautomation.model

data class Contact(var id: String, var firstName: String, var lastName: String,
                   var mobile: String, var home: String, var work: String, var other: String,
                   var enabled: Boolean, var birthday: String, var photoUri: String) {
  constructor(): this("", "", "", "", "", "", "", true, "", "")
}