package com.github.beothorn.filterious

import android.content.Context
import android.graphics.*
import android.graphics.Color.argb
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import com.github.beothorn.filterious.nodes.*

@RequiresApi(Build.VERSION_CODES.O)
class CustomView(context: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    init{
        val bitmapImmutable = BitmapFactory.decodeResource(context.resources, R.drawable.sampleimage)

        val imageInput = ImageInput()
        val grayScale = GrayScale()
        val combine = Combine()
        val combineB = Combine()
        val grayScaleB = GrayScale()
        val grayScaleC = GrayScale()

        imageInput.setInput("_ImageArray", Type.BITMAP, bitmapImmutable)
        grayScale.setInput("Image", Type.RGBA_MATRIX, imageInput.output(""))

        combine.setInput("Image A", Type.RGBA_MATRIX, imageInput.output(""))
        combine.setInput("Image B", Type.RGBA_MATRIX, grayScale.output(""))

        grayScaleB.setInput("Image", Type.RGBA_MATRIX, combine.output(""))

        combineB.setInput("Image A", Type.RGBA_MATRIX, grayScaleB.output(""))
        combineB.setInput("Image B", Type.RGBA_MATRIX, combine.output(""))

        grayScaleC.setInput("Image", Type.RGBA_MATRIX, combineB.output(""))


        val result = grayScaleC.output("") as Array<Array<FloatArray>>

        setImageBitmap(createBitmapFromMatrix(result))
    }

    private fun createBitmapFromMatrix(result: Array<Array<FloatArray>>):Bitmap {
        val bmp = Bitmap.createBitmap(result.size, result[0].size, Bitmap.Config.ARGB_8888)
        for (x in result.indices) {
            for (y in result[x].indices) {
                val pixel = result[x][y]
                bmp.setPixel(x, y, argb(pixel[Node.ALPHA], pixel[Node.RED], pixel[Node.GREEN], pixel[Node.BLUE]))
            }
        }
        return bmp
    }

}