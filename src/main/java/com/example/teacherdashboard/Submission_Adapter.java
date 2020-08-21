package com.example.teacherdashboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

class Submission_Adapter extends RecyclerView.Adapter<Submission_Adapter.Subm_holder> {
    Context context;
    ArrayList<Submission> data;
    Onsubmissionclicked onsubmissionclicked;


    public Submission_Adapter(Context context, ArrayList<Submission> data) {
        this.context = context;
        this.data = data;
    }

    public void show_fileClickListener(Onsubmissionclicked onsubmissionclicked){
        this.onsubmissionclicked=onsubmissionclicked;
    }

    @NonNull
    @Override
    public Subm_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.sbmsn_view,parent,false);

        return new Subm_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Subm_holder holder, int position) {


            if (data.get(position).getOptional_message() != null) {
                holder.opnl_msg.setText("Message: " + data.get(position).getOptional_message());
            }
            holder.sbjct.setText("Subject: " + data.get(position).getSubject());
            holder.sb_date.setText("Submitted On: " + data.get(position).getSubmitted_on());
            holder.std_name.setText(data.get(position).getStudent());




    }

    @Override
    public int getItemCount() {
        return data.size();

    }

    public class Subm_holder extends RecyclerView.ViewHolder {
        TextView opnl_msg,std_name,sb_date,sbjct;
        Button sfile;
        public Subm_holder(@NonNull View itemView) {
            super(itemView);
            opnl_msg=itemView.findViewById(R.id.opn_msg);
           std_name =itemView.findViewById(R.id.std_nm);
           sb_date=itemView.findViewById(R.id.sbmt_on);
           sbjct =itemView.findViewById(R.id.sbjt);
           sfile=itemView.findViewById(R.id.btn_sfile);

           sfile.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onsubmissionclicked.btnshowfile(getAdapterPosition());




               }
           });
        }

    }

    interface Onsubmissionclicked{
        void btnshowfile(int position);
    }
}
