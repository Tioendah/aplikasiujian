package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Image_Slider;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Ui.WebViewBerita;

import java.util.List;


public class Adapter_ImageSlider extends SliderViewAdapter<Adapter_ImageSlider.SliderViewHolder> {

    private Context context;
    private final List<Model_data_Image_Slider>model_data_image_sliderslist;

    public Adapter_ImageSlider(Context context, List<Model_data_Image_Slider> model_data_image_sliderslist) {
        this.context = context;
        this.model_data_image_sliderslist = model_data_image_sliderslist;
    }

    class SliderViewHolder extends SliderViewAdapter.ViewHolder {
        private final ImageView ivautoimageslider;
        private final TextView judul;
        private final TextView tanggal;
        private final TextView sumberv;
        private final TextView Hari;
        private final TextView id;
        private final TextView bacaselekapnya;
        private final View view;
        public SliderViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            ivautoimageslider = itemView.findViewById(R.id.iv_auto_image_slider);
            judul = itemView.findViewById(R.id.tv_auto_image_slider);
            sumberv = itemView.findViewById(R.id.id_sumber);
            Hari = itemView.findViewById(R.id.tv3_autoimageslider);
            bacaselekapnya = itemView.findViewById(R.id.tv_bacaselekapnya);
            tanggal = itemView.findViewById(R.id.tv2_autoimageslider);
            view = itemView.findViewById(R.id.v_tranparanblack);
//            this.view = itemView;
            context = itemView.getContext();
        }
    }
    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View View = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_image_slider,null);
        return new SliderViewHolder(View);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        Model_data_Image_Slider modelDataImageSlider = model_data_image_sliderslist.get(position);
        viewHolder.id.setText(String.valueOf(modelDataImageSlider.getId()));
        viewHolder.judul.setText(modelDataImageSlider.getJudul());
        viewHolder.tanggal.setText(modelDataImageSlider.getTanggal());
        viewHolder.sumberv.setText(modelDataImageSlider.getSumber());
        viewHolder.Hari.setText(modelDataImageSlider.getHari()+",");
        Glide.with(context)
                .load(modelDataImageSlider.getGambar())
                .fitCenter()
                .into(viewHolder.ivautoimageslider);
        viewHolder.ivautoimageslider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model_data_Image_Slider model_data_image_slider = new Model_data_Image_Slider();
                model_data_image_slider.setUrl(modelDataImageSlider.getUrl());
                String key = "2";

                Intent intent = new Intent(context, WebViewBerita.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(WebViewBerita.KEY_Web,model_data_image_slider);
                intent.putExtra(WebViewBerita.KEY_key,key);
                context.startActivity(intent);
            }
        });

        //logika untuk menghilangkan tulisan "baca selengakapnya
//        String hide = viewHolder.judul.getText().toString();
//        if (hide.equals("")){
//            viewHolder.bacaselekapnya.setVisibility(View.GONE);
//            viewHolder.Hari.setVisibility(View.GONE);
//            viewHolder.view.setVisibility(View.GONE);
//        }else{
//            viewHolder.bacaselekapnya.setVisibility(View.VISIBLE);
//            viewHolder.Hari.setVisibility(View.VISIBLE);
//            viewHolder.view.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public int getCount() {
        return model_data_image_sliderslist.size();
    }


}