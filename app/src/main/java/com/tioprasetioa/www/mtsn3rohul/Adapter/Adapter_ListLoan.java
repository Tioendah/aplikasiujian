package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_ListLoan;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Ui.Detail_LoanList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Adapter_ListLoan extends RecyclerView.Adapter<Adapter_ListLoan.ViewHolder> {

    private final List<ModelData_ListLoan>list ;
    private Context context;

    public Adapter_ListLoan(List<ModelData_ListLoan> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView memberid;
        private final TextView isreturn;
        private final TextView itemcode;
        private final TextView loandate;
        private final TextView returndate;
        private final TextView mapel;
        private TextView duedate;
        private final RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.linearLayout_listloan);
            memberid = itemView.findViewById(R.id.tx_memberid);
            isreturn = itemView.findViewById(R.id.tx_isreturn);
            mapel = itemView.findViewById(R.id.tx_mapel);
            itemcode = itemView.findViewById(R.id.tx_itemcode);
            loandate = itemView.findViewById(R.id.tx_loandate);
            returndate = itemView.findViewById(R.id.tx_returndate);
            context = itemView.getContext();
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_list_loan, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelData_ListLoan modelDataListLoan = list.get(position);
        holder.memberid.setText(modelDataListLoan.getMember_id());
        holder.isreturn.setText(String.valueOf(modelDataListLoan.getIs_return()));
        holder.loandate.setText(modelDataListLoan.getLoan_date());
        holder.itemcode.setText(modelDataListLoan.getItem_code());
        holder.returndate.setText(modelDataListLoan.getDue_date());
        holder.mapel.setText(modelDataListLoan.getMapel());



        //cek status terlambat
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime tglsekarang = LocalDateTime.now();
        String tglpeminjaman = dateTimeFormatter.format(tglsekarang);
        String tglbtsTempo = holder.returndate.getText().toString();
        if (tglpeminjaman.compareTo(tglbtsTempo)>0){
            holder.returndate.setTextColor(Color.RED);
            holder.memberid.setTextColor(Color.RED);
            holder.itemcode.setTextColor(Color.RED);
            holder.loandate.setTextColor(Color.RED);
            holder.mapel.setTextColor(Color.RED);
        }

        //HIDE USER YANG SUDAH DIKEMBALIKAN

        String hide = holder.isreturn.getText().toString();
        if (hide.equals("0")){
            holder.relativeLayout.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelData_ListLoan modelDataListLoan1 = new ModelData_ListLoan();
                modelDataListLoan1.setMember_id(modelDataListLoan.getMember_id());
                Intent intent = new Intent(context, Detail_LoanList.class);
                intent.putExtra(Detail_LoanList.KEY_LISTLOAN,modelDataListLoan1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}