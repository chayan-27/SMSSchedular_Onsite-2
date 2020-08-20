package com.example.smsschedular;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleHolder> {


    List<LiveTest> liveTests;

    public RecycleAdapter(List<LiveTest> liveTests) {
        this.liveTests = liveTests;
    }

    @NonNull
    @Override
    public RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.messagecard, parent, false);

        return new RecycleHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecycleHolder holder, int position) {
        holder.textView.setText(liveTests.get(position).getTitle());
        holder.textView1.setText(liveTests.get(position).getBody());
        holder.textView3.setText(liveTests.get(position).getTime());
        if (System.currentTimeMillis() < liveTests.get(position).getTimeinmillis()) {
            holder.cardView.setBackgroundColor(Color.parseColor("#FFA500"));
        } else {
            holder.cardView.setBackgroundColor(Color.GREEN);

        }


    }


    @Override
    public int getItemCount() {
        return liveTests.size();
    }

    public class RecycleHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView1;
        TextView textView3;
        CardView cardView;

        public RecycleHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.subject);
            textView1 = (TextView) itemView.findViewById(R.id.title);
            textView3 = (TextView) itemView.findViewById(R.id.sdate);
            cardView = (CardView) itemView.findViewById(R.id.cards);
        }
    }
}
