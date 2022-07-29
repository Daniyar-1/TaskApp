package com.example.taskapp.ui.note;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.databinding.FragmentNewNoteBinding;
import com.example.taskapp.ui.note.models.Note;

public class NewNoteFragment extends Fragment {

    private FragmentNewNoteBinding binding;
    private final String REQUEST_KEY = "newNote";
    private NavController navController;
    private Note note;
    private boolean edit = false;


    public NewNoteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);
        binding = FragmentNewNoteBinding.inflate(inflater, container, false);

        initViews();

        if (getArguments() != null) {
            getDataForEdit();
            edit = true;
        }
        return binding.getRoot();
    }

    private void initViews() {

        Bundle bundle = new Bundle();

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        binding.imgItem.setImageURI(data.getData());
                        bundle.putString("img", String.valueOf(data.getData()));
                    }
                });


        binding.imgItem.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            someActivityResultLauncher.launch(intent);
        });


        binding.btnAdd.setOnClickListener(v -> {
            String title = binding.etTitle.getText().toString();
            String desc = binding.etDesc.getText().toString();
            String imgUrl = String.valueOf(binding.imgItem.getImageAlpha());
            if (note != null) {
                note.setTitle(title);
                note.setDescription(desc);
                note.setImgURL(imgUrl);
                App.getInstance().getDatabase().noteDao().update(note);
                navController.navigateUp();

               /* if (title.isEmpty()) {
                    binding.etTitle.setError("Enter title!");
                } else if (desc.isEmpty()) {
                    binding.etDesc.setError("Enter description!");*/
            } else {
                bundle.putString("title", title);
                bundle.putString("description", desc);
                bundle.putBoolean("edit", edit);

                note = new Note(imgUrl, title, desc);
                App.getInstance().getDatabase().noteDao().insert(note);

                getActivity().getSupportFragmentManager().setFragmentResult(REQUEST_KEY, bundle);
                navController.navigateUp();

            }
        });
    }

    private void getDataForEdit() {
        note = (Note) getArguments().getSerializable("edit");
        binding.etTitle.setText(note.getTitle());
        binding.etDesc.setText(note.getDescription());
        binding.imgItem.setImageURI(Uri.parse(note.getImgURL()));

    }
}