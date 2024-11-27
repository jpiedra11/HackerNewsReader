package mx.aztek.hackernewsreader.model

import com.google.gson.annotations.SerializedName

data class StoryTitle(
    @SerializedName("matchLevel") var matchLevel: String? = null,
    @SerializedName("matchedWords") var matchedWords: ArrayList<String> = arrayListOf(),
    @SerializedName("value") var value: String? = null,
)
