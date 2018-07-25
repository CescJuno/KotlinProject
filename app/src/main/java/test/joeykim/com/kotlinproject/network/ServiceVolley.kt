package test.joeykim.com.kotlinproject.network

import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import org.json.JSONException
import java.io.UnsupportedEncodingException
import com.android.volley.Request.Method.POST
import com.android.volley.toolbox.StringRequest




class ServiceVolley: ServiceInterface {
    val TAG = ServiceVolley::class.java.simpleName
    val basePath = ""
    override fun post1(path: String, params: HashMap<String,String>?, header:HashMap<String,String>?, completionHandler: (response: String?, error:VolleyError?) -> Unit) {
        val jsonObjRequest = object : StringRequest(Method.POST, basePath + path,
                Response.Listener { response ->
                    Log.d(TAG, "/post request OK! Response: $response")
                    completionHandler(response, null)
                },
                Response.ErrorListener { error ->
                    VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                    completionHandler(null, error)
                }) {

            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }

            @Throws(AuthFailureError::class)
            override fun getParams(): HashMap<String, String>? {
                return params
            }

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


        BackendVolley.instance?.addToRequestQueue(jsonObjRequest, TAG)
    }
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

            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded"
            }

            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val gson = Gson()
                var param: MutableMap<String, String> = gson.fromJson(params.toString(), object : TypeToken<Map<String, Any>>() {}.type)

                return param
            }

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

            /*
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {

                var body: String? = null
                try {

                    body = params.toString()
                } catch (e: JSONException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

                try {
                    return body!!.toString().toByteArray(charset("utf-8"))
                } catch (e: UnsupportedEncodingException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

                return null
            }
            */


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