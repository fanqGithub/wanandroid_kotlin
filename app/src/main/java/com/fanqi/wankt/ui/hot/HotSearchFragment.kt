package com.fanqi.wankt.ui.hot

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fanqi.wankt.R
import com.fanqi.wankt.common.bean.FriendData
import com.fanqi.wankt.constant.Constant
import com.fanqi.wankt.ui.ArticleContentActivity
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import java.util.*


class HotSearchFragment : Fragment() {

    private lateinit var notificationsViewModel: HotSearchViewModel
    private lateinit var hotSearchFlex: FlexboxLayout
    private lateinit var commonUseFlex: FlexboxLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(HotSearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        hotSearchFlex = root.hotSearchFlex
        commonUseFlex = root.commonUseFlex
        setStatusColor(resources.getColor(R.color.black))

        notificationsViewModel.text.observe(this, Observer {
        })

        initData()

        return root
    }


    private fun initData() {
        hotSearchFlex.removeAllViews()
        commonUseFlex.removeAllViews()

        notificationsViewModel.getHotData()
        notificationsViewModel.getCommonData()

        notificationsViewModel.hotSearchLiveData.observe(this, Observer {
            it.forEachIndexed { index, friendData ->
                val itemView = createTagView(index, friendData)
                hotSearchFlex.addView(itemView)
            }
        })

        notificationsViewModel.commonUseLiveData.observe(this, Observer {
            it.forEachIndexed { index, friendData ->
                val itemView = createTagView(index, friendData)
                commonUseFlex.addView(itemView)
            }
        })
    }

    private fun createTagView(position: Int, friendData: FriendData): TextView {
        var random: Random = Random()
        val r = random.nextInt(256)
        val g = random.nextInt(256)
        val b = random.nextInt(256)

        val textView = TextView(context)
        textView.gravity = Gravity.CENTER
        textView.text = friendData.name
        textView.setTextColor(resources.getColor(R.color.black))
        textView.setBackgroundColor(Color.rgb(r, g, b))
        textView.setTextSize(14F)
        textView.setTag(position)
        textView.setPadding(30, 20, 30, 20)

        val layoutParams = FlexboxLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(20, 20, 20, 20)
        textView.layoutParams = layoutParams

        textView.setOnClickListener {
            if (!TextUtils.isEmpty(friendData.link)) {
                Intent(activity, ArticleContentActivity::class.java).run {
                    putExtra(Constant.CONTENT_URL_KEY, friendData.link)
                    startActivity(this)
                }
            } else {
                Toast.makeText(context, "link empty", Toast.LENGTH_SHORT).show()
            }
        }
        return textView
    }

    private fun setStatusColor(color: Int) {
        val window = activity?.getWindow()
        window!!.statusBarColor = color
    }
}