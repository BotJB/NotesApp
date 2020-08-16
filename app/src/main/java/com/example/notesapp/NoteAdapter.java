package com.example.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note,NoteAdapter.noteViewholder> {
    public onItemClickListner listner;

    public NoteAdapter() {
        super(DIFFCALL);
    }
public static DiffUtil.ItemCallback<Note> DIFFCALL=new DiffUtil.ItemCallback<Note>() {
    @Override
    public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
        return oldItem.getId()==newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
        return oldItem.getSubject().equals(newItem.getSubject())&&
                oldItem.getMessage().equals(newItem.getSubject())&&
                oldItem.getPriority()==newItem.getPriority();
    }
};
    @NonNull
    @Override
    public noteViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new noteViewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull noteViewholder holder, int position) {
Note current=getItem(position);
holder.title.setText(current.getSubject());
holder.description.setText(current.getMessage());
holder.priority.setText(String.valueOf(current.getPriority()));

    }



    public Note getNoteAt(int position){
        return getItem(position);
    }

    class noteViewholder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView priority;
        private TextView description;

        public noteViewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tv_Title);
            priority=itemView.findViewById(R.id.tv_Priority);
            description=itemView.findViewById(R.id.tv_Description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listner!=null&&position!=RecyclerView.NO_POSITION){
                    listner.onItemclick(getItem(position));
                }}
            });{

            };
        }
    }
    public interface onItemClickListner{
        public void onItemclick(Note note);
    }
    public void setONItemClickListner(onItemClickListner listner){
        this.listner=listner;
    }
}
