package com.example.ipingpong.views.Adaptors;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipingpong.R;
import com.example.ipingpong.shared.entities.ClassificationResult;

import java.util.ArrayList;

public class montieringAdaptor extends RecyclerView.Adapter<montieringAdaptor.ViewHolder> {

    private ArrayList<ClassificationResult> strokeClassifiedList;
    Context mContext;

    public montieringAdaptor(ArrayList<ClassificationResult> strokeClassifiedList, Context context) {
        this.strokeClassifiedList = strokeClassifiedList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.monitoring_strokes_recyclerview_items, parent, false);
        return new montieringAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final ClassificationResult stroke = strokeClassifiedList.get(position);

        if (!stroke.isMistake()) {
            holder.layout.setBackgroundColor(Color.parseColor("#95F985"));
            holder.imageView.setImageResource(R.drawable.correct);
        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#ffcccb"));
            holder.imageView.setImageResource(R.drawable.wrong);
            holder.strokeErrorTypeTV.setText("Error: " + stroke.getErrorType());
        }

        holder.classificationResultTV.setText(stroke.getStrokeType());



    }

    @Override
    public int getItemCount() {
        if(strokeClassifiedList == null){
            return 0;
        }
        return strokeClassifiedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView classificationResultTV, strokeErrorTypeTV;
        public ImageView imageView;
        public ConstraintLayout layout;

        public ViewHolder(View itemView) {

            super(itemView);
            classificationResultTV = itemView.findViewById(R.id.classificationResultTV);
            strokeErrorTypeTV = itemView.findViewById(R.id.strokeErrorTypeTV);
            imageView = itemView.findViewById(R.id.imageView);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
