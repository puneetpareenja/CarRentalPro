package com.pareenja.carrentalpro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pareenja.carrentalpro.models.Person;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAllUsersAdapter extends FirestoreRecyclerAdapter<Person, ViewAllUsersAdapter.ViewHolder> {

    private ViewAllUsersAdapter.OnItemClickListener listener;

    public ViewAllUsersAdapter(@NonNull FirestoreRecyclerOptions<Person> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Person person) {
        viewHolder.userPhoneTextView.setText(String.valueOf(person.getPhone()));
        viewHolder.userEmailTextView.setText(person.getEmail());
        viewHolder.userNameTextView.setText(person.getName());
    }

    @NonNull
    @Override
    public ViewAllUsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.user_item_view, parent, false);
        return new ViewAllUsersAdapter.ViewHolder(view);
    }

    public void setOnItemClickListener(ViewAllUsersAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userCircularImageView;
        TextView userNameTextView;
        TextView userEmailTextView;
        TextView userPhoneTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            userCircularImageView = itemView.findViewById(R.id.circular_image_user_image);
            userNameTextView = itemView.findViewById(R.id.text_view_username);
            userEmailTextView = itemView.findViewById(R.id.text_view_email);
            userPhoneTextView = itemView.findViewById(R.id.text_view_phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION
                            && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

    }
}