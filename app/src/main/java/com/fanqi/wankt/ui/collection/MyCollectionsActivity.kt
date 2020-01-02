package com.fanqi.wankt.ui.collection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.fanqi.wankt.R
import com.fanqi.wankt.common.bean.Collection
import com.fanqi.wankt.common.bean.ProjectItem
import com.fanqi.wankt.constant.Constant
import com.fanqi.wankt.ui.ArticleContentActivity
import com.fanqi.wankt.ui.project.ProjectItemAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_my_collections.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class MyCollectionsActivity : AppCompatActivity() {

    private lateinit var viewModel: MycollectionViewModel
    private lateinit var collectionRecyclerView: RecyclerView
    private var colletionList = arrayListOf<Collection>()
    private lateinit var mAdapter: CollectionAdapter
    private lateinit var refreshView: SmartRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_collections)
        var actionBar = getSupportActionBar()
        actionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this).get(MycollectionViewModel::class.java)
        collectionRecyclerView = collectionList
        refreshView = refreshLayout
        refreshView.setRefreshHeader(ClassicsHeader(this));

        mAdapter = CollectionAdapter(colletionList)
        mAdapter.setItemCallBack(itemCallBack)
        collectionRecyclerView.adapter = mAdapter

        colletionList.clear()
        viewModel.initPage().observe(this, Observer {
            colletionList.addAll(it)
            mAdapter.notifyDataSetChanged()
        })
    }


    private var itemCallBack = object : CollectionAdapter.ItemCallBack {
        override fun onItemClick(item: Collection) {
            Intent(this@MyCollectionsActivity, ArticleContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, item.link)
                startActivity(this)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

}
