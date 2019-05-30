package com.webmagic.demo.dao;

import com.webmagic.demo.pojo.JobInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobInfoDao extends JpaRepository<JobInfo,Long> {
}
