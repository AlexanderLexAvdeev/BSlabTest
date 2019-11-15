package ru.bslab.test.avdeevav.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import ru.bslab.test.avdeevav.R
import ru.bslab.test.avdeevav.repository.data.GiftCard
import ru.bslab.test.avdeevav.repository.loader.ImageLoader
import ru.bslab.test.avdeevav.utility.CreditsUtil
import ru.bslab.test.avdeevav.utility.CurrencyUtil

class GiftCardListAdapter(private val iGiftCard: IGiftCard) : RecyclerView.Adapter<ViewHolder>() {

    private var giftCardList: ArrayList<GiftCard> = ArrayList()

    fun update(giftCardList: ArrayList<GiftCard>) {

        this.giftCardList.clear()
        this.giftCardList.addAll(giftCardList)
        notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun getItemCount(): Int {

        return giftCardList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return GiftCardViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.gift_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val giftCardViewHolder = holder as GiftCardViewHolder
        val giftCard: GiftCard = giftCardList[position]

        giftCardViewHolder.setOnClickListener(
                View.OnClickListener {
                    iGiftCard.showDetail(giftCard)
                }
        )
        ImageLoader.load(giftCard.image_url, giftCardViewHolder.logo)
        giftCardViewHolder.price.text =
                CurrencyUtil.getFormattedString(giftCard.title, giftCard.currency)
        giftCardViewHolder.credits.text =
                CreditsUtil.getFormattedString(giftCard.credits)
    }


    private inner class GiftCardViewHolder(itemView: View) : ViewHolder(itemView) {

        private val card: CardView = itemView.findViewById(R.id.giftCard)
        var logo: ImageView = itemView.findViewById(R.id.giftCardLogo)
        val price: TextView = itemView.findViewById(R.id.giftCardPrice)
        val credits: TextView = itemView.findViewById(R.id.giftCardCredits)

        fun setOnClickListener(listener: View.OnClickListener) {

            card.setOnClickListener(listener)
        }
    }
}