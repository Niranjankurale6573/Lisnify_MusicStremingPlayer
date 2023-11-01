package com.musicstremingapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.LayoutInflaterCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.musicstremingapplication.Models.SliderModel;
import com.musicstremingapplication.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<SliderModel>sliderModelsList;
     public SliderAdapter(Context context, ArrayList<SliderModel> sliderModelsList) {
        this.context = context;
        this.sliderModelsList= sliderModelsList;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_item,null);

        ImageView slider_image=view.findViewById(R.id.slider_image);
        TextView slider_title=view.findViewById(R.id.slider_title);

        slider_image.setImageResource(sliderModelsList.get(position).getImage());
        slider_title.setText(sliderModelsList.get(position).getSliderName());
        container.addView(view);
        return view;
    }
    @Override
    public int getCount() {
        return sliderModelsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
