package com.zafar.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        noteID = intent.getIntExtra("noteID", -1);

        if(noteID != -1){
            editText.setText(MainActivity.notes.get(noteID));
        }else{
            MainActivity.notes.add("");
            noteID = MainActivity.notes.size() - 1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteID, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.zafar.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return handled;
            }
        });
    }

}
