package com.seweryn.ratesapplication.utils.resources

import android.content.Context

class ResourceWrapper(private val context: Context) {

    fun getIdentifier(stringKey: String, resourceType: ResourceType): Int {
        return context.resources.getIdentifier(
            stringKey.toLowerCase(),
            resourceType.typeName,
            context.packageName
        )
    }
}