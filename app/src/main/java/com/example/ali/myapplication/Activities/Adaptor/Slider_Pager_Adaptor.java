package com.example.ali.myapplication.Activities.Adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.ali.myapplication.R;

import java.util.ArrayList;


/**
 * Created by Ali on 4/24/2017.
 */

public class Slider_Pager_Adaptor extends PagerAdapter {
    Context context;
    ArrayList<Integer> images, titleList;
    LayoutInflater layoutInflater;
    ProgressBar progressBar;
    View view1, view2;
    boolean flag = true;

    public Slider_Pager_Adaptor(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images = images;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.slider_image_item, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(images.get(position));
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}