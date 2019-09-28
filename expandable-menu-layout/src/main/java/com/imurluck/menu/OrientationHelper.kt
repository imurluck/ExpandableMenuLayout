package com.imurluck.menu

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout

/**
 * used for
 * create by imurluck
 * create at 2019-09-28
 */
interface OrientationHelper {

    fun getMenuItemView(): View

    fun getMainButtonSize(): Int

    fun measureMainButtonSize(otherMenuChild: View)

    fun getMenuItemSize(): Int

    fun resetChildSize(child: View)

    fun getChildLayoutParams(): LinearLayout.LayoutParams

    class HorizontalHelper(private val context: Context, private val parent: LinearLayout) : OrientationHelper {

        private var mainButtonSize = 0

        override fun measureMainButtonSize(otherMenuChild: View) {
            mainButtonSize = otherMenuChild.measuredHeight * 5 / 3
        }


        override fun getChildLayoutParams()
            = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            gravity = Gravity.CENTER_VERTICAL
        }

        override fun resetChildSize(child: View) {
            child.layoutParams.makeWidthMeasureSpec(getMenuItemSize(), View.MeasureSpec.EXACTLY)
        }

        override fun getMainButtonSize(): Int = mainButtonSize

        override fun getMenuItemSize(): Int
            = (parent.measuredWidth - getMainButtonSize()) / (parent.childCount - 1)

        override fun getMenuItemView(): View
            = LayoutInflater.from(context).inflate(R.layout.item_menu_option_vertical,
                parent, false)


    }

    class VerticalHelper(private val context: Context, private val parent: LinearLayout) : OrientationHelper {

        private var mainButtonSize = 0

        override fun measureMainButtonSize(otherMenuChild: View) {
            mainButtonSize = otherMenuChild.measuredWidth * 5 / 3
        }

        override fun getChildLayoutParams(): LinearLayout.LayoutParams
            = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }

        override fun resetChildSize(child: View) {
            child.layoutParams.makeHeightMeasureSpec(getMenuItemSize(), View.MeasureSpec.EXACTLY)
        }

        override fun getMainButtonSize(): Int = mainButtonSize

        override fun getMenuItemSize(): Int
            = (parent.measuredHeight - getMainButtonSize()) / (parent.childCount - 1)

        override fun getMenuItemView(): View
            = LayoutInflater.from(context).inflate(R.layout.item_menu_option_horizontal,
                parent, false)

    }
}