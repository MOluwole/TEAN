package com.self.oluwole.tean

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.PageTransformer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.squareup.picasso.Picasso


class Read : AppCompatActivity() {

    companion object {
        var name = ""
        var page = 0
        var arrayList: ArrayList<String> = ArrayList()
    }

    class PageCurlPageTransformer : PageTransformer {

        override fun transformPage(page: View, position: Float) {

            Log.d("Animation test", "transformPage, position = " + position + ", page = " + page.getTag(R.id.pager))
            if (page is PageCurl) {
                if (position > -1.0f && position < 1.0f) {
                    // hold the page steady and let the views do the work
                    page.translationX = -position * page.width
                } else {
                    page.translationX = 0.0f
                }
                if (position <= 1.0f && position >= -1.0f) {
                    (page as PageCurl).setCurlFactor(position)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_read)
        var intent: Intent = intent
        name = intent.extras.getString("name")
        page = intent.extras.getInt("page")

        arrayList = ArrayList()
        (1..page).mapTo(arrayList) { "http://res.cloudinary.com/dj4hinyoa/image/upload/pg_$it/v1502564722/$name.jpg" }

        var mCustomPagerAdapter = CustomPagerAdapter(this)
        var mViewPager: ViewPager = findViewById(R.id.pager)
        mViewPager.adapter = mCustomPagerAdapter
        mViewPager.setPageTransformer(false, PageCurlPageTransformer())



    }

    class CustomPagerAdapter(context: Context?): PagerAdapter() {

        var context: Context? = null
        var mLayoutInflater: LayoutInflater? = null

        init {
            this.context = context
            mLayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        }

        override fun getCount(): Int {
            return arrayList.size
        }

        override fun isViewFromObject(view: View?, obj: Any?): Boolean {
            return view == obj as RelativeLayout
        }

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            var itemView = mLayoutInflater?.inflate(R.layout.pager_item, container, false) as View
            var imageView: TouchImageView? = itemView.findViewById(R.id.imageView)
            var fab: FloatingActionButton? = itemView.findViewById(R.id.fab)

            fab?.setOnClickListener { view ->
                Snackbar.make(view, "Adding of comments coming soon", Snackbar.LENGTH_LONG).show()
            }

            Picasso.with(context)
                    .load(arrayList[position])
                    .into(imageView, object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            //do smth when picture is loaded successfully
                            imageView?.setZoom(1.toFloat())
                        }

                        override fun onError() {
                            Toast.makeText(context, "An error occured. Try again", Toast.LENGTH_SHORT).show()
                        }
                    })
            container?.addView(itemView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            Toast.makeText(context, position.toString() + "/" + arrayList.size, Toast.LENGTH_SHORT).show()

            return itemView
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container?.removeView(`object` as RelativeLayout)
        }
    }
}
