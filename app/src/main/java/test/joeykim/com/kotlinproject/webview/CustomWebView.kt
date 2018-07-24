package test.joeykim.com.kotlinproject.webview

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Message
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.*
import java.net.URISyntaxException

class CustomWebView {

    private var customWeb: WebView? = null
    private var alertIsp: AlertDialog? = null
    internal var context: Activity? = null
    internal var fcontext: android.support.v4.app.Fragment? = null
    var jsListener: WebViewJSListener? = null

    constructor(mContext: Activity?, fContext: android.support.v4.app.Fragment?){
        context = mContext
        fcontext = fContext
    }


    fun initView(web: WebView) {

        customWeb = web
        configurateWebView(customWeb!!)

        if(context == null){
            alertIsp = null

            alertIsp = AlertDialog.Builder(fcontext?.activity)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("알림")
                    .setMessage("모바일 ISP 어플리케이션이 설치되어 있지 않습니다. \n설치를 눌러 진행 해 주십시요.\n취소를 누르면 결제가 취소 됩니다.")
                    .setPositiveButton("설치") { dialog, which ->
                        //ISP 설치 페이지 URL
                        customWeb!!.loadUrl("http://mobile.vpay.co.kr/jsp/MISP/andown.jsp")
                        fcontext?.activity?.finish()
                    }

                    .setNegativeButton("취소") { dialog, which ->
                        Toast.makeText(context, "(-1)결제를 취소 하셨습니다.", Toast.LENGTH_SHORT).show()
                        fcontext?.activity?.finish()
                    }.create()

        }else{
            alertIsp = null
            alertIsp = AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("알림")
                    .setMessage("모바일 ISP 어플리케이션이 설치되어 있지 않습니다. \n설치를 눌러 진행 해 주십시요.\n취소를 누르면 결제가 취소 됩니다.")
                    .setPositiveButton("설치") { dialog, which ->
                        //ISP 설치 페이지 URL
                        customWeb!!.loadUrl("http://mobile.vpay.co.kr/jsp/MISP/andown.jsp")
                        context?.finish()
                    }

                    .setNegativeButton("취소") { dialog, which ->
                        Toast.makeText(context, "(-1)결제를 취소 하셨습니다.", Toast.LENGTH_SHORT).show()
                        context?.finish()
                    }.create()
        }


    }


    fun configurateWebView(web: WebView) {

        //WebClict 셋팅
        val wvClient = mClient()
        val chClient = chromeClient()
        web.webViewClient = wvClient
        web.webChromeClient = chClient

        web.isHorizontalScrollBarEnabled = true
        web.isVerticalScrollBarEnabled = true

        val webSettings = web.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.setAppCacheEnabled(false)
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.setSupportMultipleWindows(true)
        jsListener = object : WebViewJSListener {
            override fun sendAppKey(oneId: String, aisvToken: String) {
                val resOneId = oneId
                val resAisvToken = aisvToken
            }

            override fun setAisvToken() {
                if(context == null){
                    fcontext?.activity?.runOnUiThread(Runnable {
                        val aisvToken = "e5349ef54fe24002b17ba8d689aec20d"
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            web.evaluateJavascript("BridgeFunc.setAisvToken('$aisvToken');", null)
                        }
                    })
                }else{
                    context?.runOnUiThread(Runnable {
                        val aisvToken = "e5349ef54fe24002b17ba8d689aec20d"
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            web.evaluateJavascript("BridgeFunc.setAisvToken('$aisvToken');", null)
                        }
                    })
                }

            }
        }
        val webInterface = WebViewInterface()
        webInterface.setWebAppJSInterface(jsListener as WebViewJSListener)
        web.addJavascriptInterface(webInterface, "Android")


        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.mixedContentMode = webSettings.mixedContentMode
        }

        if (Build.VERSION.SDK_INT >= 19) {
            web.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            //뒤로가기시 cache error라고 나오는문제 해결
            web.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        } else {
            web.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            webSettings.textZoom = 100
            webSettings.minimumFontSize = 1
            webSettings.loadWithOverviewMode = true
            webSettings.useWideViewPort = false
        }

        webSettings.textSize = WebSettings.TextSize.NORMAL
        web.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        web.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)

        web.clearCache(true)

        web.settings.builtInZoomControls = true
        web.settings.displayZoomControls = false
        web.settings.setSupportZoom(true)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = webSettings.mixedContentMode
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.setAcceptThirdPartyCookies(web, true)
        }

    }

    fun LoadUrl(str: String, extraHeaders: Map<String, String>?) {

        if(context == null){

            //customWeb?.loadUrl(str)

            fcontext?.activity?.runOnUiThread{
                if (extraHeaders == null) {
                    customWeb?.loadUrl(str)
                } else {
                    customWeb?.loadUrl(str, extraHeaders)
                }
            }


        }else{
            context?.runOnUiThread{
                if (extraHeaders == null) {
                    customWeb?.loadUrl(str)
                } else {
                    customWeb?.loadUrl(str, extraHeaders)
                }
            }

        }

    }
    inner class mClient : WebViewClient() {

        val INTENT_PROTOCOL_START = "intent:"
        val INTENT_PROTOCOL_INTENT = "#Intent;"
        val INTENT_PROTOCOL_END = ";end;"
        val GOOGLE_PLAY_STORE_PREFIX = "market://details?id="

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            try {
                /*
                if (!pgBar.isShown() && isRedirected) {
                    pgBar.setVisibility(View.VISIBLE)
                    pgBar.bringToFront()
                    Log.d("Test : ", "PageStart")

                }
                isRedirected = true
                */
                //progress bar start
            } catch (e: Exception) {
                Log.e(javaClass.name, "onPageStarted : " + e.toString())
            }

        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            val uri = Uri.parse(url)
            val strUrl = uri.toString()

            if (strUrl.startsWith("clova://")) {
                //context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                val otherIntent = Intent(Intent.ACTION_VIEW)
                otherIntent.data = Uri.parse("인코딩된URL")
                if(context == null){
                    fcontext?.activity?.startActivity(otherIntent)
                }else{
                    context?.startActivity(otherIntent)
                }

                return true
            }

            return handleUri(view, strUrl)
        }

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val uri = request.url
            val strurl = uri.toString()
            return handleUri(view, strurl)
        }

        private fun handleUri(view: WebView, url: String): Boolean {

            if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:") && !url.startsWith("appcall://")) {

                var intent: Intent
                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    Log.d("<INICIS_TEST>", "intent getDataString : " + intent.dataString!!)
                } catch (ex: URISyntaxException) {
                    Log.e("<INICIS_TEST>", "URI syntax error : " + url + ":" + ex.message)
                    return false
                }

                try {
                    if(context == null){
                        fcontext?.activity?.startActivity(intent)
                    }else{
                        context?.startActivity(intent)
                    }
                } catch (e: ActivityNotFoundException) {
                    /* ISP어플이 현재 폰에 없다면 아래 처리에서
                * 알림을 통해 처리하도록 하였습니다.
                * 삼성카드 및 기타 안심클릭에서는
                * 카드사 웹페이지에서 알아서 처리하기때문에
                * WEBVIEW에서는 별다른 처리를 하지 않아도 처리됩니다. */
                    if (url.startsWith("ispmobile://")) {
                        //onCreateDialog에서 정의한 ISP 어플리케이션 알럿을 띄워줍니다. //(ISP 어플리케이션이 없을경우)
                        alertIsp?.show()
                        return false

                    } else if (url.startsWith("intent://")) { //intent 형태의 스키마 처리
                        try {
                            var excepIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                            val packageNm = excepIntent.`package`
                            Log.d("<INIPAYMOBILE>", "excepIntent getPackage : " + packageNm!!)
                            excepIntent = Intent(Intent.ACTION_VIEW)
                            excepIntent.data = Uri.parse("market://search?q=$packageNm")
                            if(context == null){
                                fcontext?.activity?.startActivity(excepIntent)
                            }else{
                                context?.startActivity(excepIntent)
                            }
                        } catch (e1: URISyntaxException) {

                        }
                        return true

                    } else if (url.startsWith("lguthepay://")) {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        val uri = Uri.parse(intent.dataString)
                        intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        val existPackage =  context!!.packageManager.getLaunchIntentForPackage(intent.`package`)
                        if (existPackage != null) {
                            if(context == null){
                                fcontext?.activity?.startActivity(intent)
                            }else{
                                context?.startActivity(intent)
                            }
                        } else {
                            val marketIntent = Intent(Intent.ACTION_VIEW)
                            marketIntent.data = Uri.parse("market://details?id=" + intent.`package`!!)

                            if(context == null){
                                fcontext?.activity?.startActivity(marketIntent)
                            }else{
                                context?.startActivity(marketIntent)
                            }
                        }
                        //finish();
                        return true

                    }
                }

            } else {
                /*
                    Intent: <- After Catch other WebView open
				    */
                if (url.startsWith(INTENT_PROTOCOL_START)) {
                    val customUrlStartIndex = INTENT_PROTOCOL_START.length
                    val customUrlEndIndex = url.indexOf(INTENT_PROTOCOL_INTENT)
                    if (customUrlEndIndex < 0) {
                        return false
                    } else {
                        val customUrl = url.substring(customUrlStartIndex, customUrlEndIndex)

                        try {
                            if(context == null){
                                fcontext?.activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(customUrl)))
                            }else{
                                context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(customUrl)))
                            }
                        } catch (e: ActivityNotFoundException) {
                            val packageStartIndex = customUrlEndIndex + INTENT_PROTOCOL_INTENT.length
                            val packageEndIndex = url.indexOf(INTENT_PROTOCOL_END)

                            val packageName = url.substring(packageStartIndex, if (packageEndIndex < 0) url.length else packageEndIndex)
                            val newString = packageName.replace("package=", "")
                            if(context == null){
                                fcontext?.activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_STORE_PREFIX + newString)))
                            }else{
                                context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_STORE_PREFIX + newString)))
                            }
                        }

                        return true
                    }
                } else if (url.startsWith("example-app:")) { //내부 새창 열기

                } else {

                    //view.loadUrl(url);
                }

            }


            return false
        }

        override fun onPageFinished(view: WebView, url: String) {
            try {


            } catch (e: Exception) {
                Log.e(javaClass.name, "onPageFinished : " + e.toString())
            }

        }

        override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
            Log.d("onReceivedSslError", "onReceivedSslError")
           //pgBar.setVisibility(View.GONE)
        }

        /*        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            Log.d("onReceivedSslError", "onReceivedSslError");
            pgBar.setVisibility(View.GONE);
            //super.onReceivedSslError(view, handler, error);
        }*/


    }

    inner class chromeClient : WebChromeClient() {
        private val mVideoProgressView: View? = null
        private var mCustomView: View? = null
        private var customViewCallback: WebChromeClient.CustomViewCallback? = null


        override fun onShowCustomView(view: View, requestedOrientation: Int, callback: WebChromeClient.CustomViewCallback) {
            onShowCustomView(view, callback)    //To change body of overridden methods use File | Settings | File Templates.
        }

        override fun onShowCustomView(view: View, callback: WebChromeClient.CustomViewCallback) {

            // if a view already exists then immediately terminate the new one

            if (mCustomView != null) {
                callback.onCustomViewHidden()
                return
            }
            //Main Relative windows.open 으로 열면 뷰를 addview함
            mCustomView = view
            //WebViewActivity.customViewContainer.setVisibility(View.VISIBLE)
            //WebViewActivity.customViewContainer.addView(view)

            customViewCallback = callback

        }

        override fun getVideoLoadingProgressView(): View? {

            if (mVideoProgressView == null) {
            }
            return mVideoProgressView
        }

        override fun onHideCustomView() {
            super.onHideCustomView()    //To change body of overridden methods use File | Settings | File Templates.

            if (mCustomView == null) return

            //WebViewActivity.customViewContainer.setVisibility(View.GONE)

            // Hide the custom view.
            mCustomView!!.visibility = View.GONE

            // Remove the custom view from its container.
            //WebViewActivity.customViewContainer.removeView(mCustomView)
            customViewCallback!!.onCustomViewHidden()

            mCustomView = null

        }


        // 메소드 구현
        override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message): Boolean {


            val rel_params_H = convertDpPixel(45)
            val linParent = LinearLayout(view.context)
            val linParent_params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            linParent.orientation = LinearLayout.VERTICAL

            linParent.layoutParams = linParent_params

            val relTop = RelativeLayout(view.context)
            val rel_params = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    rel_params_H)
            relTop.setBackgroundColor(Color.parseColor("#80000000"))
            relTop.layoutParams = rel_params


            val imv = ImageView(view.context)
            val imv_params = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            //imv.setBackgroundResource(R.drawable.imgchapter)
            imv_params.leftMargin = rel_params_H
            imv.layoutParams = imv_params

            relTop.addView(imv)
            linParent.addView(relTop)

            //웹뷰 추가되는부분
            val newWebView = WebView(view.context)
            //WebView newWebView = new WebView(MainActivity.mContext);
            configurateWebView(newWebView)
            newWebView.tag = "subWebView"
            newWebView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            //customViewContainer.setVisibility(View.VISIBLE)
            linParent.addView(newWebView)
            //customViewContainer.addView(linParent)    // 화면에 보여질 수 있도록 add view

            val btnX = Button(context)
            btnX.setOnClickListener { onCloseWindow(newWebView) }

            val rlParams = RelativeLayout.LayoutParams(convertDpPixel(15), convertDpPixel(15))
            rlParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            rlParams.addRule(RelativeLayout.CENTER_VERTICAL)
            //rlParams.topMargin = convertDpPixel(15);
            rlParams.rightMargin = convertDpPixel(15)

            //x 이미지
            btnX.layoutParams = rlParams
            //btnX.setBackgroundResource(R.drawable.btn_close)
            relTop.addView(btnX)
            //MainActivity.customViewContainer.addView(btnX);


            val transport = resultMsg.obj as WebView.WebViewTransport
            transport.webView = newWebView
            resultMsg.sendToTarget()

            /*
            if (!pgBar.isShown()) {
                pgBar.setVisibility(View.VISIBLE)
                pgBar.bringToFront()
            }
            */
            return true
        }

        override fun onCloseWindow(window: WebView) {
            super.onCloseWindow(window)

            //MainActivity.customViewContainer.removeView(window);    // 화면에서 제거
            //mainWeb.removeAllViews();
            //customViewContainer.removeAllViews()    // 화면에서 제거
            //customViewContainer.setVisibility(View.GONE)


        }

        fun convertDpPixel(dp: Int): Int {
            var r = context!!.getResources()
            if(context == null){
                r = fcontext!!.getResources()
            }

            val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
            return px.toInt()
        }

    }
}