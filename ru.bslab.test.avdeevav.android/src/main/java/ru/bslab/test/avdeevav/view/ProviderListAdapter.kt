package ru.bslab.test.avdeevav.view

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import ru.bslab.test.avdeevav.R
import ru.bslab.test.avdeevav.repository.data.GiftCard
import ru.bslab.test.avdeevav.repository.data.Provider

class ProviderListAdapter(val context: Context?, val iGiftCard: IGiftCard) : RecyclerView.Adapter<ViewHolder>() {

    private val providerList: ArrayList<Provider> = ArrayList()

    fun update(providerList: List<Provider>?) {

        if (providerList == null) {

            return
        }

        this.providerList.clear()
        this.providerList.addAll(providerList)
        notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun getItemCount(): Int {

        return providerList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ProviderViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.provider_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val providerViewHolder = holder as ProviderViewHolder
        val provider: Provider = providerList[position]

        providerViewHolder.title.text = provider.title
        providerViewHolder.setGiftCards(provider.gift_cards)
    }


    private inner class ProviderViewHolder(itemView: View) : ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.providerTitle)
        private val giftCardList: RecyclerView = itemView.findViewById(R.id.giftCardList)
        private val giftCardListAdapter = GiftCardListAdapter(iGiftCard)

        init {
            giftCardList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            giftCardList.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    if (parent.getChildAdapterPosition(view) == 0) {
                        context?.let { context ->
                            outRect.left = context.resources.getDimensionPixelSize(R.dimen.giftCardsOffsetHorizontal)
                        }
                    } else {
                        parent.adapter?.let { adapter ->
                            if (parent.getChildAdapterPosition(view) == adapter.itemCount - 1) {
                                context?.let { context ->
                                    outRect.right = context.resources.getDimensionPixelSize(R.dimen.giftCardsOffsetHorizontal)
                                }
                            }
                        }
                    }
                }
            })
            giftCardList.setHasFixedSize(true)
            giftCardListAdapter.setHasStableIds(true)
            giftCardList.adapter = giftCardListAdapter
        }

        fun setGiftCards(giftCards: ArrayList<GiftCard>) {

            giftCardListAdapter.update(giftCards)
        }
    }
}