package com.github.beothorn.filterious.nodes

import com.github.beothorn.filterious.CustomView

class GrayScale {

    private var input:Array<Array<FloatArray>> = Array(0){Array(0){FloatArray(4)}}

    fun setInput(name:String, input:Any){
        this.input = input as Array<Array<FloatArray>>
    }

    fun callNode():Array<Array<FloatArray>>{
        val result = Array(input.size){
            Array(input[0].size){
                FloatArray(4)
            }
        }
        for(x in input.indices){
            for(y in input[x].indices){
                val gray = ( (0.3 * input[x][y][CustomView.RED]) + (0.59 * input[x][y][CustomView.GREEN]) + (0.11 * input[x][y][CustomView.BLUE]) )
                result[x][y][CustomView.RED] = gray.toFloat()
                result[x][y][CustomView.GREEN] = gray.toFloat()
                result[x][y][CustomView.BLUE] = gray.toFloat()
            }
        }
        return result
    }
}