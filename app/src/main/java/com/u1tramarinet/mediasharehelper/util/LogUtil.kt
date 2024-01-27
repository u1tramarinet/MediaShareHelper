package com.u1tramarinet.mediasharehelper.util

import android.util.Log

object LogUtil {

    private const val PREFIX_IN = "[IN ]"
    private const val PREFIX_OUT = "[OUT]"

    fun methodIn(tag: String, args: String? = null, message: String? = null) {
        print(Log.DEBUG, tag, message, PREFIX_IN)
        printSequenceCall(args)
    }

    fun methodOut(tag: String, message: String? = null) {
        print(Log.DEBUG, tag, message, PREFIX_OUT)
    }

    fun <T> methodReturn(tag: String, result: T, message: String? = null): T {
        val msg = listOfNotNull("result=$result", message).joinToString(" ")
        print(Log.DEBUG, tag, msg, PREFIX_OUT)
        printSequenceReturn(result = msg)
        return result
    }

    fun d(tag: String, message: String? = null) {
        print(Log.DEBUG, tag, message)
    }

    fun v(tag: String, message: String? = null) {
        print(Log.VERBOSE, tag, message)
    }

    private fun print(priority: Int, tag: String, message: String?, prefix: String? = null) {
        val analyzer = StackTraceAnalyzer(Throwable().stackTrace.toList())
        val method = formatMethodInfo(analyzer.getCallee())
        val msg = listOfNotNull(prefix, message, method).joinToString(" ")
        Log.println(priority, tag, msg)
    }

    private fun printSequenceCall(args: String? = null) {
        val analyzer = StackTraceAnalyzer(Throwable().stackTrace.toList())
        SequentialLogUtil.request(analyzer.getCaller(), analyzer.getCallee(), args = args)
    }

    private fun printSequenceReturn(result: String) {
        val analyzer = StackTraceAnalyzer(Throwable().stackTrace.toList())
        val caller = analyzer.getCaller()
        if (caller != null) {
            SequentialLogUtil.responseSync(analyzer.getCallee(), caller, result)
        }
    }

    private fun formatMethodInfo(element: StackTraceAnalyzer.ElementInfo): String {
        val fileName = element.fileName
        val lineNumber = element.lineNumber
        val methodName = element.methodName
        return "$methodName()@$fileName:$lineNumber"
    }
}