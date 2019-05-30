package com.webmagic.demo.task;

import com.webmagic.demo.pojo.JobInfo;
import com.webmagic.demo.utils.MathSalary;
import org.checkerframework.checker.units.qual.A;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Component
public class JobProcess implements PageProcessor {

    String url="https://search.51job.com/list/000000,000000,0000,01,9,99,java,2,1.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";



    @Override
    public void process(Page page) {
        //解析页面 获取招聘信息详情url地址
        List<Selectable> list = page.getHtml().css("div#resultList div.el").nodes();
        //若为空 则为详情页 否则列表页
        if (list.size()==0){
            this.saveJobInfo(page);
        }else{

            for (Selectable selectable:list) {
                //获取url地址
                String jobInfoUrl = selectable.links().toString();
                //把获取到的地址放到任务队列中
                page.addTargetRequest(jobInfoUrl);
                //获取下一页url
                String bkUrl = page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();
                //把url放入任务队列
                page.addTargetRequest(bkUrl);
            }
        }
        String s = page.getHtml().toString();
        System.out.println(s);
    }

    //解析页面获取详情信息
    private void saveJobInfo(Page page) {
        //创建招聘详情对象
        JobInfo jobInfo = new JobInfo();
        //解析页面
         Html html = page.getHtml();
        //获取数据 封装到对象
         jobInfo.setCompanyName(html.css("div.cn p.cname a","text").toString());
         jobInfo.setCompanyInfo(Jsoup.parse(html.css("div.tmsg").toString()).text());
         jobInfo.setCompanyAddr(Jsoup.parse(html.css("div.bmsg").nodes().get(1).toString()).text());
         jobInfo.setJobName(html.css("div.cn h1","text").toString());
         jobInfo.setJobInfo(Jsoup.parse(html.css("div.job_msg").toString()).text());
         jobInfo.setJobAddr(Jsoup.parse(html.css("div.bmsg").nodes().get(1).toString()).text());
         //获取薪资
         Integer[] salary = MathSalary.getSalary(html.css("div.cn strong", "text").toString());
         jobInfo.setSalaryMin(salary[0]);
         jobInfo.setSalaryMax(salary[1]);
         jobInfo.setUrl(page.getUrl().toString());
         //获取发布时间
        String type = Jsoup.parse(html.css("div.cn p.ltype").toString()).text();
        String[] split = type.trim().split("\\|");
        String time=null;
        for (String s : split) {
            if (s.matches(".*发布")){
                time=s;
                break;
            }
        }
        jobInfo.setTime(time);
        //将数据保存起来
        page.putField("jobInfo",jobInfo);
    }

    private Site site=Site.me()
           // .setCharset("utf-8")
            .setTimeOut(10*1000)
            .setRetryTimes(3)
            .setRetrySleepTime(3000);

    @Override
    public Site getSite() {
        return site;
    }

    @Autowired
    private SpringDataPipeline springDataPipeline;

    //任务启动后 等待多久执行
    //@Scheduled(initialDelay = 1000,fixedDelay = 1000*100)
    public void process(){
        Spider.create(new JobProcess())
                .addUrl(url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(10)
                .addPipeline(springDataPipeline)
                .run();



    }
}
