package ru.bslab.test.avdeevav.repository.loader

import java.lang.Exception

import android.content.Context
import androidx.loader.content.AsyncTaskLoader

import retrofit2.Response

import ru.bslab.test.avdeevav.R
import ru.bslab.test.avdeevav.repository.ConnectionErrorCallback
import ru.bslab.test.avdeevav.repository.client.WebClient
import ru.bslab.test.avdeevav.repository.data.Provider
import ru.bslab.test.avdeevav.repository.data.Providers

class ProviderLoader(
        context: Context,
        private val connectionErrorCallback: ConnectionErrorCallback
) : AsyncTaskLoader<List<Provider>>(context) {

    override fun onStartLoading() {

        super.onStartLoading()

        forceLoad()
    }

    override fun loadInBackground(): List<Provider>? {

        val response: Response<Providers>?

        try {
            response = WebClient.getProviderService().getList().execute()
        } catch (e: Exception) {
            connectionErrorCallback.onError(context.resources.getString(R.string.noConnection))
            return null
        }

        response?.let {
            return if (it.isSuccessful) {
                it.body()?.providers
            } else {
                connectionErrorCallback.onError(
                        "%s %s".format(context.resources.getString(R.string.connectionError), it.code())
                )
                null
            }
        } ?: run {
            return null
        }
        //return dataFlowTest()
    }


    // For UI test only
    /*
    private fun dataFlowTest(): List<Provider> {

        val amazonGiftCards: ArrayList<GiftCard> = ArrayList()
        val iTunesGiftCards: ArrayList<GiftCard> = ArrayList()
        val providers: ArrayList<Provider> = ArrayList()

        amazonGiftCards.add(
                GiftCard(
                        1,
                        false,
                        "$10 Amazon.com",
                        8500,
                        "http://g-ec2.images-amazon.com/images/G/01/social/api-share/amazon_logo_500500.png",
                        101,
                        "USD",
                        "Buy everything from Amazon. It's great.",
                        "http://www.amazon.com"
                )
        )
        amazonGiftCards.add(
                GiftCard(
                        2,
                        false,
                        "$20 Amazon.com",
                        15000,
                        "http://g-ec2.images-amazon.com/images/G/01/social/api-share/amazon_logo_500500.png",
                        4,
                        "USD",
                        "Buy everything from Amazon. It's great.",
                        "http://www.amazon.com"
                )
        )
        amazonGiftCards.add(
                GiftCard(
                        3,
                        false,
                        "$30 Amazon.com",
                        20000,
                        "http://g-ec2.images-amazon.com/images/G/01/social/api-share/amazon_logo_500500.png",
                        2,
                        "USD",
                        "Buy everything from Amazon. It's great.",
                        "http://www.amazon.com"
                )
        )

        iTunesGiftCards.add(
                GiftCard(
                        101,
                        false,
                        "$10 iTines Gift Card",
                        8500,
                        "https://www.ixbt.com/short/images/2017/Dec/card-25_8_3.jpg",
                        101,
                        "USD",
                        "Buy everything from iTunes. It's great.",
                        "http://www.apple.com"
                )
        )

        providers.add(
                Provider(
                        1,
                        "Amazon.com",
                        "http://g-ec2.images-amazon.com/images/G/01/social/api-share/amazon_logo_500500.png",
                        amazonGiftCards
                )
        )
        providers.add(
                Provider(
                        2,
                        "iTunes",
                        "http://culttech.com/wp-content/uploads/2017/11/itunes-password.jpg",
                        iTunesGiftCards
                )
        )

        Thread.sleep(4000)

        return providers
    }
    */
}