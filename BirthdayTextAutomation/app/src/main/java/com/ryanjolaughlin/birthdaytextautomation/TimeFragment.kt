package com.ryanjolaughlin.birthdaytextautomation

import android.app.TimePickerDialog
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Context.JOB_SCHEDULER_SERVICE
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
import androidx.core.view.get
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.ryanjolaughlin.birthdaytextautomation.model.Enabled
import com.ryanjolaughlin.birthdaytextautomation.texting.TextService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception
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
  private lateinit var timePrefixView: TextView
  private lateinit var upcomingList : LinearLayout
  private var includeFirstName : Boolean = false

  private val MS_IN_SECONDS = 1000
  private val SECONDS_IN_MINUTES = 60
  private val MINUTES_IN_HOUR = 60
  private val HOURS_IN_DAY = 24
  private val JOB_ID = 945

  override fun onResume() {
    super.onResume()

    // Saving numEnabled since updating the upcoming birthdays will activate the onCheckedChangedListener
    // Which will double count additions or subtractions
    val savedNumEnabled = Data.numEnabled
    Data.upcomingBirthdays.forEachIndexed { index, s ->
      val switch : Switch = upcomingList[index].findViewById(R.id.contact_switch)

      switch.isChecked = Data.contactsMap[s]!!.enabled
    }
    Data.numEnabled = savedNumEnabled
    updateCount(Data.numEnabled)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val fragmentView = inflater.inflate(R.layout.fragment_time, container, false)

    timePrefixView = fragmentView.findViewById(R.id.time_prefix)

    //#region input time
    val inputTextView: TextView = fragmentView.findViewById(R.id.time_input)

    inputTextView.setOnClickListener {
      val cal = Calendar.getInstance()
      val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        setTime(cal, fragmentView)
      }
      TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }
    //#endregion

    //#region default message
    val defaultMessageTextView = fragmentView.findViewById<TextView>(R.id.default_message_content)
    defaultMessageTextView.setOnClickListener {
      if (includeFirstName) {
        includeFirstName = false
        defaultMessageTextView.text = getString(R.string.default_message_no_comma)
      } else {
        includeFirstName = true
        if (Data.upcomingBirthdays.size > 0) {
          defaultMessageTextView.text =
            resources.getString(R.string.default_message_comma, Data.contactsMap[Data.upcomingBirthdays[0]]!!.firstName)
        } else {
          defaultMessageTextView.text = resources.getString(R.string.default_message_comma, "Ada")
        }
      }
    }

    //#region upcoming birthdays
    Data.upcomingBirthdays.forEachIndexed { index, id ->
      // Image
      val contact = Data.contactsMap[id]
      val v : View = View.inflate(context, R.layout.contact, null)
      val upcomingPhoto : ImageView = v.findViewById(R.id.contact_photo)
      upcomingPhoto.setImageURI(Uri.parse(contact!!.photoUri))

      upcomingPhoto.clipToOutline = true
      if(upcomingPhoto.drawable == null) {
        val colorGenerator = ColorGenerator.MATERIAL
        val textDrawable = TextDrawable.builder().buildRound(contact.firstName[0].toString(), colorGenerator.getColor(contact.firstName + contact.lastName))

        upcomingPhoto.setImageDrawable(textDrawable)
      }

      val nameView : TextView = v.findViewById(R.id.contact_name)
      nameView.text = contact.firstName +  " " + contact.lastName

      val dateView : TextView = v.findViewById(R.id.contact_birthday)
      dateView.text = contact.birthday
      dateView.setTextColor(Color.parseColor("#000000"))

      val switchView : Switch = v.findViewById(R.id.contact_switch)
      switchView.isChecked = contact.enabled

      switchView.setOnCheckedChangeListener  { v, isChecked ->
        if (v.isPressed) {

          Data.contactsMap[contact.id]!!.enabled = isChecked

          if (isChecked) {
            Data.numEnabled++
            CoroutineScope(IO).launch {
              Data.db.enabledDao().insert(Enabled(contact.id))
            }
          } else {
            Data.numEnabled--
            CoroutineScope(IO).launch {
              Data.db.enabledDao().delete(contact.id)
            }
          }

          updateCount(Data.numEnabled)
        }
      }

      upcomingList = fragmentView.findViewById(R.id.upcoming_birthdays_list)
      upcomingList.addView(v, index)
    }

    updateCount(Data.numEnabled)

    return fragmentView
  }

  private fun updateCount(count: Int) {
    timePrefixView.text = resources.getQuantityString(R.plurals.time_header, count, count)
  }

  private fun setTime(cal: Calendar, view: View) {
    val inputTextView: TextView = view.findViewById(R.id.time_input)
    inputTextView.text = SimpleDateFormat("HH:mm").format(cal.time)
    // TODO: Change time that alarm is scheduled for
    cancelJob(view)
    scheduleJob(view)
  }

  private fun scheduleJob(view: View) {
    val jobInfo = JobInfo.Builder(JOB_ID, ComponentName(context!!,TextService::class.java))
      .setPeriodic((MS_IN_SECONDS * SECONDS_IN_MINUTES * MINUTES_IN_HOUR * HOURS_IN_DAY).toLong())
      .setPersisted(true)
      .build()

    try {
      val jobScheduler = context!!.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
      jobScheduler.schedule(jobInfo)
    } catch (e: Exception) {
      print(e)
    }
  }

  private fun cancelJob(view: View) {
    val jobScheduler = context!!.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
    jobScheduler.cancel(JOB_ID)
  }

  companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    @JvmStatic
    fun newInstance(): TimeFragment {
      return TimeFragment()
    }
  }
}
