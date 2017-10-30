package com.example.shenxuesong.jdtt.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shenxuesong.jdtt.R;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by shenxuesong on 2017/9/14.
 */

public class Fragment01 extends Fragment {
    private ViewPager vp1;
    private TabLayout tb;
    private List<String> list=new ArrayList<String>();//收集标题
    private ViewPager vp;
    private TextView tv;
    private LinearLayout ll;
    private List<View> dolist=new ArrayList<View>();
    private List<String> str=new ArrayList<String>();
    private List<ImageView> imagelist=new ArrayList<ImageView>();
    private int current=0;
    private int old_list=0;
    private  String string;
    private Handler h=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){


                int i=msg.arg1;
                vp.setCurrentItem(i);
                tv.setText(str.get(i%imagelist.size()));
                dolist.get(old_list).setBackgroundResource(R.drawable.shape);
                dolist.get(i%imagelist.size()).setBackgroundResource(R.drawable.shape1);
                old_list=i%imagelist.size();
            }

        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment01,container,false);
        //查找组件的ID
        vp1=(ViewPager) view.findViewById(R.id.vp1);
        vp=(ViewPager) view.findViewById(R.id.vp);
        tv=(TextView) view.findViewById(R.id.tv);
        ll=(LinearLayout)view.findViewById(R.id.ll) ;
        tb=(TabLayout)view.findViewById(R.id.tb);
        //TabLayout和ViewPager
        init();
        //Viewpager和小圆点的联动
        initData();

        return view;

    }



    private void init() {
        //tab的标题
        list.add("头条");
        list.add("社会");
        list.add("国内");
        list.add("国际");
        list.add("娱乐");
        list.add("体育");
        list.add("军事");
        list.add("科技");
        list.add("财经");
        list.add("时尚");

        //将TabLayout和Viewpager关联

        tb.setupWithViewPager(vp1);

        //设置适配器
        vp1.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                NewsFragment newsFragment = new NewsFragment();
                Bundle bundle = new Bundle();
                if(list.get(position).equals("头条")){
                    bundle.putString("name","top");
                }
                if(list.get(position).equals("社会")){
                    bundle.putString("name","shehui");
                }
                else
                if(list.get(position).equals("国内")){
                    bundle.putString("name","guonei");
                }
                else
                if(list.get(position).equals("国际")){
                    bundle.putString("name","guoji");
                }
                else
                if(list.get(position).equals("娱乐")){
                    bundle.putString("name","yule");
                }
                else
                if(list.get(position).equals("财经")){
                    bundle.putString("name","caijing");
                }
                else
                if(list.get(position).equals("军事")){
                    bundle.putString("name","junshi");
                }
                else
                if(list.get(position).equals("科技")){
                    bundle.putString("name","keji");
                }
                else
                if(list.get(position).equals("体育")){
                    bundle.putString("name","tiyu");
                }
                else
                if(list.get(position).equals("时尚")){
                    bundle.putString("name","shishang");
                }
                //动态传值给NewsFragment
                newsFragment.setArguments(bundle);
                return newsFragment;
            }

            @Override
            public int getCount() {
                return list.size();
            }
            //显示标题的方法
            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }
        });


    }
    private void initData() {
        //解析
        jiexi();
    }

    private void jiexi() {
        new AsyncTask<String,Integer,String>(){
            @Override
            protected String doInBackground(String... strings) {
                String st="";
                try {
                    URL url = new URL("http://v.juhe.cn/toutiao/index?type=top&key=597b4f9dcb50e051fd725a9ec54d6653");
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
                List<JavaBean.ResultBean.DataBean> data = gson.fromJson(s, JavaBean.class).getResult().getData();
               for (int i=0;i<data.size();i++){
                   String thumbnail_pic_s02 = data.get(i).getThumbnail_pic_s02();
                   String title = data.get(i).getTitle();
                   //将标题添加到集合
                   str.add(title);
                  //获取网络请求的图片数据，并把它添加到imagelist中
                   getImageView(thumbnail_pic_s02);
                   //得到圆点
                   getYuanDian();
               }
                //执行pagerAdapter适配器的方法
                initadapter();
            }
        }.execute();
    }

    private void initadapter() {
        //设置适配器
        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                View imageView = imagelist.get(position % imagelist.size());

                container.addView(imageView);

                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        //设置圆点的第一个被选中
        dolist.get(0).setBackgroundResource(R.drawable.shape1);
        //设置viewpager的当前位置
        vp.setCurrentItem(50000000);

        //启动定时器
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                current=vp.getCurrentItem()+1;
                Message msg = Message.obtain();
                msg.arg1=current;
                msg.what=1;

                h.sendMessage(msg);
            }
        }, 3000, 2000);
    }

    private void getImageView(String picUrl) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageSize size=new ImageSize(200,100);
        ImageLoader.getInstance().displayImage(picUrl,imageView,size);
        imagelist.add(imageView);

    }
    private void getYuanDian() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item, null);
        View imageview = view.findViewById(R.id.view);
        //不懂
        dolist.add(imageview);

        ll.addView(view);

    }
}
