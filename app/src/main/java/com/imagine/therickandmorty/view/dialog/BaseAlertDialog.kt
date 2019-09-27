package com.imagine.readtheposts.view.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.imagine.therickandmorty.R

class BaseAlertDialog : DialogFragment() {
    private var timer = false
    private var title: String? = null
    private var okText: String? = null
    private var message: String? = null
    private var cancelText: String? = null

    private var positiveListener: DialogPositiveListener? = null
    private var negativeListener: DialogNegativeListener? = null

    private var alertDialog: AlertDialog? = null

    fun setTitle(title: String) {
        this.title = title
    }

    fun setTimer(timer: Boolean) {
        this.timer = timer
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun setOkText(okText: String?) {
        this.okText = okText
    }

    fun setCancelText(cancelText: String?) {
        this.cancelText = cancelText
    }

    fun setPositiveListener(positiveListener: DialogPositiveListener?) {
        this.positiveListener = positiveListener
    }

    fun setNegativeListener(negativeListener: DialogNegativeListener?) {
        this.negativeListener = negativeListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        alertDialog = build().create()

        context?.apply {
            alertDialog!!.setOnShowListener { dialogInterface ->
            alertDialog!!.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
            alertDialog!!.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
            }
        }

        alertDialog!!.setOnKeyListener { di, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (negativeListener != null) {
                    if (timer) {
                        dismiss()
                    } else {
                        negativeListener!!.onCancelClicked()
                    }
                }
            }
            true
        }
        alertDialog!!.setCanceledOnTouchOutside(false)
        return alertDialog!!
    }

    private fun build(): AlertDialog.Builder {
        val builder = if (TextUtils.isEmpty(cancelText))
            onlyPositiveButtonBuilder()
        else
            defaultBuilder()
        return builder
    }

    private fun defaultBuilder(): AlertDialog.Builder {
        return AlertDialog.Builder(activity!!)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                okText
            ) { di: DialogInterface, _: Int ->
                dismiss()
                if (positiveListener != null) {
                    positiveListener!!.onOkClicked()
                }
            }
            .setNegativeButton(
                cancelText
            ) { di: DialogInterface, _: Int ->
                dismiss()
                if (negativeListener != null) {
                    negativeListener!!.onCancelClicked()
                }
            }
    }

    private fun onlyPositiveButtonBuilder(): AlertDialog.Builder {
        return AlertDialog.Builder(activity!!)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                okText
            ) { di: DialogInterface, whichButton: Int ->
                dismiss()
                if (positiveListener != null) {
                    positiveListener!!.onOkClicked()
                }
            }
    }
}
