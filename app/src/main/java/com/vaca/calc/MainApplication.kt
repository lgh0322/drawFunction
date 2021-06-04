package com.vaca.calc

import android.app.Application
import com.tencent.bugly.crashreport.CrashReport

class MainApplication :Application() {


    override fun onCreate() {
        super.onCreate()
        CrashReport.initCrashReport(this, "c7bbd0dd02", false);
    }
}