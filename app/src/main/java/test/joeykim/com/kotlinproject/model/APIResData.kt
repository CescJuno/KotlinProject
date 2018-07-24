package test.joeykim.com.kotlinproject.model

import com.google.gson.annotations.SerializedName

data class APIResData(
    @SerializedName("resultCode") val resultCode: String,
    @SerializedName("arrayData") val arrayData: List<Books>
)

data class Books(
    @SerializedName("b_subnam") val b_subnam: String,
    @SerializedName("b_nam") val b_nam: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("linkUrl") val linkUrl: String
)

/*
data class Books(val b_subnam: String, val imgUrl: String, val b_nam: String, val linkUrl: String) {

}
*/