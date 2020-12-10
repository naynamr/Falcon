package com.example.falconlaunch.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.falconlaunch.models.ApiData
import com.example.falconlaunch.network.Api
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel(){

    private val _response = MutableLiveData<ArrayList<ApiData>>()
    val apiResponse: LiveData<ArrayList<ApiData>>
        get() = _response


    fun getApiDetails() {
        Api.retrofitService.launches().enqueue(
            object : retrofit2.Callback<ArrayList<ApiData>> {
                override fun onFailure(
                    call: Call<ArrayList<ApiData>>,
                    t: Throwable
                ) {
                }

                override fun onResponse(
                    call: Call<ArrayList<ApiData>>,
                    response: Response<ArrayList<ApiData>>
                ) {
                    if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                        _response.value =  response.body()
                    }
                }
            }
        )
    }


}