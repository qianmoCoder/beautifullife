package com.ddu.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by VK on 2017/2/14.<br></br>
 * -
 */
class FadingEdgeRecyclerView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        // 列表是否垂直
        isVerticalFadingEdgeEnabled = true
        // 阴影高度
        setFadingEdgeLength(500)
    }

    // 顶部阴影强度，这里用系统的默认效果，所以没有重写
    override fun getTopFadingEdgeStrength(): Float {
        return super.getTopFadingEdgeStrength()
    }

    //  底部阴影强度，这里不需要，所以设置为0f
    override fun getBottomFadingEdgeStrength(): Float {
        return 0f
    }
}