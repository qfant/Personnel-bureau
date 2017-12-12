package com.page.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.framework.adapter.utils.QSimpleAdapter;
import com.haolb.client.R;
import com.page.home.CamerasResult;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class ContactAdapter extends QSimpleAdapter<CamerasResult.CameraBean> {
    private int type;

    public ContactAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, ViewGroup parent) {
        return inflate(R.layout.pub_person_item, null, false);
    }

    @Override
    protected void bindView(View view, Context context, CamerasResult.CameraBean item, int position) {
//        ImageView imageView = (ImageView) view.findViewById(R.id.image);
//        ImageLoader.getInstance(context).loadImage(item.pic, imageView,R.drawable.moren);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
