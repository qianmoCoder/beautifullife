package com.ddu.ui.fragment.life

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter
import com.ddu.icore.ui.adapter.common.ViewHolder
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.fragment.work.FragmentA
import com.ddu.ui.view.BottomView
import com.ddu.ui.view.DividerItemDecoration
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieDataSet
import kotlinx.android.synthetic.main.fragment_life_mortgage_item.*
import java.util.*

/**
 * Created by yzbzz on 16/4/21.
 */
class LoanFragment : DefaultFragment(), View.OnClickListener {

    private var mList: MutableList<String>? = null

    protected var mParties = arrayOf("a", "b", "c", "LogicActions")

    private var isEnabled = false

    override fun initData(savedInstanceState: Bundle?) {
        mList = ArrayList()
        for (i in 1..29) {
            mList!!.add(i.toString() + "年" + "(" + i * 12 + "期" + ")")
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_life_mortgage_item
    }

    override fun initView() {
        setDefaultTitle("房贷计算")

        rl_mortgage.setOnClickListener(this)
        btn_calculator.setOnClickListener(this)
        btn_calculator1.setOnClickListener(this)
    }

    private fun setData() {
        ll_mortgage.visibility = View.VISIBLE
        pc_mortgage.setUsePercentValues(true)
        pc_mortgage.setDescription("Hello")
        pc_mortgage.isDrawHoleEnabled = false
        //        pc_mortgage.setDragDecelerationFrictionCoef(0.95f);

        val yVals1 = ArrayList<Entry>()
        for (i in 0..2) {
            yVals1.add(Entry(30f, i))
        }

        val xVals = ArrayList<String>()
        for (i in 0..2) {
            xVals.add(mParties[i % mParties.size])
        }

        val colors = ArrayList<Int>()
        COLORFUL_COLORS.indices.forEach {
            colors.add(it)
        }

        val dataSet = PieDataSet(yVals1, "Biu")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.colors = colors

        //        PieData data = new PieData(xVals, dataSet);
        //        data.setValueFormatter(new PercentFormatter());
        //        data.setValueTextSize(11f);
        //        data.setValueTextColor(Color.WHITE);
        //
        //        pc_mortgage.setData(data);
        //        pc_mortgage.highlightValue(null);
        //
        //        pc_mortgage.invalidate();
        //
        //
        //        Legend l = pc_mortgage.getLegend();
        //        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        ////        l.setXEntrySpace(7f);
        ////        l.setYEntrySpace(0f);
        //        l.setYOffset(10f);
    }

    override fun onClick(v: View?) {
        when (view?.id) {
            R.id.rl_mortgage -> showBottomDialog()
            R.id.btn_calculator -> setData()
            R.id.btn_calculator1 -> {
                btn_calculator!!.isEnabled = isEnabled
                isEnabled = !isEnabled
                startFragment(FragmentA::class.java)
            }
            else -> {
            }
        }
    }

    private fun showBottomDialog() {
        val dialog = BottomView(baseActivity)
        val view = LayoutInflater.from(baseActivity).inflate(R.layout.fragment_life_mortgage_bottom, null)
        val recyclerView = view.findViewById<View>(R.id.rv_bottom) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(baseActivity)

        val defaultRecycleViewAdapter = object : DefaultRecycleViewAdapter<String>(baseActivity, mList) {
            override fun getLayoutId(viewType: Int): Int {
                return R.layout.rv_item_linear
            }

            override fun bindView(viewHolder: ViewHolder, data: String, position: Int) {
                viewHolder.setText(R.id.tv_title, data)
                viewHolder.setOnClickListener(R.id.ll_detail, View.OnClickListener {
                    dialog.dismissBottomView()
                    tv_loan.text = data
                })
            }
        }
        recyclerView.adapter = defaultRecycleViewAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(baseActivity, DividerItemDecoration.VERTICAL))
        dialog.setContentView(view)
        dialog.setAnimation(R.style.BottomToTopAnim)
        dialog.showBottomView(true)
    }


    override fun isShowTitleBar(): Boolean {
        return false
    }

    companion object {

        val COLORFUL_COLORS = intArrayOf(Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0), Color.rgb(106, 150, 31), Color.rgb(179, 100, 53))
    }
}
