package com.pareenja.carrentalpro.admin;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.auth.User;
import com.pareenja.carrentalpro.R;
import com.pareenja.carrentalpro.adapters.AdminViewAllUsersAdapter;
import com.pareenja.carrentalpro.models.Person;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminUserFragment extends Fragment {

    private static final String TAG = "AdminUserFragment";

    ArrayList<User> carArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("users");
    private AdminViewAllUsersAdapter adminViewAllUsersAdapter;
    private View layout;
    private ExtendedFloatingActionButton fab;

    public AdminUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_admin_user, container, false);
        fab = layout.findViewById(R.id.fab_add_new_user);
        fab.setOnClickListener(fabListener);
        setUpRecyclerView();
        return layout;
    }

    private ExtendedFloatingActionButton.OnClickListener fabListener =
            v -> {
                Intent intent = new Intent(getContext(), AddUserActivity.class);
                startActivity(intent);
            };

    private void setUpRecyclerView() {
        Query query = userRef.whereEqualTo("personRole", "CUSTOMER");

        FirestoreRecyclerOptions<Person> userFirestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Person>()
                        .setQuery(query, Person.class)
                        .build();

        Log.d(TAG, "setUpRecyclerView: Everything is working fine");
        adminViewAllUsersAdapter = new AdminViewAllUsersAdapter(userFirestoreRecyclerOptions);
        RecyclerView recyclerView = layout.findViewById(R.id.admin_user_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adminViewAllUsersAdapter);

        adminViewAllUsersAdapter.setOnItemClickListener(new AdminViewAllUsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Person person = documentSnapshot.toObject(Person.class);
                person.setId(documentSnapshot.getId());

//                Intent intent = new Intent(ViewCarsActivity.this, CarDetailsActivity.class);
//                intent.putExtra("car", car);
//                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adminViewAllUsersAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adminViewAllUsersAdapter.stopListening();
    }

}
