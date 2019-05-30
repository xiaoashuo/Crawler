package com.webmagic.demo.service.impl;

import com.webmagic.demo.dao.JobInfoDao;
import com.webmagic.demo.pojo.JobInfo;
import com.webmagic.demo.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoDao jobInfoDao;

    @Override
    @Transactional
    public void save(JobInfo jobInfo) {
      //根据url 和发布时间查询数据
        JobInfo param=new JobInfo();
        param.setUrl(jobInfo.getUrl());
        param.setTime(jobInfo.getTime());
        List<JobInfo> list = findJobInfo(param);
        if (list.size()==0){
            jobInfoDao.saveAndFlush(jobInfo);
        }
    }

    @Override
    public List<JobInfo> findJobInfo(JobInfo jobInfo) {
        Example example=Example.of(jobInfo);
        List list = this.jobInfoDao.findAll(example);
        return list;
    }
}
