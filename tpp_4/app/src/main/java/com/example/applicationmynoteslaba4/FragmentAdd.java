package com.example.applicationmynoteslaba4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.applicationmynoteslaba4.helpers.DatabaseHelper;

public class FragmentAdd extends Fragment {
    private EditText editTextNoteDescription;
    private Button buttonAdd;
    private DatabaseHelper databaseHelper;

    public FragmentAdd() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        editTextNoteDescription = view.findViewById(R.id.editTextNoteDescription);
        buttonAdd = view.findViewById(R.id.buttonAdd);
        databaseHelper = new DatabaseHelper(getActivity());

        buttonAdd.setOnClickListener(v -> addNote());

        return view;
    }

    private void addNote() {
        String description = editTextNoteDescription.getText().toString().trim();

        if (!description.isEmpty()) {
            long result = databaseHelper.addNote(description);
            if (result != -1) {
                Toast.makeText(getActivity(), "Note added successfully", Toast.LENGTH_SHORT).show();
                editTextNoteDescription.setText("");
            } else {
                Toast.makeText(getActivity(), "Error adding note", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Please enter a description", Toast.LENGTH_SHORT).show();
        }
    }
}
