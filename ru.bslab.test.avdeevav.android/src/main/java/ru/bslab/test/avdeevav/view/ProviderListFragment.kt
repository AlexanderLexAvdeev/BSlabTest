package ru.bslab.test.avdeevav.view

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.loader.app.LoaderManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import ru.bslab.test.avdeevav.R
import ru.bslab.test.avdeevav.repository.data.Provider
import ru.bslab.test.avdeevav.repository.ProviderPresenter

class ProviderListFragment : Fragment(), IProviderList {

    companion object {

        private const val PROVIDER_LIST_FRAGMENT = "ProviderListFragment"

        @JvmStatic
        fun getInstance(fragmentManager: FragmentManager): Fragment {

            var fragment: Fragment? = fragmentManager.findFragmentByTag(PROVIDER_LIST_FRAGMENT)

            if (fragment == null) {
                fragment = ProviderListFragment()
            }

            return fragment
        }

        fun getTag(): String {

            return PROVIDER_LIST_FRAGMENT
        }
    }

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var providerListAdapter: ProviderListAdapter
    private lateinit var providerPresenter: ProviderPresenter

    private lateinit var iGiftCard: IGiftCard

    override fun onAttach(context: Context) {

        super.onAttach(context)

        iGiftCard = context as IGiftCard
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return initView(inflater.inflate(R.layout.fragment_provider_list, container, false))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        providerPresenter = ProviderPresenter(context, LoaderManager.getInstance(this), this)
        providerPresenter.getProviders(false)
    }

    override fun onDetach() {

        providerPresenter.detach()

        super.onDetach()
    }


    override fun setUpdating(updating: Boolean) {

        if (!isAdded) {

            return
        }

        refreshLayout.isRefreshing = updating
    }

    override fun updateList(providers: List<Provider>?) {

        if (!isAdded) {

            return
        }

        providerListAdapter.update(providers)
    }

    override fun showProblem(problem: String) {

        if (!isAdded) {

            return
        }

        Toast.makeText(context, problem, Toast.LENGTH_SHORT).show()
    }


    private fun initView(view: View): View {

        initProviderList(view)
        initRefreshLayout(view)

        return view
    }

    private fun initProviderList(view: View) {

        val providerList: RecyclerView = view.findViewById(R.id.providerList)
        providerListAdapter = ProviderListAdapter(context, iGiftCard)

        providerList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        providerList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = resources.getDimensionPixelSize(R.dimen.providerListItemsOffsetVertical)
                } else {
                    parent.adapter?.let { adapter ->
                        if (parent.getChildAdapterPosition(view) == adapter.itemCount - 1) {
                            context?.let { context ->
                                outRect.bottom =
                                        context.resources.getDimensionPixelSize(R.dimen.providerListItemsOffsetVertical)
                            }
                        }
                    }
                }
            }
        })
        providerList.setHasFixedSize(true)
        providerListAdapter.setHasStableIds(true)
        providerList.adapter = providerListAdapter
    }

    private fun initRefreshLayout(view: View) {

        refreshLayout = view.findViewById(R.id.refreshLayout)
        refreshLayout.setOnRefreshListener {
            setUpdating(true)
            providerPresenter.getProviders(true)
        }
    }
}