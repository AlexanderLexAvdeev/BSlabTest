package ru.bslab.test.avdeevav.repository

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.loader.app.LoaderManager
import androidx.loader.app.LoaderManager.LoaderCallbacks
import androidx.loader.content.Loader

import ru.bslab.test.avdeevav.repository.data.Provider
import ru.bslab.test.avdeevav.repository.loader.ProviderLoader

class ProviderRepository(
        private var context: Context?,
        private var loaderManager: LoaderManager?,
        private var providerRepositoryCallback: ProviderRepositoryCallback?
) : LoaderCallbacks<List<Provider>>, ConnectionErrorCallback {

    private val providerLoaderId = 1

    fun detach() {

        context = null
        loaderManager = null
        providerRepositoryCallback = null
    }


    fun getProviders(forceLoad: Boolean) {

        if (forceLoad) {
            loaderManager?.restartLoader(providerLoaderId, null, this)
        } else {
            loaderManager?.initLoader(providerLoaderId, null, this)
        }
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Provider>> {

        return ProviderLoader(context!!, this)
    }

    override fun onLoadFinished(loader: Loader<List<Provider>>, data: List<Provider>?) {

        providerRepositoryCallback?.onUpdate(data)
    }

    override fun onLoaderReset(loader: Loader<List<Provider>>) {
    }

    override fun onError(error: String) {

        Handler(Looper.getMainLooper()).post {
            providerRepositoryCallback?.onProblem(error)
        }
    }
}