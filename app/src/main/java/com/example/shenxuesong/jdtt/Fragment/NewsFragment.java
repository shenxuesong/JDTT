package com.example.shenxuesong.jdtt.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shenxuesong.jdtt.Adapter.MyAdapterlist;
import com.example.shenxuesong.jdtt.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


import me.maxwin.view.XListView;

/**
 * Created by shenxuesong on 2017/9/14.
 */

public class NewsFragment extends Fragment implements XListView.IXListViewListener {
    private XListView xlv;
     private String string; //接收Fragment01传来的值
    private List<JavaBean.ResultBean.DataBean> data;
    private  MyAdapterlist myAdapterlist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.news_item, container, false);
        xlv= (XListView) view.findViewById(R.id.xlv);

        //接收Fragment01传来的值
        Bundle bundle = getArguments();
        string = bundle.getString("name");
        //下拉刷新的设置
        xlv.setPullLoadEnable(true);
        //解析
        jiexi();
        //对XLisrView进行上拉加载，下拉刷新的功能
        getXlv();
        return view;
    }



    private void jiexi() {
        new AsyncTask<String,Integer,String>(){
            @Override
            protected String doInBackground(String... strings) {
                String st="";
                try {
                    URL url = new URL("http://v.juhe.cn/toutiao/index?type="+string+"&key=597b4f9dcb50e051fd725a9ec54d6653");
                    HttpURLConnection http=(HttpURLConnection)url.openConnection();
                    int code = http.getResponseCode();
                    if(code==200){
                        InputStream in = http.getInputStream();
                        byte[] b=new byte[1024];
                        int len=0;
                        while ((len=in.read(b))!=-1){
                            st+=new String(b,0,len);
                        }

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return st;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Gson gson = new Gson();
                 data = gson.fromJson(s, JavaBean.class).getResult().getData();
                myAdapterlist = new MyAdapterlist(data, getActivity());
                xlv.setAdapter(myAdapterlist);


            }
        }.execute();

    }

    private  void getXlv() {

         xlv.setXListViewListener(this);

    }

    @Override
    public void onRefresh() {
        jiexi();
        //调用停止刷新的
        stopLod();
    }

    @Override
    public void onLoadMore() {
        new AsyncTask<String,Integer,String>(){
            @Override
            protected String doInBackground(String... strings) {
                String st="";
                try {
                    URL url = new URL("http://v.juhe.cn/toutiao/index?type="+string+"&key=597b4f9dcb50e051fd725a9ec54d6653");
                    HttpURLConnection http=(HttpURLConnection)url.openConnection();
                    int code = http.getResponseCode();
                    if(code==200){
                        InputStream in = http.getInputStream();
                        byte[] b=new byte[1024];
                        int len=0;
                        while ((len=in.read(b))!=-1){
                            st+=new String(b,0,len);
                        }

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return st;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Gson gson = new Gson();
                List<JavaBean.ResultBean.DataBean> data1 = gson.fromJson(s, JavaBean.class).getResult().getData();
                data.addAll(data1);
                myAdapterlist .notifyDataSetChanged();


            }
        }.execute();
        //停止加载的方法
        stopLod();
    }
    //停止刷新和停止加载的方法
    private void stopLod(){
        xlv.stopLoadMore();
        xlv.stopRefresh();
        xlv.setRefreshTime("刚刚");
    }
}
