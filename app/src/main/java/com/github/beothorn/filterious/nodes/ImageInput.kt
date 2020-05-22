package com.github.beothorn.filterious.nodes

import android.graphics.Bitmap
import android.graphics.Color
import com.github.beothorn.filterious.nodes.Node.Companion.ALPHA
import com.github.beothorn.filterious.nodes.Node.Companion.BLUE
import com.github.beothorn.filterious.nodes.Node.Companion.GREEN
import com.github.beothorn.filterious.nodes.Node.Companion.RED
import com.github.beothorn.filterious.nodes.Type.RGBA_MATRIX
import com.google.gson.Gson
import kotlin.random.Random

data class ImageInputState(val id: String, val scale:Int)

class ImageInput(id: String = Random.nextInt().toString()): Node{

    private var output:Array<Array<FloatArray>> = Array(0){Array(0){FloatArray(4)}}

    private var state = ImageInputState(id, 4)

    override fun getInputs(): Array<NodePoint> = emptyArray()
    override fun getOutputs(): Array<NodePoint> = arrayOf(
        NodePoint(RGBA_MATRIX, "Image")
    )

    override fun setInput(name: String, type: Type, input: Any) {
        if(name == "_ImageArray" && type == Type.BITMAP){
            val bitmapImmutable: Bitmap = input as Bitmap

            val height = bitmapImmutable.height / state.scale
            val width = bitmapImmutable.width / state.scale

            output = Array(width){
                Array(height){
                    FloatArray(4)
                }
            }

            for (y in 0 until height){
                for (x in 0 until width){
                    val pixel = bitmapImmutable.getPixel(x*state.scale, y*state.scale)
                    output[x][y][RED] = Color.red(pixel)/255F
                    output[x][y][GREEN] = Color.green(pixel)/255F
                    output[x][y][BLUE] = Color.blue(pixel)/255F
                    output[x][y][ALPHA] = Color.alpha(pixel)/255F
                }
            }
        }
    }

    override fun output(name: String): Any = output

    override fun serialize(): String = Gson().toJson(state)

    override fun deserialize(state: String){
        this.state = Gson().fromJson(state, ImageInputState::class.java)
    }

    override fun getId(): String = this.state.id

}