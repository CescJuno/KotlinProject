package test.joeykim.com.kotlinproject.model

import com.google.gson.annotations.SerializedName


class APIReqData {
    /*
    var trxId: String = ""
    get() = if (field.length > 0) field else "trxId"
    set(value){
        if(value.length > 0) field = value else ""
    }
    */
    var common: CommonRequest?
    var data: DataRequest?

    constructor(common: CommonRequest, data: DataRequest?) {
        this.common = common
        this.data = data
    }

    data class CommonRequest(
            @SerializedName("trxId") var trxId: String? = "",
            @SerializedName("name") var name: String? = "",
            @SerializedName("image") var image: String? = "",
            @SerializedName("description") var description: String? = ""
    )

    data class DataRequest(
            @SerializedName("speaker") var speaker: String? = "",
            @SerializedName("speed") var speed: String? = "",
            @SerializedName("text") var text: String? = ""
    )
}

