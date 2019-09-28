package com.imurluck.menu

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.children
import androidx.core.view.forEach

/**
 * used for
 * create by imurluck
 * create at 2019-09-28
 */
class ExpandableMenuLayoutImpl @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defaultStyle: Int = 0
) : LinearLayout(context, attr, defaultStyle) {


    private var menuResourceId = ILLEGAL_RESOURCE_ID

    private val menuViewList = mutableListOf<View>()

    private var menu: Menu = MenuBuilder(context)

    private var orientationHelper = createOrientationHelper()

    @ToggleState
    private var currentToggleState = TOGGLE_STATE_OPEN

    private var mainButton = Button(context).apply {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        background = ColorDrawable(Color.BLUE)
        setOnClickListener {
            toggle()
        }
    }


    private var mainButtonGravity: Int = GRAVITY_CENTER_MAIN_BUTTON

    init {
        initAttrs(attr)
        background ?: setDefaultBackground()
        addMenus()
    }

    private fun addMenus() {
        menu.forEach {
            val menuView = orientationHelper.getMenuItemView()
            setupMenuViewAttrs(menuView, it)
            menuViewList.add(menuView)
        }
        val mainButtonIndex = getMainButtonIndex(menuViewList)
        for (i in 0 until menuViewList.size) {
            if (i == mainButtonIndex) {
                this@ExpandableMenuLayoutImpl.addView(mainButton,
                    orientationHelper.getChildLayoutParams())
            }
            this@ExpandableMenuLayoutImpl.addView(menuViewList[i],
                orientationHelper.getChildLayoutParams())
        }
    }

    private fun getMainButtonIndex(menuViewList: MutableList<View>): Int {
        return when (mainButtonGravity) {
            GRAVITY_START_MAIN_BUTTON -> 0
            GRAVITY_CENTER_MAIN_BUTTON -> menuViewList.size / 2
            else -> menuViewList.size
        }
    }

    private fun setupMenuViewAttrs(menuView: View, menuItem: MenuItem) {
        val iconDrawable = menuItem.icon
        iconDrawable ?: throw IllegalArgumentException("menu item ${menuItem.itemId} icon can not be null")
        menuView.findViewById<ImageView>(R.id.menu_icon_img).background = iconDrawable
        menuView.findViewById<TextView>(R.id.menu_title_tv).text = menuItem.title
    }

    private fun initAttrs(attr: AttributeSet?) {
        attr?.let {
            context.obtainStyledAttributes(it, R.styleable.ExpandableMenuLayout)
                .apply {
                    if (hasValue(R.styleable.ExpandableMenuLayout_menu)) {
                        inflateMenu(getResourceId(R.styleable.ExpandableMenuLayout_menu, ILLEGAL_RESOURCE_ID))
                    }
                    mainButtonGravity = getInt(R.styleable.ExpandableMenuLayout_main_button_gravity,
                        GRAVITY_CENTER_MAIN_BUTTON)
                    recycle()
                }
        }

    }

    private fun inflateMenu(menuId: Int) {
        MenuInflater(context).inflate(menuId, menu)
    }

    private fun setDefaultBackground() {
        setBackgroundColor(getSystemColor(context, ID_COLOR_ACCENT))
    }

    @ColorInt
    private fun getSystemColor(context: Context, colorId: Int): Int
        = TypedValue().apply {
        context.theme.resolveAttribute(colorId, this, false)
    }.data

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (currentToggleState == TOGGLE_STATE_CLOSE) {
            return
        }
        if (menuViewList.size == 0) {
            throw IllegalStateException("menu count is 0")
        }
        orientationHelper.measureMainButtonSize(menuViewList[0])
        children.iterator().forEach {
            orientationHelper.resetChildSize(it)
        }
        mainButton.layoutParams.makeWidthMeasureSpec(orientationHelper.getMainButtonSize(), MeasureSpec.EXACTLY)
        mainButton.layoutParams.makeHeightMeasureSpec(orientationHelper.getMainButtonSize(), MeasureSpec.EXACTLY)
    }

    private fun toggle() {
        if (currentToggleState == TOGGLE_STATE_CLOSE) {
            openMenu()
        } else {
            closeMenu()
        }
        requestLayout()
    }

    private fun openMenu() {
        TransitionManager.beginDelayedTransition(parent as ViewGroup?)
        menuViewList.forEach {
            it.visibility = View.VISIBLE
        }
        currentToggleState = TOGGLE_STATE_OPEN
    }

    private fun closeMenu() {
        TransitionManager.beginDelayedTransition(parent as ViewGroup?)
        menuViewList.forEach {
            it.visibility = View.GONE
        }
        currentToggleState = TOGGLE_STATE_CLOSE
    }


    companion object {

        /**
         * id of colorAccent
         */
        private val ID_COLOR_ACCENT = R.attr.colorAccent

        private const val ILLEGAL_RESOURCE_ID = 0

        private const val TOGGLE_STATE_CLOSE = 0
        private const val TOGGLE_STATE_OPEN = 1
    }

    @IntDef(TOGGLE_STATE_CLOSE, TOGGLE_STATE_OPEN)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ToggleState

}