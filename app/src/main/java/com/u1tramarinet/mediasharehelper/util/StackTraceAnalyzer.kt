package com.u1tramarinet.mediasharehelper.util

import android.util.Log

class StackTraceAnalyzer(
    stackTraceElements: List<StackTraceElement>,
) {
    private val elements: List<ElementInfo>

    init {
        elements = stackTraceElements
            .filter { isValidElement(it) }
            .map { convertToElementInfo(it) }
    }

    fun getCallee(): ElementInfo {
        return elements.first()
    }

    fun getCaller(): ElementInfo? {
        val own = elements.first()
        var caller = elements.firstOrNull {
            it.fileName == own.fileName
                    && it.methodName != own.methodName
                    && !RESERVED_METHOD_NAMES.contains(it.methodName)
        }
        if (caller == null) {
            caller = elements.firstOrNull { it.fileName != own.fileName }
        }
        return caller
    }

    private fun convertToElementInfo(stackTraceElement: StackTraceElement): ElementInfo {
        return ElementInfo(
            fullFileName = stackTraceElement.fileName,
            fileName = extractFileName(fullClassName = stackTraceElement.fileName),
            fullClassName = stackTraceElement.className,
            className = extractClassName(stackTraceElement.className),
            methodName = stackTraceElement.methodName,
            lineNumber = stackTraceElement.lineNumber,
        )
    }

    private fun isValidElement(stackTraceElement: StackTraceElement): Boolean {
        val result = (stackTraceElement.fileName != null)
                && (!RESERVED_FILE_NAMES.contains(stackTraceElement.fileName))
                && (stackTraceElement.className != null)
                && (stackTraceElement.className.contains(PACKAGE_NAME))
                && (stackTraceElement.methodName != null)
                && (!stackTraceElement.methodName.contains("$"))
        if (DEBUG) {
            val sign = if (result) "[*]" else "[ ]"
            Log.v(
                "DEBUG",
                "$sign${stackTraceElement.fileName}:${stackTraceElement.lineNumber} ${stackTraceElement.className} ${stackTraceElement.methodName}"
            )
        }
        return result
    }

    private fun extractFileName(fullClassName: String): String {
        return fullClassName.split(".").first()
    }

    private fun extractClassName(fullClassName: String): String {
        val splitByDollar = fullClassName.split(".").last().split("$")
        return if (splitByDollar[0].endsWith("Kt") && splitByDollar.size > 1) {
            splitByDollar[1]
        } else {
            splitByDollar[0]
        }
    }

    companion object {
        private const val PACKAGE_NAME = "com.u1tramarinet.mediasharehelper"
        private val RESERVED_FILE_NAMES = listOf("${LogUtil::class.java.simpleName}.kt")
        private val RESERVED_METHOD_NAMES = listOf("invokeSuspend")
        private const val DEBUG = false
    }

    data class ElementInfo(
        val fullFileName: String,
        val fileName: String,
        val fullClassName: String,
        val className: String,
        val methodName: String,
        val lineNumber: Int,
    )
}