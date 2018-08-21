package com.baize.fireeyekotlin.base

import android.support.v7.widget.RecyclerView
import java.util.ArrayList

/**
 */
abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseRecyclerViewHolder<T, *>>() {
    val data: MutableList<T> = ArrayList()

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<T, *>, position: Int) {
        holder.onBaseBindViewHolder(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addAll(list: List<T>) {
        data.addAll(list)
    }

    fun add(t: T) {
        data.add(t)
    }

}

//请求地址
//请求方式
//请求参数（包括参数含义、是否必传、来源）
//请求示例
//返回示例