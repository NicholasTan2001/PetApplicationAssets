package com.example.mypet; //CD21068

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Diary extends AppCompatActivity implements MyDiaryAdapter.OnEntryEditListener {
    private EditText diaryEntryEditText;
    private Button saveButton;
    private RecyclerView displayRecyclerView;

    private DatabaseReference databaseReference;
    private ArrayList<String> entries;
    private ArrayList<String> entryIds;
    private MyDiaryAdapter adapter;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        userId = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("diaryEntries");

        diaryEntryEditText = findViewById(R.id.diary_entry);
        saveButton = findViewById(R.id.save_button);
        displayRecyclerView = findViewById(R.id.display_list);

        entries = new ArrayList<>();
        entryIds = new ArrayList<>();
        adapter = new MyDiaryAdapter(this, entries, entryIds);
        adapter.setOnEntryEditListener(this);

        displayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayRecyclerView.setAdapter(adapter);

        displayDiaryEntries();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiaryEntry(null);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                deleteDiaryEntry(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(displayRecyclerView);
    }

    @Override
    public void onEntryEdit(String entryId, String newText) {
        updateDiaryEntry(entryId, newText);
    }

    private void saveDiaryEntry(String entryId) {
        String entry = diaryEntryEditText.getText().toString();
        if (!entry.isEmpty()) {
            if (entryId == null) {
                entryId = databaseReference.push().getKey();
            }

            if (entryId != null) {
                databaseReference.child(entryId).setValue(entry)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Diary.this, "Entry saved", Toast.LENGTH_SHORT).show();
                                    diaryEntryEditText.setText("");
                                    displayDiaryEntries();
                                } else {
                                    Toast.makeText(Diary.this, "Failed to save entry", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } else {
            Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDiaryEntry(String entryId, String newText) {
        databaseReference.child(entryId).setValue(newText)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Diary.this, "Entry updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Diary.this, "Failed to update entry", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void displayDiaryEntries() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                entries.clear();
                entryIds.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String entry = dataSnapshot.getValue(String.class);
                    String entryId = dataSnapshot.getKey();
                    entries.add(entry);
                    entryIds.add(entryId);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Diary.this, "Failed to read entries", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteDiaryEntry(int position) {
        String entryId = entryIds.get(position);

        databaseReference.child(entryId).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Diary.this, "Entry deleted", Toast.LENGTH_SHORT).show();
                            displayDiaryEntries();
                        } else {
                            Toast.makeText(Diary.this, "Failed to delete entry", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
