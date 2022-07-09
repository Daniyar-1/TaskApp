package com.example.taskapp.ui.note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.taskapp.R;
import com.example.taskapp.databinding.FragmentNoteBinding;
import com.example.taskapp.ui.note.adapters.NoteAdapter;
import com.example.taskapp.ui.note.models.Note;

import java.util.ArrayList;
import java.util.List;


public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;
    private NoteAdapter adapter;
    private final String REQUEST_KEY = "newNote";
    private final List<Note> notes = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater, container, false);

        binding.rvNote.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new NoteAdapter();
        binding.rvNote.setAdapter(adapter);
        initViews();
        adapter.setList(notes);

        return binding.getRoot();

    }

    private void initViews() {
        binding.fabNote.setOnClickListener(v -> {
           openForm();
        });
        listenNoteData();
    }

    private void openForm() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new NewNoteFragment());
        transaction.addToBackStack("NewNoteFragment");
        transaction.commit();
    }

    private void listenNoteData() {
      getParentFragmentManager().setFragmentResultListener(REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (requestKey.equals(REQUEST_KEY)) {
                    String title = result.getString("title");
                    String desc = result.getString("description");
                    adapter.addNewNote(new Note(title, desc));
                    Toast.makeText(requireContext(), "Ура блядь", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}