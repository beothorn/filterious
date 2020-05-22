package com.github.beothorn.filterious.nodes

import com.github.beothorn.filterious.nodes.Node.Companion.ALPHA
import com.github.beothorn.filterious.nodes.Node.Companion.BLUE
import com.github.beothorn.filterious.nodes.Node.Companion.GREEN
import com.github.beothorn.filterious.nodes.Node.Companion.RED
import com.github.beothorn.filterious.nodes.Type.RGBA_MATRIX
import com.google.gson.Gson
import kotlin.random.Random

data class GrayScaleState(val id: String)

class GrayScale(id: String = Random.nextInt().toString()) : Node{

    private var output:Array<Array<FloatArray>> = Array(0){Array(0){FloatArray(4)}}

    private var state = GrayScaleState(id)

    override fun getInputs(): Array<NodePoint> = arrayOf(
        NodePoint(RGBA_MATRIX, "Image")
    )

    override fun getOutputs(): Array<NodePoint> = arrayOf(
        NodePoint(RGBA_MATRIX, "Grayscale Image")
    )

    override fun setInput(name: String, type: Type, input: Any) {
        if(name == "Image" && type == RGBA_MATRIX){
            var inputMatrix = input as Array<Array<FloatArray>>

            output = Array(inputMatrix.size){
                Array(inputMatrix[0].size){
                    FloatArray(4)
                }
            }
            for(x in inputMatrix.indices){
                for(y in inputMatrix[x].indices){
                    val gray = ( (0.3 * inputMatrix[x][y][RED]) + (0.59 * inputMatrix[x][y][GREEN]) + (0.11 * inputMatrix[x][y][BLUE]) )
                    output[x][y][RED] = gray.toFloat()
                    output[x][y][GREEN] = gray.toFloat()
                    output[x][y][BLUE] = gray.toFloat()
                    output[x][y][ALPHA] = inputMatrix[x][y][ALPHA]
                }
            }
        }
    }

    override fun output(name: String): Any = output

    override fun serialize(): String = Gson().toJson(state)

    override fun deserialize(state: String){
        this.state = Gson().fromJson(state, GrayScaleState::class.java)
    }

    override fun getId(): String = this.state.id
}