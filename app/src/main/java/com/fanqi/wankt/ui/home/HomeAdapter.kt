package com.fanqi.wankt.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fanqi.wankt.R
import com.fanqi.wankt.common.bean.DataX
import kotlinx.android.synthetic.main.item_home_article.view.*

/**
 * Created by fanqi on 2019-12-20.
 * Description:
 */
@SuppressLint("ParcelCreator")
class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.HomeHolder>(), Parcelable {

    private lateinit var dataXList: List<DataX>
    private lateinit var mContext: Context

    constructor(dataXList: List<DataX>, mContext: Context) : this() {
        this.dataXList = dataXList
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.item_home_article, parent, false)
        return HomeHolder(view)
    }

    override fun getItemCount(): Int {
        if (dataXList == null) {
            return 0
        }
        return dataXList.size
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        var dataX: DataX = dataXList[position]
        holder.title.text = dataX.title
        if (!TextUtils.isEmpty(dataX.author)) {
            holder.share_user.text = "作者：${dataX.author}"
        } else {
            holder.share_user.text = "分享人：${dataX.shareUser}"
        }
        holder.category.text = "分类：${dataX.superChapterName}"
        holder.time.text = dataX.niceDate
    }


    class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val share_user: TextView = itemView.findViewById(R.id.share_user)
        val category: TextView = itemView.findViewById(R.id.category)
        val time: TextView = itemView.findViewById(R.id.time)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }
}