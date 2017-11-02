package com.page.home;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class WorkerRepairResult extends BaseResult {

    public WorkerRepairData data;

    public static class WorkerRepairData implements BaseData {
        public int totalNum;
        public List<repair> repairList;
    }


    public static class repair implements BaseData {
        public String statusCN;
        public String address;
        public String createtime;
        public String phone;
        public String intro;
        public String pic;
        public int id;
        public int status;
        public int type;
        public String url;
        public String imageUrl;
    }
}
