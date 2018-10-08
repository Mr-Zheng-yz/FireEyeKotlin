package com.baize.fireeyekotlin.ui

import android.os.Bundle
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.base.BaseActivity

class AdviseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advise)
        title = "意见反馈"
        showContentView()
    }
}
