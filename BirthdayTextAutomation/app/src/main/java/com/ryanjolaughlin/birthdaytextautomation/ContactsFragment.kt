package com.ryanjolaughlin.birthdaytextautomation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [ContactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ContactsFragment : Fragment() {
  private lateinit var lastRefresh: Date
  private lateinit var contactsAdapter: ContactsRecyclerAdapter
  private lateinit var recyclerView: RecyclerView

  override fun onResume() {
    super.onResume()

//    if (lastRefresh != Data.lastUpdated) {
      // push new contacts in to adapter
      recyclerView.adapter = ContactsRecyclerAdapter(Data.nameSorted)
      lastRefresh = Data.lastUpdated
//    }

    recyclerView.scrollToPosition(Data.contactsRecyclerPosition)
  }

  override fun onPause() {
    super.onPause()
    Data.contactsRecyclerPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val contactsView: View = inflater.inflate(R.layout.fragment_contacts, container, false)

    recyclerView = contactsView.findViewById(R.id.contacts_recycler)

    recyclerView.apply {
      layoutManager = LinearLayoutManager(requireContext())
      contactsAdapter = ContactsRecyclerAdapter(Data.nameSorted)
      adapter = contactsAdapter
    }

    lastRefresh = Data.lastUpdated


    contactsView.contacts_swipe_to_refresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireContext(), R.color.white))
    contactsView.contacts_swipe_to_refresh.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.blue))

    contactsView.contacts_swipe_to_refresh.setOnRefreshListener {
      // Reload contacts
      contactsView.contacts_swipe_to_refresh.isRefreshing = false
    }

    return contactsView
  }


  companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ContactsFragment.
     */
    @JvmStatic
    fun newInstance(): ContactsFragment {
      return ContactsFragment()
    }
  }
}
