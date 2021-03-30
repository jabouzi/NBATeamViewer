package com.skanderjabouzi.nbateamviewer

import org.mockito.ArgumentCaptor
import java.io.*


open class BaseTest {
    val ASSET_BASE_PATH = "../app/src/main/assets/"

    open fun <T> captureArg(argumentCaptor: ArgumentCaptor<T>): T =
            argumentCaptor.capture()

    @Throws(IOException::class)
    open fun readJsonFile(filename: String): String? {
        val file: InputStream = FileInputStream(ASSET_BASE_PATH + filename)
        val formArray = ByteArray(file.available())
        file.read(formArray)
        file.close()
        return String(formArray)
    }
}