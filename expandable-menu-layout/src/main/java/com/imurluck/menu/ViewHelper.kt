package com.imurluck.menu

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.HORIZONTAL

/**
 * used for
 * create by imurluck
 * create at 2019-09-28
 */
internal fun View.getMeasureWidthAfterPadding() = measuredWidth - paddingLeft - paddingRight

internal fun View.getMeasureHeightAfterPadding() = measuredHeight - paddingTop - paddingBottom

internal fun ViewGroup.LayoutParams.makeWidthMeasureSpec(width: Int, mode: Int) {
    this.width = View.MeasureSpec.makeMeasureSpec(width, mode)
}

internal fun ViewGroup.LayoutParams.makeHeightMeasureSpec(height: Int, mode: Int) {
    this.height = View.MeasureSpec.makeMeasureSpec(height, mode)
}

internal fun ExpandableMenuLayoutImpl.createOrientationHelper(): OrientationHelper {
    return if (orientation == HORIZONTAL) {
        OrientationHelper.HorizontalHelper(context, this)
    } else {
        OrientationHelper.VerticalHelper(context, this)
    }
}
