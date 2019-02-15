package com.android.slatestudio.test.utils

import android.app.Activity
import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.android.slatestudio.test.R
import com.android.slatestudio.test.ui.base.BaseMvpActivity
import javax.inject.Inject

class DialogHelper @Inject
constructor() {
    private var dlg: AlertDialog? = null
    private var dialogOpened: Boolean = false

    fun showAlert(
        ctx: Context, dialogTitle: String? = null, dialogMessage: String? = null,
        positiveButton: String = ctx.getString(android.R.string.ok), negativeButton: String? = null,
        cancelable: Boolean = true,
        onPositiveClick: ((instance: DialogHelper) -> Unit)? = null,
        onNegativeClick: ((instance: DialogHelper) -> Unit)? = { closeDialog() }
    ) {
        if (ctx is Activity && ctx.isFinishing) {
            return
        }
        closeDialog()

        val builder = AlertDialog.Builder(ctx)
        val alertDialogView = View.inflate(ctx, R.layout.dialog_fully_custom, null)

        val title = alertDialogView.findViewById<TextView>(R.id.tvDialogTitle)
        if (dialogTitle != null) {
            title.text = dialogTitle
        } else {
            title.visibility = View.GONE
        }

        val message = alertDialogView.findViewById<TextView>(R.id.tvDialogMessage)
        if (dialogMessage != null) {
            message.text = dialogMessage
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                message.text = Html.fromHtml(dialogMessage, Html.FROM_HTML_MODE_LEGACY)
            } else {
                message.text = Html.fromHtml(dialogMessage)
            }
            message.movementMethod = TextViewLinkHandler.getInstance()
        }

        val btnPositive = alertDialogView.findViewById<TextView>(R.id.btnDialogPositive)
        btnPositive.text = positiveButton
        if (onPositiveClick != null) {
            btnPositive.setOnClickListener {
                onPositiveClick.invoke(this)
                closeDialog()
            }
        } else {
            btnPositive.setOnClickListener { closeDialog() }
        }

        val btnNegative = alertDialogView.findViewById<TextView>(R.id.btnDialogNegative)
        if (onNegativeClick != null && negativeButton != null) {
            btnNegative.text = negativeButton
            btnNegative.setOnClickListener { onNegativeClick.invoke(this) }
        } else {
            btnNegative.visibility = View.GONE
        }

        builder.setView(alertDialogView)
        builder.setCancelable(cancelable)
        builder.setOnCancelListener { closeDialog() }
        dlg = builder.create()
        openDialog()
    }

    fun showCustomDialog(
        ctx: Context, customView: View, cancelable: Boolean = false,
        positiveButton: String? = null, negativeButton: String? = null,
        onPositiveClick: ((instance: DialogHelper) -> Unit)? = null,
        onNegativeClick: ((instance: DialogHelper) -> Unit)? = null,
        themeResId: Int = NO_THEME
    ) {
        if (ctx is BaseMvpActivity && !ctx.isActive) {
            return
        }
        closeDialog()

        val builder = getDialogBuilder(ctx, themeResId)
        builder.setView(customView)
        builder.setCancelable(cancelable)

        if (!TextUtils.isEmpty(positiveButton) && onPositiveClick != null) {
            builder.setPositiveButton(positiveButton) { _, _ ->
                onPositiveClick.invoke(this)
            }
        } else {
            builder.setPositiveButton(positiveButton) { _, _ -> closeDialog() }
        }

        if (!TextUtils.isEmpty(negativeButton) && onNegativeClick != null) {
            builder.setNegativeButton(negativeButton) { _, _ ->
                onNegativeClick.invoke(this)
            }
        }

        builder.setOnCancelListener { closeDialog() }
        dlg = builder.create()
        openDialog()
    }

    fun closeDialog() {
        if (this.dialogOpened && dlg != null) {
            try {
                dlg!!.dismiss()
                dlg = null
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
        this.dialogOpened = false
    }

    private fun openDialog() {
        if (!this.dialogOpened && dlg != null) {
            try {
                this.dialogOpened = true
                dlg!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getDialogBuilder(ctx: Context, themeResId: Int): AlertDialog.Builder {
        return when (themeResId) {
            NO_THEME -> android.support.v7.app.AlertDialog.Builder(ctx)
            TRANS_THEME -> android.support.v7.app.AlertDialog.Builder(ctx, TRANS_THEME)
            else -> android.support.v7.app.AlertDialog.Builder(ctx, themeResId)
        }
    }

    companion object {
        private val TAG = DialogHelper::class.java.name
        const val NO_THEME = -99
        const val TRANS_THEME = -98

        fun transparentBg(context: Context): Int {
            return ResourceUtil.getStyleId(context, "transparentBackgroundDialog")
        }
    }
}
