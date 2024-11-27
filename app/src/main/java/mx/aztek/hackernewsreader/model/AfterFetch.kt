package mx.aztek.hackernewsreader.model

import com.google.gson.annotations.SerializedName

data class AfterFetch(
    @SerializedName("format") var format: Format? = Format(),
)
