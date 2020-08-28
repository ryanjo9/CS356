package com.ryanjolaughlin.birthdaytextautomation.device

import android.provider.ContactsContract
import com.ryanjolaughlin.birthdaytextautomation.model.Contact
import android.content.ContentResolver
import android.provider.ContactsContract.CommonDataKinds
import android.provider.ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY
import android.content.ContentUris

object Contacts {
  fun getAllContacts(cr: ContentResolver) : HashMap<String, Contact> {
    val contacts = HashMap<String, Contact>()

    //#region get phone numbers
    val phoneProjection = arrayOf(
      ContactsContract.RawContacts._ID,
      ContactsContract.Contacts.DISPLAY_NAME,
      CommonDataKinds.Phone.PHOTO_URI,
      CommonDataKinds.Phone.NUMBER,
      CommonDataKinds.Phone.TYPE,
      CommonDataKinds.Photo.CONTACT_ID
    )

    val uri = CommonDataKinds.Phone.CONTENT_URI
    val filter = "" + ContactsContract.Contacts.HAS_PHONE_NUMBER + " > 0"
    val order = ContactsContract.Contacts.DISPLAY_NAME + " ASC"// LIMIT " + limit + " offset " + lastId + "";

    val phoneCur = cr.query(uri, phoneProjection, filter, null, order)
    while (phoneCur!!.moveToNext()) {
//      val displayName = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
      val con = Contact()
      con.id = phoneCur.getString(phoneCur.getColumnIndex(CommonDataKinds.Photo.CONTACT_ID))
      con.photoUri = ContentUris.withAppendedId(
        ContactsContract.Contacts.CONTENT_URI, con.id.toLong()
      ).toString() + "/" + ContactsContract.Contacts.Photo.CONTENT_DIRECTORY

      if (!contacts.containsKey(con.id)) {
        contacts[con.id] = con
      }


      val phoneNumber = phoneCur.getString(phoneCur.getColumnIndex(CommonDataKinds.Phone.NUMBER))

      when (phoneCur.getInt(phoneCur.getColumnIndex(CommonDataKinds.Phone.TYPE))) {
        CommonDataKinds.Phone.TYPE_HOME -> contacts[con.id]?.home = phoneNumber
        CommonDataKinds.Phone.TYPE_MOBILE -> contacts[con.id]?.mobile = phoneNumber
        CommonDataKinds.Phone.TYPE_WORK -> contacts[con.id]?.work = phoneNumber
        else -> con.other = phoneNumber
      }
    }
    phoneCur.close()
    //#endregion


    //#region get first/last name and birthday
    val whereName = ContactsContract.Data.MIMETYPE + " = ?"
    val whereNameParams = arrayOf(CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)

    val nameCur = cr.query(
      ContactsContract.Data.CONTENT_URI,
      null,
      whereName,
      whereNameParams,
      CommonDataKinds.StructuredName.GIVEN_NAME
    )

    while (nameCur!!.moveToNext()) {
      val given = nameCur.getString(nameCur.getColumnIndex(CommonDataKinds.StructuredName.GIVEN_NAME))
      val family = nameCur.getString(nameCur.getColumnIndex(CommonDataKinds.StructuredName.FAMILY_NAME))
      val nId = nameCur.getString(nameCur.getColumnIndex(CommonDataKinds.Identity.CONTACT_ID))
      val lookupKey = nameCur.getString(nameCur.getColumnIndex(CommonDataKinds.Identity.LOOKUP_KEY))

      var birthday = ""
      val bdc = cr.query(
        ContactsContract.Data.CONTENT_URI,
        arrayOf(CommonDataKinds.Event.DATA),
        ContactsContract.Data.CONTACT_ID + " = " + nId + " AND " + ContactsContract.Data.MIMETYPE + " = '" + CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' AND " + CommonDataKinds.Event.TYPE + " = " + TYPE_BIRTHDAY,
        null,
        ContactsContract.Data.DISPLAY_NAME
      )
      while (bdc!!.moveToNext()) {
        birthday = bdc.getString(0)
      }
      bdc.close()

      contacts[nId]?.lookupKey = lookupKey
      contacts[nId]?.firstName = given
      contacts[nId]?.lastName = if (family != null) family else ""
      contacts[nId]?.birthday = if (birthday.isNotEmpty()) birthday else ""
    }
    nameCur.close()

    return contacts
  }
}