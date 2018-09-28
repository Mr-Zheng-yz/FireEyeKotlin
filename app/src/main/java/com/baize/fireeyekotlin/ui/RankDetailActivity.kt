package com.baize.fireeyekotlin.ui

import android.os.Bundle
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_rank_detail.*

class RankDetailActivity : BaseActivity() {

    private lateinit var mName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank_detail)
        mName = intent.getStringExtra("name")
        title = "$mName"
        initView()
    }

    private fun initView() {
        showContentView()
        tv_find_detail.text = mName
    }
}
