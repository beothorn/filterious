package com.github.beothorn.filterious

import android.content.Context
import android.graphics.*
import android.graphics.Color.argb
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.graphics.get
import com.github.beothorn.filterious.nodes.Combine
import com.github.beothorn.filterious.nodes.GrayScale

@RequiresApi(Build.VERSION_CODES.O)
class CustomView(context: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    companion object {
        const val RED = 0
        const val GREEN = 1
        const val BLUE = 2
        const val ALPHA = 3
    }

    init{
        val bitmapImmutable = BitmapFactory.decodeResource(context.resources, R.drawable.sampleimage)

        val scale = 4

        val height = bitmapImmutable.height / scale
        val width = bitmapImmutable.width / scale

        var pixels = Array(width){
            Array(height){
                FloatArray(4)
            }
        }

        for (y in 0 until height){
            for (x in 0 until width){
                val pixel = bitmapImmutable.getPixel(x*scale, y*scale)
                pixels[x][y][RED] = Color.red(pixel)/255F
                pixels[x][y][GREEN] = Color.green(pixel)/255F
                pixels[x][y][BLUE] = Color.blue(pixel)/255F
                pixels[x][y][ALPHA] = Color.alpha(pixel)/255F
            }
        }

        val grayScale = GrayScale()
        grayScale.setInput("Image", pixels)
        val gray = grayScale.callNode()
        val combine = Combine()
        combine.setInput("Image A", pixels)
        combine.setInput("Image B", gray)
        val result = combine.callNode()

        setImageBitmap(createBitmapFromMatrix(result))
    }

    private fun createBitmapFromMatrix(result: Array<Array<FloatArray>>):Bitmap {
        val bmp = Bitmap.createBitmap(result.size, result[0].size, Bitmap.Config.ARGB_8888)
        for (x in result.indices) {
            for (y in result[x].indices) {
                val pixel = result[x][y]
                bmp.setPixel(x, y, argb(pixel[ALPHA], pixel[RED], pixel[GREEN], pixel[BLUE]))
            }
        }
        return bmp
    }

}