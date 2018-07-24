package test.joeykim.com.kotlinproject.webview


interface WebViewJSListener {
    fun sendAppKey(oneId: String, aisvToken: String)
    fun setAisvToken()
}
