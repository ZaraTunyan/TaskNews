package com.nytimes.task.network.data


import com.google.gson.annotations.SerializedName

data class NewsItem(
    @SerializedName("id") val id: Long,
    @SerializedName("published_date") val publishedDate: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("abstract") val abstract: String?,
    @SerializedName("byline") val byline: String?,
    @SerializedName("media") val mediaList: List<Media>?,
    @SerializedName("source") val source: String?
)

data class Media(
    @SerializedName("type") val type : MediaType?,
    @SerializedName("media-metadata") val metadataList : List<Metadata>?
)

data class Metadata(
    @SerializedName("url") val url : String?,
    @SerializedName("format") val format : String?
)

enum class MediaType{
    image,
    video
}

enum class ImageFormatType(val type : String){
    ImageThumbnail("Standard Thumbnail"),
    ImageThreeByTwo("mediumThreeByTwo210")
}