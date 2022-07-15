package com.lynn.controller;

import com.lynn.pojo.AssistantArticle;
import com.lynn.service.AssistantArticleService;
import com.lynn.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AssistantArticleController {

    /**
     *创建数据库操作接口对象
     */
    @Autowired
    AssistantArticleService assistantArticleService;

    /**
     *通过用户名来获取用户所有信息
     */
    @RequestMapping(value ="/getAllArticle")
    public List<AssistantArticle> getAllArticle(){
        return assistantArticleService.getAllArticle();
    }

    /**
     *通过用户名来获取用户所有信息
     */
    @RequestMapping(value ="/getHeadArticle")
    public List<AssistantArticle> getHeadArticle(){
        return assistantArticleService.getHeadArticle();
    }

    /**
     *通过标题查找文章
     */
    @RequestMapping(value ="/findArticleByTitle")
    public List<AssistantArticle> findArticleByTitle(@RequestParam("article_title") String article_title) {
        return assistantArticleService.findArticleByTitle(article_title);
    }

    /**
     *通过内容查找文章
     */
    @RequestMapping(value ="/findArticleByContent")
    public List<AssistantArticle> findArticleByContent(@RequestParam("article_content") String article_content){
        return assistantArticleService.findArticleByContent(article_content);
    }

    /**
     *删除文章
     */
    @RequestMapping(value ="/deleteArticle")
    public String deleteArticle(@RequestParam("article_id") int article_id) {
        if(assistantArticleService.deleteArticle(article_id)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    /**
     *更新文章数据（手动）
     */
    @RequestMapping(value ="/updateArticle")
    public String updateArticle() {
        String result = HttpClientUtil.getInstance().getHttpResponse("updateArticle");
        if(!result.equals("ERROR")) {
            System.out.println("手动更新文章数据成功！");
            return result;
        } else {
            System.out.println("手动更新文章数据失败！");
            return "ERROR";
        }
    }

    /**
     *更新文章数据（自动）
     */
    @RequestMapping(value ="/updateArticleAutomatic")
    public String updateArticleAutomatic(@RequestParam("interval") int interval) {
        String result = HttpClientUtil.getInstance().getHttpResponse("updateArticleAutomatic"+"?interval="+interval);
        if(!result.equals("ERROR")) {
            System.out.println("自动更新文章数据设置成功！");
            return "SUCCESS";
        } else {
            System.out.println("自动更新文章数据设置失败！");
            return "ERROR";
        }
    }

}