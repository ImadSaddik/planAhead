package com.example.mytimetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddEditEntryActivity extends AppCompatActivity {
    public static final String EXTRA_ELEMENT =
            "com.example.mytimetable.EXTRA_ELEMENT";
    public static final String EXTRA_TYPE =
            "com.example.mytimetable.EXTRA_TYPE";
    public static final String EXTRA_DAY =
            "com.example.mytimetable.EXTRA_DAY";
    public static final String EXTRA_START_TIME =
            "com.example.mytimetable.EXTRA_START_TIME";
    public static final String EXTRA_DURATION =
            "com.example.mytimetable.EXTRA_DURATION";
    public static final String EXTRA_START_WEEK =
            "com.example.mytimetable.EXTRA_START_WEEK";
    public static final String EXTRA_LAST_WEEK =
            "com.example.mytimetable.EXTRA_LAST_WEEK";
    public static final String EXTRA_ID =
            "com.example.mytimetable.EXTRA_ID";

    TextInputLayout textInputLayoutElement, textInputLayoutDay, textInputLayoutFirstWeek, textInputLayoutLastWeek;
    TextInputLayout textInputLayoutType, textInputLayoutStartTime, textInputLayoutDuration;
    TextInputEditText editTextFirstWeek, editTextLastWeek, editTextStartTime, editTextDuration;
    TextInputEditText editTextElement;
    AutoCompleteTextView dropDownMenuDay, dropDownMenuType;
    Button saveEntryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        init();
        populateDropDownMenus();

        saveEntryBtn.setOnClickListener(v -> saveEntry());

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit entry");
            editTextDuration.setText(String.valueOf(intent.getIntExtra(EXTRA_DURATION, 2)));
            editTextFirstWeek.setText(String.valueOf(intent.getIntExtra(EXTRA_START_WEEK, 1)));
            editTextLastWeek.setText(String.valueOf(intent.getIntExtra(EXTRA_LAST_WEEK, 14)));
            editTextStartTime.setText(intent.getStringExtra(EXTRA_START_TIME));
            dropDownMenuDay.setText(intent.getStringExtra(EXTRA_DAY));
            editTextElement.setText(intent.getStringExtra(EXTRA_ELEMENT));
            dropDownMenuType.setText(intent.getStringExtra(EXTRA_TYPE));
        } else {
            setTitle("Add entry");
        }
    }

    private static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    private void saveEntry() {
        String element, day, type, startTime;
        int startWeek, endWeek, duration;
        boolean elementBool, dayBool, typeBool, firstWeekBool, lastWeekBool, timeBool, durationBool;

        elementBool = editTextElement.getText().toString().equals("");
        dayBool = dropDownMenuDay.getText().toString().equals("");
        typeBool = dropDownMenuType.getText().toString().equals("");

        firstWeekBool = editTextFirstWeek.getText().toString().equals("") ||
                Integer.parseInt(editTextFirstWeek.getText().toString()) < 1;
        lastWeekBool = editTextLastWeek.getText().toString().equals("") ||
                Integer.parseInt(editTextLastWeek.getText().toString()) > 14;
        timeBool = editTextStartTime.getText().toString().equals("") ||
                !isValidDate(editTextStartTime.getText().toString());
        durationBool = editTextDuration.getText().toString().equals("") ||
                Integer.parseInt(editTextDuration.getText().toString()) < 2 ||
                Integer.parseInt(editTextDuration.getText().toString()) > 3;

        // Showing the errors
        if (elementBool) {
            textInputLayoutElement.setError("You need to type an element!");
            textInputLayoutElement.setErrorEnabled(true);
        } else {
            textInputLayoutElement.setErrorEnabled(false);
        }

        if (dayBool) {
            textInputLayoutDay.setError("You need to choose a day!");
            textInputLayoutDay.setErrorEnabled(true);
        } else {
            textInputLayoutDay.setErrorEnabled(false);
        }

        if (typeBool) {
            textInputLayoutType.setError("You need to choose a type!");
            textInputLayoutType.setErrorEnabled(true);
        } else {
            textInputLayoutType.setErrorEnabled(false);
        }

        if (firstWeekBool) {
            textInputLayoutFirstWeek.setError("Specify the first week!");
            textInputLayoutFirstWeek.setErrorEnabled(true);
        } else {
            textInputLayoutFirstWeek.setErrorEnabled(false);
        }

        if (lastWeekBool) {
            textInputLayoutLastWeek.setError("Specify the last week!");
            textInputLayoutLastWeek.setErrorEnabled(true);
        } else {
            textInputLayoutLastWeek.setErrorEnabled(false);
        }

        if (timeBool) {
            textInputLayoutStartTime.setError("The time should be in this format '00:00'");
            textInputLayoutStartTime.setErrorEnabled(true);
        } else {
            textInputLayoutStartTime.setErrorEnabled(false);
        }

        if (durationBool) {
            textInputLayoutDuration.setError("The duration can be either 2 or 3");
            textInputLayoutDuration.setErrorEnabled(true);
        } else {
            textInputLayoutDuration.setErrorEnabled(false);
        }

        // Saving if everything is entered
        if (!elementBool && !dayBool && ! typeBool && !firstWeekBool && !lastWeekBool && !timeBool && ! durationBool) {
            element = editTextElement.getText().toString();
            day = dropDownMenuDay.getText().toString();
            type = dropDownMenuType.getText().toString();
            startTime = editTextStartTime.getText().toString();
            startWeek = Integer.parseInt(editTextFirstWeek.getText().toString());
            endWeek = Integer.parseInt(editTextLastWeek.getText().toString());
            duration = Integer.parseInt(editTextDuration.getText().toString());

            // Correct the formatting of the start time
            StringBuilder sb = new StringBuilder();
            if (startTime.length() < 5) {
                startTime = sb.append("0").append(startTime).toString();
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_START_WEEK, startWeek);
            data.putExtra(EXTRA_LAST_WEEK, endWeek);
            data.putExtra(EXTRA_DURATION, duration);
            data.putExtra(EXTRA_TYPE, type);
            data.putExtra(EXTRA_DAY, day);
            data.putExtra(EXTRA_ELEMENT, element);
            data.putExtra(EXTRA_START_TIME, startTime.substring(0, 5));

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_ID, id);
            }

            setResult(RESULT_OK, data);
            finish();
        }
    }

    private void init() {
        saveEntryBtn = findViewById(R.id.save_entry_button);
        textInputLayoutDay = findViewById(R.id.textInputLayoutDay);
        textInputLayoutDuration = findViewById(R.id.textInputLayoutDuration);
        textInputLayoutFirstWeek = findViewById(R.id.textInputLayoutFirstWeek);
        textInputLayoutLastWeek = findViewById(R.id.textInputLayoutLastWeek);
        textInputLayoutType = findViewById(R.id.textInputLayoutType);
        textInputLayoutStartTime = findViewById(R.id.textInputLayoutStartTime);
        textInputLayoutElement = findViewById(R.id.textInputLayoutElement);
        editTextFirstWeek = findViewById(R.id.editTextFirstWeek);
        editTextLastWeek = findViewById(R.id.editTextLastWeek);
        editTextStartTime = findViewById(R.id.editTextStartTime);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextElement = findViewById(R.id.editTextElement);
        dropDownMenuDay = findViewById(R.id.dropDownMenuDay);
        dropDownMenuType = findViewById(R.id.dropDownMenuType);
    }

    private void populateDropDownMenus() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.drop_down_menu, Utils.DAYS_OF_THE_WEEK);
        dropDownMenuDay.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.drop_down_menu, Utils.TYPES);
        dropDownMenuType.setAdapter(arrayAdapter);
    }
}