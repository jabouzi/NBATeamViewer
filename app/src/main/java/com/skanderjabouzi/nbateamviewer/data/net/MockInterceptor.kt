package com.skanderjabouzi.nbateamviewer.data.net

import android.content.Context
import android.util.Log
import com.skanderjabouzi.nbateamviewer.core.App
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URI
import java.util.*

class MockInterceptor(val context: Context): Interceptor {

    private var contentType = "application/json"

    /**
     * Set content type for header
     * @param contentType Content type
     * @return MockInterceptor
     */
    fun setContentType(_contentType: String): MockInterceptor {
        contentType = _contentType
        return this
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val listSuggestionFileName: MutableList<String> = ArrayList()
        val method = chain.request().method.toLowerCase()
        var response: Response? = null
        // Get Request URI.
        val uri = chain.request().url.toUri()
        Log.d(TAG, "--> Request url: [" + method.toUpperCase() + "]" + uri.toString())
        Log.d(TAG, "--> File :$chain")
        val defaultFileName = getFileName(chain)
        Log.d(TAG, "--> File :$defaultFileName")

        //create file name with http method
        //eg: getLogin.json
        listSuggestionFileName.add(method + upCaseFirstLetter(defaultFileName))

        //eg: login.json
        listSuggestionFileName.add(defaultFileName)
        val responseFileName = getFirstFileNameExist(listSuggestionFileName, uri)
        if (responseFileName != null) {
            val fileName = getFilePath(uri, responseFileName)
            Log.d(TAG, "Read data from file: $fileName")
            try {
                val `is` = context.assets.open(fileName)
                val r = BufferedReader(InputStreamReader(`is`))
                val responseStringBuilder = StringBuilder()
                var line: String?
                while (r.readLine().also { line = it } != null) {
                    responseStringBuilder.append(line).append('\n')
                }
                Log.d(TAG, "Response: $responseStringBuilder")
                response = Response.Builder()
                    .code(200)
                    .message(responseStringBuilder.toString())
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(
                        responseStringBuilder.toString().toByteArray()
                            .toResponseBody(contentType.toMediaTypeOrNull())
                    )
                    .addHeader("content-type", contentType)
                    .build()
            } catch (e: IOException) {
                Log.e(TAG, e.message, e)
            }
        } else {
            for (file in listSuggestionFileName) {
                Log.e(TAG, "File not exist: " + getFilePath(uri, file))
            }
            response = chain.proceed(chain.request())
        }
        Log.d(TAG, "<-- END [" + method.toUpperCase() + "]" + uri.toString())
        return response!!
    }

    private fun upCaseFirstLetter(str: String): String {
        return str.substring(0, 1).toUpperCase() + str.substring(1)
    }

    @Throws(IOException::class)
    private fun getFirstFileNameExist(inputFileNames: List<String>, uri: URI): String? {
        var mockDataPath = uri.host + uri.path
        mockDataPath = mockDataPath.substring(0, mockDataPath.lastIndexOf('/'))
        Log.d(TAG, "Scan files in: $mockDataPath")
        //List all files in folder
        val files = context.assets.list(mockDataPath)
        Log.d(TAG, "Files: " + Arrays.toString(files))
        for (fileName in inputFileNames) {
            if (fileName != null) {
                for (file in files!!) {
                    if (fileName == file) {
                        return fileName
                    }
                }
            }
        }
        return null
    }

    private fun getFileName(chain: Interceptor.Chain): String {
        val fileName = chain.request().url.pathSegments[chain.request().url.pathSegments.size - 1]
        return if (fileName.isEmpty()) "index" else fileName
    }

    private fun getFilePath(uri: URI, fileName: String): String {
        val path: String
        path = if (uri.path.lastIndexOf('/') != uri.path.length - 1) {
            uri.path.substring(0, uri.path.lastIndexOf('/') + 1)
        } else {
            uri.path
        }
        return uri.host + path + fileName
    }

    companion object {
        private val TAG = MockInterceptor::class.java.simpleName
    }
}