package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_DetailListLoan2;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_UpdateLoan;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Ui.Detail_LoanList;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_ListLoanDetail2 extends RecyclerView.Adapter<Adapter_ListLoanDetail2.ViewHolder> {

    private final List<ModelData_DetailListLoan2>data ;
    private Context context;

    public Adapter_ListLoanDetail2(List<ModelData_DetailListLoan2> data, Context context) {
        this.data = data;
        this.context = context;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView item_code;
        private final TextView loandate;
        private final TextView returndate;
        private final TextView mapel;
        private final TextView isreturn;
        private final TextView memberid;
        private final TextView duedate;
        private final TextView status;
        private final Button button;
        private final RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            memberid = itemView.findViewById(R.id.tx_member_id);
            item_code = itemView.findViewById(R.id.tx_item_code);
            loandate = itemView.findViewById(R.id.tx_loan_date);
            returndate = itemView.findViewById(R.id.tx_return_date);
            isreturn = itemView.findViewById(R.id.tx_is_return);
            mapel = itemView.findViewById(R.id.tx_mapellistloan2);
            status = itemView.findViewById(R.id.tx_status);
            duedate = itemView.findViewById(R.id.tx_duedate);
            button = itemView.findViewById(R.id.btn_kembalikan);
            relativeLayout = itemView.findViewById(R.id.rl_detail2);
            context = itemView.getContext();
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_list_loan2, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelData_DetailListLoan2 model = data.get(position);
        holder.item_code.setText(model.getItem_code());
        holder.loandate.setText(model.getLoan_date());
        String str = model.getMapel();
        if (str.isEmpty()){
            holder.mapel.setText("data Kosong");
        }else {
            holder.mapel.setText(model.getMapel());
        }
        holder.returndate.setText(model.getReturn_date());
        holder.isreturn.setText(model.getIs_return());
        holder.memberid.setText(model.getMember_id());
        holder.duedate.setText(model.getDue_date());

        //cek data terlambat
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime tglsekarang = LocalDateTime.now();
        String tglpeminjaman = dateTimeFormatter.format(tglsekarang);
        String tglbtsTempo = holder.duedate.getText().toString();
        if (tglpeminjaman.compareTo(tglbtsTempo)>0){
            holder.status.setText("Status[Terlambat]");
            holder.status.setTextColor(Color.RED);
            holder.status.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }else {
            holder.status.setText("Status[Belum Terlambat]");
        }

        //cek HIDE USER
        String hide = holder.isreturn.getText().toString();
        if (hide.equals("1")){
            holder.relativeLayout.setVisibility(View.GONE);
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = model.getLoan_id();
                String returnbook = "1";
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime sekarantg = LocalDateTime.now();
                String waktu3 = dateTimeFormatter.format(sekarantg);
                Api api = Server.koneksiretrofit().create(Api.class);
                Call<Response_UpdateLoan> call = api.update(id,returnbook,waktu3);
                call.enqueue(new Callback<Response_UpdateLoan>() {
                    @Override
                    public void onResponse(Call<Response_UpdateLoan> call, Response<Response_UpdateLoan> response) {
                        String pesan = response.body().getPesan();
                        Utils.Toast(context,pesan);
                    }

                    @Override
                    public void onFailure(Call<Response_UpdateLoan> call, Throwable t) {

                    }
                });
                if (context instanceof  Detail_LoanList){
                    ((Detail_LoanList)context).refresh();
                }

            }
        });
//    }}
    }
    @Override
    public int getItemCount() {
        return data.size();
    }


}