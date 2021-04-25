package study.kstockapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import study.kstockapp.R
import study.kstockapp.databinding.DetailWebviewLayoutBinding

class DetailWebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var webSettings: WebSettings
    private lateinit var binding: DetailWebviewLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val symbol = intent.getStringExtra("symbol")
        if (symbol == null) finish()

        binding = DataBindingUtil.setContentView(this,
                R.layout.detail_webview_layout
        )
        binding.detailWebview.webViewClient = WebViewClient()
        webView = binding.detailWebview

        webSettings = webView.settings
        webSettings.javaScriptEnabled = false
        webSettings.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
        webSettings.javaScriptCanOpenWindowsAutomatically = false // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        webSettings.loadWithOverviewMode = true // 메타태그 허용 여부
        webSettings.useWideViewPort = true // 화면 사이즈 맞추기 허용 여부
        webSettings.setSupportZoom(false) // 화면 줌 허용 여부
        webSettings.builtInZoomControls = false // 화면 확대 축소 허용 여부
        @Suppress("DEPRECATION")
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN // 컨텐츠 사이즈 맞추기
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE // 브라우저 캐시 허용 여부
        webSettings.domStorageEnabled = true // 로컬저장소 허용 여부

        webView.loadUrl("https://finance.yahoo.com/quote/$symbol")

    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            finish()
        }
    }
}