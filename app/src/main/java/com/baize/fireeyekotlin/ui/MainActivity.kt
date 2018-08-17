package com.baize.fireeyekotlin.ui

import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.databinding.ActivityMainBinding
import com.baize.fireeyekotlin.http.DefaultSubscriber
import com.baize.fireeyekotlin.http.reqs.ReqoQsbk
import com.baize.fireeyekotlin.mvvm.model.QsbkListBean
import com.baize.fireeyekotlin.test.TestFragment
import com.baize.fireeyekotlin.ui.home.HomeFragment
import com.baize.fireeyekotlin.utils.DebugUtil
import com.baize.fireeyekotlin.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * TODO 多余的四个选中状态图标待删除
 */
class MainActivity : AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener {
    private var bindingView: ActivityMainBinding? = null

    private val fragments = ArrayList<Fragment>()
    private var homeFragment: Fragment? = null
    private var findFragment: Fragment? = null
    private var hotFragment: Fragment? = null
    private var mineFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        initView()
        initToolbar()
        setDefaultFragment()
    }

    private fun initView() {
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED)
        bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        bottom_navigation_bar
                .addItem(BottomNavigationItem(R.drawable.home_normal, "首页"))
                .addItem(BottomNavigationItem(R.drawable.find_normal, "发现"))
                .addItem(BottomNavigationItem(R.drawable.hot_normal, "热门"))
                .addItem(BottomNavigationItem(R.drawable.mine_normal, "我的"))
                .setActiveColor(R.color.black)
                .setFirstSelectedPosition(0)
                .initialise()

        bottom_navigation_bar.setTabSelectedListener(this)
    }

    //一开始设置默认的Fragment
    private fun setDefaultFragment() {
        var fm = getSupportFragmentManager()
        var transaction = fm.beginTransaction()
        homeFragment = HomeFragment()
        fragments.add(homeFragment as HomeFragment)
        transaction.replace(R.id.fl_content, homeFragment)
        transaction.commit()
    }

    //隐藏列表中碎片
    private fun hideFragment(transaction: FragmentTransaction) {
        fragments.forEach { transaction.hide(it) }
    }

    private fun initToolbar() {
        var today = getToday()
        tv_bar_title.text = today
        tv_bar_title.typeface = Typeface.createFromAsset(this.assets, "fonts/Lobster-1.4.otf")
        iv_search.setOnClickListener {
            showToast("搜索")
            ReqoQsbk.instance.getQsbk(1)
                    .subscribe(object : DefaultSubscriber<QsbkListBean>() {
                        override fun _onError(errMsg: String) {
                            showToast(errMsg)
                            DebugUtil.e(msg = errMsg)
                        }

                        override fun _onNext(entity: QsbkListBean) {
                            DebugUtil.e(msg = entity.toString())
                            showToast(entity.items!![0].content.toString())
                        }
                    })
        }
    }

    private fun getToday(): String {
        var list = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        var data = Date()
        var calendar: Calendar = Calendar.getInstance()
        calendar.time = data
        var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0) {
            index = 0
        }
        return list[index]
    }

    override fun onTabSelected(position: Int) {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        hideFragment(transaction)
        when (position) {
            0 -> {
                tv_bar_title.text = getToday()
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)
                if (homeFragment == null) {
                    homeFragment = HomeFragment()
                    transaction.add(R.id.fl_content, homeFragment)
                    fragments.add(homeFragment as HomeFragment)
                } else {
                    transaction.show(homeFragment)
                }
            }
            1 -> {
                tv_bar_title.text = "Discover"
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)
                if (findFragment == null) {
                    findFragment = TestFragment()
                    transaction.add(R.id.fl_content, findFragment)
                    fragments.add(findFragment as TestFragment)
                } else {
                    transaction.show(findFragment)
                }
            }
            2 -> {
                tv_bar_title.text = "Ranking"
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)
                if (hotFragment == null) {
                    hotFragment = TestFragment()
                    transaction.add(R.id.fl_content, hotFragment)
                    fragments.add(hotFragment as TestFragment)
                } else {
                    transaction.show(hotFragment)
                }
            }
            3 -> {
                tv_bar_title.visibility = View.INVISIBLE
                iv_search.setImageResource(R.drawable.icon_setting)
                if (mineFragment == null) {
                    mineFragment = TestFragment()
                    transaction.add(R.id.fl_content, mineFragment)
                    fragments.add(mineFragment as TestFragment)
                } else {
                    transaction.show(mineFragment)
                }
            }
        }
        transaction.commit()
    }

    override fun onTabReselected(position: Int) {
    }

    override fun onTabUnselected(position: Int) {
    }

}
