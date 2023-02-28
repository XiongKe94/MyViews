package com.carl.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import java.util.*

/**
 * @author Xiong Ke
 * @date 2018/1/18
 */

abstract class BasePreferencesHelper(protected var mContext: Context, prefFileName: String) {
    private val prefName by lazy { "preferences_$prefFileName" }
    private val mPrefs by lazy { mContext.getSharedPreferences(prefName, Context.MODE_PRIVATE) }

    private val editor: SharedPreferences.Editor
        @SuppressLint("CommitPrefEdits")
        get() = mPrefs.edit()

    val all: Map<String, *>
        get() = mPrefs.all

    operator fun contains(key: String): Boolean {
        return mPrefs.contains(key)
    }

    protected fun putAndApply(key: String, value: String) {
        putAndApply(editor.putString(key, value))
    }

    protected fun putAndApply(key: String, value: Int) {
        putAndApply(editor.putInt(key, value))
    }

    protected fun putAndApply(key: String, value: Float) {
        putAndApply(editor.putFloat(key, value))
    }


    protected fun putAndApply(key: String, value: Boolean) {
        putAndApply(editor.putBoolean(key, value))
    }

    protected fun putAndApply(key: String, value: Long) {
        putAndApply(editor.putLong(key, value))
    }

    protected fun putAndApply(key: String, value: Set<String>) {
        putAndApply(editor.putStringSet(key, value))
    }

    protected fun putAndApply(key: String, value: Array<String>) {
        putAndApply(editor.putStringSet(key, HashSet(listOf(*value))))
    }

    private fun putAndApply(editor: SharedPreferences.Editor) {
        SharedPreferencesCompat.apply(editor)
    }

    protected operator fun get(key: String, defValue: String): String {
        return mPrefs.getString(key, defValue)!!
    }

    protected operator fun get(key: String, defValue: Int): Int {
        return mPrefs.getInt(key, defValue)
    }

    protected operator fun get(key: String, defValue: Float): Float {
        return mPrefs.getFloat(key, defValue)
    }

    protected operator fun get(key: String, defValue: Boolean): Boolean {
        return mPrefs.getBoolean(key, defValue)
    }

    protected operator fun get(key: String, defValue: Long): Long {
        return mPrefs.getLong(key, defValue)
    }

    protected operator fun get(key: String, defValue: Set<String>): Set<String> {
        return mPrefs.getStringSet(key, defValue)!!
    }

    protected operator fun get(key: String): Array<String?> {
        val stringSet = mPrefs.getStringSet(key, HashSet())
        val strings = arrayOfNulls<String>(stringSet!!.size)
        stringSet.toTypedArray()
        return strings
    }

    fun remove(key: String) {
        val editor = editor
        editor.remove(key)
        SharedPreferencesCompat.apply(editor)
    }

    fun clear() {
        val editor = editor
        editor.clear()
        SharedPreferencesCompat.apply(editor)
    }

    private object SharedPreferencesCompat {
        fun apply(editor: SharedPreferences.Editor) {
            editor.apply()
            editor.commit()
        }
    }
}
