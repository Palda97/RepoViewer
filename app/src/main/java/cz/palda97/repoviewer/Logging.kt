package cz.palda97.repoviewer

import android.util.Log

/**
 * Print msg as string via Log.d
 */
fun <T : Any> T.log(msg: Any?) {
    val tag: String = this::class.java.canonicalName?.toString()?.split(".")?.last() ?: "null"
    Log.d(tag, msg.toString())
}