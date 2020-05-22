package com.github.beothorn.filterious.nodes

enum class Type {
    ANY, BITMAP, RGBA_MATRIX, COLOR_ARRAY, NUMBER
}

data class NodePoint(val type: Type, val name: String)

interface Node {
    companion object {
        const val RED = 0
        const val GREEN = 1
        const val BLUE = 2
        const val ALPHA = 3
    }

    fun getInputs():Array<NodePoint>
    fun getOutputs():Array<NodePoint>
    fun setInput(name:String, type: Type, input:Any)
    fun output(name:String):Any
    fun serialize():String
    fun deserialize(state:String)
    fun getId():String
}