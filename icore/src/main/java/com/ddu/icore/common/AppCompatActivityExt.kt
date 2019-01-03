package com.ddu.icore.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.replaceFragmentInActivity(fragment: androidx.fragment.app.Fragment, frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

fun AppCompatActivity.addFragmentToActivity(fragment: androidx.fragment.app.Fragment, tag: String) {
    supportFragmentManager.transact {
        add(fragment, tag)
    }
}

//inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel() =
//        ViewModelProviders.of(this).get(T::class.java)

private inline fun androidx.fragment.app.FragmentManager.transact(action: androidx.fragment.app.FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}