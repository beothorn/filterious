package com.github.beothorn.filterious.nodes

import com.github.beothorn.filterious.nodes.Node.Companion.ALPHA
import com.github.beothorn.filterious.nodes.Node.Companion.BLUE
import com.github.beothorn.filterious.nodes.Node.Companion.GREEN
import com.github.beothorn.filterious.nodes.Node.Companion.RED
import org.junit.Test

import org.junit.Assert.*

class CombineTest {

    @Test
    fun testOutput() {
        val combine = Combine()
        combine.deserialize("""{"id":"42","operation":"Multiply"}""")
        assertEquals("""{"id":"42","operation":"Multiply"}""",combine.serialize())

        assertArrayEquals(arrayOf(
            NodePoint(Type.RGBA_MATRIX, "Image A"),
            NodePoint(Type.RGBA_MATRIX, "Image B")
        ), combine.getInputs())

        assertArrayEquals(arrayOf(
            NodePoint(Type.RGBA_MATRIX, "Result")
        ), combine.getOutputs())

        val imgA:Array<Array<FloatArray>> =  Array(1){Array(1){
            FloatArray(4){0.5F}
        }}

        val imgB =  Array(1){Array(1){
            FloatArray(4){0.5F}
        }}

        combine.setInput("Image A", Type.RGBA_MATRIX, imgA)
        combine.setInput("Image B", Type.RGBA_MATRIX, imgB)

        val result = combine.output("Result") as Array<Array<FloatArray>>
        assertEquals(0.25F, result[0][0][RED])
        assertEquals(0.25F, result[0][0][GREEN])
        assertEquals(0.25F, result[0][0][BLUE])
        assertEquals(1F, result[0][0][ALPHA])

    }
}

