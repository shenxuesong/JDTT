package com.example.shenxuesong.jdtt.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shenxuesong.jdtt.Fragment.JavaBean;
import com.example.shenxuesong.jdtt.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.List;

/**
 * Created by shenxuesong on 2017/9/14.
 */

public class MyAdapterlist extends BaseAdapter {
    private List<JavaBean.ResultBean.DataBean> data;
    private Context context;

    public MyAdapterlist(List<JavaBean.ResultBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    //判断条目的子布局
    @Override
    public int getItemViewType(int position) {
        int re=0;
        if(data.get(position).getThumbnail_pic_s()!=null&&data.get(position).getThumbnail_pic_s02()!=null&&data.get(position).getThumbnail_pic_s03()!=null){
            re=0;
        }
        else if(data.get(position).getThumbnail_pic_s()!=null&&data.get(position).getThumbnail_pic_s02()!=null&&data.get(position).getThumbnail_pic_s03()==null){
            re=1;
        }
        else if(data.get(position).getThumbnail_pic_s()!=null&&data.get(position).getThumbnail_pic_s02()==null&&data.get(position).getThumbnail_pic_s03()==null){
            re=2;
        }
        return re;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //进行listview优化
        ViewHolder1 holder1=null;
        ViewHolder2 holder2=null;
        ViewHolder3 holder3=null;
        int re = getItemViewType(i);
        if(re==0){
            if(view==null){
                view=View.inflate(context, R.layout.item1,null);

                holder1=new ViewHolder1();

                holder1.tv=(TextView) view.findViewById(R.id.tv);
                holder1.image1=(ImageView)view.findViewById(R.id.image1);
                holder1.image2=(ImageView)view.findViewById(R.id.image2);
                holder1.image3=(ImageView)view.findViewById(R.id.image3);

                view.setTag(holder1);

            }else{
                holder1= (ViewHolder1) view.getTag();
            }
            holder1.tv.setText(data.get(i).getTitle()+"\n"+data.get(i).getAuthor_name()+"\n"+data.get(i).getDate());
            //获得图片的路劲
            String thumbnail_pic_s = data.get(i).getThumbnail_pic_s();
            String thumbnail_pic_s02 = data.get(i).getThumbnail_pic_s02();
            String thumbnail_pic_s03 = data.get(i).getThumbnail_pic_s03();
            //设置图片的尺寸
            ImageSize size=new ImageSize(100,100);
            //进行网络请求加载图片
            ImageLoader.getInstance().displayImage(thumbnail_pic_s,holder1.image1,size);
            ImageLoader.getInstance().displayImage(thumbnail_pic_s02,holder1.image2,size);
            ImageLoader.getInstance().displayImage(thumbnail_pic_s03,holder1.image3,size);
        }else if(re==1){
            if(view==null){
                view=View.inflate(context,R.layout.item2,null);
                holder2=new ViewHolder2();
                holder2.tv=(TextView) view.findViewById(R.id.tv);
                holder2.image1=(ImageView)view.findViewById(R.id.image1);
                holder2.image2=(ImageView)view.findViewById(R.id.image2);

                view.setTag(holder2);
            }else{
                holder2= (ViewHolder2) view.getTag();
            }
            holder2.tv.setText(data.get(i).getTitle()+"\n"+data.get(i).getAuthor_name()+"\n"+data.get(i).getDate());
            String thumbnail_pic_s = data.get(i).getThumbnail_pic_s();
            String thumbnail_pic_s02 = data.get(i).getThumbnail_pic_s02();

            ImageSize size=new ImageSize(100,100);
            ImageLoader.getInstance().displayImage(thumbnail_pic_s,holder2.image1,size);
            ImageLoader.getInstance().displayImage(thumbnail_pic_s02,holder2.image2,size);
        }else if(re==2){
            if(view==null){
                view=View.inflate(context, R.layout.item3,null);
                holder3=new ViewHolder3();
                holder3.tv=(TextView) view.findViewById(R.id.tv);
                holder3.image1=(ImageView)view.findViewById(R.id.image1);

                view.setTag(holder3);
            }else{
                holder3= (ViewHolder3) view.getTag();
            }
            holder3.tv.setText(data.get(i).getTitle()+"\n"+data.get(i).getAuthor_name()+"\n"+data.get(i).getDate());
            String thumbnail_pic_s = data.get(i).getThumbnail_pic_s();
            ;
            ImageSize size=new ImageSize(100,100);
            ImageLoader.getInstance().displayImage(thumbnail_pic_s,holder3.image1,size);

        }



        return view;
    }
    class ViewHolder3{
        TextView tv;
        ImageView image1;
    }
    class ViewHolder2{
        TextView tv;
        ImageView image1;
        ImageView image2;
    }
    class ViewHolder1{
        TextView tv;
        ImageView image1;
        ImageView image2;
        ImageView image3;


    }
}
