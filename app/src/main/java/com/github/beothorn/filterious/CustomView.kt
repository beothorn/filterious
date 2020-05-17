package com.github.beothorn.filterious

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.withMatrix

class CustomView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    private var bitmap:Bitmap                   
    init{
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sampleimage)
        setImageBitmap(bitmap)
    }

}