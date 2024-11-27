package mx.aztek.hackernewsreader.model

import com.google.gson.annotations.SerializedName

data class ProcessingTimingsMS(
    @SerializedName("_request") var request: Request? = Request(),
    @SerializedName("afterFetch") var afterFetch: AfterFetch? = AfterFetch(),
    @SerializedName("fetch") var fetch: Fetch? = Fetch(),
    @SerializedName("total") var total: Int? = null,
)
