package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Server.IPServer;
import com.tioprasetioa.www.mtsn3rohul.Ui.dataalumni.DataAlumni;
import com.tioprasetioa.www.mtsn3rohul.Ui.Profil_Guru;
import com.tioprasetioa.www.mtsn3rohul.Ui.Profil_Sekolah;
import com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen.Rekap_Absen;
import com.tioprasetioa.www.mtsn3rohul.Ui.ppdb.PPDB;
import com.tioprasetioa.www.mtsn3rohul.Ui.ppdb.TesAkademik_PPDB;
import com.tioprasetioa.www.mtsn3rohul.Ui.ujian.BerandaUjian;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.ArrayList;

public class Adapter_IconHome extends RecyclerView.Adapter<Adapter_IconHome.ViewHolder> {

    private final Context context;
    private final ArrayList<String>textlist;
    private final ArrayList<Integer>imagelist;
    private final ArrayList<Integer>backlist;


    //ekskul

    public String urlyoutube;

    public Adapter_IconHome(Context context, ArrayList<String> textlist, ArrayList<Integer> imagelist, ArrayList<Integer> backlist) {
        this.context = context;
        this.textlist = textlist;
        this.imagelist = imagelist;
        this.backlist = backlist;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        private final Context context;
        private ImageView relativeLayout;
        private ImageView imageView;
        private final ImageView imageView2;
        public ViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            textView = itemView.findViewById(R.id.tx_home);
            imageView = itemView.findViewById(R.id.image_home);
            imageView2 = itemView.findViewById(R.id.image_homebackground);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    switch (getAdapterPosition()){
                        case 0:
                            intent = new Intent(context, Profil_Sekolah.class);
                            intent.putExtra(Profil_Sekolah.KEY_Url,urlyoutube);
                            context.startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(context, Profil_Guru.class);
                            context.startActivity(intent);
                            break;
                        case 2:
                           intent = new Intent(context, DataAlumni.class);
                           context.startActivity(intent);
                            break;
                        case 5:
                            intent = new Intent(context, TesAkademik_PPDB.class);
                            context.startActivity(intent);
//                            intent = new Intent(context, Ekskul.class);
//                            context.startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(context, Rekap_Absen.class);
                            context.startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(context, BerandaUjian.class);
                            context.startActivity(intent);
                            break;
//                            intent = new Intent(context, Home_PerangkatPembelajaran.class);
//                            context.startActivity(intent);
                        case 6:
                            Uri gmmIntenUri=Uri.parse("https://goo.gl/maps/29LuPHxRXd8hR1Hd7");
                            Intent i = new Intent(Intent.ACTION_VIEW,gmmIntenUri);
                            i.setPackage("com.google.android.apps.maps");
                            context.startActivity(i);
                            break;
                        case 7:
                            Intent intent1 = new Intent(context, PPDB.class);
                            context.startActivity(intent1);
                            break;
                    }
                }

            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_icon_home,null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(Adapter_IconHome.ViewHolder holder, int position) {
        final String txt = textlist.get(position);
        holder.textView.setText(txt);
        holder.imageView.setImageResource(imagelist.get(position));
        holder.imageView2.setImageResource(backlist.get(position));

        String urlyoutubee = IPServer.link()+"Url_Youtube.php";
        Utils utils = new Utils();
        utils.VolleyString(context, urlyoutubee, "Url", new Utils.VolleyDataString() {
            @Override
            public void onError(String errorMesage) {
                Log.e("Error", errorMesage);
            }

            @Override
            public void onSuccess(String result) {
                urlyoutube = result;
            }

        });
    }
    @Override
    public int getItemCount() {
        return textlist.size();
    }


}