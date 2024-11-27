package mx.aztek.hackernewsreader.model

import com.google.gson.annotations.SerializedName

data class Exhaustive(
    @SerializedName("nbHits") var nbHits: Boolean? = null,
    @SerializedName("typo") var typo: Boolean? = null,
)
