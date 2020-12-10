package com.example.falconlaunch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.falconlaunch.R
import com.example.falconlaunch.Utility
import com.example.falconlaunch.models.ApiData
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.internal.Util

class MainActivity : AppCompatActivity() {

    private var viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Utility.networkCheck(this)) {
            progress_bar.visibility = View.VISIBLE
            viewModel.getApiDetails()
        }


        viewModel.apiResponse.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                loadData(list = it)
            }
        })
    }

    /**
     *  ArrayData
     */
    private fun loadData(list: ArrayList<ApiData>) {
        falcon_list.layoutManager = LinearLayoutManager(this)
        val adapter: Adapter = Adapter(this, list)
        falcon_list.adapter= adapter
        progress_bar.visibility = View.GONE
    }
}