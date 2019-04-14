package edu.svsu.rentit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.models.User;

public class UserViewAdapter extends RecyclerView.Adapter{

    ArrayList<User> list = new ArrayList<User>();
    Context context;

    public UserViewAdapter(ArrayList<User> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_recycler_view_item, viewGroup, false);

        UserViewHolder holder = new UserViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ((UserViewHolder)viewHolder).setModel(list.get(i));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int pos, User user) {
        list.add(pos, user);
        notifyItemInserted(pos);
    }

    public void remove(User user) {
        int pos = list.indexOf(user);
        list.remove(pos);
        notifyItemRemoved(pos);
    }
}
