package cz.palda97.repoviewer.model.network

import com.koushikdutta.async.future.Future
import cz.palda97.repoviewer.model.Either
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Coroutine support for Ion.
 * This method differs from the official one by always
 * returning something instead of throwing exceptions.
 */
suspend fun <T> Future<T>.await(): Either<Exception, T> {
    return suspendCoroutine {
        this.setCallback { e, result ->
            if (e != null)
                it.resume(Either.Left(e))
            else
                it.resume(Either.Right(result))
        }
    }
}