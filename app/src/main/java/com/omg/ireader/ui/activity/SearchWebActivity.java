package com.omg.ireader.ui.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.liulishuo.filedownloader.FileDownloader;
import com.omg.ireader.R;
import com.omg.ireader.RxBus;
import com.omg.ireader.event.AddBookCityEvent;
import com.omg.ireader.model.bean.BookCityBean;
import com.omg.ireader.model.local.BookCityRepository;
import com.omg.ireader.ui.base.BaseActivity;
import com.omg.ireader.utils.Constant;
import com.omg.ireader.utils.ToastUtils;
import com.omg.ireader.utils.down.DownLoadWebFileManager;
import com.omg.ireader.widget.WebSearchOnTouchView;
import com.omg.ireader.widget.view.menu.AllAngleExpandableButton;
import com.omg.ireader.widget.view.menu.ButtonData;
import com.omg.ireader.widget.view.menu.ButtonEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SearchWebActivity extends BaseActivity {
    @BindView(R.id.search_web_view)
    WebView mWebView;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.touch_frame)
    WebSearchOnTouchView mTouchFrame;
    @BindView(R.id.web_button_expandable)
    AllAngleExpandableButton mWebButtonExpandable;
    private String mPathUrl = "https://www.baidu.com/";

    @Override
    protected int getContentId() {
        return R.layout.activity_search_web;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        //这是用来传递是进入的哪一个网站，如果是null就是进入百度搜索
        if (extras != null && extras.getString(Constant.BOOK_CITY_TYPE) != null) {
            mPathUrl = extras.getString(Constant.BOOK_CITY_TYPE);
        }
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initWebViewSetting();
        initWebButtonExpandable();
    }

    private void initWebButtonExpandable() {
        mTouchFrame.setAllAngleExpandableButton(mWebButtonExpandable);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.book_case_plus, R.drawable.book_case_mark,R.drawable.book_case_heart, R.drawable.book_case_share };
        int[] color = {R.color.light_blue, R.color.red, R.color.green, R.color.yellow};
        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData;
            if (i == 0) {
                buttonData = ButtonData.buildIconButton(this, drawable[i], 15);
            } else {
                buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            }
            buttonData.setBackgroundColorId(this, color[i]);
            buttonDatas.add(buttonData);
        }
        mWebButtonExpandable.setButtonDatas(buttonDatas);
        mWebButtonExpandable.setButtonEventListener(mButtonEventListener);
    }

    private void initWebViewSetting() {
        mWebView.requestFocusFromTouch();
        mWebView.setWebViewClient(new SearchIReaderWebClient());
        mWebView.setWebChromeClient(new SearchIReaderWebChromeClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//默认是使用本地缓存的
        webSettings.setAppCacheMaxSize(8 * 1024 * 1024);   //缓存最多可以有8M
        webSettings.setAllowFileAccess(true);   // 可以读取文件缓存(manifest生效)
        // 支持使用localStorage(H5页面的支持)
        webSettings.setDomStorageEnabled(true);
        // 应用可以有数据库
        webSettings.setDatabaseEnabled(true);
        String dbPath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(dbPath);
        // 应用可以有缓存
        webSettings.setAppCacheEnabled(true);
        String appCaceDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCaceDir);
        // 设置可以支持缩放
        webSettings.setUseWideViewPort(true);
        // 扩大比例的缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        // 隐藏缩放按钮
        webSettings.setDisplayZoomControls(false);
        // 开启javascript设置
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.setDownloadListener(new SearchIReaderDownloadListener());
        mWebView.loadUrl(mPathUrl);
    }

    @Override
    protected void initClick() {
        super.initClick();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    class SearchIReaderWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            collectUrl=url;
            view.loadUrl(url);
            return true;
        }
    }
    private String collectUrl="";
    class SearchIReaderWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (mProgressBar != null) {
                mProgressBar.setMax(100);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    Log.i("SearchIReaderWeb", "这是加载的时候的进度---》》" + newProgress);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);//设置进度值
                }
            }
            super.onProgressChanged(view, newProgress);

        }

        // 扩充缓存的容量
        @Override
        public void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota,
                                             WebStorage.QuotaUpdater quotaUpdater) {
            quotaUpdater.updateQuota(spaceNeeded * 2);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }


    }

    private String getUrlName(String url){
        String urlName = "未知名称";
        try {
            urlName = URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = urlName.substring(urlName.lastIndexOf('/') + 1);
        return result;
    }
    class SearchIReaderDownloadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            String result = getUrlName(url);
            new SweetAlertDialog(SearchWebActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("温馨提示")
                    .setContentText("是否要下载：" + result)
                    .setCancelText("取消")
                    .setConfirmText("确定")
                    .showCancelButton(true)
                    .setCancelClickListener(sDialog -> {
                        sDialog.cancel();
                    }).setConfirmClickListener(sweetAlertDialog -> {
                ToastUtils.show("开始下载：" + result);
                FileDownloader.setup(SearchWebActivity.this);
                DownLoadWebFileManager.getInstance().startDownLoad(url);
                sweetAlertDialog.cancel();
            }).show();
        }
    }

    ButtonEventListener mButtonEventListener=new ButtonEventListener() {
        @Override
        public void onButtonClicked(int index) {
            switch (index){
                case 1:
                    ToastUtils.show("书签功能正在开发！");
                    break;
                case 2:
                    String title = getUrlName(collectUrl);
                    ToastUtils.show("加入常用书城！");
                    BookCityBean bookCityBean = new BookCityBean(title, collectUrl, R.drawable.read_online, collectUrl);
                    BookCityRepository.getInstance().saveBookCityOption(bookCityBean);
                    RxBus.getInstance().post(new AddBookCityEvent(bookCityBean));
                    break;
                case 3:
                    ToastUtils.show("分享功能正在开发！");
                    break;
            }
        }

        @Override
        public void onExpand() {
        }
        @Override
        public void onCollapse() {
        }
    };
}
