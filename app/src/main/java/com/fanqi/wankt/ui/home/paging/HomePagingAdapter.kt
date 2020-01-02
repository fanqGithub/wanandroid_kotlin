package com.fanqi.wankt.ui.home.paging

import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fanqi.wankt.R
import com.fanqi.wankt.common.bean.DataX
import com.fanqi.wankt.ui.home.HomeAdapter
import kotlinx.android.synthetic.main.item_home_article.view.*

/**
 * Created by fanqi on 2019-12-23.
 * Description:
 */
class HomePagingAdapter :
    PagedListAdapter<DataX, HomePagingAdapter.HomePageHolder>(DATAX_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_article, parent, false)
        var holder = HomePageHolder(view)
        holder.itemView.setOnClickListener(View.OnClickListener {
            getItem(holder.adapterPosition)?.let { it1 ->
                callBack.onItemClick(
                    holder.adapterPosition,
                    it1
                )
            }
        })
        holder.favorite.setOnClickListener {
            getItem(holder.adapterPosition)?.let { it1 ->
                callBack.onFavoriteClick(
                    holder.adapterPosition,
                    it1
                )
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: HomePageHolder, position: Int) {
        val datax = getItem(position)
        datax?.let { holder.bind(it) }
    }


    class HomePageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.title
        private val share_user = itemView.share_user
        private val category = itemView.category
        private val time = itemView.time
        val favorite = itemView.favorite
        private val freshTag = itemView.fresh_tag
        private val desc = itemView.desc

        fun bind(dataX: DataX) {
            if (dataX.fresh) {
                freshTag.visibility = View.VISIBLE
                freshTag.text = "新"
            } else {
                freshTag.visibility = View.GONE
            }

            if (!TextUtils.isEmpty(dataX.desc)) {
                desc.visibility = View.VISIBLE
                desc.text = Html.fromHtml(dataX.desc)
            } else {
                desc.visibility = View.GONE
            }

            title.text = dataX.title
            if (!TextUtils.isEmpty(dataX.author)) {
                share_user.text = "作者：${dataX.author}"
            } else {
                share_user.text = "分享人：${dataX.shareUser}"
            }
            category.text = "分类：${dataX.superChapterName}"
            time.text = dataX.niceDate
            favorite.setImageResource(
                if (dataX.collect) R.drawable.ic_favorite_red_24dp else R.drawable.ic_favorite_black_24dp
            )

        }
    }

    companion object {
        private val DATAX_COMPARATOR = object : DiffUtil.ItemCallback<DataX>() {
            override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean =
                newItem == oldItem
        }
    }

    interface HomeListCallBack {
        fun onItemClick(position: Int, dataX: DataX)
        fun onFavoriteClick(position: Int, dataX: DataX)
    }

    private lateinit var callBack: HomeListCallBack

    fun setHomeListCallBack(homeListCallBack: HomeListCallBack) {
        this.callBack = homeListCallBack
    }

}