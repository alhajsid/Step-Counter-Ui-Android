package com.example.testapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.adaptor.ViewPagerFragmentAdapter
import com.example.testapp.data.StepData
import com.example.testapp.fragment.UserActivityFragment
import com.example.testapp.utils.SharedPref
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus

class MainActivity : AppCompatActivity(), SensorEventListener {

    var steps=0f
    private var running = false
    private lateinit var sharedPref:SharedPref
    private var sensorManager: SensorManager? = null
    private lateinit var myAdapter: ViewPagerFragmentAdapter
    private val items= arrayListOf("Days","Week","Month","Year")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
        initLocalDb()
        initSensor()

    }

    private fun initLocalDb() {
        sharedPref = SharedPref(this)
        steps = sharedPref.steps
    }

    private fun initUi(){
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

    private fun initSensor(){
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()

        running = true
        val stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepsSensor == null) {
            Toast.makeText(this, "No Step Counter Sensor !", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        if (running) {
            steps = event.values[0]
            EventBus.getDefault().post(StepData(steps))
            sharedPref.steps = steps
        }
    }

}