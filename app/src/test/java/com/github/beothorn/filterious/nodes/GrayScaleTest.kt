package com.github.beothorn.filterious.nodes

import com.github.beothorn.filterious.nodes.Node.Companion.ALPHA
import com.github.beothorn.filterious.nodes.Node.Companion.BLUE
import com.github.beothorn.filterious.nodes.Node.Companion.GREEN
import com.github.beothorn.filterious.nodes.Node.Companion.RED
import org.junit.Test

import org.junit.Assert.*

internal class GrayScaleTest {

    @Test
    fun testOutput() {
        val grayScale = GrayScale()
        grayScale.deserialize("""{"id":"42"}""")
        assertEquals("""{"id":"42"}""",grayScale.serialize())

        assertArrayEquals(arrayOf(
            NodePoint(Type.RGBA_MATRIX, "Image")
        ), grayScale.getInputs())

        assertArrayEquals(arrayOf(
            NodePoint(Type.RGBA_MATRIX, "Grayscale Image")
        ), grayScale.getOutputs())

        val imgA:Array<Array<FloatArray>> =  Array(1){Array(1){
            FloatArray(4){0.5F}
        }}

        grayScale.setInput("Image", Type.RGBA_MATRIX, imgA)

        val result = grayScale.output("Grayscale Image") as Array<Array<FloatArray>>
        val grayPixel = (0.5F * 0.3F) + (0.5F * 0.59F) + (0.5F * 0.11F)
        assertEquals(grayPixel, result[0][0][RED])
        assertEquals(grayPixel, result[0][0][GREEN])
        assertEquals(grayPixel, result[0][0][BLUE])
        assertEquals(0.5F, result[0][0][ALPHA])
    }

}