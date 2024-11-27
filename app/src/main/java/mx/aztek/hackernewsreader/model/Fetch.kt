package mx.aztek.hackernewsreader.model

import com.google.gson.annotations.SerializedName

data class Fetch(
    @SerializedName("query") var query: Int? = null,
    @SerializedName("scanning") var scanning: Int? = null,
    @SerializedName("total") var total: Int? = null,
)
