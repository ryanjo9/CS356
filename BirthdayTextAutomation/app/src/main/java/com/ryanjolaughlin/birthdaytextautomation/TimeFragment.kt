package com.ryanjolaughlin.birthdaytextautomation

import android.app.TimePickerDialog
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [TimeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TimeFragment : Fragment() {
  private var enabled = true

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val fragmentView: View = inflater.inflate(R.layout.fragment_time, container, false)

    //#region input time
    val inputTextView: TextView = fragmentView.findViewById(R.id.time_input)

    inputTextView.setOnClickListener {
      val cal = Calendar.getInstance()
      val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        setTime(cal, fragmentView)
      }
      TimePickerDialog(getContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }
    //#endregion

    //#region on off
    val onOffTextView: TextView = fragmentView.findViewById(R.id.on_off)

    onOffTextView.setOnClickListener {
      val statusTextView: TextView = fragmentView.findViewById(R.id.status)

      statusTextView.text = if (this.enabled) getString(R.string.disabled) else getString(R.string.running)
      onOffTextView.setTextColor(if (this.enabled)  ResourcesCompat.getColor(resources, R.color.green, null) else ResourcesCompat.getColor(resources, R.color.red, null))
      onOffTextView.text = if (this.enabled) getString(R.string.enable_action) else getString(R.string.disable_action)

      this.enabled = !this.enabled
    }
    //#endregion

    //#region upcoming birthdays

    Data.upcomingBirthdays.forEachIndexed { index, contact ->
      // Image
      val v : View = View.inflate(context, R.layout.contact, null)
      val upcomingPhoto : ImageView = v.findViewById(R.id.contact_photo)
      upcomingPhoto.setImageURI(Uri.parse(contact.photoUri))

      if(upcomingPhoto.drawable == null) upcomingPhoto.setImageResource(R.drawable.ic_tag_faces_black_24dp);

      val nameView : TextView = v.findViewById(R.id.upcoming_name)
      nameView.text = contact.firstName +  " " + contact.lastName

      val dateView : TextView = v.findViewById(R.id.upcoming_date)
      dateView.text = contact.birthday
      dateView.setTextColor(Color.parseColor("#000000"))

      val switchView : Switch = v.findViewById(R.id.contact_switch)
      switchView.isChecked = contact.enabled

      val list : LinearLayout = fragmentView.findViewById(R.id.upcoming_birthdays_list)
      list.addView(v, index)
    }

    return fragmentView
  }

  private fun setTime(cal: Calendar, view: View) {
    val inputTextView: TextView = view.findViewById(R.id.time_input)
    inputTextView.text = SimpleDateFormat("HH:mm").format(cal.time)
    // TODO: Change time that alarm is scheduled for
  }

  companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    @JvmStatic
    fun newInstance(): TimeFragment {
      return TimeFragment()
    }
  }
}
