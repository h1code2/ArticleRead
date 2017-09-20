package huangtx.article.read;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.polaric.colorful.CActivity;

import es.dmoral.toasty.Toasty;

/**
 * Created by huangtongx on 2017/5/19.
 */

public class WebActivity extends CActivity {

    private String URL = null;
    private String TITLE = null;
    private com.tencent.smtt.sdk.WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Intent intent = getIntent();
        URL = intent.getStringExtra("url");
        TITLE = intent.getStringExtra("title");
        setTitle(TITLE);
        loadContent();
//        Toasty.success(getApplicationContext(), URL, Toast.LENGTH_LONG).show();
    }

    private void loadContent() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Document doc = Jsoup.connect(URL).get();
                    doc.select("p.footCopy").remove();
                    Elements ele1 = doc.select("h1.green");
                    Elements ele2 = doc.select("div.singleCon");
                    msg.obj = ele1.toString() + ele2.toString()+"<h4>声明：本软件部分文章以及插图来源于互联网以及网友荐稿，若侵犯了您的权利，请联系我们立即删除！</h4>";
                } catch (Exception e) {

                }
                handler.sendMessage(msg);
            }
        }).start();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mWebView.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
            mWebView.loadData(msg.obj.toString(), "text/html; charset=UTF-8", null);//这种写法可以正确解码
        }
    };

    private void initView() {
        mWebView = (com.tencent.smtt.sdk.WebView) findViewById(R.id.forum_context);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            // 防止加载网页时调起系统浏览器
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);//屏蔽超链接
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_web_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.web_menu_share:
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, TITLE + "”" + URL + ":来自：Article Read");
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1, "分享内容："));
                break;
//            case R.id.web_menu_copy:
//                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(WebActivity.this.CLIPBOARD_SERVICE);
//                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, URL));  // 将内容set到剪贴板
//                Toasty.success(getApplicationContext(), "地址已经复制", Toast.LENGTH_LONG).show();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
