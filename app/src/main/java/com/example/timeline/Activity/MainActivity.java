package com.example.timeline.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.timeline.Adapters.TimeAdapter;
import com.example.timeline.R;
import com.example.timeline.Time.TimeComparator;
import com.example.timeline.Time.TimeData;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Collections;

import java.util.Date;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    //存储列表数据
    List<TimeData> list = new ArrayList<>();
    TimeAdapter adapter;
    String s = getUnixTransferTime();//获取当前时间戳并转化为日期格式
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        //悬浮按钮,启动编辑界面
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EventActivity.class);
                startActivity(intent);
            }
        });
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        RecyclerView rlView = (RecyclerView) findViewById(R.id.activity_rlview);
        //初始化数据
        initData();
        // 将数据按照时间排序
        TimeComparator comparator = new TimeComparator();
        Collections.sort(list, comparator);
        // recyclerview绑定适配器
        rlView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TimeAdapter(this, list);
        rlView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.
                OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this,"you clicked Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"you clicked Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this,"you clicked Setting",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String returnedTime = data.getStringExtra("time");
                    String returnedContent = data.getStringExtra("content");
                    String t = "20180202";
                    String c = "20180303";
                    initData(t,c);
                    initData(returnedTime,returnedContent);
                }
                break;
            default:
        }
    }

    private void initData() {
        list.add(new TimeData(s, "时间戳"));
        list.add(new TimeData("20170710", "我是第一个数据"));
        list.add(new TimeData("20140709", "我是多数据模块第一个数据"));
        list.add(new TimeData("20140706", "我是最后一个数据"));
        list.add(new TimeData("20140706", "我是最后一个数据"));
    }
    private void initData(String dtime,String dcontent){
        list.add(new TimeData(dtime,dcontent));
    }
    private String getUnixTransferTime(){
        System.out.println("转换的日期是：");
        long time = (int) (System.currentTimeMillis() / 1000);
        Date date = new Date(time*1000);
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        String nowDateString=format.format(date);

        return nowDateString;
    }
    private void refreshData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

}


