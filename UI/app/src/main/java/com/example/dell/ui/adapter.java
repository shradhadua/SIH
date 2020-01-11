package com.example.dell.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {
//    public final String key_title="title";
//    public final String key_url="url";
//    public final String key_date="date";
//    public final String key_author="author";
//    public final String key_content="content";
 List<DataFish>  data= Collections.emptyList();
    private Context context;
    private LayoutInflater inflater;

    DataFish current;
    int currentPos=0;

    public adapter(List<DataFish> data,Context context){
        this.data=data;
        inflater= LayoutInflater.from(context);
        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // this method will be called whenever our ViewHolder is created
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DataFish current=data.get(i);
        viewHolder.title.setText(current.title);
        viewHolder.url.setText(current.url);
        viewHolder.date.setText(current.date);
        viewHolder.author.setText(current.author);
        viewHolder.content.setText(current.content);
    }

    @Override

    //return the size of the listItems (developersList)

    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        // define the View objects

        public TextView title,url,author,content,date;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            // initialize the View objects

           title=(TextView)itemView.findViewById(R.id.txtTitle);
           url=(TextView)itemView.findViewById(R.id.txtURL);
            author=(TextView)itemView.findViewById(R.id.txtAuthor);

            content=(TextView)itemView.findViewById(R.id.txtContent);

            date=(TextView)itemView.findViewById(R.id.txtPubDate);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

        }

    }
}

