package com.xll.administrator.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xll.administrator.R;
import java.util.ArrayList;
import java.util.HashMap;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVHolder> {
    ArrayList<HashMap<String, String>> list;
    Context context;
    int which;
    int width;
    int height;
    public static int fraPlay = 1;
    public static int fraNews = 2;
    public static int fraBoard = 3;
    public static int fraFlea = 4;
    public static int fraTotal=5;
    public static int fraRecyler=6;

    public RVAdapter(Context context,ArrayList<HashMap<String,String>> list,int which,int width,int height){
        this.context=context;
        this.list=list;
        this.which=which;
        this.width=width;
        this.height=height;

    }

    public RVAdapter(Context context, ArrayList<HashMap<String, String>> list, int which) {
        this.list = list;
        this.context = context;
        this.which = which;
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (which == fraPlay) {
            view = inflater.inflate(R.layout.item_play_together, null);
        }else if(which==fraNews){
            view= inflater.inflate(R.layout.item_activity_news,null);
        } else if(which==fraBoard){
            view=inflater.inflate(R.layout.item_notice,null);
        }else if(which==fraFlea){
            view = LayoutInflater.from(context).inflate(R.layout.item_flea, null);
        }else if(which==fraRecyler){
            view = LayoutInflater.from(context).inflate(R.layout.flea_item_recyle, null);
        }else if(which==fraTotal){
            view=LayoutInflater.from(context).inflate(R.layout.item_out_total,null,true);
        }
        RVHolder holder = new RVHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVHolder holder, int position) {
        HashMap<String, String> map = list.get(position);
        if(which==fraPlay){
            bindPlayView(holder,map);
        }else if(which==fraNews){
            bindActivityNews(holder,map);
        }else if(which==fraBoard){
            bindNoticeView(holder,map);
        }else if(which==fraFlea){
            bindFleaView(holder,map);
        }else if(which==fraRecyler){
            bindRecyleView(holder,map);
        }else if(which==fraTotal){
            bindOutNews(holder,map);
        }

    }


    private void bindOutNews(RVHolder holder,HashMap<String,String> map){
        holder.detail.setText(map.get("detail"));
        holder.ptime.setText(map.get("ptime"));
        HttpUtils.imgVolleyShow(map.get("img"), holder.img, R.drawable.avatar_r, R.drawable.avatar_r);
    }

    private void bindActivityNews(RVHolder holder,HashMap<String,String> map){
       // holder.img
        holder.title.setText(map.get("detail"));
        holder.host.setText(map.get("host"));
    }

    private void bindPlayView(RVHolder holder,HashMap<String,String> map) {
        holder.detail.setText(map.get("briefIntro"));
        holder.gendar.setImageResource(map.get("gendar").equals("female") ? R.drawable.female : R.drawable.male);
        HttpUtils.imgVolleyShow(map.get("img"),holder.avatar,R.drawable.avatar_r,R.drawable.avatar_r);
       // holder.avatar
        holder.ptime.setText(map.get("ptime"));
        holder.time.setText(map.get("time"));
        holder.name.setText(map.get("name"));
    }

    private void bindNoticeView(RVHolder holder, HashMap<String,String> map){
        ViewGroup.LayoutParams lp=holder.img.getLayoutParams();
        lp.width=width;
        lp.height= (int) (height+Math.random()*100);
        holder.img.setLayoutParams(lp);
        holder.title.setText(map.get("title"));
        HttpUtils.imgVolleyShow(map.get("img"), holder.img, R.drawable.avatar_r, R.drawable.avatar_r);
    }

    private void bindFleaView(RVHolder holder,HashMap<String,String> map){
       // holder.img

        ViewGroup.LayoutParams lp=holder.img.getLayoutParams();
        lp.width=width;
        lp.height= (int) (height+Math.random()*100);
        holder.img.setLayoutParams(lp);
        holder.title.setText(map.get("title"));
    }


    private void bindRecyleView(RVHolder holder,HashMap<String,String>map){
        holder.name.setText(String.format(context.getString(R.string.fleaName), map.get("name")));
        holder.place.setText(String.format(context.getString(R.string.fleaPlace), map.get("place")));
        holder.worktime.setText(String.format(context.getString(R.string.fleaTime), map.get("worktime")));
        holder.detail.setText(String.format(context.getString(R.string.fleaDetail), map.get("detail")));
        holder.connect.setText(String.format(context.getString(R.string.fleaConnect), map.get("connect")));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RVHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ImageView avatar,gendar;
        public TextView name,detail,ptime,time,category;

        public ImageView img;
        public TextView title,host;

        public TextView place,worktime,connect;

        private void findOutView(View view){
            detail= (TextView) view.findViewById(R.id.detail);
            ptime= (TextView) view.findViewById(R.id.ptime);
            img= (ImageView) view.findViewById(R.id.img);
        }

        private void findPlayView(View view) {
            avatar= (ImageView) view.findViewById(R.id.avatar);
            gendar= (ImageView) view.findViewById(R.id.gendar);
            name= (TextView) view.findViewById(R.id.name);
            detail= (TextView) view.findViewById(R.id.detail);
            ptime= (TextView) view.findViewById(R.id.ptime);
            time= (TextView) view.findViewById(R.id.time);
            category= (TextView) view.findViewById(R.id.category);

        }

        private void findActivityNewsView(View view){
            img= (ImageView) view.findViewById(R.id.img);
            title= (TextView) view.findViewById(R.id.title);
            host= (TextView) view.findViewById(R.id.host);
        }

        private void findNoticeView(View view){
            img= (ImageView) view.findViewById(R.id.img);
            title= (TextView) view.findViewById(R.id.title);
            ptime= (TextView) view.findViewById(R.id.ptime);
        }

        private void findFleaView(View view){
            img= (ImageView) view.findViewById(R.id.img);
            title= (TextView) view.findViewById(R.id.title);
        }

        private void findRecylerView(View view){
            img= (ImageView) view.findViewById(R.id.img);
            name= (TextView) view.findViewById(R.id.name);
            place= (TextView) view.findViewById(R.id.place);
            worktime= (TextView) view.findViewById(R.id.worktime);
            detail= (TextView) view.findViewById(R.id.detail);
            connect= (TextView) view.findViewById(R.id.connect);
        }

        RVHolder(View itemView) {
            super(itemView);
            if (which == fraPlay) {
                findPlayView(itemView);
            }else if(which==fraNews){
                findActivityNewsView(itemView);
            } else if(which==fraBoard){
                findNoticeView(itemView);
            }else if(which==fraFlea){
                findFleaView(itemView);
            }else if(which==fraRecyler){
                findRecylerView(itemView);
            }else if(which==fraTotal){
                findOutView(itemView);
            }
        }
    }


}
