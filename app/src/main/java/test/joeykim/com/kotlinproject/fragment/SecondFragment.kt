package test.joeykim.com.kotlinproject.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import test.joeykim.com.kotlinproject.R
import test.joeykim.com.kotlinproject.webview.CustomWebView
import test.joeykim.com.kotlinproject.activity.VideoViewActivity


/**
 * Created by joey.j.kim on 2018. 3. 12..
 */
class SecondFragment : Fragment(){
    var web1: WebView? = null
    var view_first: View? = null
    var mContext: Fragment? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(view_first == null)
            view_first = inflater!!.inflate(R.layout.fragment_second, container,
                false)
        else{

            val intent = VideoViewActivity.newIntent(this.context!!, null)
            startActivity(intent)
            return view_first
        }

        web1 = view_first?.findViewById(R.id.web1)
        mContext = this


        //git Test1
        //var customWeb1 = CustomWebView(null, mContext)
        //customWeb1.initView(web1!!)
        //customWeb1.LoadUrl("https://m.daum.net", null)

        val intent = VideoViewActivity.newIntent(this.context!!, null)
        startActivity(intent)

        return view_first
    }



}