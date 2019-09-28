package com.example.expandablemenulayout

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_vector_button.*

class VectorButtonActivity : AppCompatActivity() {

    private var isClose = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector_button)
        vectorBtn.setOnClickListener {
            toggle()
        }
    }

    private fun toggle() {
        if (Build.VERSION.SDK_INT < 21) {
            return
        }
        if (isClose) {
            val closeDrawable = resources.getDrawable(R.drawable.faboptions_ic_close_animatable, null)
            vectorBtn.background = closeDrawable
            (closeDrawable as AnimatedVectorDrawable).start()
        } else {
            val openDrawable = resources.getDrawable(R.drawable.faboptions_ic_menu_animatable, null)
            vectorBtn.background = openDrawable
            (openDrawable as AnimatedVectorDrawable).start()
        }
        isClose = !isClose
    }
}
