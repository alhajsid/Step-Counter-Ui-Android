package com.example.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.testapp.adaptor.ViewPagerFragmentAdapter
import com.example.testapp.fragment.UserActivityFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var myAdapter: ViewPagerFragmentAdapter
    val items= arrayListOf("Days","Week","Month","Year")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myAdapter = ViewPagerFragmentAdapter(this)

        // add Fragments in your ViewPagerFragmentAdapter class
        myAdapter.addFragment(UserActivityFragment())
        myAdapter.addFragment(UserActivityFragment())
        myAdapter.addFragment(UserActivityFragment())
        myAdapter.addFragment(UserActivityFragment())

        view_pager.adapter = myAdapter

        TabLayoutMediator(tabLayout, view_pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text=items[position]
            }).attach()

    }

}