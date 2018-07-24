package test.joeykim.com.kotlinproject.network

import com.android.volley.VolleyError
import org.json.JSONObject

class APIController constructor(serviceInjection: ServiceInterface): ServiceInterface {
    private val service: ServiceInterface = serviceInjection

    override fun post(path: String, params: JSONObject?, header:HashMap<String,String>?, completionHandler: (response: JSONObject?, error: VolleyError?) -> Unit) {
        service.post(path, params, header, completionHandler)
    }

    override fun get(path: String, params: JSONObject?, header:HashMap<String,String>?, completionHandler: (response: JSONObject?) -> Unit) {
        service.get(path, params, header, completionHandler)
    }
}