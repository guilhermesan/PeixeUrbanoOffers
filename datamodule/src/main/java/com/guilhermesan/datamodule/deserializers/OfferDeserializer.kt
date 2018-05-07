package com.guilhermesan.datamodule.deserializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.guilhermesan.datacontracts.vos.Offer
import java.lang.reflect.Type

class OfferDeserializer : JsonDeserializer<Offer> {

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Offer {
        val jo = json.asJsonObject
        val thumbnailUrl = jo["images"].asJsonArray[0].asJsonObject["image"].asString
        val title = jo["partner"].asJsonObject["name"].asString
        val description = jo["short_title"].asString
        val price = jo["sale_price"].asDouble
        val id = jo["deal_id"].asString
        return Offer(id,thumbnailUrl,title,description,price)
    }
}