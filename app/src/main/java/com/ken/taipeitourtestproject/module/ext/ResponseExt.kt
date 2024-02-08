package com.ken.taipeitourtestproject.module.ext

import retrofit2.Response
import java.io.IOException
import java.lang.NullPointerException

@Throws(IOException::class)
fun <T : Response<R>, R> T.checkSuccessful(): T {
    if (this.isSuccessful) {
        return this
    } else {
        throw IOException()
    }
}

@Throws(NullPointerException::class)
fun <T : Response<R>, R> T.requireBody(): R {
    return this.body() ?: throw NullPointerException("response body is null")
}