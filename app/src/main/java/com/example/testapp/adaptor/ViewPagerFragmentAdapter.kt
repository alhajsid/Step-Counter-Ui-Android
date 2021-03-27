package com.example.testapp.adaptor

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerFragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val arrayList: ArrayList<Fragment> = ArrayList()

    private fun getItem(position: Int )= arrayList[position]

    fun addFragment(fragment: Fragment) =
        arrayList.add(fragment)

    override fun getItemCount() = arrayList.size

    override fun createFragment(position: Int) = getItem(position)


}