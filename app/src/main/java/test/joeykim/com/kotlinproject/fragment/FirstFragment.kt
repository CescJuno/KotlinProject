package test.joeykim.com.kotlinproject.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_first.*
import org.json.JSONObject
import test.joeykim.com.kotlinproject.R
import test.joeykim.com.kotlinproject.adapter.ItemAdapter
import test.joeykim.com.kotlinproject.common.Utils
import test.joeykim.com.kotlinproject.model.*
import test.joeykim.com.kotlinproject.model.APIReqData.DataRequest
import test.joeykim.com.kotlinproject.model.APIReqData.CommonRequest
import test.joeykim.com.kotlinproject.network.APIController
import test.joeykim.com.kotlinproject.network.ServiceVolley
import java.net.URLEncoder

/**
 * Created by joey.j.kim on 2018. 3. 12..
 */

/*
class FirstFragment : BasePresenterFragment<MainContract.View, MainContract.Presenter>(), MainContract.View {

    override fun showMessage(message: String) {
        Toast.makeText(this.activity, "SSSS", Toast.LENGTH_SHORT).show()
    }
    override fun getLayoutResource(): Int {
        return R.layout.fragment_first
    }

    override fun onCreatePresenter() = MainPresenter()
}
class FirstFragment : BaseFragment() {

    override fun getLayoutResource(): Int {
        return R.layout.fragment_first
    }
}
*/
class FirstFragment : Fragment(){

    var view1: View? = null
    var apiController: APIController? = null
    var itemList: ArrayList<Any>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //if(view1 != null)
        //    return view1
        //else {
            view1 = inflater!!.inflate(R.layout.fragment_first, container,
                    false)


            val service = ServiceVolley()
            apiController = APIController(service)
            itemList = ArrayList<Any>()

            /*
            val commonReq = CommonRequest()
            commonReq.trxId = "111"
            commonReq.image = "bbb"
            commonReq.description = "ccc"
            commonReq.name = "ddddd"

            var dataReq = DataRequest()
            dataReq.speaker = "mijin"
            dataReq.speed = "0"
            dataReq.text = "안녕하세요"

            var apiReq = APIReqData(common = commonReq, data = dataReq)

            val gson = Gson()
            val gsonStr = gson.toJson(apiReq.data)
            val params = JSONObject(gsonStr)
            */

            callApi("http://211.45.65.186:8080/ocr/rest/oauth/token","initLogin")

            return view1
        //}
    }

    /**
     * open Api Call
     */
    fun callApi(str: String, typ: String){
        val headers = HashMap<String, String>()
        headers.put("Accept", "application/json")
        val bodyObject= HashMap<String,String>()
        var bodyObject1= JSONObject()
        if(typ.equals("initLogin")){

            var util = Utils()
            var base64key = util.encodeString("my-trusted-client:secret")
            headers.put("Authorization", "Basic "+ base64key)
            bodyObject.put("grant_type","password")
            bodyObject.put("username","uuid")
            bodyObject.put("password","")
        }else if(typ.equals("refreshToken")){
            var util = Utils()
            var base64key = util.encodeString("my-trusted-client:secret")
            headers.put("Authorization", "Basic "+ base64key)
            bodyObject.put("grant_type","refresh_token")
            bodyObject.put("refresh_token","aaaaaaa")
        }else{
            headers.put("Authorization", "Bearer "+ "46e0ce09-6987-4895-a8bb-24af2eb471e1")
            val commonReq = CommonRequest()
            commonReq.trxId = "111"
            commonReq.image = "bbb"
            commonReq.description = "ccc"
            commonReq.name = "ddddd"

            var dataReq = DataRequest()
            dataReq.speaker = "mijin"
            dataReq.speed = "0"
            dataReq.text = "안녕하세요"

            var apiReq = APIReqData(common = commonReq, data = dataReq)

            val gson = Gson()
            val gsonStr = gson.toJson(apiReq.data)
            bodyObject1 = JSONObject(gsonStr)
        }

        if(typ.equals("initLogin") || typ.equals("refreshToken")) {
            apiController?.post1(str, bodyObject, headers) { response, error ->
                Log.d("TEST", "$response $error")
                if (error != null) {
                    if (error.networkResponse.statusCode == 401) {
                        callApi("http://211.45.65.186:8080/ocr/rest/oauth/token", "refreshToken")
                    } else {

                    }
                    //Log.d("${error.networkResponse.statusCode}","$error")
                } else {
                    val gson = Gson()
                    val parser = JsonParser()
                    val rootObj = parser.parse(response.toString())
                }

            }
        }else{
            apiController?.post(str, bodyObject1, headers) { response, error ->
                Log.d("TEST", "$response $error")
                if (error != null) {
                    if (error.networkResponse.statusCode == 401) {
                        callApi("http://211.45.65.186:8080/ocr/rest/oauth/token", "refreshToken")
                    } else {

                    }
                    //Log.d("${error.networkResponse.statusCode}","$error")
                } else {
                    val gson = Gson()
                    val parser = JsonParser()
                    val rootObj = parser.parse(response.toString())
                }

            }
        }
    }


    fun searchNaver(str: String){
        var encodeStr = URLEncoder.encode(str, "UTF-8");
        //val path = "https://openapi.naver.com/v1/search/image?query="+encodeStr+"&display=10&start=1&sort=sim"
        val path = "http://192.168.18.70:8080/ocr/rest/getSampleBoardList";
        /*
        apiController?.post(path,null, null) {response ->
            Log.d("TEST","$response")


            val gson = Gson()
            val parser = JsonParser()
            val rootObj = parser.parse(response.toString())
            /*
            var post = gson.fromJson(rootObj, APIResData_NSearch::class.java)
            //println(post.lastBuildDate)
            var arrayList = ArrayList<HashMap<String,String>>()
            var i = 0
            post.items.forEach{
                var list = HashMap<String,String>()
                list.put("title", it.title)
                list.put("link", it.link)
                list.put("thumbnail", it.thumbnail)
                list.put("sizewidth", it.sizewidth)
                list.put("sizeheight", it.sizeheight)
                arrayList.add(i, list)
                i++
            }
            itemList?.add(0, arrayList)

            searchBookInfo(0)
            */
        }
        */
        apiController?.get(path, null, null){ response ->
            Log.d("TEST","$response")


            val gson = Gson()
            val parser = JsonParser()
            val rootObj = parser.parse(response.toString())

            var post = gson.fromJson(rootObj, APIResData_NSearch::class.java)
            /*
            //println(post.lastBuildDate)
            var arrayList = ArrayList<HashMap<String,String>>()
            var i = 0
            post.items.forEach{
                var list = HashMap<String,String>()
                list.put("title", it.title)
                list.put("link", it.link)
                list.put("thumbnail", it.thumbnail)
                list.put("sizewidth", it.sizewidth)
                list.put("sizeheight", it.sizeheight)
                arrayList.add(i, list)
                i++
            }
            itemList?.add(0, arrayList)

            searchBookInfo(0)
            */
        }
    }

    fun searchBookInfo(intVal:Int){
        //var encodeStr = URLEncoder.encode(str, "UTF-8");
        val path = "http://www.duranno.com/bible/api/info/bookInfo.asp?rdid="+intVal
        apiController?.get(path, null, null){ response ->
            Log.d("TEST","$response")


            val gson = Gson()
            val parser = JsonParser()
            val rootObj = parser.parse(response.toString())

            var post = gson.fromJson(rootObj, APIResData::class.java)
            //println(post.lastBuildDate)
            var arrayList = ArrayList<HashMap<String,String>>()
            var i = 0
            post.arrayData.forEach{
                var list = HashMap<String,String>()
                list.put("b_nam", it.b_nam)
                list.put("b_subnam", it.b_subnam)
                list.put("imgUrl", it.imgUrl)
                list.put("linkUrl", it.linkUrl)
                arrayList.add(i, list)
                i++
            }
            itemList?.add(1, arrayList)

            rv_item_list.layoutManager = LinearLayoutManager(context)
            rv_item_list.layoutManager = GridLayoutManager(context,1)
            rv_item_list.adapter = ItemAdapter(itemList, context)
        }
    }
}
