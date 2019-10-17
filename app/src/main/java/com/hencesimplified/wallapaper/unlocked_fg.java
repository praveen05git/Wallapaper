package com.hencesimplified.wallapaper;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class unlocked_fg extends Fragment {

    private List<sample_photos> listPhotos;
    private RecyclerView myrv;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private RecyclerViewAdapter1 myAdap;


    public unlocked_fg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_unlocked_fg, container, false);


        firebaseDatabase=FirebaseDatabase.getInstance();
        listPhotos=new ArrayList<>();
        myrv=root.findViewById(R.id.unlocked_rec_id);
        myrv.setHasFixedSize(true);
        myrv.setLayoutManager(new GridLayoutManager(getContext(),3));
        myAdap = new RecyclerViewAdapter1(getContext(),listPhotos);


        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("Page",2);
        editor.apply();

        databaseReference = firebaseDatabase.getReference("unlocked");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    sample_photos photos = postSnapshot.getValue(sample_photos.class);
                    listPhotos.add(photos);
                }

                myrv.setAdapter(myAdap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return root;
    }

}
