package com.baize.fireeyekotlin.bean

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by 彦泽 on 2018/8/21.
 */
data class VideoBean(var feed: String?, var title: String?, var description: String?,
                     var duration: Long?, var playUrl: String?, var category: String?,
                     var blurred: String?, var collect: Int?, var share: Int?, var reply: Int?, var time: Long) : Parcelable, Serializable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(feed)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeValue(duration)
        parcel.writeString(playUrl)
        parcel.writeString(category)
        parcel.writeString(blurred)
        parcel.writeValue(collect)
        parcel.writeValue(share)
        parcel.writeValue(reply)
        parcel.writeLong(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoBean> {
        override fun createFromParcel(parcel: Parcel): VideoBean {
            return VideoBean(parcel)
        }

        override fun newArray(size: Int): Array<VideoBean?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "VideoBean(feed=$feed, title=$title, description=$description, duration=$duration, playUrl=$playUrl, category=$category, blurred=$blurred, collect=$collect, share=$share, reply=$reply, time=$time)"
    }
}