package com.ddu.icore.dialog

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddu.icore.R
import com.ddu.icore.entity.ShareEntity
import kotlinx.android.synthetic.main.fragment_share.*

class ShareDialogFragment : BottomDialogFragment(), View.OnClickListener {

    private var shareEntities: List<ShareEntity>? = null
    private var shareAdapter: ShareAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_share, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_cancel.setOnClickListener(this)

        var spanCount = SPAN_COUNT
        shareEntities?.apply {
            spanCount = if (size < SPAN_COUNT) {
                size % SPAN_COUNT
            } else {
                SPAN_COUNT
            }
        }

        val gridLayoutManager = GridLayoutManager(context, spanCount)
        rv_share.layoutManager = gridLayoutManager

        shareAdapter = ShareAdapter(context, shareEntities)
        rv_share.adapter = shareAdapter

        shareAdapter?.setOnItemClickListener(object : ShareAdapter.OnClickListener<ShareEntity> {
            override fun onClick(data: ShareEntity?, position: Int) {
                val url = data?.url
                if (!url.isNullOrEmpty()) {
//                    Navigator.startShowDetailActivity(ctx, WebViewFragment::class.java, bundle)
                }
            }
        })
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.tv_cancel) {
            dismissAllowingStateLoss()
        }
    }

    companion object {

        private const val SPAN_COUNT = 3

        fun newInstance(list: List<ShareEntity>): ShareDialogFragment {
            return ShareDialogFragment().apply {
                shareEntities = list
            }
        }
    }


}
