package mx.aztek.hackernewsreader.model

import com.google.gson.annotations.SerializedName

data class HighlightResult(
    @SerializedName("author") var author: Author? = Author(),
    @SerializedName("comment_text") var commentText: CommentText? = CommentText(),
    @SerializedName("story_title") var storyTitle: StoryTitle? = StoryTitle(),
    @SerializedName("story_url") var storyUrl: StoryUrl? = StoryUrl(),
)
