package com.example.mytimetable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EntryAdapter extends ListAdapter<Entry, EntryAdapter.EntryHolder> {
    private OnItemClickListener listener;

    public EntryAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Entry> DIFF_CALLBACK = new DiffUtil.ItemCallback<Entry>() {
        @Override
        public boolean areItemsTheSame(@NonNull Entry oldItem, @NonNull Entry newItem) {
            return oldItem.getId() == oldItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Entry oldItem, @NonNull Entry newItem) {
            return oldItem.getDuration() == newItem.getDuration() &&
                    oldItem.getStartWeek() == newItem.getStartWeek() &&
                    oldItem.getEndWeek() == newItem.getEndWeek() &&
                    oldItem.getNameOfElement().equals(newItem.getNameOfElement()) &&
                    oldItem.getDay().equals(newItem.getDay()) &&
                    oldItem.getType().equals(newItem.getType()) &&
                    oldItem.getStartingFrom().equals(newItem.getStartingFrom());
        }
    };

    @NonNull
    @Override
    public EntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_item, parent, false);

        return new EntryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryHolder holder, int position) {
        Entry currentEntry = getItem(position);
        holder.dayOfTheWeek.setText(currentEntry.getDay());
        holder.elementName.setText(currentEntry.getNameOfElement());
        holder.Type.setText(currentEntry.getType());
        holder.duration.setText(addHours(currentEntry.getStartingFrom(),
                currentEntry.getDuration(),
                currentEntry.getDay())
        );

        String weekDuration = "[" + currentEntry.getStartWeek() + " - " + currentEntry.getEndWeek() + "]";
        holder.weekStartEnd.setText(weekDuration);
    }

    public Entry getEntryAt(int position) {
        return getItem(position);
    }

    private String addHours(String startTime, int duration, String day) {
        StringBuilder sb = new StringBuilder();
        sb.append(startTime).append("  -  ");

        int hour = Integer.parseInt(startTime.substring(0, 2)) + duration;
        String str = hour > 9 ? String.valueOf(hour) : "0" + hour;

        if (!day.equals(Utils.FRIDAY)) {
            sb.append(str).append(":30");
        } else {
            sb.append(str).append(":00");
        }
        return sb.toString();
    }

    class EntryHolder extends RecyclerView.ViewHolder {
        private TextView dayOfTheWeek, elementName, weekStartEnd, Type, duration;


        public EntryHolder(@NonNull View itemView) {
            super(itemView);
            dayOfTheWeek = itemView.findViewById(R.id.dayText);
            elementName = itemView.findViewById(R.id.elementDeModule);
            weekStartEnd = itemView.findViewById(R.id.startEndWeek);
            Type = itemView.findViewById(R.id.typeOfElement);
            duration = itemView.findViewById(R.id.durationOfElement);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Entry entry);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
