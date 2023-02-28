package com.carl.views.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentActivity
import com.carl.views.R


/**
 * @author xiongke
 * @date 2019/5/23
 */
abstract class BaseDialog : AppCompatDialogFragment() {
    private val defaultDim = 0.2f
    private val fragmentTag: String by lazy { javaClass.simpleName }
    protected val mContext by lazy { this.requireContext() }
    protected val mActivity by lazy { this.requireActivity() }
    protected val mFragment by lazy { this }
    protected var mDialog: Dialog? = null

    override fun onStart() {
        super.onStart()
        mDialog = this.dialog
        this.initDialogParamData()
        this.mDialog?.window?.attributes?.apply {
            this.dimAmount = dimAmount()
            this.width = getWidth()
            this.height = getHeight()
            this.gravity = getGravity()
            this@BaseDialog.dialog?.window?.attributes = this
        }
        this.dialog?.setOnKeyListener(object : DialogInterface.OnKeyListener {
            override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return isCloseBackKey()
                }
                return false
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, getDialogTheme())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDialog = this.dialog
        this.dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        this.dialog?.setCanceledOnTouchOutside(getCancelOutside())
        val view = getRootView()
        initFragmentParamData()
        bindView(view)
        return view
    }

    protected abstract fun getRootView(): View
    protected abstract fun bindView(v: View)
    protected open fun getDialogTheme() = R.style.BottomDialogTheme
    protected open fun initDialogParamData() = Unit
    protected open fun initFragmentParamData() = Unit
    protected open fun getWidth() = WindowManager.LayoutParams.WRAP_CONTENT
    protected open fun getHeight() = WindowManager.LayoutParams.WRAP_CONTENT
    protected open fun dimAmount() = defaultDim
    protected open fun getCancelOutside() = true
    protected open fun isCloseBackKey() = false
    protected open fun getGravity() = Gravity.BOTTOM

    fun show(mActivity: FragmentActivity) {
        show(mActivity.supportFragmentManager, fragmentTag)
    }
}

