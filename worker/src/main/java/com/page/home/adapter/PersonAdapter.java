package com.page.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.adapter.utils.QSimpleAdapter;
import com.haolb.client.R;

import static com.page.home.TownsResult.*;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class PersonAdapter extends QSimpleAdapter<TownBean> {

    public PersonAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, ViewGroup parent) {
        return inflate(R.layout.pub_person_item, null, false);
    }

    @Override
    protected void bindView(View view, Context context, TownBean item, int position) {
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView tvInfo = (TextView) view.findViewById(R.id.text_name);
//        ImageLoader.getInstance(context).loadImage(item.pic, imageView,R.drawable.moren);
    }

}
