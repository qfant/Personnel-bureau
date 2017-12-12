package com.page.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.cache.ImageLoader;
import com.haolb.client.R;
import com.page.detail.DetailResult.DetailData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.framework.net.Request.RequestFeature.BLOCK;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class DetailActivity extends BaseActivity {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.btn_detail)
    TextView btnDetail;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_end)
    Button btnEnd;
    @BindView(R.id.image_big)
    ImageView imageBig;
    @BindView(R.id.ll_big)
    LinearLayout llBig;
    private DetailData item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setTitleBar("详情", true);
        item = (DetailData) myBundle.getSerializable("CameraBean");
        setData();
    }

    private void setData() {
        ImageLoader.getInstance(this).loadImage(item.pic, image, R.drawable.moren);
        ImageLoader.getInstance(this).loadImage(item.pic, imageBig, R.drawable.moren);
        llBig.setVisibility(View.GONE);
        tvAddress.setText(item.address);
        tvTitle.setText(item.phone);
        btnDetail.setText(item.statusCN);
        tvIntro.setText(item.intro);
        btnStart.setVisibility(View.GONE);
        btnEnd.setVisibility(View.GONE);
        if (item.status == 3) {
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setText("开始接单");
        } else if (item.status == 1 || item.status == 4) {
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setText("开始处理");
        } else if (item.status == 5) {
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setText("处理完成");
        } else {
            btnStart.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn_start, R.id.btn_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                if (item.status == 3) {
                    DetailParam param = new DetailParam();
                    param.id = item.id;
                    Request.startRequest(param, ServiceMap.receiveRepair, mHandler, BLOCK);
                } else if (item.status == 1 || item.status == 4) {
                    DetailParam param = new DetailParam();
                    param.id = item.id;
                    Request.startRequest(param, ServiceMap.startRepair, mHandler, BLOCK);
                } else if (item.status == 5) {
                    DetailParam param = new DetailParam();
                    param.id = item.id;
                    Request.startRequest(param, ServiceMap.endRepair, mHandler, BLOCK);
                }
                break;
            case R.id.btn_end:
                break;
        }
    }

    void reloadData() {
        DetailParam param = new DetailParam();
        param.id = item.id;
        Request.startRequest(param, ServiceMap.getRepair, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.receiveRepair) {
            if (param.result.bstatus.code == 0) {
                reloadData();
            }
            showToast(param.result.bstatus.des);
        } else if (param.key == ServiceMap.startRepair) {
            if (param.result.bstatus.code == 0) {
                reloadData();
            }
            showToast(param.result.bstatus.des);
        } else if (param.key == ServiceMap.endRepair) {
            if (param.result.bstatus.code == 0) {
                reloadData();
            }
            showToast(param.result.bstatus.des);
        } else if (param.key == ServiceMap.getRepair) {
            if (param.result.bstatus.code == 0) {
                DetailResult result = (DetailResult) param.result;
                item = result.data;
                setData();
            }
            showToast(param.result.bstatus.des);
        }
        return super.onMsgSearchComplete(param);
    }


    @OnClick({R.id.image, R.id.image_big, R.id.ll_big})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image:
                if (llBig.getVisibility() == View.GONE) {
                    llBig.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.image_big:
            case R.id.ll_big:

                if (llBig.getVisibility() == View.VISIBLE) {
                    llBig.setVisibility(View.GONE);
                }
                break;
        }
    }
}
