package ru.bslab.test.avdeevav.view

import ru.bslab.test.avdeevav.repository.data.Provider

interface IProviderList {

    fun setUpdating(updating: Boolean)
    fun updateList(providers: List<Provider>?)
    fun showProblem(problem: String)
}