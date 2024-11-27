package mx.aztek.hackernewsreader.model

import com.google.gson.annotations.SerializedName

data class HackerNews(
    @SerializedName("exhaustive") var exhaustive: Exhaustive? = Exhaustive(),
    @SerializedName("exhaustiveNbHits") var exhaustiveNbHits: Boolean? = null,
    @SerializedName("exhaustiveTypo") var exhaustiveTypo: Boolean? = null,
    @SerializedName("hits") var hits: ArrayList<Hits> = arrayListOf(),
    @SerializedName("hitsPerPage") var hitsPerPage: Int? = null,
    @SerializedName("nbHits") var nbHits: Int? = null,
    @SerializedName("nbPages") var nbPages: Int? = null,
    @SerializedName("page") var page: Int? = null,
    @SerializedName("params") var params: String? = null,
    @SerializedName("processingTimeMS") var processingTimeMS: Int? = null,
    @SerializedName("processingTimingsMS") var processingTimingsMS: ProcessingTimingsMS? = ProcessingTimingsMS(),
    @SerializedName("query") var query: String? = null,
    @SerializedName("serverTimeMS") var serverTimeMS: Int? = null,
)
