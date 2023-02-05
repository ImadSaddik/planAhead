package com.example.mytimetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class EditEntriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private EntryAdapter adapter;
    private EntryViewModel entryViewModel;
    private FloatingActionButton addFAB, deleteFAB;

    public static final int ADD_ENTRY_REQUEST = 1;
    public static final int EDIT_ENTRY_REQUEST = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_entries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        initRecyclerView();

        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);
        entryViewModel.getAllEntries().observe(getViewLifecycleOwner(), entries -> adapter.submitList(entries));

        addFAB.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddEditEntryActivity.class);
            startActivityForResult(intent, ADD_ENTRY_REQUEST);
        });

        deleteFAB.setOnClickListener(v -> {
            LiveData<List<Entry>> deletedEntries = entryViewModel.getAllEntries();
            List<Entry> list = deletedEntries.getValue();
            entryViewModel.deleteAllEntries();

            Snackbar.make(deleteFAB, R.string.snack_bar_fab, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo_swipe, v1 -> {
                        if (list != null) {
                            for (Entry e : list)
                                entryViewModel.insert(e);
                        }
                    }).show();

        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Entry swipedEntry = adapter.getEntryAt(viewHolder.getAdapterPosition());
                entryViewModel.delete(swipedEntry);
                Snackbar.make(view, R.string.snack_bar_swipe, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo_swipe, v -> entryViewModel.insert(swipedEntry)).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(entry -> {
            Intent intent = new Intent(getActivity(), AddEditEntryActivity.class);
            intent.putExtra(AddEditEntryActivity.EXTRA_DAY, entry.getDay());
            intent.putExtra(AddEditEntryActivity.EXTRA_ELEMENT, entry.getNameOfElement());
            intent.putExtra(AddEditEntryActivity.EXTRA_TYPE, entry.getType());
            intent.putExtra(AddEditEntryActivity.EXTRA_DURATION, entry.getDuration());
            intent.putExtra(AddEditEntryActivity.EXTRA_START_TIME, entry.getStartingFrom());
            intent.putExtra(AddEditEntryActivity.EXTRA_LAST_WEEK, entry.getEndWeek());
            intent.putExtra(AddEditEntryActivity.EXTRA_START_WEEK, entry.getStartWeek());
            intent.putExtra(AddEditEntryActivity.EXTRA_ID, entry.getId());

            startActivityForResult(intent, EDIT_ENTRY_REQUEST);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ENTRY_REQUEST && resultCode == -1) {
            Entry entry = retrieveEntryAttr(data);
            entryViewModel.insert(entry);
            Toast.makeText(getActivity(), "Entry saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_ENTRY_REQUEST && resultCode == -1) {
            int id = data.getIntExtra(AddEditEntryActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(getActivity(), "Entry can't be updated!", Toast.LENGTH_SHORT).show();
                return;
            }
            Entry entry = retrieveEntryAttr(data);
            entry.setId(id);
            entryViewModel.update(entry);
            Toast.makeText(getActivity(), "Entry updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Entry not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private Entry retrieveEntryAttr(Intent data) {
        String element = data.getStringExtra(AddEditEntryActivity.EXTRA_ELEMENT);
        String day = data.getStringExtra(AddEditEntryActivity.EXTRA_DAY);
        String type = data.getStringExtra(AddEditEntryActivity.EXTRA_TYPE);
        String startTime = data.getStringExtra(AddEditEntryActivity.EXTRA_START_TIME);
        int startWeek = data.getIntExtra(AddEditEntryActivity.EXTRA_START_WEEK, 1);
        int endWeek = data.getIntExtra(AddEditEntryActivity.EXTRA_LAST_WEEK, 30);
        int duration = data.getIntExtra(AddEditEntryActivity.EXTRA_DURATION, 2);

        return new Entry(endWeek, startWeek, duration, startTime, element, type, day,
                Utils.dayToInt(day));
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new EntryAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.entryRecyclerViewEditEntries);
        addFAB = view.findViewById(R.id.floating_action_button_edit_entries);
        deleteFAB = view.findViewById(R.id.floating_action_button_remove_all_entries);
    }
}
