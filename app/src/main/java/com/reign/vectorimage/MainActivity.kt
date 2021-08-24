package com.reign.vectorimage

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ImageViewCompat

class MainActivity : AppCompatActivity() {

    private val leftColor = Color.parseColor("#1AFA29")
    private val rightColor = Color.parseColor("#FF0000")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val drawable =  VectorImage(this, R.drawable.ic_headset)
            .setPartPathColor("left1", leftColor, true)
            .setPartPathColor("left2", leftColor, true)
            .setPartPathColor("right1", rightColor, true)
            .setPartPathColor("right2", rightColor, true)
            .create()

        findViewById<AppCompatImageView>(R.id.iv).setImageDrawable(drawable)
    }
}