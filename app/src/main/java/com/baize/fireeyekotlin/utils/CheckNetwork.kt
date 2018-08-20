package com.baize.fireeyekotlin.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkInfo

/**
 * 用于判断是不是联网状态
 *
 * @author Dzy
 */
class CheckNetwork {

    companion object {

        /**
         * 判断网络是否联通
         */
        fun isNetworkConnected(context: Context): Boolean {
            @SuppressWarnings("static-access")
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val info: NetworkInfo? = cm.activeNetworkInfo
            return info != null && info.isConnected
        }

        fun isWifiConnected(context: Context): Boolean {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val info: NetworkInfo? = cm.activeNetworkInfo
            return info != null && (info.type == TYPE_WIFI)
        }
    }

}
