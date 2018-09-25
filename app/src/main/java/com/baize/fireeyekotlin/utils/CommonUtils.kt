package com.baize.fireeyekotlin.utils

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup

import com.baize.fireeyekotlin.app.FireEyeApp

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random

/**
 * Created by jingbin on 2016/11/22.
 * 获取原生资源
 */
class CommonUtils {

    /**
     * 得到年月日的"日"
     */
    private val date: String
        get() {
            val date = Date()
            val dateFm = SimpleDateFormat("dd")
            return dateFm.format(date)
        }

    companion object {

        /**
         * 随机颜色
         */
        fun randomColor(): Int {
            val random = Random()
            val red = random.nextInt(150) + 50//50-199
            val green = random.nextInt(150) + 50//50-199
            val blue = random.nextInt(150) + 50//50-199
            return Color.rgb(red, green, blue)
        }


        fun getDrawable(resid: Int): Drawable? {
            return ContextCompat.getDrawable(FireEyeApp.instance(), resid)
            //        return getResoure().getDrawable(resid);
        }

        fun getColor(resid: Int): Int {
            return resoure.getColor(resid)
        }

        val resoure: Resources
            get() = FireEyeApp.instance().resources

        fun getStringArray(resid: Int): Array<String> {
            return resoure.getStringArray(resid)
        }

        fun getString(resid: Int): String {
            return resoure.getString(resid)
        }

        fun getDimens(resId: Int): Float {
            return resoure.getDimension(resId)
        }

        fun removeSelfFromParent(child: View?) {

            if (child != null) {

                val parent = child.parent

                (parent as? ViewGroup)?.removeView(child)
            }
        }
    }

}
