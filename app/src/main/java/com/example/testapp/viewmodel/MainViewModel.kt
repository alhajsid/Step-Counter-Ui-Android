package com.example.testapp.viewmodel

import StepModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.repo.MainRepositories
import com.example.testapp.utils.Resource

class MainViewModel : ViewModel() {

    var stepModel: MutableLiveData<Resource<StepModel>> = MutableLiveData()

    lateinit var mRepo: MainRepositories


    fun getStepModel() {
        if (!this::mRepo.isInitialized) {
            mRepo = MainRepositories.getInstance()
        }
        stepModel.value =Resource.loading(null)
        mRepo.getStepsChart().observeForever {
            stepModel.value=it
        }

    }

    fun observeStepModel() = stepModel


}