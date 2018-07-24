package test.joeykim.com.kotlinproject.network

import org.json.JSONObject

interface ServiceInterface {
    fun post(path: String, params: JSONObject?, header:HashMap<String,String>?, completionHandler: (response: JSONObject?) -> Unit)
    fun get(path: String, params: JSONObject?, header:HashMap<String,String>?, completionHandler: (response: JSONObject?) -> Unit)
}