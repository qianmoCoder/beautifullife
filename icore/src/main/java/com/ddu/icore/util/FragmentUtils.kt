package com.ddu.icore.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by liuhongzhe on 16/7/15.
 */
object FragmentUtils {

    val FRAGMENT_ADD = 1
    val FRAGMENT_REPLACE = 2
    val FRAGMENT_ADD_TO_BACK_STACK = 3

    fun addFragment(fragmentManage: FragmentManager, fragment: Fragment, frameId: Int) {
        attachFragment(FRAGMENT_ADD, fragmentManage, fragment, frameId)
    }

    fun replaceFragment(fragmentManage: FragmentManager, fragment: Fragment, frameId: Int) {
        attachFragment(FRAGMENT_REPLACE, fragmentManage, fragment, frameId)
    }

    fun addToBackStackFragment(fragmentManage: FragmentManager, fragment: Fragment, frameId: Int) {
        attachFragment(FRAGMENT_ADD_TO_BACK_STACK, fragmentManage, fragment, frameId)
    }

    fun attachFragment(type: Int, fragmentManager: FragmentManager, fragment: Fragment, frameId: Int) {
        attachFragment(type, fragmentManager.beginTransaction(), fragment, frameId)
    }

    fun attachFragment(type: Int, ft: androidx.fragment.app.FragmentTransaction, fragment: Fragment, frameId: Int) {
        val tag = fragment.javaClass.name
        when (type) {
            FRAGMENT_ADD -> ft.add(frameId, fragment, tag)
            FRAGMENT_REPLACE -> ft.replace(frameId, fragment, tag)
            FRAGMENT_ADD_TO_BACK_STACK -> {
                ft.add(frameId, fragment, tag)
                ft.setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ft.addToBackStack(null)
            }
            else -> ft.replace(frameId, fragment, tag)
        }
        ft.commitAllowingStateLoss()
    }

}
