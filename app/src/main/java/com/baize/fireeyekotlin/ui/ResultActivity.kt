package com.baize.fireeyekotlin.ui

import android.os.Bundle
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : BaseActivity() {

    lateinit var mKeyWord: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        mKeyWord = intent.getStringExtra("keyWord")
        title = "\'$mKeyWord\'相关"
        initView()
    }

    private fun initView() {
        btn_show_content.setOnClickListener({
            showContentView()
        })
        btn_show_empty.setOnClickListener({
            showEmpty()
            Thread(Runnable {
                Thread.sleep(2000)
                runOnUiThread {
                    showContentView()
                }
            }).start()
        })
        btn_show_error.setOnClickListener({
            showError()
        })
        btn_show_loading.setOnClickListener({
            showLoading()
            Thread(Runnable {
                Thread.sleep(2000)
                runOnUiThread {
                    showContentView()
                }
            }).start()
        })
        showContentView()
    }

    override fun onRefresh() {
        showContentView()
    }

}
