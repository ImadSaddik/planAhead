package com.example.mytimetable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private EntryViewModel entryViewModel;
    private TextInputEditText textInputEditTextName;
    private TextInputLayout textInputLayoutName;
    private FloatingActionButton btnAddEntry, confirmFab;
    private RelativeLayout relativeLayoutSetup;
    private ConstraintLayout constraintLayoutFetchData;
    private RecyclerView recyclerView;
    private TextView welcomeTxt;
    private EntryAdapter adapter;

    public static final int ADD_ENTRY_REQUEST = 1;
    public static final int EDIT_ENTRY_REQUEST = 2;
    public static final String PREFS_KEY = "PREFS";
    public static final String NAME_KEY = "NAME";
    public static final String BOOL_PREFS_KEY = "FIRST_START";

    // Constraint layout views
    TextInputLayout textInputLayoutDay, textInputLayoutWeek;
    AutoCompleteTextView autoCompleteDropDownMenuDays, autoCompleteDropDownMenuWeeks;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        populateDropDownMenus();
        initRecyclerView();

        SharedPreferences prefs = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean(BOOL_PREFS_KEY, true);

        if (firstStart) {
            constraintLayoutFetchData.setVisibility(View.GONE);
            relativeLayoutSetup.setVisibility(View.VISIBLE);
        } else {
            constraintLayoutFetchData.setVisibility(View.VISIBLE);
            relativeLayoutSetup.setVisibility(View.GONE);

            String str = "Welcome back " + loadDataFromSharedPrefs().split(" ")[0];
            welcomeTxt.setText(str);
        }

        btnAddEntry.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditEntryActivity.class);
            startActivityForResult(intent, ADD_ENTRY_REQUEST);
        });

        confirmFab.setOnClickListener(v -> {
            boolean nameBool = textInputEditTextName.getText().toString().equals("");

            if (nameBool) {
                textInputLayoutName.setError("Add your name!");
                textInputLayoutName.setErrorEnabled(true);
            } else {
                textInputLayoutName.setErrorEnabled(false);
                SharedPreferences prefs1 = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs1.edit();
                editor.putBoolean(BOOL_PREFS_KEY, false);
                editor.putString(NAME_KEY, textInputEditTextName.getText().toString());
                editor.apply();
                constraintLayoutFetchData.setVisibility(View.VISIBLE);
                relativeLayoutSetup.setVisibility(View.GONE);

                String str = "Welcome back " + loadDataFromSharedPrefs().split(" ")[0];
                welcomeTxt.setText(str);
            }
        });

        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);
        entryViewModel.getAllEntries().observe(this, entries -> {
            adapter.submitList(entries);
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
                Snackbar.make(viewHolder.itemView, R.string.snack_bar_swipe, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo_swipe, v -> entryViewModel.insert(swipedEntry)).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(entry -> {
            Intent intent = new Intent(MainActivity.this, AddEditEntryActivity.class);
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

    private String loadDataFromSharedPrefs() {
        SharedPreferences prefs = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return prefs.getString(NAME_KEY, "");
    }

    private void populateDropDownMenus() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.drop_down_menu, Utils.DAYS_OF_THE_WEEK);
        autoCompleteDropDownMenuDays.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.drop_down_menu, Utils.WEEKS);
        autoCompleteDropDownMenuWeeks.setAdapter(arrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ENTRY_REQUEST && resultCode == RESULT_OK) {
            Entry entry = retrieveEntryAttr(data);
            entryViewModel.insert(entry);
            Toast.makeText(this, "Entry saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_ENTRY_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditEntryActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Entry can't be updated!", Toast.LENGTH_SHORT).show();
                return;
            }
            Entry entry = retrieveEntryAttr(data);
            entry.setId(id);
            entryViewModel.update(entry);
            Toast.makeText(this, "Entry updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Entry not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        btnAddEntry = findViewById(R.id.floating_action_button);
        confirmFab = findViewById(R.id.floating_action_button_submit);
        textInputEditTextName = findViewById(R.id.editTextName);
        textInputLayoutName = findViewById(R.id.textInputLayoutName);
        relativeLayoutSetup = findViewById(R.id.relLayParentSetup);
        constraintLayoutFetchData = findViewById(R.id.constrainLayFetchingData);
        recyclerView = findViewById(R.id.entryRecyclerView);

        submitBtn = findViewById(R.id.submitBtn);
        welcomeTxt = findViewById(R.id.welcomeTxtView);
        textInputLayoutDay = findViewById(R.id.textInputLayoutDays);
        textInputLayoutWeek = findViewById(R.id.textInputLayoutWeeks);
        autoCompleteDropDownMenuDays = findViewById(R.id.dropDownMenuDays);
        autoCompleteDropDownMenuWeeks = findViewById(R.id.dropDownMenuWeeks);
    }

    private Entry retrieveEntryAttr(Intent data) {
        String element = data.getStringExtra(AddEditEntryActivity.EXTRA_ELEMENT);
        String day = data.getStringExtra(AddEditEntryActivity.EXTRA_DAY);
        String type = data.getStringExtra(AddEditEntryActivity.EXTRA_TYPE);
        String startTime = data.getStringExtra(AddEditEntryActivity.EXTRA_START_TIME);
        int startWeek = data.getIntExtra(AddEditEntryActivity.EXTRA_START_WEEK, 1);
        int endWeek = data.getIntExtra(AddEditEntryActivity.EXTRA_LAST_WEEK, 14);
        int duration = data.getIntExtra(AddEditEntryActivity.EXTRA_DURATION, 2);

        return new Entry(endWeek, startWeek, duration, startTime, element, type, day,
                Utils.dayToInt(day));
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new EntryAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void submitOnClickMethod(View view) {
        // Checking if the user has chosen something from the drop down menus
        boolean bool1 = !autoCompleteDropDownMenuDays.getText().toString().equals("");
        boolean bool2 = !autoCompleteDropDownMenuWeeks.getText().toString().equals("");

        // Printing an error in the 1st drop down menu
        if (!bool1) {
            textInputLayoutDay.setError("You need to choose a day!");
            textInputLayoutDay.setErrorEnabled(true);
        } else
            textInputLayoutDay.setErrorEnabled(false);

        // Printing an error in the 2nd drop down menu
        if (!bool2) {
            textInputLayoutWeek.setError("A week is necessary to continue!");
            textInputLayoutWeek.setErrorEnabled(true);
        } else
            textInputLayoutWeek.setErrorEnabled(false);

        // Moving to the next activity if only the fields are filled
        if (bool1 && bool2) {
            Intent intent = new Intent(MainActivity.this, FetchDataToUser.class);
            intent.putExtra(Utils.DAY_STRING_KEY, autoCompleteDropDownMenuDays.getText().toString())
                    .putExtra(Utils.WEEK_STRING_KEY, autoCompleteDropDownMenuWeeks.getText().toString());
            startActivity(intent);
        }
    }
}