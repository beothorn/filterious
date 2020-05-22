package com.github.beothorn.filterious.nodes

import com.github.beothorn.filterious.nodes.Node.Companion.ALPHA
import com.github.beothorn.filterious.nodes.Node.Companion.BLUE
import com.github.beothorn.filterious.nodes.Node.Companion.GREEN
import com.github.beothorn.filterious.nodes.Node.Companion.RED
import com.github.beothorn.filterious.nodes.Type.RGBA_MATRIX
import com.google.gson.Gson
import kotlin.random.Random

data class CombineState(val id: String, val operation: String)

class Combine(id: String = Random.nextInt().toString()) : Node{

    private var imageA:Array<Array<FloatArray>> = Array(0){Array(0){FloatArray(4)}}
    private var imageB:Array<Array<FloatArray>> = Array(0){Array(0){FloatArray(4)}}
    private var output:Array<Array<FloatArray>> = Array(0){Array(0){FloatArray(4)}}
    private val IMAGE_A = "Image A"
    private val IMAGE_B = "Image B"

    private var state = CombineState(id, "Multiply")

    private fun recalculateOutput(){
        output = Array(imageA.size){
            Array(imageA[0].size){
                FloatArray(4)
            }
        }
        if(state.operation == "Multiply"){
            for(x in imageA.indices){
                if(x < imageB.size){
                    for(y in imageA[x].indices){
                        if(y < imageB[x].size){
                            output[x][y][RED] =  imageA[x][y][Node.RED] * imageB[x][y][Node.RED]
                            output[x][y][GREEN] =  imageA[x][y][GREEN] * imageB[x][y][GREEN]
                            output[x][y][BLUE] =  imageA[x][y][BLUE] * imageB[x][y][BLUE]
                            output[x][y][ALPHA] = 1.0F
                        }
                    }
                }
            }
        }
    }

    override fun getInputs(): Array<NodePoint> = arrayOf(
        NodePoint(RGBA_MATRIX, IMAGE_A),
        NodePoint(RGBA_MATRIX, IMAGE_B)
    )

    override fun getOutputs(): Array<NodePoint> = arrayOf(
        NodePoint(RGBA_MATRIX, "Result")
    )

    override fun setInput(name:String, type: Type, input:Any){
        if(name == IMAGE_A && type == RGBA_MATRIX){
            imageA = input as Array<Array<FloatArray>>
            recalculateOutput()
        }
        if(name == IMAGE_B && type == RGBA_MATRIX){
            imageB = input as Array<Array<FloatArray>>
            recalculateOutput()
        }
    }

    override fun output(name:String):Any = output

    override fun serialize(): String = Gson().toJson(state)

    override fun deserialize(state: String){
        this.state = Gson().fromJson(state, CombineState::class.java)
    }

    override fun getId(): String = this.state.id
}