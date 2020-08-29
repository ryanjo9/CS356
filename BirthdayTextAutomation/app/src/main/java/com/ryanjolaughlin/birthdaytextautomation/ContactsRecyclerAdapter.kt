package com.ryanjolaughlin.birthdaytextautomation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.ryanjolaughlin.birthdaytextautomation.model.Contact
import kotlinx.android.synthetic.main.contact.view.*
import com.ryanjolaughlin.birthdaytextautomation.model.Enabled
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class ContactsRecyclerAdapter(private val items: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return ContactViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact, parent, false))
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when(holder) {
      is ContactViewHolder -> {
        val contact = Data.contactsMap.get(items.get(position))
        if (contact != null) {
          holder.bind(contact, position, items.size)
        }
      }
    }
  }

  override fun getItemCount(): Int {
    return items.size
  }

  class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val EDIT_REQUEST_CODE = 4

    fun bind(contact: Contact, position: Int, size: Int) = with(itemView) {
      bindBackground(itemView, position, size, context)
      // Set Photo
      contact_photo.setImageURI(Uri.parse(contact.photoUri))
      contact_photo.clipToOutline = true
      if(contact_photo.drawable == null) contact_photo.setImageResource(R.drawable.ic_tag_faces_black_24dp)

      // Set Name
      contact_name.text = contact.firstName + " " + contact.lastName

      // Set Birthday
      if (contact.birthday.isNotEmpty()) {
        contact_birthday.text = contact.birthday
        contact_birthday.setTextColor(Color.parseColor("#000000"))

        // Hook up switch
        contact_switch.visibility = View.VISIBLE
        contact_switch.isChecked = contact.enabled
        contact_switch.setOnClickListener {
          val prev = Data.contactsMap[contact.id]!!.enabled

          Data.contactsMap[contact.id]!!.enabled = !prev

          if (prev) {
            Data.numEnabled--
            CoroutineScope(IO).launch {
              Data.db.enabledDao().delete(contact.id)
            }
          }
          else {
            Data.numEnabled++
          CoroutineScope(IO).launch{
            Data.db.enabledDao().insert(Enabled(contact.id))
          }
          }
        }

      } else {
        contact_birthday.text = "Add"
        contact_birthday.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))

        // Hide switch
        contact_switch.visibility = View.GONE
      }

      contact_birthday.setOnClickListener {
//          println("Adding birthday for id: " + contact.id)
        val longId = contact.id.toLong()
        val selectedContactUri = ContactsContract.Contacts.getLookupUri(longId, contact.lookupKey)

        // Creates a new Intent to edit a contact
        val editIntent = Intent(Intent.ACTION_EDIT).apply {
          /*
         * Sets the contact URI to edit, and the data type that the
         * Intent must match
         */
          setDataAndType(selectedContactUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE)
          }

          // Sets the special extended data for navigation
          editIntent.putExtra("finishActivityOnSaveCompleted", true)
          // Sends the Intent
          (context as MainActivity).supportFragmentManager.fragments[1].startActivityForResult(editIntent, EDIT_REQUEST_CODE)
//          (context as MainActivity).startActivityForResult(editIntent, EDIT_REQUEST_CODE)
        }


    }

    private fun bindBackground(itemView: View, position: Int, size: Int, con: Context) {
      val drawableRes: Int = when {
        size == 1 -> R.drawable.list_item_single
        position == 0 -> R.drawable.list_item_top
        position == size - 1 -> R.drawable.list_item_bottom
        else -> R.drawable.list_item_middle
      }
      itemView.background = ResourcesCompat.getDrawable(con.resources, drawableRes, null)
    }
  }
}