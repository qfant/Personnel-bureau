package com.page.home.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.detail.DetailActivity;
import com.page.detail.DetailParam;
import com.page.detail.DetailResult;
import com.page.home.WorkerRepairResult;
import com.page.home.adapter.ContactAdapter;
import com.page.home.adapter.ContactListAdapter;
import com.page.home.adapter.HomeAdapter;
import com.page.home.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2017/11/15.
 */

public class ContactListActivity extends BaseActivity {

    @BindView(R.id.main_lv)
    ListView mainLv;
    @BindView(R.id.main_srl)
    SwipeRefreshLayout mainSrl;
    private ContactListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_contact_list);
        ButterKnife.bind(this);
        setTitleBar("小李村", true);
        initData();
    }

    private void initData() {
        adapter = new ContactListAdapter(getContext());
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
                HomeAdapter adapter = (HomeAdapter) adapterView.getAdapter();
                WorkerRepairResult.repair item = adapter.getItem(i);
                item.type = adapter.getType();
                DetailParam param = new DetailParam();
                param.id = item.id;
                Request.startRequest(param, ServiceMap.getRepair, mHandler, Request.RequestFeature.BLOCK);
            }
        });
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    private void loadData() {
        WorkerRepairResult.repair repair = new WorkerRepairResult.repair();
        repair.url ="";
//        repair.imageUrl="";
        List<WorkerRepairResult.repair> list =new ArrayList<>();
        list.add(repair);
        list.add(repair);
        list.add(repair);
        list.add(repair);
        list.add(repair);
        list.add(repair);
        adapter.setData(list);
//        mainSrl.setRefreshing(true);
//        WorkerRepairParam param = new WorkerRepairParam();
//        param.type = type + 1;
//        Request.startRequest(param, ServiceMap.getWorkerRepairs, mHandler, Request.RequestFeature.ADD_ONORDER);
    }
    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (super.onMsgSearchComplete(param)) {
            return true;
        }
        if (param.key == ServiceMap.getWorkerRepairs) {
            WorkerRepairResult result = (WorkerRepairResult) param.result;
            if (result.bstatus.code == 0) {
                if (adapter != null) {
                    adapter.setData(result.data.equimpents);
                }
                if (mainSrl != null) {
                    mainSrl.setRefreshing(false);
                }
            }
        } else if (param.key == ServiceMap.getRepair) {
            if (param.result.bstatus.code == 0) {
                DetailResult result = (DetailResult) param.result;
                Bundle bundle = new Bundle();
                bundle.putSerializable("repair", result.data);
                qStartActivity(DetailActivity.class, bundle);
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



}
