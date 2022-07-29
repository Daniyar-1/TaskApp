package com.example.taskapp.ui.note.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.ui.note.models.Note;
import com.example.taskapp.ui.note.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    private List<Note> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public NoteAdapter(List<Note> list) {
        this.list = list;
    }

    public void updateNote(Note notes,int pos){
        this.list.set(pos, notes);
        notifyDataSetChanged();
    }

    public void addNewNote(Note notes) {
        this.list.add(0,notes);
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.desc.setText(list.get(position).getDescription());
        holder.img.setImageURI(Uri.parse(list.get(position).getImgURL()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title, desc;
        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(getAdapterPosition()));
            itemView.setOnLongClickListener(v -> {
                onItemClickListener.onItemLongClick(getAdapterPosition());
                return true;
            });
            title = itemView.findViewById(R.id.tv_title_item);
            desc = itemView.findViewById(R.id.tv_desc_item);
            img = itemView.findViewById(R.id.img_item);
        }
    }
}
