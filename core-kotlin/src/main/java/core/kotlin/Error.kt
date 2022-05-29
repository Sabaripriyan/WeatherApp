package core.kotlin

sealed class Error(
    val message: String,
    val throwable: Throwable?
)

open class UnknownError(
    message: String = "unknown Error",
    throwable: Throwable? = null
) : Error(message,throwable)
