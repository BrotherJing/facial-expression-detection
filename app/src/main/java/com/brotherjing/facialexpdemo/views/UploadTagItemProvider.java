package com.brotherjing.facialexpdemo.views;

/**
 * Created by Brotherjing on 2016-01-01.
 */
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brotherjing.facialexpdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 靖哥 on 2014/8/28 0028.
 */
public class UploadTagItemProvider {

    private String TAG = "PictureTagsItemProvider";

    public static void createItem(final Context context, MyViewGroup layout, final String tagName) {
        //this.layout = layout;
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.tag_item, null);
        TextView tv = (TextView) view.findViewById(R.id.bt_junxun_picture_item);
        tv.setText(tagName);
        layout.addView(view);
    }
}
