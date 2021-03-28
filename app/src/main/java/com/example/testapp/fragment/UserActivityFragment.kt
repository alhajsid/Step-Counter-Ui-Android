package com.example.testapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.example.testapp.data.StepData
import com.example.testapp.utils.CALORY_IN_ONE_STEP
import com.example.testapp.utils.METER_IN_ONE_STEP
import com.example.testapp.utils.Status
import com.example.testapp.utils.TIME_IN_ONE_STEP
import com.example.testapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_activity.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class UserActivityFragment : Fragment(R.layout.fragment_activity) {

    private val targetSteps=5000f
    private lateinit var mainViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //registering to listen data from activity
        EventBus.getDefault().register(this)

        initView()

        mainViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.observeStepModel().observe(this, androidx.lifecycle.Observer {
            when(it.status){
                Status.SUCCESS -> {
                    val inputFormat =
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                    val list=ArrayList<Pair<String,Float>>()
                    for (i in it.data!!.data){
                        val date=inputFormat.parse(i.date)
                        val day = DateFormat.format("dd", date) as String
                        list.add(Pair(day,i.steps.toFloat()))
                    }
                    updateChart(list)
                }
                Status.ERROR -> Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show()
                Status.LOADING -> {

                }
            }
        })
        mainViewModel.getStepModel()
    }

    override fun onStart() {
        super.onStart()
        updateView((activity as MainActivity).steps)
    }

    /** main activity call this function when new data arrive from sensor*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun listeningData(data: StepData){
        updateView(data.steps)
    }

    private fun initView(){
        //feeding static data to chart (here we can call api here to get data from server as well get data from local storage)
        //setting max to 5000 as this is user target to achieve
        progress_circular.min=0f
        progress_circular.max=targetSteps

    }

    @SuppressLint("SetTextI18n")
    private fun updateView(steps:Float){

        tv_steps.text="$steps"

        progress_circular.setValue(steps)

        tv_total_steps.text="$steps"

        val calories= (steps * CALORY_IN_ONE_STEP).toInt()
        val distance = (steps * METER_IN_ONE_STEP).toInt()
        val time = getTimeFromSteps(steps)

        tv_calories.text="$calories kl"
        tv_distance.text="$distance m"
        tv_time.text=time

        if (steps>=targetSteps){
            tv_you_still_need.text=getString(R.string.target_reached)
            tv_steps_to_go.text="$targetSteps"
            tv_steps_to_reach.text=""
        }else{
            tv_steps_to_go.text="${targetSteps-steps}"
        }

    }

    private fun updateChart(list:ArrayList<Pair<String,Float>>){
        chart.animate(list)
        chart.invalidate()
    }

    fun getTimeFromSteps(steps: Float):String{
        var timeSuffix="scnd"
        var time = steps * TIME_IN_ONE_STEP
        if (time>60){
            time /= 60
            timeSuffix = "mnts"
            if (time>60){
                time /= 60
                timeSuffix = "hrs"
            }
        }
        return "${time.toInt()} $timeSuffix"
    }

    override fun onDestroy() {
        //unregistering to listen data from activity
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}