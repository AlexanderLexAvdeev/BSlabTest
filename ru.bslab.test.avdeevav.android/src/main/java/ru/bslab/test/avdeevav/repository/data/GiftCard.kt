package ru.bslab.test.avdeevav.repository.data

data class GiftCard(
        val id: Long,
        val featured: Boolean,
        val title: String,
        val credits: Int,
        val image_url: String,
        val codes_count: Int,
        val currency: String,
        val description: String,
        val redeem_url: String
)