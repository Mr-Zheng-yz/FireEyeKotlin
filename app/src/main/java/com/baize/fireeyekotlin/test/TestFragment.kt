package com.baize.fireeyekotlin.test

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.databinding.FragmentTestBinding

/**
 * Created by 彦泽 on 2018/8/14.
 */
class TestFragment : Fragment() {
    var bindingView: FragmentTestBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingView = DataBindingUtil.inflate<FragmentTestBinding>(inflater, R.layout.fragment_test, container, false)
        return bindingView?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}