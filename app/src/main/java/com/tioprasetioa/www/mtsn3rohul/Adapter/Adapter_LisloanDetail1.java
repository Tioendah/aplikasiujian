package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_DetailListLoan;
import com.tioprasetioa.www.mtsn3rohul.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_LisloanDetail1 extends RecyclerView.Adapter<Adapter_LisloanDetail1.ViewHolder> {

    private final List<ModelData_DetailListLoan>list;
    private final Context context;
    private static  final int REQUEST_PHONE_CALL =1;
    Toast toast;

    public Adapter_LisloanDetail1(List<ModelData_DetailListLoan> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView memberid;
        private final TextView membername;
        private TextView gender;
        private final TextView memberphone;
        private final TextView pin;
        private final CircleImageView circleImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            memberid = itemView.findViewById(R.id.tx_memberidlistloan);
            membername = itemView.findViewById(R.id.tx_membernamelisloant);
            memberphone = itemView.findViewById(R.id.call_parent);
            pin = itemView.findViewById(R.id.call_user);
            circleImageView = itemView.findViewById(R.id.circleImageView);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_lisloan_detail1,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelData_DetailListLoan model = list.get(position);
        String gender = model.getGender();
        String phoneuser = model.getPin();
        String phoneparent = model.getMember_phone();
        holder.membername.setText(model.getMember_name());
        holder.memberid.setText(model.getMember_id());
        holder.circleImageView.setImageResource(R.drawable.ic_default);

        holder.memberphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneparent!=null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneparent, null));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    toast = Toast.makeText(context,"Nomor Tidak Tersedia",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            }
        });
        holder.pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneuser!=null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneuser, null));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    toast = Toast.makeText(context,"Nomor Tidak Tersedia",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}