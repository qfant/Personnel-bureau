package com.page.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.framework.activity.BaseFragment;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.detail.DetailActivity;
import com.page.detail.DetailParam;
import com.page.detail.DetailResult;
import com.page.detail.EquipmentResult;
import com.page.home.WorkerRepairParam;
import com.page.home.WorkerRepairResult;
import com.page.home.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2017/8/13.
 * 首页
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.main_lv)
    ListView mainLv;
    @BindView(R.id.main_srl)
    SwipeRefreshLayout mainSrl;
    Unbinder unbinder;
    private int type = 0;
    private HomeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_home_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        adapter = new HomeAdapter(getContext());
        mainLv.setAdapter(adapter);
        mainSrl.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mainSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        mainLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                HomeAdapter adapter = (HomeAdapter) adapterView.getAdapter();
                WorkerRepairResult.repair item = adapter.getItem(i);
                DetailParam param = new DetailParam();
                param.id = item.id;
                Request.startRequest(param, ServiceMap.equipment, mHandler, Request.RequestFeature.BLOCK);
            }
        });
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    private void loadData() {
//        WorkerRepairResult.repair repair = new WorkerRepairResult.repair();
//        repair.rtmp ="";
//        repair.imageUrl="";
//        List<WorkerRepairResult.repair> list =new ArrayList<>();
//        list.add(repair);
//        list.add(repair);
//        list.add(repair);
//        list.add(repair);
//        list.add(repair);
//        list.add(repair);
//        adapter.setData(list);
//        mainSrl.setRefreshing(true);
        WorkerRepairParam param = new WorkerRepairParam();
        param.type = 1;
        Request.startRequest(param, ServiceMap.equipments, mHandler, Request.RequestFeature.ADD_ONORDER);
    }
    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (super.onMsgSearchComplete(param)) {
            return true;
        }
        if (param.key == ServiceMap.equipments) {
            WorkerRepairResult result = (WorkerRepairResult) param.result;
            if (result.bstatus.code == 0) {
                if (adapter != null) {
                    adapter.setType(type);
                    adapter.setData(result.data.equimpents);
                }
                if (mainSrl != null) {
                    mainSrl.setRefreshing(false);
                }
            }
        } else if (param.key == ServiceMap.equipment) {
            if (param.result.bstatus.code == 0) {

                qStartActivity(VideoActivity.class);
                EquipmentResult result = (EquipmentResult) param.result;
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", result.data);
                qStartActivity(VideoActivity.class, bundle);
            } else {
                showToast(param.result.bstatus.des);
            }
        }
        return super.onMsgSearchComplete(param);
    }

    @Override
    public void onNetEnd(NetworkParam param) {
        if (mainSrl != null) {
            mainSrl.setRefreshing(false);
        }
        super.onNetEnd(param);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setType(int type) {
        this.type = type;
    }
}
