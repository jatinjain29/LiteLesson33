package com.example.teacherdashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class OClass_Adapter extends RecyclerView.Adapter<OClass_Adapter.viewHolder> {
Context context;

ArrayList<ArrayList<ScheduleClass>>  data;

    public OClass_Adapter(Context context, ArrayList<ArrayList<ScheduleClass>> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.online_classes,parent,false);
        return new viewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
       holder.Day.setText(rtnDay(position));

        if(data.get(position).size()>0) {
            holder.c1.setText(data.get(position).get(0).getCClass()+" : " + "    " + data.get(position).get(0).getStarttime() + " - " + data.get(position).get(0).getEndtime());
        }
        if(data.get(position).size()>1) {
            holder.c2.setText(data.get(position).get(1).getCClass() +" : " + "    " + data.get(position).get(1).getStarttime() + " - " + data.get(position).get(1).getEndtime());
        }
            if(data.get(position).size()>2) {
            holder.c3.setText(data.get(position).get(2).getCClass() +" : " + "    " + data.get(position).get(2).getStarttime() + " - " + data.get(position).get(2).getEndtime());
        }
        if(data.get(position).size()>3) {
            holder.c4.setText(data.get(position).get(3).getCClass() +" : " + "    " + data.get(position).get(3).getStarttime() + " - " + data.get(position).get(3).getEndtime());
        }
            if(data.get(position).size()>4) {
            holder.c5.setText(data.get(position).get(4).getCClass() +" : " + "    " + data.get(position).get(4).getStarttime() + " - " + data.get(position).get(4).getEndtime());
        }

        }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public String rtnDay(int i){
        if(i==0){
            return "Monday";
        }
        else if(i==1){
            return "Tuesday";
        }
        else  if(i==2){
            return "Wednesday";
        }
        else  if(i==3){
            return "Thursday";
        }
        else if(i==4){
            return "Friday";
        }
        else if(i==5){
            return "Saturday";
        }
        else if(i==6){
            return "Sunday";
        }
        return "monday";
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView Day,c1,c2,c3,c4,c5;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Day=itemView.findViewById(R.id.Oclass);
            c1=itemView.findViewById(R.id.c1);
            c2=itemView.findViewById(R.id.c2);
            c3=itemView.findViewById(R.id.c3);
            c4=itemView.findViewById(R.id.c4);
            c5=itemView.findViewById(R.id.c5);


        }
    }
}
