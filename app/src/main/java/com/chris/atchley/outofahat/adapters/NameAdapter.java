package com.chris.atchley.outofahat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.chris.atchley.outofahat.R;

import java.util.ArrayList;

/**
 * Created by Bob on 2/24/2016.
 */
public class NameAdapter extends BaseAdapter {

    private ArrayList<String> list = new ArrayList<>();
    private Context mContext;
    public Button addNameButton;

    public NameAdapter(ArrayList<String> list, Context context){
        this.list = list;
        this.mContext=context;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView== null){
            //brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.name_list_item,null);
            holder = new ViewHolder();
            holder.nameTextView = (TextView) convertView.findViewById(R.id.nameLabel);
            holder.deleteButton = (Button) convertView.findViewById(R.id.deleteButton);


            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.nameTextView.setText(list.get(position));
        return convertView;
    }

    private static class ViewHolder{
        TextView nameTextView;
        Button deleteButton;

    }
}
