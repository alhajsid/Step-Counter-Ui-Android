package com.example.testapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.R
import kotlinx.android.synthetic.main.fragment_activity.*


class UserActivityFragment : Fragment(R.layout.fragment_activity) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simple.setValue(45f)

        val list=ArrayList<Pair<String,Float>>()
        list.add(Pair("01",6f))
        list.add(Pair("02",4f))
        list.add(Pair("03",7f))
        list.add(Pair("04",5f))
        list.add(Pair("05",9f))
        list.add(Pair("06",5f))
        list.add(Pair("07",8f))
        chart.animate(list)
        chart.invalidate()

    }
}