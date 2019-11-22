package com.ddu.ui.fragment.study.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ddu.R
import com.ddu.help.TabLayoutHelper
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_ui_tab_layout.*


/**
 * Created by lhz on 16/5/6.
 */
@IElement("UI")
class TabLayoutFragment : DefaultFragment() {

    override fun getLayoutId() = R.layout.fragment_ui_tab_layout

    val fragmentList = arrayListOf<Fragment>()
    val titles = arrayOf("体育新闻", "新闻新闻", "好看福利", "新闻新闻")
    lateinit var tagFragmentAdapter: TabFragmentAdapter

    override fun initView() {
        fragmentList.add(TabFragment.getInstance(Color.BLUE));
        fragmentList.add(TabFragment.getInstance(Color.GREEN));
        fragmentList.add(TabFragment.getInstance(Color.YELLOW));
        fragmentList.add(TabFragment.getInstance(Color.BLUE));

        tagFragmentAdapter = TabFragmentAdapter(childFragmentManager, fragmentList, titles)
        view_pager.setNoScroll(true)
        view_pager.adapter = tagFragmentAdapter
        tb_bar.setupWithViewPager(view_pager)
        (titles.indices).forEach {
            tb_bar.getTabAt(it)?.setCustomView(R.layout.fragment_common_tab_item)
        }
//        tb_bar.tabMode = TabLayout.MODE_SCROLLABLE
//        setTabLayout()
    }

    fun setTabLayout() = TabLayoutHelper.Builder(tb_bar)
            .setIndicatorColor(Color.BLUE)
            .setIndicatorHeight(6)
            .setIndicatorWith(100)
            .setTabItemMarginLeft(20)
            .setIndicatorDrawable(R.drawable.bg_tab_red)
            .setNormalTextColor(Color.GRAY)
            .setSelectedTextColor(Color.RED)
            .setSelectedBold(true)
            .setIndicatorMargin(40)
//                .setTabItemWith(300)
            .setTabItemPadding(20)
//                .setSelectedBackgroundColor(Color.YELLOW)
//                .setNormalBackgroundColor(Color.DKGRAY)
            .setTabItemMarginLeft(20)
            .build();


    override fun isShowTitleBar(): Boolean {
        return false
    }

    companion object {

        fun newInstance(taskId: String): TabLayoutFragment {
            val arguments = Bundle()
            arguments.putString("ARGUMENT_TASK_ID", taskId)
            val fragment = TabLayoutFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    class TabFragment : Fragment() {

        @Nullable
        override fun onCreateView(@NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View {
            val view: View = inflater.inflate(R.layout.fragment_tab, container, false)
            val arguments: Bundle? = arguments
            if (arguments != null) {
                val color = arguments.getInt(KEY)
                view.setBackgroundColor(color)
            }
            return view
        }

        companion object {
            private const val KEY = "key"
            fun getInstance(backGroundColor: Int): TabFragment {
                val bundle = Bundle()
                bundle.putInt(KEY, backGroundColor)
                val tabFragment = TabFragment()
                tabFragment.arguments = bundle
                return tabFragment
            }
        }
    }

    class TabFragmentAdapter(fm: FragmentManager, private val fragments: List<Fragment>, private val titles: Array<String>) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

    }
}
