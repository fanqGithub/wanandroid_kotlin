package com.fanqi.wankt.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.fanqi.wankt.R
import com.fanqi.wankt.base.Preference
import com.fanqi.wankt.common.bean.DataX
import com.fanqi.wankt.constant.Constant
import com.fanqi.wankt.ui.ArticleContentActivity
import com.fanqi.wankt.ui.login.LoginActivity
import com.fanqi.wankt.ui.home.paging.HomePagingAdapter
import com.fanqi.wankt.utils.BannerImageLoader
import com.fanqi.wankt.utils.ColorInfo
import com.fanqi.wankt.utils.Logger
import com.fanqi.wankt.utils.toast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.youth.banner.Banner
import com.youth.banner.BannerConfig


//https://androidwave.com/android-paging-library/
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    //    private lateinit var homeAdapter: HomeAdapter
    private lateinit var listData: ArrayList<DataX>
    private lateinit var recyclerView: RecyclerView
    private lateinit var homePagingAdapter: HomePagingAdapter
    private lateinit var banner: Banner
    private lateinit var listBannerUrl: ArrayList<String>
    private lateinit var ivBannerHeadBg: ImageView
    private lateinit var imageLoader: BannerImageLoader
    private var isInit: Boolean = true
    private var count: Int = 0
    private lateinit var refreshLayout: SmartRefreshLayout

    private var colorList = arrayListOf<ColorInfo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
                //Fragment绑定ViewModel , of(getActivity())与of(this)的区别
                //mViewModel = ViewModelProviders.of(getActivity()).get(DemoViewModel.class);
                // 使用getActivity()获得的ViewModel 作用域在Activity里和所有他创建碎片的里,意思是你在其他Fragment也获取相同内存地址的ViewModel
                //mViewModel = ViewModelProviders.of(this).get(DemoViewModel.class);
                // 这个ViewModel是独立的,只为这个Fragment单独服务,其他Fragment无法获取到相同内存地址的ViewModel
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = root.findViewById(R.id.recycler_view)
        ivBannerHeadBg = root.findViewById(R.id.iv_banner_head_bg)
//        swipRefresh = root.findViewById(R.id.swipeRefresh)
        refreshLayout = root.findViewById(R.id.refreshLayout)
        refreshLayout.setEnableLoadMore(false)
        refreshLayout.setRefreshHeader(ClassicsHeader(context))

        banner = root.findViewById(R.id.banner)

        init()

        //---------传统的recyclerview使用
//        homeViewModel.initData()
//
//        //写法一
//        homeViewModel.homeData.observe(this, Observer {
//            listData.addAll(it)
//            homeAdapter.notifyDataSetChanged()
//            Logger.d(listData.size.toString())
//        })

        //写法二：完整的写，应该是这样的
//        homeViewModel.homeData.observe(this,object :Observer<List<DataX>>{
//            override fun onChanged(t: List<DataX>?) {
//
//            }
//
//        })
        refreshLayout.setOnRefreshListener {
            it.finishRefresh(2000)
        }

        return root
    }

    fun init() {
        colorList.clear()
        listBannerUrl = arrayListOf()
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner.setOnBannerListener {
            Intent(activity, ArticleContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, listBannerUrl[it])
                startActivity(this)
            }
        }
        banner.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Logger.d("onPageScrolled position:$position")
                var pst = position
                var nexPst = pst + 1
                if (pst == count - 1) {
                    nexPst = 0
                }
                val vibrantColor = ColorUtils.blendARGB(
                    imageLoader.getMutedLightColor(position),
                    imageLoader.getMutedLightColor(nexPst),
                    positionOffset
                )
                ivBannerHeadBg.setBackgroundColor(vibrantColor)
//                refreshLayout.setBackgroundColor(vibrantColor)
                setStatusColor(vibrantColor)
            }

            override fun onPageSelected(position: Int) {
                Logger.d("selected position:$position")
                if (isInit) {
                    isInit = false
                    Handler().postDelayed({
                        val vibrantColor = imageLoader.getMutedLightColor(0)
                        ivBannerHeadBg.setBackgroundColor(vibrantColor)
//                        refreshLayout.setBackgroundColor(vibrantColor)
                        setStatusColor(vibrantColor)
                    }, 100)

                }
            }

        })
        homeViewModel.loadBanner()
        homeViewModel.homeBannerLiveData.observe(this, Observer {
            var listTitles = arrayListOf<String>()
            var listImages = arrayListOf<String>()
            count = it.size
            for (item: com.fanqi.wankt.common.bean.Banner in it) {
                listTitles.add(item.title)
                listImages.add(item.imagePath)
                listBannerUrl.add(item.url)
                var colorInfo = ColorInfo()
                colorInfo.imgUrl = item.imagePath
                colorList.add(colorInfo)
            }

            imageLoader = BannerImageLoader(colorList)
            banner.setImageLoader(imageLoader)
            banner.setImages(listImages)
//            banner.setBannerTitles(listTitles)
            banner.isAutoPlay(true)
            banner.setDelayTime(3000)
            banner.start()
        })

        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        listData = arrayListOf()
//        homeAdapter = context?.let { HomeAdapter(listData, it) }!!

        //采用paginglib
        homePagingAdapter = HomePagingAdapter()
        homePagingAdapter.setHomeListCallBack(object : HomePagingAdapter.HomeListCallBack {
            override fun onFavoriteClick(position: Int, dataX: DataX) {
                Logger.d("click item=${dataX.toString()}")
                var userName: String by Preference(Constant.USER_NAME, "")
                if (userName.isEmpty()) {
                    Intent(activity, LoginActivity::class.java).run {
                        startActivity(this)
                    }
                } else {
//                    context?.let { toast("收藏开发中...", it) }
                    var isCollect = !dataX.collect
                    dataX.collect = isCollect
                    homePagingAdapter.notifyDataSetChanged()
                    if (isCollect) {
                        homeViewModel.collectArt(dataX.id)
                    } else {
                        homeViewModel.removeCollect(dataX.id)
                    }
                }
            }

            override fun onItemClick(position: Int, dataX: DataX) {
                Logger.d(dataX.toString())
                Intent(activity, ArticleContentActivity::class.java).run {
                    putExtra(Constant.CONTENT_URL_KEY, dataX.link)
                    putExtra(Constant.CONTENT_ID_KEY, dataX.id)
                    putExtra(Constant.CONTENT_TITLE_KEY, dataX.title)
                    startActivity(this)
                }
            }
        })
        homeViewModel.homePagList.observe(this, Observer {
            //            swipRefresh.isRefreshing = false
            homePagingAdapter.submitList(it)
        })

        recyclerView.adapter = homePagingAdapter
    }

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay();
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }

    fun setStatusColor(color: Int) {
        val window = activity?.getWindow()
        window!!.statusBarColor = color
    }

}