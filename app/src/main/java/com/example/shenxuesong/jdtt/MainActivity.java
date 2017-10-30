package com.example.shenxuesong.jdtt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.shenxuesong.jdtt.Fragment.Fragment01;
import com.example.shenxuesong.jdtt.Fragment.Fragment02;
import com.example.shenxuesong.jdtt.Fragment.Fragment03;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private ViewPager vp;
    private DrawerLayout dl;
    private ListView lv;
    private List<Fragment> list=new ArrayList<Fragment>();
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //查找组件的id
        vp=(ViewPager)findViewById(R.id.vp);
        dl=(DrawerLayout)findViewById(R.id.activity_main);
        lv=(ListView)findViewById(R.id.lv);
        rg=(RadioGroup)findViewById(R.id.rg) ;

        //对Fragment的进行相关操作的自定义方法
        initView();

        //进行侧拉的自定义方法
        initData();


    }

    private void initView() {
        //将Fragmnet添加到list中
        list.add(new Fragment01());
        list.add(new Fragment02());
        list.add(new Fragment03());

        //设置适配器
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        //vp和rg 进行对应控制
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              switch (position){
                  case 0 :
                      rg.check(R.id.but1);
                  break;
                  case 1 :
                      rg.check(R.id.but2);
                  break;
                  case 2 :
                      rg.check(R.id.but3);
                  break;
              }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rg.setOnCheckedChangeListener( this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.but1:
                vp.setCurrentItem(0);
            break;
            case R.id.but2:
                vp.setCurrentItem(1);
            break;
            case R.id.but3:
                vp.setCurrentItem(2);
            break;
        }
    }
    //制作侧拉
    private void initData() {
        
        dl.openDrawer(lv);


    }

}
