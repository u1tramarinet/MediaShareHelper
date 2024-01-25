package com.u1tramarinet.mediasharehelper.util

import android.util.Log

object LogUtil {

    private const val PREFIX_IN = "[IN ]"
    private const val PREFIX_OUT = "[OUT]"
    private const val TAG_SEQUENCE = "SEQUENCE"

    fun methodIn(tag: String, message: String? = null) {
        print(Log.DEBUG, tag, message, PREFIX_IN)
        printSequenceLog()
    }

    fun methodOut(tag: String, message: String? = null) {
        print(Log.DEBUG, tag, message, PREFIX_OUT)
    }

    fun <T> methodReturn(tag: String, result: T, message: String? = null): T {
        val msg = appendPrefix("result=$result", message)
        print(Log.DEBUG, tag, msg, PREFIX_OUT)
        return result
    }

    fun d(tag: String, message: String? = null) {
        print(Log.DEBUG, tag, message)
    }

    fun v(tag: String, message: String? = null) {
        print(Log.VERBOSE, tag, message)
    }

    private fun print(priority: Int, tag: String, message: String?, prefix: String? = null) {
        val methodInfo = getMethodInfo(Throwable().stackTrace.toList())
        val method = formatMethodInfo(methodInfo.first)
        val msg = listOfNotNull(prefix, message, method).joinToString(" ")
        Log.println(priority, tag, msg)
    }

    private fun printSequenceLog() {
        val elements = Throwable().stackTrace.toList()
        val filteredElements = filterStackTrace(elements)
        val own = filteredElements.firstOrNull() ?: return
        var caller: StackTraceElement? = filteredElements.firstOrNull {
            it.fileName == own.fileName && it.methodName != own.methodName
        }
        if (caller == null) {
            caller = filteredElements.firstOrNull {
                it.fileName != own.fileName
            } ?: return
        }
        Log.println(
            Log.DEBUG,
            TAG_SEQUENCE,
            "${getClassName(caller.className)}->${getClassName(own.className)}: ${own.methodName}()"
        )
    }

    private fun getClassName(className: String): String {
        val splitByDot = className.split(".")
        val splitByDollar = splitByDot.last().split("$")
        return if (splitByDollar.size > 1 && splitByDollar[0].endsWith("Kt")) {
            splitByDollar[1]
        } else {
            splitByDollar[0]
        }
    }

    private fun getMethodInfo(stackTraceElements: List<StackTraceElement>): Pair<StackTraceElement, StackTraceElement?> {
        val filteredElements = filterStackTrace(stackTraceElements)
        stackTraceElements.forEach { element ->
            val fileName = element.fileName
            val lineNumber = element.lineNumber
            val className = element.className
            val methodName = element.methodName
            Log.println(
                Log.VERBOSE,
                "[DEBUG]",
                "file=$fileName:$lineNumber, class=$className, method=$methodName",
            )
        }
        val first = filteredElements.first()
        val second = filteredElements.firstOrNull { it.fileName != first.fileName }
        return Pair(first, second)
    }

    private fun filterStackTrace(stackTraceElements: List<StackTraceElement>): List<StackTraceElement> {
        return stackTraceElements.filter { it.fileName != "${LogUtil::class.java.simpleName}.kt" }
            .filter { it.className.contains("com.u1tramarinet.mediasharehelper") }
            .filter { !it.methodName.contains("$") }
    }

    private fun formatMethodInfo(element: StackTraceElement): String {
        val fileName = element.fileName
        val lineNumber = element.lineNumber
        val methodName = element.methodName
        return "$methodName()@$fileName:$lineNumber"
    }

    fun appendPrefix(prefix: String, message: String?): String {
        return prefix + if (message != null) " $message" else ""
    }
}