package com.fazal.dreamsolutions.gymappnew.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fazal.dreamsolutions.gymappnew.R;

public class HEADERVIEWHOLDER extends RecyclerView.ViewHolder{
    public TextView headerTitle;
    public TextView headerDesciption;
    public HEADERVIEWHOLDER(View itemView) {
        super(itemView);
        headerTitle = (TextView)itemView.findViewById(R.id.header_title);
        headerDesciption = (TextView)itemView.findViewById(R.id.header_description);
    }
}