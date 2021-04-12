package com.jye.hiadnroid.base.demo

import android.app.Application
import com.jye.hiandroid.base.HiBase

/**
 * @author jye
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        HiBase.init(this)
    }
}