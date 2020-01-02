package com.fanqi.wankt.ui.mine

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import androidx.lifecycle.Observer

import com.fanqi.wankt.R
import com.fanqi.wankt.base.Preference
import com.fanqi.wankt.constant.Constant
import com.fanqi.wankt.ui.collection.MyCollectionsActivity
import com.fanqi.wankt.ui.login.LoginActivity
import com.fanqi.wankt.ui.login.LoginViewModel
import com.fanqi.wankt.utils.toast
import kotlinx.android.synthetic.main.mine_fragment.*

class MineFragment : Fragment(), View.OnClickListener {

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.userName -> {
                if (userNameTv.text.equals("点击登录")) {
                    Intent(activity, LoginActivity::class.java).run {
                        startActivity(this)
                    }
                }
            }
            R.id.collectionsView -> {
                if (userNameTv.text.equals("点击登录")) {
                    Intent(activity, LoginActivity::class.java).run {
                        startActivity(this)
                    }
                } else {
                    Intent(activity, MyCollectionsActivity::class.java).run {
                        startActivity(this)
                    }
                }
            }
        }
    }

    private lateinit var mineViewModel: MineViewModel
    private lateinit var userNameTv: TextView
    private lateinit var tvCoins: TextView
    private lateinit var tvCollections: TextView
    private lateinit var tvSort: TextView
    private lateinit var collectionView: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mineViewModel =
            ViewModelProviders.of(this).get(MineViewModel::class.java)

        val root = inflater.inflate(R.layout.mine_fragment, container, false)

        userNameTv = root.findViewById(R.id.userName)
        tvCoins = root.findViewById(R.id.tvCoins)
        tvCollections = root.findViewById(R.id.tvCollections)
        tvSort = root.findViewById(R.id.tvSort)
        collectionView = root.findViewById(R.id.collectionsView)
        userNameTv.setOnClickListener(this)
        collectionView.setOnClickListener(this)
        setStatusColor(resources.getColor(R.color.colorPrimary))
        initView()

        mineViewModel.initUserData()
        mineViewModel.mineData.observe(this, Observer {
            if (!it.userName.isEmpty()) {
                userNameTv.text = it.userName
            } else {
                userNameTv.text = "点击登录"
            }
        })

        mineViewModel.userCoinData.observe(this, Observer {
            if (it != null) {
                tvCoins.text = "${it.coinCount}"
                tvSort.text = "${it.rank}"
            } else {
                tvCoins.text = ""
                tvSort.text = ""
            }
        })

        return root
    }

    private fun initView() {
        userNameTv.text = "点击登录"
        tvCoins.text = ""
        tvSort.text = ""
    }

    private fun setStatusColor(color: Int) {
        val window = activity?.getWindow()
        window!!.statusBarColor = color
    }

}
