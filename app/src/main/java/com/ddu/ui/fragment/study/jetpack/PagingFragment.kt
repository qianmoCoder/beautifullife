package com.ddu.ui.fragment.study.jetpack

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ddu.icore.common.ext.act
import com.ddu.icore.common.ext.ctx
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_jetpack_paging.*


/**
 * Created by yzbzz on 2019/1/24.
 */
@IElement("Jetpack")
class PagingFragment : DefaultFragment() {

    override fun getLayoutId() = com.ddu.R.layout.fragment_jetpack_paging

    override fun initView() {
        val myAdapter = MyAdapter()
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .build()

        val liveData = LivePagedListBuilder(MyDataSourceFactory(), config).build()
        liveData.observe(this, Observer {
            myAdapter.submitList(it)
        })

        rv_paging.layoutManager = LinearLayoutManager(act)
        rv_paging.adapter = myAdapter
    }

    inner class MyDataSourceFactory : DataSource.Factory<Int, DataBean>() {
        override fun create(): DataSource<Int, DataBean> {
            return MyDataSource()
        }

    }

    inner class MyDataSource : ItemKeyedDataSource<Int, DataBean>() {
        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<DataBean>
        ) {
            callback.onResult(loadData(0, params.requestedLoadSize))
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<DataBean>) {
            callback.onResult(loadData(params.key, params.requestedLoadSize))
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<DataBean>) {
        }

        override fun getKey(item: DataBean): Int {
            return item.id
        }
//        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<DataBean>) {
//            callback.onResult(loadData(params.startPosition, 10))
//        }
//
//        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<DataBean>) {
//            callback.onResult(loadData(0, 10), 0, 10)
//        }
    }

    inner class DataBean {
        var id: Int = 0
        var data: String = ""
    }

    fun loadData(startPosition: Int, count: Int): List<DataBean> {
        Thread.sleep(2000)
        val list = mutableListOf<DataBean>()
        for (i in 0 until count) {
            val dataBean = DataBean()
            dataBean.id = startPosition + i
            dataBean.data = "测试的内容=${ dataBean.id}"
            list.add(dataBean)
        }

        return list
    }

    private inner class MyAdapter : PagedListAdapter<DataBean, MyViewHolder>(mDiffCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(ctx)
                .inflate(android.R.layout.simple_list_item_2, null)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data = getItem(position)
            holder.text1.text = position.toString()
            holder.text2.setText(data?.data)
        }
    }

    private val mDiffCallback = object : DiffUtil.ItemCallback<DataBean>() {
        override fun areItemsTheSame(@NonNull oldItem: DataBean, @NonNull newItem: DataBean): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(@NonNull oldItem: DataBean, @NonNull newItem: DataBean): Boolean {
            return oldItem === newItem
        }
    }

    private inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text1: TextView
        var text2: TextView

        init {

            text1 = itemView.findViewById(android.R.id.text1)
            text1.setTextColor(Color.RED)

            text2 = itemView.findViewById(android.R.id.text2)
            text2.setTextColor(Color.BLUE)
        }
    }

}