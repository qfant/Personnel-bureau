package com.page.home;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/12/12.
 */

public class PersonsResult extends BaseResult {

    public PersonsData data;

    /**
     * townResult	列表	JsonArray
     * townResult [i].id	id	string
     * townResult [i].name	名称	String
     * townResult [i].villages	村	JsonArray
     * townResult [i]. villages[i].name	村名	String
     * townResult [i]. villages[i].id	村id	String
     */
    public static class PersonsData implements BaseData {
        public List<PersonBean> persons;
    }

    public static class PersonBean implements BaseData {
        public String id;
        public String name;
    }
}
