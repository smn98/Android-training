package com.stcet.todoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    RealmResults<Todo> realmResults;
    Context mContext;
    public Adapter(RealmResults<Todo> todos, Context context){
        realmResults = todos;
        mContext = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Todo todo = realmResults.get(position);

        assert todo != null;
        holder.todo.setText(todo.getTodoItem());
        holder.details.setText(todo.getDetails());
        holder.due.setText(todo.getDuedate());
        //holder.card.setBackgroundColor(Color.parseColor(todo.getColor()));
        String color = todo.getColor();
        if(color.equals("Green"))
            holder.colorLayout.setBackgroundResource(R.drawable.recycler_shape_green);
        else if(color.equals("Red"))
            holder.colorLayout.setBackgroundResource(R.drawable.recycler_shape_red);
        else if(color.equals("Yellow"))
            holder.colorLayout.setBackgroundResource(R.drawable.recycler_shape_yellow);
    }


    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public RealmResults<Todo> getData() {
        return realmResults;
    }

    public void removeItem(int position) {
        //realmResults.remove(position);
        Realm r = Realm.getDefaultInstance();
        r.beginTransaction();
        Todo todo = realmResults.get(position);
        todo.deleteFromRealm();
        r.commitTransaction();

        notifyItemRemoved(position);
    }

    public void restoreItem(Todo item, int position) {
        //realmResults.add(position, item);
        Realm r = Realm.getDefaultInstance();
        r.beginTransaction();
        //r.insert(item);
        //Log.d("log111", mTmp.toString());
        r.commitTransaction();

        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView todo;
        private TextView details;
        private TextView due;
        private ConstraintLayout card;
        private ConstraintLayout colorLayout;

        public MyViewHolder(@NonNull View view) {
            super(view);
            todo = view.findViewById(R.id.todo_tv);
            details = view.findViewById(R.id.details_tv);
            due = view.findViewById(R.id.due_tv);
            card=view.findViewById(R.id.rootView);
            colorLayout=view.findViewById(R.id.recycler_color_consLayout);
        }
    }
}
