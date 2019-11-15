package ru.bslab.test.avdeevav.repository

import ru.bslab.test.avdeevav.repository.data.Provider

interface ProviderRepositoryCallback {

    fun onUpdate(providers: List<Provider>?)
    fun onProblem(problem: String)
}