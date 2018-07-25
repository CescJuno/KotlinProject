package test.joeykim.com.kotlinproject.network

import com.android.volley.VolleyError
import org.json.JSONObject

interface ServiceInterface {
    fun post1(path: String, params: HashMap<String,String>?, header:HashMap<String,String>?, completionHandler: (response: String?, error: VolleyError?) -> Unit)
    fun post(path: String, params: JSONObject?, header:HashMap<String,String>?, completionHandler: (response: JSONObject?, error: VolleyError?) -> Unit)
    fun get(path: String, params: JSONObject?, header:HashMap<String,String>?, completionHandler: (response: JSONObject?) -> Unit)
}