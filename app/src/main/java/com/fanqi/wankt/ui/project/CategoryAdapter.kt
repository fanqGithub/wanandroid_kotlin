package com.fanqi.wankt.ui.project

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.fanqi.wankt.R
import com.fanqi.wankt.common.bean.DataX
import com.fanqi.wankt.common.bean.ProjectCategory
import com.fanqi.wankt.ui.home.HomeAdapter
import kotlinx.android.synthetic.main.item_project_category.view.*

/**
 * Created by fanqi on 2019-12-26.
 * Description:
 */
class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    private lateinit var context: Context
    private lateinit var categoryList: List<ProjectCategory>

    private var selectPosition = 0

    constructor(list: List<ProjectCategory>) : this() {
        this.categoryList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        this.context = parent.context
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project_category, parent, false)
        var holder = CategoryHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.itemView.getTag()
            selectPosition = position as Int
            notifyDataSetChanged()
            if (categoryAdapterCallBack != null) {
                categoryAdapterCallBack.onSelected(selectPosition, categoryList[position])
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        var projectCategory = categoryList[position]
        holder.category.text = Html.fromHtml(projectCategory.name)
        holder.itemView.setTag(position)
        if (selectPosition == position) {
            holder.category.background = context.getDrawable(R.color.white)
            holder.category.setTextColor(context.getColor(R.color.color_category_selected))
        } else {
            holder.category.background = context.getDrawable(R.color.color_category_bg_normal)
            holder.category.setTextColor(context.getColor(R.color.color_text_black))
        }
    }

    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category = itemView.categoryTv
    }

    interface CategoryAdapterCallBack {
        fun onSelected(position: Int, category: ProjectCategory)
    }

    lateinit var categoryAdapterCallBack: CategoryAdapterCallBack

    fun setCategoryCallBack(callBack: CategoryAdapterCallBack) {
        this.categoryAdapterCallBack = callBack
    }
}