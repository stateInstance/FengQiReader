package com.omg.ireader.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.omg.ireader.R;
import com.omg.ireader.ui.base.BaseTabActivity;
import com.omg.ireader.ui.fragment.BookCaseFragment;
import com.omg.ireader.ui.fragment.BookCityFragment;
import com.omg.ireader.ui.fragment.FindFragment;
import com.omg.ireader.utils.Constant;
import com.omg.ireader.utils.SharedPreUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseTabActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int WAIT_INTERVAL = 2000;
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private boolean isPrepareFinish = false;
    private BookCaseFragment bookShelfFragment;

    @Override
    protected int getContentId() {
        return R.layout.activity_base_tab;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        toolbar.setLogo(R.drawable.launcher_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout); //这是带Home旋转开关按钮 ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close); drawer.setDrawerListener(toggle); toggle.syncState();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        initNavigatListener();
    }

    private void initNavigatListener() {
        NavigationView mNavgationView = (NavigationView) findViewById(R.id.nav_view);
        mNavgationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected List<Fragment> createTabFragments() {
        initFragment();
        return mFragmentList;
    }

    private void initFragment() {
//        Fragment bookShelfFragment = new BookShelfFragment();
        bookShelfFragment = new BookCaseFragment();
//        Fragment communityFragment = new CommunityFragment();
        Fragment communityFragment = new BookCityFragment();
        Fragment discoveryFragment = new FindFragment();
        mFragmentList.add(bookShelfFragment);
        mFragmentList.add(communityFragment);
        mFragmentList.add(discoveryFragment);
    }

    @Override
    protected List<String> createTabTitles() {
        String[] titles = getResources().getStringArray(R.array.nb_fragment_title);
        return Arrays.asList(titles);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        String load = SharedPreUtils.getInstance()
                .getString(Constant.FIRST_LOAD);
        if (load.equals("")) {
            showChooseDialog();
        }
    }

    private void showChooseDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("温馨提示")
                .setContentText("首次加载是导入书籍？")
                .setCancelText("网络加载")
                .setConfirmText("本地导入")
                .showCancelButton(true)
                .setCancelClickListener(sDialog -> {
                    mVp.setCurrentItem(1);
                    sDialog.cancel();
                }).setConfirmClickListener(sweetAlertDialog -> {
            Intent intent = new Intent(MainActivity.this, NativeFileSystemActivity.class);
            startActivity(intent);
            sweetAlertDialog.cancel();
        }).show();
        SharedPreUtils.getInstance()
                .putString(Constant.FIRST_LOAD, Constant.FIRST_LOAD);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Class<?> activityCls = null;
        switch (id) {
            case R.id.menu_action_search:
                activityCls = SearchActivity.class;
                break;
            default:
                break;
        }
        if (activityCls != null) {
            Intent intent = new Intent(this, activityCls);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (menu != null && menu instanceof MenuBuilder) {
            try {
                Method method = menu.getClass().
                        getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(menu, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    public void onBackPressed() {
        if (bookShelfFragment != null && bookShelfFragment.backPress()) {
        } else {
            if (!isPrepareFinish) {
                mVp.postDelayed(
                        () -> isPrepareFinish = false, WAIT_INTERVAL
                );
                isPrepareFinish = true;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Class<?> activityCls = null;
        switch (id) {
            case R.id.action_search:
                activityCls = SearchWebActivity.class;
                break;
            case R.id.action_download:
                activityCls = DownloadActivity.class;
                break;
            case R.id.action_scan_local_book:
                activityCls = NativeFileSystemActivity.class;
                break;
            case R.id.action_feedback:
                break;
            case R.id.action_settings:
                activityCls = MoreSettingActivity.class;
                break;
            default:
                break;
        }
        if (activityCls != null) {
            Intent intent = new Intent(this, activityCls);
            startActivity(intent);
        }
        return true;
    }
}
