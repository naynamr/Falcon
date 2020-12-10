package com.example.falconlaunch

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

object Utility {

    /**
     *  Network check method
     */
    fun networkCheck(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
            return true
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.network_error_message),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
    }
}