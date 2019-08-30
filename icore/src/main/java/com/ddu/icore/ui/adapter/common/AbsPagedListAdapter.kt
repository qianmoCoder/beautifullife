package com.ddu.icore.ui.adapter.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import com.ddu.icore.callback.Consumer1

/**
 * Created by yzbzz on 2019-08-22.
 */
abstract class AbsPagedListAdapter<T>(
    ctx: FragmentActivity,
    diffCallback: DiffUtil.ItemCallback<T>
) :
    PagedListAdapter<T, ViewHolder>(diffCallback) {

    private val liveData: LiveData<PagedList<T>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .setPrefetchDistance(5)
//            .setInitialLoadSizeHint(20)
            .build()

        liveData = LivePagedListBuilder(MyDataSourceFactory(), config).build()
        liveData.observe(ctx, Observer {
            submitList(it)
        })
    }

    val mContext = ctx

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(getLayoutId(), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindView(holder, getItem(position), position)
    }

    inner class MyDataSourceFactory : DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> {
            return MyDataSource()
        }
    }

    inner class MyDataSource : PageKeyedDataSource<Int, T>() {

        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, T>
        ) {
            consumer(1, object : Consumer1<List<T>> {
                override fun accept(t: List<T>) {
                    val size = params.requestedLoadSize
                    callback.onResult(t, 1, 2)
                }
            })
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
            consumer(params.key.inc(), object : Consumer1<List<T>> {
                override fun accept(t: List<T>) {
                    callback.onResult(t, params.key.inc())
                }
            })
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        }

    }

    fun getLiveData(): LiveData<PagedList<T>> {
        return liveData
    }


    abstract fun getLayoutId(): Int

    abstract fun bindView(viewHolder: ViewHolder, data: T?, position: Int)

    abstract fun consumer(index: Int, call: Consumer1<List<T>>)

}