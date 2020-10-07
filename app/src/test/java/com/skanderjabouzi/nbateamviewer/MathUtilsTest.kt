package com.skanderjabouzi.nbateamviewer

import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.skanderjabouzi.nbateamviewer.util.MathUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import com.nhaarman.mockitokotlin2.argumentCaptor


@RunWith(MockitoJUnitRunner::class)
class MathUtilsTest {

    @Mock
    lateinit var mathUtils: MathUtils

    @Test
    fun test1() {
        `when`(mathUtils.add(1, 1)).thenReturn(2)
        assertEquals(2, mathUtils.add(1, 1))
        argumentCaptor<Int>().apply {
            verify(mathUtils).add(capture(), capture())
            assertEquals(listOf(1, 1), allValues)
        }
    }

    @Test
    fun test2() {
        `when`(mathUtils.squareLong(2L)).thenReturn(4L)
        assertEquals(4L, mathUtils.squareLong(2L))
        argumentCaptor<Long>().apply {
            verify(mathUtils).squareLong(capture())
            assertTrue(2L == firstValue)
        }

    }

    @Test
    fun test3() {
        `when`(mathUtils.isInteger(anyString())).thenReturn(true)
        assertTrue(mathUtils.isInteger("1"))
        assertTrue(mathUtils.isInteger("999"))
        argumentCaptor<String>().apply {
            verify(mathUtils, times(2)).isInteger(capture())
            assertEquals(listOf("1", "999"), allValues)
        }
    }
}