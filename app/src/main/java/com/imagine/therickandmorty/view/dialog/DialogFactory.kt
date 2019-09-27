package com.imagine.readtheposts.view.dialog

class DialogFactory {
    fun getBaseDialog(
        title: String,
        text: String,
        okButton: String,
        cancelButton: String,
        timer: Boolean,
        positiveListener: DialogPositiveListener?,
        negativeListener: DialogNegativeListener?
    ): BaseAlertDialog {
        val baseDialog = BaseAlertDialog()
        baseDialog.setTitle(title)
        baseDialog.setMessage(text)
        baseDialog.setOkText(okButton)
        baseDialog.setTimer(timer)
        baseDialog.setCancelText(cancelButton)
        baseDialog.setPositiveListener(positiveListener)
        baseDialog.setNegativeListener(negativeListener)
        return baseDialog
    }
}