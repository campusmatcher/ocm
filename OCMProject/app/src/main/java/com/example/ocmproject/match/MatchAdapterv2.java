package com.example.ocmproject.match;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ocmproject.R;
import com.example.ocmproject.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

//https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
//https://developer.android.com/guide/topics/ui/layout/recyclerview#java
//https://guides.codepath.com/android/using-the-recyclerview
//https://stackoverflow.com/questions/45474333/how-can-i-set-onclicklistener-to-two-buttons-in-recyclerview

/**
 * Custom Recycle Adapter Class for RecyclerView
 * !! It is recommended to create a model class (like User class) for easy implementation
 */
public class MatchAdapterv2 extends RecyclerView.Adapter<MatchAdapterv2.ViewHolder> {
    private List<User> data;
    private LayoutInflater inflater;
    private ItemClickListener mClickListener;
    private Context context;
    private String currentID;

    /**
     *
     * @param context the activity where RecyclerView is located
     * @param data List of model class
     */
    public MatchAdapterv2(Context context, List<User> data){ //change all User s with your model class's name
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
        this.currentID = FirebaseAuth.getInstance().getUid();
    }

    /**
     * Inflate the custom layout with XML and return the holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.user_list_item, parent, false);// set your custom layout here
        return new ViewHolder(view);
    }

    /**
     * Bind data to elements of custom layout for each row
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        User other = data.get(position);
        holder.myTextView.setText(other.getName() + " " + other.getSurname());
        if (other.getPending().contains(currentID)){
            holder.addButton.setEnabled(false);
        }
        else if (other.getSent().contains(currentID)){
            holder.addButton.setText("ACCEPT REQUEST");
        }

    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    /**
     * Inner class to tore and recycle views
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Views in the custom layout
        TextView myTextView;
        Button addButton;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.userText);
            itemView.setOnClickListener(this);
            addButton = itemView.findViewById(R.id.addButton);
            addButton.setOnClickListener(this);

        }

        @Override
        /**
         * onClick method for items in the custom layout (there is only one onClick!!! Don't add another one)
         */
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
                //addButton.setEnabled(false);
            }

        }
    }

    public User getItem(int i){
        return data.get(i);
    }

    public void setClickListener(ItemClickListener itemClickListener){
        mClickListener = itemClickListener;;
    }

    /**
     * !!!!!!!!!!!!!!!!!
     * Interface that has to be implemented by the activity including recyclerview
     */
    public interface ItemClickListener {
        public void onItemClick(View view, int position);

    }

}

/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
In the activity that uses RecyclerView use the code below to bind adapter to RecyclerView and define onClickItem method

 class ..... implements RecycleAdapter.ItemClickListener

 ArrayList<User> list;
 RecycleAdapter adapter;
 RecyclerView recycleView;

list = new ArrayList<>();
adapter = new RecycleAdapter(this, list);
recycleView = findViewById(R.id.recycleTest);
recycleView.setLayoutManager(new LinearLayoutManager(this));
adapter.setClickListener(this);
recycleView.setAdapter(adapter);

public void onItemClick(View view, int position) {
    ...
 */


