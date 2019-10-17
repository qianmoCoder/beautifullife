package com.ddu.icore

import com.ddu.icore.provider.EmptyProvider

/**
 * Created by yzbzz on 2019-10-17.
 */
class ICoreInitProvider: EmptyProvider() {

    override fun onCreate(): Boolean {
        ICore.init(context)
        return true
    }

}