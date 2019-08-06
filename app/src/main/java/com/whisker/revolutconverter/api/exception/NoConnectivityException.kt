package com.whisker.revolutconverter.api.exception

import java.io.IOException

class NoConnectivityException : IOException() {

    override val message: String?
        get() = "No network available. Check your internet connection."
}