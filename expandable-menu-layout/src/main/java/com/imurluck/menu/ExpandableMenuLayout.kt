package com.imurluck.menu

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.core.content.ContextCompat

/**
 * used for
 * create by imurluck
 * create at 2019-09-28
 */
class ExpandableMenuLayout @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defaultStyle: Int = 0
) : LinearLayout(context, attr, defaultStyle) {

    private val impl = ExpandableMenuLayoutImpl(context, attr, defaultStyle).apply {
        layoutParams = LayoutParams(context, attr)
        layoutParams.width = WRAP_CONTENT
        layoutParams.height = WRAP_CONTENT
        val mainButtonGravity = getMainButtonGravity(context, attr)
        if (this@ExpandableMenuLayout.orientation == HORIZONTAL) {
            val tempLp = layoutParams as LayoutParams
            tempLp.gravity = Gravity.CENTER_VERTICAL
//            when (mainButtonGravity) {
//                GRAVITY_CENTER_MAIN_BUTTON -> tempLp.gravity = tempLp.gravity or Gravity.CENTER_HORIZONTAL
//                GRAVITY_START_MAIN_BUTTON -> tempLp.gravity = tempLp.gravity or Gravity.START
//                GRAVITY_END_MAIN_BUTTON -> tempLp.gravity = tempLp.gravity or Gravity.END
//            }
        } else if (this@ExpandableMenuLayout.orientation == VERTICAL) {
            val tempLp = layoutParams as LayoutParams
            tempLp.gravity = Gravity.CENTER_HORIZONTAL
            when (mainButtonGravity) {
                GRAVITY_CENTER_MAIN_BUTTON -> tempLp.gravity = tempLp.gravity or Gravity.CENTER_VERTICAL
                GRAVITY_START_MAIN_BUTTON -> tempLp.gravity = tempLp.gravity or Gravity.TOP
                GRAVITY_END_MAIN_BUTTON -> tempLp.gravity = tempLp.gravity or Gravity.BOTTOM
            }
        }
    }

    private fun getMainButtonGravity(context: Context, attr: AttributeSet?): Any {
        var gravity = GRAVITY_CENTER_MAIN_BUTTON
        attr?.let {
            context.obtainStyledAttributes(it, R.styleable.ExpandableMenuLayout).apply {
                gravity = getInt(R.styleable.ExpandableMenuLayout_main_button_gravity, GRAVITY_CENTER_MAIN_BUTTON)
                recycle()
            }
        }
        return gravity
    }

    init {
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark))
        addView(impl)
    }
}