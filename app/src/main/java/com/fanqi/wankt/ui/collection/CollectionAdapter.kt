package com.fanqi.wankt.ui.collection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fanqi.wankt.R
import com.fanqi.wankt.common.bean.Collection
import com.fanqi.wankt.common.bean.ProjectItem
import kotlinx.android.synthetic.main.item_collection_article.view.*

/**
 * Created by fanqi on 2020-01-02.
 * Description:
 */
class CollectionAdapter() : RecyclerView.Adapter<CollectionAdapter.CollectionHolder>() {

    private lateinit var collectionList: List<Collection>

    constructor(list: List<Collection>) : this() {
        this.collectionList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection_article, parent, false)
        var holder = CollectionHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.itemView.getTag()
            if (itemCallBack != null) {
                itemCallBack.onItemClick(collectionList[position as Int])
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return collectionList.size
    }

    override fun onBindViewHolder(holder: CollectionHolder, position: Int) {
        val collection = collectionList[position]
        holder.artTitle.text = collection.title
        holder.artChapterName.text = collection.chapterName
        holder.artTime.text = collection.niceDate
        holder.itemView.setTag(position)
    }


    class CollectionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artTitle = itemView.title
        val artTime = itemView.time
        val artChapterName = itemView.chapterName
    }

    interface ItemCallBack {
        fun onItemClick(item: Collection)
    }

    private lateinit var itemCallBack: ItemCallBack

    fun setItemCallBack(callBack: ItemCallBack) {
        this.itemCallBack = callBack
    }
}