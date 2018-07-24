package test.joeykim.com.kotlinproject.model

import com.google.gson.annotations.SerializedName

data class APIResData_NSearch(
    @SerializedName("lastBuildDate") val lastBuildDate: String,
    @SerializedName("total") val total: String,
    @SerializedName("start") val start: Int,
    @SerializedName("display") val display: Int,
    @SerializedName("items") val items: List<items>
)

data class items(
    @SerializedName("title") val title: String,
    @SerializedName("link") val link: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("sizeheight") val sizeheight: String,
    @SerializedName("sizewidth") val sizewidth: String
)

/*
data class Books(val b_subnam: String, val imgUrl: String, val b_nam: String, val linkUrl: String) {

}
*/