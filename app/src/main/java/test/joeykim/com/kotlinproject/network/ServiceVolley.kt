package test.joeykim.com.kotlinproject.network

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class ServiceVolley: ServiceInterface {
    val TAG = ServiceVolley::class.java.simpleName
    val basePath = ""

    override fun post(path: String, params: JSONObject?, header:HashMap<String,String>?, completionHandler: (response: JSONObject?, error:VolleyError?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.POST, basePath + path, params,
                Response.Listener<JSONObject> { response ->
                    Log.d(TAG, "/post request OK! Response: $response")
                    completionHandler(response, null)
                },
                Response.ErrorListener { error ->
                    VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                    completionHandler(null, error)
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                if(header == null){
                    val headers = HashMap<String, String>()
                    //headers.put("X-Naver-Client-Id", "2CIuFkW3CrTa1QU3t9q_")
                    //headers.put("X-Naver-Client-Secret", "SlOM0Q8YTy")
                    headers.put("Authorization", "Bearer "+ "46e0ce09-6987-4895-a8bb-24af2eb471e1")
                    return headers
                }else{
                    return header
                }

            }

        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }

    override fun get(path: String, params: JSONObject?, header:HashMap<String,String>?, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.GET, basePath + path, params,
                Response.Listener<JSONObject> { response ->
                    Log.d(TAG, "/get request OK! Response: $response")
                    completionHandler(response)
                },
                Response.ErrorListener { error ->
                    VolleyLog.e(TAG, "/get request fail! Error: ${error.message}")
                    completionHandler(null)
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                if(header == null){
                    val headers = HashMap<String, String>()
                    //headers.put("X-Naver-Client-Id", "2CIuFkW3CrTa1QU3t9q_")
                    //headers.put("X-Naver-Client-Secret", "SlOM0Q8YTy")
                    headers.put("Authorization", "Bearer "+ "46e0ce09-6987-4895-a8bb-24af2eb471e1")
                    return headers
                }else{
                    return header
                }
            }




        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }
}