package com.ryanjolaughlin.birthdaytextautomation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MessagesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MessagesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MessagesFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_messages, container, false)
  }

  companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MessagesFragment.
     */
    @JvmStatic
    fun newInstance(): MessagesFragment {
      return MessagesFragment()
    }
  }
}
