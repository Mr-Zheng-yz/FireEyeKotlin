package com.baize.fireeyekotlin.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseRecyclerViewHolder<T, D : ViewDataBinding>(viewGroup: ViewGroup, layoutId: Int) : RecyclerView.ViewHolder(DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(viewGroup.context), layoutId, viewGroup, false).root) {

    var binding: D

    init {
        // 得到这个View绑定的Binding
        binding = DataBindingUtil.getBinding(this.itemView)
    }// 注意要依附 viewGroup，不然显示item不全!!

    /**
     * @param object   the data of bind
     * @param position the item position of recyclerView
     */
    abstract fun onBindViewHolder(entity: T, position: Int)

    /**
     * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
     */
    internal fun onBaseBindViewHolder(entity: T, position: Int) {
        onBindViewHolder(entity, position)
        binding.executePendingBindings()
    }
}
