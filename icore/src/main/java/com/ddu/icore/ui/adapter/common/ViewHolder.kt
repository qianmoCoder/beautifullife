package com.ddu.icore.ui.adapter.common

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ddu.icore.common.find

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mViews: SparseArray<View> = SparseArray()

    fun <T : View> getView(viewId: Int): T? {
        var view = mViews.get(viewId)
        if (null == view) {
            view = itemView.find(viewId)
            mViews.append(viewId, view)
        }
        return view as? T
    }

    fun setText(viewId: Int, text: String): ViewHolder {
        val tv = getView<TextView>(viewId)
        tv?.text = text
        return this
    }

    fun setText(viewId: Int, resId: Int): ViewHolder {
        val tv = getView<TextView>(viewId)
        tv?.setText(resId)
        return this
    }

    fun setTextColor(viewId: Int, color: Int): ViewHolder {
        val tv = getView<TextView>(viewId)
        tv?.setTextColor(color)
        return this
    }

    fun setText(viewId: Int, charSequence: CharSequence): ViewHolder {
        val tv = getView<TextView>(viewId)
        tv?.text = charSequence
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): ViewHolder {
        val view = getView<ImageView>(viewId)
        view?.setImageResource(resId)
        return this
    }

    fun setImageBitmap(viewId: Int, bitmap: Bitmap): ViewHolder {
        val view = getView<ImageView>(viewId)
        view?.setImageBitmap(bitmap)
        return this
    }

    fun setImageDrawable(viewId: Int, drawable: Drawable): ViewHolder {
        val view = getView<ImageView>(viewId)
        view?.setImageDrawable(drawable)
        return this
    }

    fun setChecked(viewId: Int, checked: Boolean): ViewHolder {
        val radioButton = getView<RadioButton>(viewId)
        radioButton?.isChecked = checked
        return this
    }

    fun setBackground(viewId: Int, background: Drawable): ViewHolder {
        val view = getView<View>(viewId)
        view?.background = background
        return this
    }

    fun setBackground(viewId: Int, color: Int): ViewHolder {
        val view = getView<View>(viewId)
        view?.setBackgroundColor(color)
        return this
    }

    fun setVisibility(viewId: Int, visibility: Int): ViewHolder {
        val view = getView<View>(viewId)
        view?.visibility = visibility
        return this
    }

    fun setOnClickListener(viewId: Int, listener: View.OnClickListener): ViewHolder {
        val view = getView<View>(viewId)
        view?.setOnClickListener(listener)
        return this
    }

    fun setOnClickListener(listener: View.OnClickListener): ViewHolder {
        itemView.setOnClickListener(listener)
        return this
    }

}
