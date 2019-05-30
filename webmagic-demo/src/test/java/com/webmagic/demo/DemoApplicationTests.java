package com.webmagic.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests implements PageProcessor {

    @Test
    public void contextLoads() {
        //创建下载器
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        //设置代理服务器信息
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("222.135.28.228",8060)));

        //执行爬虫
        Spider.create(new DemoApplicationTests())
                //设置爬取页面
                .addUrl("http://ip.360.cn/IPShare/info")
                //设置下载器
                .setDownloader(httpClientDownloader)
                //开启5个线程抓取
               // .thread(5)
              //  .addPipeline(new FilePipeline("C:\\Users\\shuoyu\\Desktop\\images"))
                //启动爬虫
                .run();
    }
    @Test
    public void test(){
        String s="我赛|尼玛|傻逼";
        String[] split = s.split("\\|");
        for (String s1 : split) {
            System.out.println(s1);

        }
    }

    //页面解析
    @Override
    public void process(Page page) {
        System.out.println(page.getHtml().toString());
      /*  //解析page 返回数据 并把解析结果放到resultItems
        page.putField("ul",page.getHtml().css("div.xing_vb ul a").all());
        //获取连接
     *//*   page.addTargetRequests(page.getHtml().css("div.xing_vb ul a").links().regex(".*5.*").all());
        page.putField("url",page.getHtml().css("div.vodh h2").all());*//*
         page.addTargetRequest("https://search.51job.com/list/000000,000000,0000,01,9,99,java,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=");
         page.addTargetRequest("https://search.51job.com/list/000000,000000,0000,01,9,99,java,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=");
         page.addTargetRequest("https://search.51job.com/list/000000,000000,0000,01,9,99,java,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=");*/
    }

    private Site site=Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(1000*10);
    //
    @Override
    public Site getSite() {
        return site;
    }
}
