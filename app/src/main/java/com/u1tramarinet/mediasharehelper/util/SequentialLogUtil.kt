package com.u1tramarinet.mediasharehelper.util

import android.util.Log

object SequentialLogUtil {
    private const val TAG = "SEQUENCE"
    private const val ARROW_REQUEST = "->"
    private const val ARROW_RESPONSE_SYNC = "<-"
    private const val ARROW_RESPONSE_ASYNC = "<--"

    fun request(
        caller: StackTraceAnalyzer.ElementInfo?,
        callee: StackTraceAnalyzer.ElementInfo,
        args: String? = null
    ) {
        logSequence(
            caller?.className ?: "",
            ARROW_REQUEST,
            callee.className,
            "${callee.methodName}(${args ?: ""})",
        )
    }

    fun execute(executor: StackTraceAnalyzer.ElementInfo, message: String? = null) {
        logSequence(
            executor.className,
            ARROW_REQUEST,
            executor.className,
            message ?: "${executor.methodName}()",
        )
    }

    fun responseSync(
        responder: StackTraceAnalyzer.ElementInfo,
        receiver: StackTraceAnalyzer.ElementInfo,
        message: String,
    ) {
        logSequence(
            receiver.className,
            ARROW_RESPONSE_SYNC,
            responder.className,
            message,
        )
    }

    fun responseAsync(
        responder: StackTraceElement,
        receiver: StackTraceElement,
        message: String,
    ) {
        logSequence(
            receiver.className,
            ARROW_RESPONSE_ASYNC,
            responder.className,
            message,
        )
    }

    private fun logSequence(first: String, arrow: String, second: String, message: String) {
        Log.v(TAG, "$first $arrow $second: $message")
    }
}