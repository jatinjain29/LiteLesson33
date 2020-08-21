package com.example.teacherdashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class AssingmentAdapter extends RecyclerView.Adapter<AssingmentAdapter.viewholder> {
    Context context;
    OnButtonClicked minterface;
    ArrayList<String> asngname;
    ArrayList<String> asngclas;
    ArrayList<String> asngldate;
    ArrayList<String> asngsubj;
    ArrayList<String> asngAdate;

    public void OnitemClicked(OnButtonClicked minterface){
        this.minterface=minterface;
    }


    public AssingmentAdapter(@NonNull Context context, ArrayList<String> asngname, ArrayList<String> asngclas, ArrayList<String> asngldate, ArrayList<String> asngsubj, ArrayList<String> asngAdate) {

        this.context = context;
        this.asngname = asngname;
        this.asngclas = asngclas;
        this.asngldate = asngldate;
        this.asngsubj = asngsubj;
        this.asngAdate = asngAdate;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.assingment, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.Subj.setText("Subject : " + asngsubj.get(position));
        holder.Clas.setText("Class : "+asngclas.get(position));
        holder.Assingeddate.setText("Assinged On : " + asngAdate.get(position));
        holder.lastdate.setText("Submit Before : " + asngldate.get(position));
        holder.Name.setText(position + 1 + ": " + asngname.get(position));
    }

    @Override
    public int getItemCount() {
        return asngname.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView Name, Clas, Assingeddate, lastdate, Subj;
        Button view, submission;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.btnView);
            submission = itemView.findViewById(R.id.btnSubmsn);
            Name = itemView.findViewById(R.id.AsngName);
            Clas = itemView.findViewById(R.id.AsngClas);
            Assingeddate = itemView.findViewById(R.id.AsngDate);
            lastdate = itemView.findViewById(R.id.AsngLastDate);
            Subj = itemView.findViewById(R.id.AsngSub);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    minterface.OnViewClicked(getAdapterPosition());
                }
            });

            submission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    minterface.OnSubmissionClicked(getAdapterPosition());
                }
            });
        }
    }


    public interface OnButtonClicked
    {
       void OnViewClicked(int position);
      void  OnSubmissionClicked(int position);
    }
}
