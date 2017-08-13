package com.self.oluwole.tean

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.cloudinary.Cloudinary
import com.squareup.picasso.Picasso


/**
 * Created by yung on 8/13/17.
 */
class MagAdapter constructor(mList: ArrayList<JavaModel>?): RecyclerView.Adapter<MagAdapter.DataViewAdapter>() {

    var mList: ArrayList<JavaModel>? = null
    var app_context: Context? = null
    init {
        this.mList = mList
    }

    override fun onBindViewHolder(holder: DataViewAdapter?, position: Int) {

        var mag_data = mList?.get(position)
        val config = HashMap<String, String>()
        config.put("cloud_name", "dj4hinyoa")
        config.put("api_key", "666221782774112")
        config.put("api_secret", "EVlUEQfHoEEkdCw2L0Wqnu1HXUE")

        val url: String = "http://res.cloudinary.com/dj4hinyoa/image/upload/w_500,h_250,c_fill/v1502564722/" + mag_data?.name + ".jpg"
        Picasso.with(app_context).load(url).into(holder?.mag_thumbnail)
        holder?.mag_name?.text = mag_data?.name

        holder?.card_details?.setOnClickListener{
            var intent: Intent = Intent(app_context as MainActivity, Read::class.java)
            var bundle: Bundle = Bundle()
            bundle.putString("name", mag_data?.name)
            bundle.putInt("page", mag_data?.num_pages!!)

            intent.putExtras(bundle)
            app_context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mList?.count() ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DataViewAdapter {
        val rootView = LayoutInflater.from(parent?.context).inflate(R.layout.mag_list, parent, false)
        val view_adapter = DataViewAdapter(rootView)
        app_context = rootView.context
        return view_adapter
    }

    class DataViewAdapter(layoutView: View): RecyclerView.ViewHolder(layoutView) {
        var mag_name = layoutView.findViewById<TextView>(R.id.text_name)
        var mag_thumbnail = layoutView.findViewById<ImageView>(R.id.img_preview)
        var card_details = layoutView.findViewById<CardView>(R.id.card_mag)
    }
}