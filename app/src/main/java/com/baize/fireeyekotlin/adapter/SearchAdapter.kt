package com.baize.fireeyekotlin.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.base.BaseRecyclerViewAdapter
import com.baize.fireeyekotlin.base.BaseRecyclerViewHolder
import com.baize.fireeyekotlin.databinding.ItemSearchBinding
import com.baize.fireeyekotlin.ui.ResultActivity
import com.baize.fireeyekotlin.utils.showToast
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * Created by 彦泽 on 2018/8/23.
 */
class SearchAdapter(context: Context, onDialogDismiss: onDialogDismiss) : BaseRecyclerViewAdapter<String>() {
    var mContext: Context = context
    var mDialogListener = onDialogDismiss


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<String, *> {
        return SearchViewHolder(parent, R.layout.item_search, mContext, mDialogListener)
    }

    class SearchViewHolder(viewGroup: ViewGroup, layoutId: Int, context: Context, dialogListener: onDialogDismiss) : BaseRecyclerViewHolder<String, ItemSearchBinding>(viewGroup, layoutId) {
        var mDialogListener = dialogListener
        var mContext: Context = context

        override fun onBindViewHolder(entity: String, position: Int) {
            binding.str = entity
            val params = binding.tvTitle.layoutParams
            if (params is FlexboxLayoutManager.LayoutParams) {
                (binding.tvTitle.layoutParams as FlexboxLayoutManager.LayoutParams).flexGrow = 1.0f
            }
            binding.tvTitle.setOnClickListener {
                val keyWord = entity
                val intent: Intent = Intent(mContext, ResultActivity::class.java)
                intent.putExtra("keyWord", keyWord)
                mContext.startActivity(intent)
                mDialogListener.onDismiss()
            }
        }
    }


    interface onDialogDismiss {
        fun onDismiss()
    }
}