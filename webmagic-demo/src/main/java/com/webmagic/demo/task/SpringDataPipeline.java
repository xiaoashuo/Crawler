package com.webmagic.demo.task;

import com.webmagic.demo.pojo.JobInfo;
import com.webmagic.demo.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class SpringDataPipeline implements Pipeline {

    @Autowired
    JobInfoService jobInfoService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        //获取封装好的招聘对象
        JobInfo jobInfo = resultItems.get("jobInfo");
        //不为空保存进数据库
        if (jobInfo!=null){
          jobInfoService.save(jobInfo);
        }
    }
}
