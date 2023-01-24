package com.example.mytimetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GetEntriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private EntryAdapter adapter;
    private EntryViewModel entryViewModel;
    private String week, day;
    private TextView title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_get_entries, container, false);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            day = bundle.getString(Utils.DAY_STRING_KEY);
            week = bundle.getString(Utils.WEEK_STRING_KEY);
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.whatYouHaveTxt);
        recyclerView = view.findViewById(R.id.recyclerViewShowFetchedData);
        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);
        initRecyclerView();

        switch (day) {
            case Utils.MONDAY:
                // update RecyclerView
                entryViewModel.getMondayEntries().observe(getViewLifecycleOwner(), this::showData);
                break;
            case Utils.TUESDAY:
                // update RecyclerView
                entryViewModel.getTuesdayEntries().observe(getViewLifecycleOwner(), this::showData);
                break;
            case Utils.WEDNESDAY:
                // update RecyclerView
                entryViewModel.getWednesdayEntries().observe(getViewLifecycleOwner(), this::showData);
                break;
            case Utils.THURSDAY:
                // update RecyclerView
                entryViewModel.getThursdayEntries().observe(getViewLifecycleOwner(), this::showData);
                break;
            case Utils.FRIDAY:
                // update RecyclerView
                entryViewModel.getFridayEntries().observe(getViewLifecycleOwner(), this::showData);
                break;
            case Utils.SATURDAY:
                // update RecyclerView
                entryViewModel.getSaturdayEntries().observe(getViewLifecycleOwner(), this::showData);
                break;
            default:
                entryViewModel.getAllEntries().observe(getViewLifecycleOwner(), entries -> adapter.submitList(entries));
        }
    }

    private void showData(List<Entry> entries) {
        List<Entry> filteredList = byWeekList(entries);
        adapter.submitList(filteredList);

        // changing the title
        if (filteredList.size() == 0) {
            String str = "You have nothing today";
            title.setText(str);
        } else {
            title.setText(R.string.this_is_what_you_have);
        }
    }

    private List<Entry> byWeekList(List<Entry> entries) {
        List<Entry> list = new ArrayList<>();
        for (Entry entry : entries) {
            if (Integer.parseInt(week) >= entry.getStartWeek() &&
                    Integer.parseInt(week) <= entry.getEndWeek()) {
                list.add(entry);
            }
        }
        return list;
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new EntryAdapter();
        recyclerView.setAdapter(adapter);
    }
}
