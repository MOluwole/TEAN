package com.self.oluwole.tean


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import android.app.NotificationManager
import android.content.Context


/**
   * A simple [Fragment] subclass.
  */
 class MagList :Fragment() {

    companion object {
        val LOG_TAG: String = "TEAN"

        fun newInstance(): MagList{
            val fragment = MagList()
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
       val rootView = inflater!!.inflate(R.layout.fragment_list, container, false)
        var myFiles: ArrayList<JavaModel>? = ArrayList()
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        var myRef: DatabaseReference = database.getReference("mags")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Toast.makeText(rootView.context, "An unexpected error occurred", Toast.LENGTH_LONG).show()
                Log.e(LOG_TAG, p0.toString())
            }

            override fun onDataChange(p0: DataSnapshot?) {
                myFiles?.clear()
                p0!!.children!!.forEach { noteSnapshot ->
                    val note = noteSnapshot.getValue(JavaModel::class.java)
                    myFiles?.add(note!!)
                }
                var mag_list_code: RecyclerView? = rootView?.findViewById(R.id.mag_list_view)
                mag_list_code?.adapter = null
                var mLayoutManager = LinearLayoutManager(context)
                mag_list_code?.layoutManager = mLayoutManager

                var mAdapter = MagAdapter(myFiles)
                mag_list_code?.adapter = mAdapter

                val mBuilder = NotificationCompat.Builder(activity)
                        .setSmallIcon(R.drawable.notification_bg_low)
                        .setAutoCancel(true)
                        .setContentTitle("The Essence Magazine")
                        .setContentText("A new magazine has been updated")
                val mNotificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                mNotificationManager.notify(5, mBuilder.build())
            }
        })
        myFiles?.clear()
        var mag_list_code: RecyclerView? = rootView?.findViewById(R.id.mag_list_view)
        mag_list_code?.adapter = null
        var mLayoutManager = LinearLayoutManager(context)
        mag_list_code?.layoutManager = mLayoutManager

        var mAdapter = MagAdapter(myFiles)
        mag_list_code?.adapter = mAdapter


        return rootView
    }


}// Required empty public constructor
