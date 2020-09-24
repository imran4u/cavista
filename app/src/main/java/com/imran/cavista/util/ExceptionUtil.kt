package com.imran.cavista.util

import java.io.IOException

/**
 * Created by imran on 2020-09-24.
 */
class ApiException(message: String?, var code: Int) : IOException(message)
class NoInternetException(message: String) : IOException(message)
