package com.ddu.ui.fragment.study.jetpack

import android.os.Message
import androidx.lifecycle.LifecycleOwner
import com.ddu.help.LifeCycleHandler1
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement


/**
 * Created by yzbzz on 2019/1/24.
 */
@IElement("Jetpack")
class HandleFragment1 : DefaultFragment() {

    override fun getLayoutId() = com.ddu.R.layout.fragment_jetpack_paging

    private lateinit var myHandler: MyLiHandle

    override fun initView() {
        myHandler = MyLiHandle(this)

        myHandler.sendEmptyMessageDelayed(1, 1000)
    }

    class MyLiHandle(owner: LifecycleOwner) : LifeCycleHandler1(owner) {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                sendEmptyMessageDelayed(1, 1000)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}