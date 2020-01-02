package com.fanqi.wankt.ui.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fanqi.wankt.R
import com.fanqi.wankt.common.bean.ProjectItem
import kotlinx.android.synthetic.main.item_project.view.*
import kotlinx.android.synthetic.main.item_project_category.view.*

/**
 * Created by fanqi on 2019-12-26.
 * Description:
 */
class ProjectItemAdapter() : RecyclerView.Adapter<ProjectItemAdapter.ProjectItemHolder>() {

    private lateinit var listProject: List<ProjectItem>

    constructor(list: List<ProjectItem>) : this() {
        this.listProject = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectItemHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project, parent, false)
        var holder = ProjectItemHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.itemView.getTag()
            if (itemCallBack != null) {
                itemCallBack.onItemClick(position as Int, listProject[position])
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return listProject.size
    }

    override fun onBindViewHolder(holder: ProjectItemHolder, position: Int) {
        var projectItem = listProject[position]
        Glide.with(holder.itemView).load(projectItem.envelopePic).into(holder.projectImg)
        holder.projectName.text = projectItem.title
        holder.projectAuthor.text = projectItem.author
        holder.projectDesc.text = projectItem.desc
        holder.projectTime.text = projectItem.niceDate
        holder.itemView.setTag(position)
    }

    class ProjectItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val projectImg = itemView.projectImg
        val projectName = itemView.projectTitle
        val projectDesc = itemView.projectDesc
        val projectAuthor = itemView.projectAuthor
        val projectTime = itemView.projectTime
        val projectFavorite = itemView.projectFavorite
    }

    interface ItemCallBack {
        fun onItemClick(position: Int, projectItem: ProjectItem)
    }

    private lateinit var itemCallBack: ItemCallBack

    fun setItemCallBack(callBack: ItemCallBack) {
        this.itemCallBack = callBack
    }


}