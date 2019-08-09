package com.whisker.revolutconverter.api.exception

import java.io.IOException

class InternalServerErrorException : IOException() {
    override val message: String?
        get() = "Internal Server Error. Please try again later."
}