package ru.bslab.test.avdeevav.repository

import android.content.Context
import androidx.loader.app.LoaderManager

import ru.bslab.test.avdeevav.repository.data.Provider
import ru.bslab.test.avdeevav.view.IProviderList

// TODO: migrate ProviderPresenter to ProviderViewModel : ViewModel
class ProviderPresenter(
        context: Context?,
        loaderManager: LoaderManager,
        private var iProviderList: IProviderList?
) : ProviderRepositoryCallback {

    private var providerRepository: ProviderRepository =
            ProviderRepository(context, loaderManager, this)

    fun getProviders(forceLoad: Boolean) {

        iProviderList?.setUpdating(true)
        providerRepository.getProviders(forceLoad)
    }

    fun detach() {

        iProviderList = null
        providerRepository.detach()
    }


    override fun onUpdate(providers: List<Provider>?) {

        iProviderList?.setUpdating(false)
        iProviderList?.updateList(providers)
    }

    override fun onProblem(problem: String) {

        iProviderList?.setUpdating(false)
        iProviderList?.showProblem(problem)
    }
}