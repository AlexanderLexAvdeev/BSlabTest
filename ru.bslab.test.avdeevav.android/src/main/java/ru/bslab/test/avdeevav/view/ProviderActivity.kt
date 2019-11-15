package ru.bslab.test.avdeevav.view

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import ru.bslab.test.avdeevav.R
import ru.bslab.test.avdeevav.repository.data.GiftCard

class ProviderActivity : AppCompatActivity(), IGiftCard {

    private var giftCard: GiftCard? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_provider)
        initView(savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()

        return true
    }

    override fun onBackPressed() {

        // if the last fragment left in back stack
        if (supportFragmentManager.backStackEntryCount == 1) {
            setupToolbar(false)
        }

        super.onBackPressed()
    }


    override fun showDetail(giftCard: GiftCard) {

        this.giftCard = giftCard

        setFragment(
                GiftCardDetailFragment.getInstance(supportFragmentManager, giftCard),
                GiftCardDetailFragment.getTag()
        )
    }


    private fun initView(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            setFragment(
                    ProviderListFragment.getInstance(supportFragmentManager),
                    ProviderListFragment.getTag()
            )
        } else {
            // if only root fragment added
            if (supportFragmentManager.backStackEntryCount == 0) {
                setupToolbar(false)
            } else {
                // TODO: fix back arrow when GiftCard no longer exist
                //       on restore from long time in background
                //       (on[Save/Restore]InstanceState)
                setupToolbar(true)
            }
        }
    }

    private fun setupToolbar(showHomeAsUp: Boolean) {

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp)
            if (showHomeAsUp) {
                actionBar.title = null
            } else {
                actionBar.setTitle(R.string.appName)
            }
        }
    }

    private fun setFragment(fragment: Fragment, tag: String) {

        val fragmentAlreadyAdded: Boolean
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        when (tag) {
            ProviderListFragment.getTag() -> {
                setupToolbar(false)
                fragmentTransaction
                        .replace(R.id.fragmentContainer, fragment, tag)
            }

            GiftCardDetailFragment.getTag() -> {
                fragmentAlreadyAdded = supportFragmentManager
                        .findFragmentByTag(GiftCardDetailFragment.getTag())?.isAdded ?: false

                if (fragmentAlreadyAdded) {

                    return
                }

                setupToolbar(true)
                fragmentTransaction
                        .add(R.id.fragmentContainer, fragment, tag)
                        .addToBackStack(tag)
            }
        }

        fragmentTransaction.commit()
    }
}