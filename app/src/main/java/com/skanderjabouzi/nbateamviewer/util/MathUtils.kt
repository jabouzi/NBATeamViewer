package com.skanderjabouzi.nbateamviewer.util

open class MathUtils {
    open fun add(x: Int, y: Int): Int {
        return x + y
    }

    open fun isInteger(s: String): Boolean {
        return s.matches("-?\\d+(\\.\\d+)?".toRegex())
    }

    open fun squareLong(l: Long): Long {
        return l * l
    }
}