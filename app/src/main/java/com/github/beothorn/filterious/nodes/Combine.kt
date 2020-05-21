package com.github.beothorn.filterious.nodes

import com.github.beothorn.filterious.CustomView

class Combine {

    private var imageA:Array<Array<FloatArray>> = Array(0){Array(0){FloatArray(4)}}
    private var imageB:Array<Array<FloatArray>> = Array(0){Array(0){FloatArray(4)}}
    private var operation = "Multiply"

    fun setInput(name:String, input:Any){
        if(name == "Image A"){
            imageA = input as Array<Array<FloatArray>>
        }
        if(name == "Image B"){
            imageB = input as Array<Array<FloatArray>>
        }
    }

    fun callNode():Array<Array<FloatArray>>{
        val result = Array(imageA.size){
            Array(imageA[0].size){
                FloatArray(4)
            }
        }
        if(operation == "Multiply"){
            for(x in imageA.indices){
                if(x < imageB.size){
                    for(y in imageA[x].indices){
                        if(y < imageB[x].size){
                            result[x][y][CustomView.RED] =  imageA[x][y][CustomView.RED] * imageB[x][y][CustomView.RED]
                            result[x][y][CustomView.GREEN] =  imageA[x][y][CustomView.GREEN] * imageB[x][y][CustomView.GREEN]
                            result[x][y][CustomView.BLUE] =  imageA[x][y][CustomView.BLUE] * imageB[x][y][CustomView.BLUE]
                            result[x][y][CustomView.ALPHA] = 1.0F
                        }
                    }
                }
            }
        }
        return result
    }



}