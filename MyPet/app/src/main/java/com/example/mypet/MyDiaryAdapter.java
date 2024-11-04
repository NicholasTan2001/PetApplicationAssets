package com.example.mypet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyDiaryAdapter extends RecyclerView.Adapter<MyDiaryAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> entries;
    private ArrayList<String> entryIds;
    private OnItemClickListener onItemClickListener;
    private OnEntryEditListener onEntryEditListener;

    public MyDiaryAdapter(Context context, ArrayList<String> entries, ArrayList<String> entryIds) {
        this.context = context;
        this.entries = entries;
        this.entryIds = entryIds;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnEntryEditListener {
        void onEntryEdit(String entryId, String newText);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnEntryEditListener(OnEntryEditListener listener) {
        this.onEntryEditListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.diaryEntryText.setText(entries.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        holder.diaryEntryText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String newText = holder.diaryEntryText.getText().toString();
                    String entryId = entryIds.get(position);
                    if (onEntryEditListener != null) {
                        onEntryEditListener.onEntryEdit(entryId, newText);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText diaryEntryText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            diaryEntryText = itemView.findViewById(R.id.diary_entry_text);
        }
    }

    public String getEntryId(int position) {
        return entryIds.get(position);
    }
}
