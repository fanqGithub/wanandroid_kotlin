package com.fanqi.wankt.common.widget;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HeaderScrollHelper {

    private int sysVersion;         //当前sdk版本，用于判断api版本
    private ScrollableContainer mCurrentScrollableContainer;

    public HeaderScrollHelper() {
        sysVersion = Build.VERSION.SDK_INT;
    }

    /** 包含有 ScrollView ListView RecyclerView 的组件 */
    public interface ScrollableContainer {

        /** @return ScrollView ListView RecyclerView 或者其他的布局的实例 */
        View getScrollableView();
    }

    /**
     * 设置滚动组件，for parent fragment
     */
    public interface ScrollableContainerForParent {
        void setCurrentScrollableContainer(View scrollContainer);
    }

    public void setCurrentScrollableContainer(ScrollableContainer scrollableContainer) {
        this.mCurrentScrollableContainer = scrollableContainer;
    }

    private View getScrollableView() {
        if (mCurrentScrollableContainer == null) return null;
        return mCurrentScrollableContainer.getScrollableView();
    }

    /**
     * 判断是否滑动到顶部方法,ScrollAbleLayout根据此方法来做一些逻辑判断
     * 目前只实现了AdapterView,ScrollView,RecyclerView
     * 需要支持其他view可以自行补充实现
     */
    public boolean isTop() {
        View scrollableView = getScrollableView();
        if (scrollableView == null) {
            throw new NullPointerException("You should call ScrollableHelper.setCurrentScrollableContainer() to set ScrollableContainer.");
        }
        if (scrollableView instanceof AdapterView) {
            return isAdapterViewTop((AdapterView) scrollableView);
        }
        if (scrollableView instanceof ScrollView) {
            return isScrollViewTop((ScrollView) scrollableView);
        }
        if (scrollableView instanceof RecyclerView) {
            return isRecyclerViewTop((RecyclerView) scrollableView);
        }
        if (scrollableView instanceof WebView) {
            return isWebViewTop((WebView) scrollableView);
        }
        if (scrollableView instanceof NestedScrollView) {
            return isNestedScrollViewTop((NestedScrollView) scrollableView);
        }
        throw new IllegalStateException("scrollableView must be a instance of AdapterView|ScrollView|RecyclerView|WebView|NestedScrollView");
    }

    private boolean isRecyclerViewTop(RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                View childAt = recyclerView.getChildAt(0);
                if (childAt == null
                        || (firstVisibleItemPosition == 0 && (childAt.getTop() - layoutManager.getTopDecorationHeight(childAt)) == 0)) { //注意减掉getTopDecorationHeight，因为设置addItemDecoration是会影响到getTop值的
                    return true;
                }
            }
        /*    return !recyclerView.canScrollVertically(-1);*/
        }
        return false;
    }

    private boolean isAdapterViewTop(AdapterView adapterView) {
        if (adapterView != null) {
            int firstVisiblePosition = adapterView.getFirstVisiblePosition();
            View childAt = adapterView.getChildAt(0);
            if (childAt == null || (firstVisiblePosition == 0 && childAt.getTop() == 0)) {
                return true;
            }
        }
        return false;
    }

    private boolean isScrollViewTop(ScrollView scrollView) {
        if (scrollView != null) {
            int scrollViewY = scrollView.getScrollY();
            return scrollViewY <= 0;
        }
        return false;
    }

    private boolean isWebViewTop(WebView scrollView) {
        if (scrollView != null) {
            int scrollViewY = scrollView.getScrollY();
            return scrollViewY <= 0;
        }
        return false;
    }

    private boolean isNestedScrollViewTop(NestedScrollView scrollView) {
        if (scrollView != null) {
            int scrollViewY = scrollView.getScrollY();
            return scrollViewY <= 0;
        }
        return false;
    }

    /**
     * 将特定的view按照初始条件滚动
     *
     * @param velocityY 初始滚动速度
     * @param distance  需要滚动的距离
     * @param duration  允许滚动的时间
     */
    @SuppressLint("NewApi")
    public void smoothScrollBy(int velocityY, int distance, int duration) {
        View scrollableView = getScrollableView();
        if (scrollableView instanceof AbsListView) {
            AbsListView absListView = (AbsListView) scrollableView;
            if (sysVersion >= 21) {
                absListView.fling(velocityY);
            } else {
                absListView.smoothScrollBy(distance, duration);
            }
        } else if (scrollableView instanceof ScrollView) {
            ((ScrollView) scrollableView).fling(velocityY);
        } else if (scrollableView instanceof RecyclerView) {
            ((RecyclerView) scrollableView).fling(0, velocityY);
        } else if (scrollableView instanceof WebView) {
            ((WebView) scrollableView).flingScroll(0, velocityY);
        }
    }
}
