package com.example.taskapp.ui.note;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskapp.databinding.FragmentNewNoteBinding;
import com.example.taskapp.ui.note.models.Note;

public class NewNoteFragment extends Fragment {

    private FragmentNewNoteBinding binding;
    private final String REQUEST_KEY = "newNote";

    public NewNoteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewNoteBinding.inflate(inflater, container, false);

        initViews();

        return binding.getRoot();
    }

    private void initViews() {
        binding.btnAdd.setOnClickListener(v -> {
            String title = binding.etTitle.getText().toString();
            String desc = binding.etDesc.getText().toString();
/*            if (title.isEmpty() ){
                binding.etTitle.setError("Enter title!");
            } else if (desc.isEmpty()) {
                binding.etDesc.setError("Enter description!");
            } else {*/

            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("description", desc);

            getParentFragmentManager().setFragmentResult(REQUEST_KEY,bundle);
            getParentFragmentManager().popBackStack();
//            }

        });
    }


}