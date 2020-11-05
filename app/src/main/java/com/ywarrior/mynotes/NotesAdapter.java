package com.ywarrior.mynotes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    Context context;
    OnItemClickListener onItemClickListener;
    List<Notes_model>notes_models;
    public NotesAdapter(Context context, List<Notes_model> notes_models, OnItemClickListener onItemClickListener) {
        this.context =context;
        this.notes_models=notes_models;
        this.onItemClickListener=onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int postion, Notes_model item, View view);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main,
                parent,false)
        );
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes_model model=notes_models.get(position);
        holder.bind(position,model,onItemClickListener);

        holder.not.setText(model.getText());
        Date date=model.getDate();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        holder.date.setText(today);
        if (model.getUrl()!=null ){
            holder.im.setImageBitmap(BitmapFactory.decodeFile(model.getUrl()));
            holder.im.setVisibility(View.VISIBLE);
        }else{
            holder.im.setVisibility(View.GONE);


        }

    }

    @Override
    public int getItemCount() {
        return notes_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView not,date;
        ImageView im;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            not=itemView.findViewById(R.id.note);
            date=itemView.findViewById(R.id.date);
            im=itemView.findViewById(R.id.imageView4);
        }

        public void bind(final int position, final Notes_model model, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position,model,v);
                }
            });
        }
    }
}
