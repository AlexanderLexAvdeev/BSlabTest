package ru.bslab.test.avdeevav.repository

interface ConnectionErrorCallback {

    // Background thread
    fun onError(error: String)
}