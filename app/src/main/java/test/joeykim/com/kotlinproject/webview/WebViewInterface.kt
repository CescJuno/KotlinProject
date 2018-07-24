package test.joeykim.com.kotlinproject.webview

import android.webkit.JavascriptInterface


class WebViewInterface {
    private var listener: WebViewJSListener? = null

    fun setWebAppJSInterface(listener: WebViewJSListener) {
        this.listener = listener
    }

    @JavascriptInterface
    fun setDebug(str: String) {

    }

    @JavascriptInterface
    fun sendAppKey(oneId: String, aisvToken: String) {
        if (listener != null) listener!!.sendAppKey(oneId, aisvToken)
    }

    @JavascriptInterface
    fun setAisvToken() {
        if (listener != null) {
            listener!!.setAisvToken()
        }
    }
}