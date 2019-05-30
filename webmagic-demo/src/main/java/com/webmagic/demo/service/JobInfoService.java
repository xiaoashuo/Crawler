package com.webmagic.demo.service;

import com.webmagic.demo.pojo.JobInfo;

import java.util.List;

public interface JobInfoService {
    /**
     * 保存工作信息
     * @param jobInfo
     */
    public void save(JobInfo jobInfo);

    /**
     * 查询列表
     * @param jobInfo
     * @return
     */
    public List<JobInfo> findJobInfo(JobInfo jobInfo);
}
