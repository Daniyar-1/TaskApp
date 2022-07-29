package com.example.taskapp.ui.note;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.databinding.FragmentNoteBinding;
import com.example.taskapp.ui.note.adapters.NoteAdapter;
import com.example.taskapp.ui.note.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment implements OnItemClickListener {

    private NavController navController;
    private FragmentNoteBinding binding;
    private NoteAdapter adapter;
    private final String REQUEST_KEY = "newNote";
    private final List<Note> notes = new ArrayList<>();
    private int position;
    private Note note;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);
        binding = FragmentNoteBinding.inflate(inflater, container, false);

        binding.rvNote.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new NoteAdapter(notes);
        binding.rvNote.setAdapter(adapter);

        initViews();
        listenNoteData();

        adapter.setOnItemClickListener(this);


        return binding.getRoot();
    }

    private void initRoom() {
        App.getInstance().getDatabase().noteDao().getAllAlive().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> note) {
                notes.clear();
                notes.addAll(App.getInstance().getDatabase().noteDao().getAll());
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRoom();

    }

    private void initViews() {
        binding.fabNote.setOnClickListener(v -> {
            openForm();
        });

    }

    private void openForm() {
        navController.navigate(R.id.action_navigation_home_to_newNoteFragment);
        /*  FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_main, new NewNoteFragment());
        transaction.addToBackStack("NewNoteFragment");
        transaction.commit();*/
    }

    private void listenNoteData() {
        getActivity().getSupportFragmentManager().setFragmentResultListener(REQUEST_KEY, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (requestKey.equals(REQUEST_KEY)) {
                    String title = result.getString("title");
                    String desc = result.getString("description");
                    String imgUrl = result.getString("img");


                    boolean edit = result.getBoolean("edit");
                    if (edit) {
                        note.setTitle(title);
                        note.setDescription(desc);
                        note.setImgURL(imgUrl);
                        adapter.updateNote(note, position);
                    } else {
                        adapter.addNewNote(new Note(imgUrl, title, desc));
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }

    private void sendDataToEdit(Note note) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("edit", note);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_home_to_newNoteFragment, bundle);

    }

    private void showAlert(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Do you wanna delete «" + note.getTitle() + "» ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                notes.remove(note);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


    @Override
    public void onItemClick(int position) {
        note = notes.get(position);
        this.position = position;
        sendDataToEdit(note);
        Toast.makeText(requireContext(), "this is " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(int position) {
        showAlert(notes.get(position));
    }
}