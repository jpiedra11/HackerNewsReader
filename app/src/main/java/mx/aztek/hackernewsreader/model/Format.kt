package mx.aztek.hackernewsreader.model

import com.google.gson.annotations.SerializedName

data class Format(
    @SerializedName("total") var total: Int? = null,
)
