package com.ryanjolaughlin.birthdaytextautomation

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
  private var enabled : Boolean = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    while (ContextCompat.checkSelfPermission(this,
        Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {

      ActivityCompat.requestPermissions(this,
        arrayOf(Manifest.permission.READ_CONTACTS),
        1)
      println("\n\n\nPermission not granted\n\n\n")
    }

    Data.loadContacts(contentResolver)

    //#region input time
    val inputTextView: TextView = findViewById(R.id.time_input)

    inputTextView.setOnClickListener {
     val cal = Calendar.getInstance()
     val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
       cal.set(Calendar.HOUR_OF_DAY, hour)
       cal.set(Calendar.MINUTE, minute)
       setTime(cal)
     }
     TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }
    //#endregion

    //#region on off
    val onOffTextView: TextView = findViewById(R.id.on_off)

    onOffTextView.setOnClickListener {
      val statusTextView: TextView = findViewById(R.id.status)

      statusTextView.text = if (this.enabled) getString(R.string.disabled) else getString(R.string.running)
      onOffTextView.setTextColor(if (this.enabled) getColor(R.color.green) else getColor(R.color.red))
      onOffTextView.text = if (this.enabled) getString(R.string.enable_action) else getString(R.string.disable_action)

      this.enabled = !this.enabled
    }
    //#endregion

    //#region upcoming birthdays

    Data.upcomingBirthdays.forEachIndexed { index, contact ->
      // Image
      val v : View = View.inflate(this, R.layout.contact, null)
      val upcomingPhoto : ImageView  = v.findViewById(R.id.contact_photo)
      upcomingPhoto.setImageURI(Uri.parse(contact.photoUri))

      if(upcomingPhoto.drawable == null) upcomingPhoto.setImageResource(R.drawable.ic_tag_faces_black_24dp);

      val nameView : TextView = v.findViewById(R.id.upcoming_name)
      nameView.text = contact.firstName +  " " + contact.lastName

      val dateView : TextView = v.findViewById(R.id.upcoming_date)
      dateView.text = contact.birthday
      dateView.setTextColor(Color.parseColor("#000000"))

      val switchView : Switch = v.findViewById(R.id.contact_switch)
      switchView.isChecked = contact.enabled

      val list : LinearLayout = findViewById(R.id.upcoming_birthdays_list)
      list.addView(v, index)
    }

    Data.upcomingBirthdays.forEach { println(it)}



  }

  private fun setTime(cal: Calendar) {
    val inputTextView: TextView = findViewById(R.id.time_input)
    inputTextView.text = SimpleDateFormat("HH:mm").format(cal.time)
    // TODO: Change time that alarm is scheduled for
  }
}
