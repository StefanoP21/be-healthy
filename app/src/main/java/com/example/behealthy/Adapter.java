package com.example.behealthy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<MyViewHolder>{
    private Context context;
    private List<DataClass> dataList;

    public Adapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recyclerImage);
        holder.recyclerTitle.setText(dataList.get(position).getDataTitle());
        holder.recyclerDesc.setText(dataList.get(position).getDataDesc());
        holder.recyclerTime.setText(dataList.get(position).getDataTime());

        holder.recyclerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vew) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", dataList.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("desc", dataList.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra("image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("key", dataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("time", dataList.get(holder.getAdapterPosition()).getDataTime());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView recyclerImage;
    TextView recyclerTitle, recyclerDesc, recyclerTime;
    CardView recyclerCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recyclerImage = itemView.findViewById(R.id.rec_image);
        recyclerTitle = itemView.findViewById(R.id.rec_title);
        recyclerDesc = itemView.findViewById(R.id.rec_desc);
        recyclerTime = itemView.findViewById(R.id.rec_time);
        recyclerCard = itemView.findViewById(R.id.rec_card);
    }
}