package com.tentcoo.jswebview;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.HybirdViewContainer;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.HybridViewContainerBuilder;
import com.tentcoo.pineapple.core.framework.ioc.factory.BeanFactory;
import com.tentcoo.pineapple.jsbridge.BridgeWebView;
import com.tentcoo.pineapple.jsbridge.CallBackFunction;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements OnClickListener {

    private Button btn1, btn2;
    private Button webjs;
    private BridgeWebView webView;
    Map<String,String> user=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user.put("name","测试");
        initIU();
        initEvent();
    }

    private void initIU() {
        // btn1 = (Button) findViewById(R.id.btn);
        // btn2 = (Button) findViewById(R.id.btn2);
        // webjs = (Button) findViewById(R.id.webjs);
        webView = (BridgeWebView) findViewById(R.id.webView);
        // webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebChromeClient(new WebChromeClient());
        // webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/dist/jssdk.html");
        /* webView.loadUrl("file:///android_asset/demo1.html");*/

        HybirdViewContainer build = BeanFactory.getInstance().build(
                getApplicationContext(), HybirdViewContainer.class,
                HybridViewContainerBuilder.getInstance(), webView);
        webView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Toast.makeText(MainActivity.this,"传过去给webview:"+data, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initEvent() {
        EventBus.getDefault().register(this);
        // btn1.setOnClickListener(this);
        // btn2.setOnClickListener(this);
        // webjs.setOnClickListener(this);
    }

    @Subscriber(tag = EventBusTag.rotation)
    private void ApiTag(String IpString) {
        Log.e("backinfo","ApiTag");
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

    }
    @Subscriber(tag=EventBusTag.getH5Data)
    private void GetH5(String IpString){
        Log.e("backinfo","跑到这里了"+IpString);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
            webView.goBack();
            return true;
        } else if (!webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
            webView.loadUrl("about:blank");
            finish();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
		/*
		 * case R.id.btn: PineApple.Init("xml/config_config.properties",
		 * getApplicationContext()); // parseBeanXml(); // parseKeyValue();
		 * parseXml(); break;
		 */
		/*
		 * case R.id.btn2: getBean(); break;
		 */
		/*
		 * case R.id.webjs: HybirdViewContainer build =
		 * BeanFactory.getInstance().build(getApplicationContext(),
		 * HybirdViewContainer.class,HybridViewContainerBuilder.getInstance(),
		 * webView); break;
		 */
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
