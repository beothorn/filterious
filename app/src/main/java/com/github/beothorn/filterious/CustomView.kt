package com.github.beothorn.filterious

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.get
import androidx.core.graphics.withMatrix

class CustomView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    private var bitmap:Bitmap                   
    init{
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sampleimage)
        for (i in 0..bitmap.width){
            for (j in 0..bitmap.height){
                val pixel = bitmap[i,j]
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = 0//Color.blue(pixel)
                val alpha = Color.alpha(pixel)
                bitmap.setPixel(i, j, Color.argb(alpha, red, green, blue))
            }
        }

        setImageBitmap(bitmap)
    }

}