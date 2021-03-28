package com.example.testapp.retrofit

import StepModel
import retrofit2.Call
import retrofit2.http.GET

interface GetDataService {

    @GET("steps.json")
    fun stepsChart(): Call<StepModel>

}