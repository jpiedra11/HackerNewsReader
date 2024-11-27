package mx.aztek.hackernewsreader.model

import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("queue") var queue: Int? = null,
    @SerializedName("roundTrip") var roundTrip: Int? = null,
)
