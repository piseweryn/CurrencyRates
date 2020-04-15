package com.seweryn.ratesapplication.utils.resources

import android.content.res.Resources

class RatesContentProviderImpl(
    private val resources: Resources,
    private val resourceWrapper: ResourceWrapper
) :
    RatesContentProvider {

    private val RATE_ICON_RES_KEY = "ic_%s_round"
    private val RATE_NAME_RES_KEY = "currency_%s_name"

    override fun getCurrencyName(currencyCode: String): String {
        return resources.getString(
            resourceWrapper.getIdentifier(
                RATE_NAME_RES_KEY.format(currencyCode),
                ResourceType.STRING
            )
        )
    }

    override fun getCurrencyIconResId(currencyCode: String): Int {
        return resourceWrapper.getIdentifier(
            RATE_ICON_RES_KEY.format(currencyCode),
            ResourceType.DRAWABLE
        )
    }

}