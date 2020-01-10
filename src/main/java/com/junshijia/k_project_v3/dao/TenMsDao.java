package com.junshijia.k_project_v3.dao;

import com.junshijia.k_project_v3.domain.TenMsData;

import java.util.List;

public interface TenMsDao {
    List<TenMsData> findAll(String tableName);
}
