package test.joeykim.com.kotlinproject.activity

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web.*
import test.joeykim.com.kotlinproject.R
import test.joeykim.com.kotlinproject.databinding.ActivityWebBinding
import test.joeykim.com.kotlinproject.webview.CustomWebView

class WebViewActivity : AppCompatActivity() {

    var mContext: Activity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Action Bar Hidden
        supportActionBar?.hide()
        val binding: ActivityWebBinding = DataBindingUtil.setContentView(this, R.layout.activity_web)

        //val user = User("kuma", 23)
        //binding.setVariable(BR.user,user)
        binding.executePendingBindings()

        //webView = findViewById(R.id.webView)
        mContext = this

        var customWeb1 = CustomWebView(mContext, null)
        customWeb1.initView(webView!!)
        customWeb1.LoadUrl("https://m.daum.net", null)


    }
}