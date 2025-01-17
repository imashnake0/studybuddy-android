package com.edricchan.studybuddy.utils

import android.content.Context
import android.view.LayoutInflater
import com.edricchan.studybuddy.BuildConfig
import com.edricchan.studybuddy.R
import com.edricchan.studybuddy.databinding.VersionDialogBinding
import com.edricchan.studybuddy.extensions.dialog.showMaterialAlertDialog
import com.edricchan.studybuddy.ui.modules.main.MainActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Utilities for the UI (user interface).
 * @param context The context to be used for the class.
 */
class UiUtils(val context: Context) {
    /**
     * The [FloatingActionButton] set on the [BottomAppBar].
     */
    val bottomAppBarFab: FloatingActionButton? = if (context is MainActivity) {
        context.findViewById(R.id.fab)
    } else {
        null
    }

    /**
     * The [BottomAppBar] of the [MainActivity] layout.
     */
    val bottomAppBar: BottomAppBar? = if (context is MainActivity) {
        context.findViewById(R.id.bottomAppBar)
    } else {
        null
    }

    /**
     * Shows a version dialog.
     */
    fun showVersionDialog() {
        context.showMaterialAlertDialog {
            setView(
                VersionDialogBinding.inflate(LayoutInflater.from(context)).apply {
                    with(context.applicationInfo) {
                        appIconImageView.setImageDrawable(
                            context.packageManager.getApplicationIcon(
                                this
                            )
                        )
                        appNameTextView.text = loadLabel(context.packageManager)
                    }
                    appVersionTextView.text = BuildConfig.VERSION_NAME
                }.root
            )
        }
    }

    companion object {
        /**
         * Retrieves an instance of the class.
         * @param context The context to be used for the class.
         */
        fun getInstance(context: Context) = UiUtils(context)
    }
}
