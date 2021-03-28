package com.example.testapp.repo

import StepModel
import android.util.Log
import retrofit2.Call
import androidx.lifecycle.MutableLiveData
import com.example.testapp.retrofit.GetDataService
import com.example.testapp.retrofit.RetrofitClientInstance
import com.example.testapp.utils.Resource
import retrofit2.Callback
import retrofit2.Response

class MainRepositories {


    companion object{
        private var instance: MainRepositories?=null
        fun getInstance(): MainRepositories {
            if (instance==null){
                instance= MainRepositories()
            }
            return instance!!
        }
    }

    fun getStepsChart(): MutableLiveData<Resource<StepModel>> {
        val data=MutableLiveData<Resource<StepModel>>()
        val service: GetDataService = RetrofitClientInstance.retrofitInstance()!!.create(
            GetDataService::class.java
        )
        val call= service.stepsChart()
        call.enqueue(object : Callback<StepModel> {

            override fun onResponse(call: Call<StepModel>, response: Response<StepModel>) {
                if (response.body()!=null) {
                    data.value = Resource.success(response.body())
                }else{
                    data.value = Resource.error("unable to connect",null)
                }
            }

            override fun onFailure(call: Call<StepModel>, t: Throwable) {
                data.value = Resource.error("unable to connect",null)
            }
        })
        return data
    }

}