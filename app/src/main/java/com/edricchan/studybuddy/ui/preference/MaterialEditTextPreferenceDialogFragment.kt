package com.edricchan.studybuddy.ui.preference

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.preference.EditTextPreferenceDialogFragmentCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.takisoft.preferencex.EditTextPreference
import com.takisoft.preferencex.PreferenceFragmentCompat

// Code from https://stackoverflow.com/a/61424867
class MaterialEditTextPreferenceDialogFragment : EditTextPreferenceDialogFragmentCompat() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mWhichButtonClicked = DialogInterface.BUTTON_NEGATIVE
        val builder = MaterialAlertDialogBuilder(requireActivity())
            .setTitle(preference.dialogTitle)
            .setIcon(preference.dialogIcon)
            .setPositiveButton(preference.positiveButtonText, this)
            .setNegativeButton(preference.negativeButtonText, this)
        val contentView = onCreateDialogView(requireActivity())
        if (contentView != null) {
            onBindDialogView(contentView)
            builder.setView(contentView)
        } else {
            builder.setMessage(preference.dialogMessage)
        }
        onPrepareDialogBuilder(builder)

        return builder.create()
    }

    /* Override the methods that access mWhichButtonClicked (because we cannot set it properly here) */

    /** Which button was clicked.  */
    private var mWhichButtonClicked = 0

    override fun onClick(dialog: DialogInterface, which: Int) {
        mWhichButtonClicked = which
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDialogClosedWasCalledFromOnDismiss = true
        super.onDismiss(dialog)
    }

    private var onDialogClosedWasCalledFromOnDismiss = false

    override fun onDialogClosed(positiveResult: Boolean) {
        if (onDialogClosedWasCalledFromOnDismiss) {
            onDialogClosedWasCalledFromOnDismiss = false
            // this means the positiveResult needs to be calculated from our mWhichButtonClicked
            super.onDialogClosed(mWhichButtonClicked == DialogInterface.BUTTON_POSITIVE)
        } else {
            super.onDialogClosed(positiveResult)
        }
    }
}

fun PreferenceFragmentCompat.showEditTextPreferenceDialog(preference: EditTextPreference) {
    val dialogFragment = MaterialEditTextPreferenceDialogFragment().apply {
        arguments = Bundle(1).apply {
            putString("key", preference.key)
        }
    }
    dialogFragment.apply {
        setTargetFragment(this@showEditTextPreferenceDialog, 0)
        show(
            this@showEditTextPreferenceDialog.parentFragmentManager,
            "androidx.preference.PreferenceFragment.DIALOG"
        )
    }
}