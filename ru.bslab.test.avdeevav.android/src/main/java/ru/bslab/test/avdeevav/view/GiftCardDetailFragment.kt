package ru.bslab.test.avdeevav.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import ru.bslab.test.avdeevav.R
import ru.bslab.test.avdeevav.repository.data.GiftCard
import ru.bslab.test.avdeevav.repository.loader.ImageLoader
import ru.bslab.test.avdeevav.utility.CreditsUtil
import ru.bslab.test.avdeevav.utility.CurrencyUtil

class GiftCardDetailFragment : Fragment() {

    companion object {

        private const val GIFT_CARD_DETAIL_FRAGMENT = "GiftCardDetailFragment"

        @JvmStatic
        fun getInstance(fragmentManager: FragmentManager, giftCard: GiftCard): Fragment {

            var fragment: Fragment? = fragmentManager.findFragmentByTag(GIFT_CARD_DETAIL_FRAGMENT)

            if (fragment == null) {
                fragment = GiftCardDetailFragment()
            }
            (fragment as GiftCardDetailFragment).setGiftCard(giftCard)

            return fragment
        }

        fun getTag(): String {

            return GIFT_CARD_DETAIL_FRAGMENT
        }
    }

    private lateinit var logo: ImageView
    private lateinit var price: TextView
    private lateinit var credits: TextView
    private lateinit var description: TextView

    private var giftCard: GiftCard? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return initView(inflater.inflate(R.layout.fragment_gift_card_detail, container, false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initGiftCard()
    }


    private fun setGiftCard(giftCard: GiftCard) {

        this.giftCard = giftCard
    }

    private fun initView(view: View): View {

        logo = view.findViewById(R.id.giftCardDetailLogo)
        price = view.findViewById(R.id.giftCardDetailPrice)
        credits = view.findViewById(R.id.giftCardDetailCredits)
        description = view.findViewById(R.id.giftCardDetailDescription)

        return view
    }

    private fun initGiftCard() {

        // init gift card if it exist or remove this fragment if not (after long term in background)
        giftCard?.let {
            ImageLoader.load(it.image_url, logo)
            price.text = CurrencyUtil.getFormattedString(it.title, it.currency)
            credits.text = CreditsUtil.getFormattedString(it.credits)
            description.text = it.description
        } ?: run {
            // TODO: update this code after data caching release
            val fragment: Fragment? = fragmentManager?.findFragmentByTag(GiftCardDetailFragment.getTag())
            fragment?.let {
                if (it.isAdded) {
                    fragmentManager?.beginTransaction()?.remove(fragment)?.commit()
                }
            }
        }
    }
}