package ru.bslab.test.avdeevav.repository.data

data class Provider(
        val id: Long,
        val title: String,
        val image_url: String,
        val gift_cards: ArrayList<GiftCard>
)