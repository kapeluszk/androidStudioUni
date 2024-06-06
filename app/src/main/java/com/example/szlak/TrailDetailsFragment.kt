package com.example.szlak

import android.content.DialogInterface
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.Telephony.Sms
import android.telephony.SmsManager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.szlak.R.id.descriptionTextView
import com.example.szlak.R.id.nameTextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TrailDetailsFragment : Fragment() {



    companion object {
        fun newInstance(trail: LocalData.Trail): TrailDetailsFragment {
            val fragment = TrailDetailsFragment()
            val args = Bundle()
            args.putSerializable("trail", trail)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trail_details, container, false)
        val fab : FloatingActionButton = view.findViewById(R.id.fab)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        val bottomToolbar = view.findViewById<Toolbar>(R.id.bottom_toolbar)
        bottomToolbar.inflateMenu((R.menu.bottom_toolbar_menu))
        bottomToolbar.setOnMenuItemClickListener{ item ->
            when (item.itemId) {
                R.id.action_trail_list ->{
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, TrailListFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.action_stoper -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, StoperFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }

                else -> {false}
            }
        }

        val params = fab.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin = 8.dpToPx() // Dodaj margines od dołu (np. 16dp)
        params.marginEnd = 8.dpToPx() // Dodaj margines od prawej (np. 16dp)
        fab.layoutParams = params

        val trail = arguments?.getSerializable("trail") as? LocalData.Trail

        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)
        val lengthTextView = view.findViewById<TextView>(R.id.lengthTextView)
        val trailImageView = view.findViewById<ImageView>(R.id.trailImageView)

        nameTextView.text = trail?.name
        descriptionTextView.text = trail?.description
        lengthTextView.text = "Expected length: ${trail?.expectedLength}"

        fab.setOnClickListener{
            showPhoneNumberDialog(trail)
        }

        if (savedInstanceState == null) {
//            val transaction = childFragmentManager.beginTransaction()
//            transaction.add(R.id.stoper_container, StoperFragment())
//            transaction.commit()
        }

        return view
    }

    private fun showPhoneNumberDialog(trail: LocalData.Trail?) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Podaj numer telefonu na jaki wysłać pozdrowienia")

        val input = EditText(requireContext())
        builder.setView(input)

        builder.setPositiveButton("Wyślij") { dialog: DialogInterface?, which: Int ->
            val phoneNumber = input.text.toString()
            sendSms(phoneNumber, trail)
        }
        builder.setNeutralButton("Anuluj") { dialog: DialogInterface?, which: Int ->
            dialog?.dismiss()
        }

        builder.show()
    }

    private fun sendSms(phoneNumber: String, trail: LocalData.Trail?){

//        val smsManager: SmsManager
//        smsManager = SmsManager.getDefault()
//        val smsBody = "Pozdrowienia ze szlaku ${trail?.name}!"
////        val smsIntent = SmsManager.getDefault().run {
////
////            sendTextMessage(phoneNumber,null,smsBody,null,null)
////        }
//        smsManager.sendTextMessage(phoneNumber,null,smsBody,null,null)

        Toast.makeText(requireContext(),"SMS został wysłany na numer: $phoneNumber",Toast.LENGTH_SHORT).show()
    }
}

fun Int.dpToPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}