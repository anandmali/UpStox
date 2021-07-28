package com.anandmali.upstox.remote

sealed class Status<out R> {
    data class Success<out R>(val response: R) : Status<R>()
    data class Failure(val error: String) : Status<Nothing>()
}
