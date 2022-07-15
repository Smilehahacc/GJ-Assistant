package com.lynn.service.Impl;

/**
 * 〈由注解标记的一个service类〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/17
 * @since 1.0.0
 */

import com.lynn.dao.AssistantArticleMapper;
import com.lynn.dao.AssistantUserMapper;
import com.lynn.pojo.AssistantArticle;
import com.lynn.service.AssistantArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssistantArticleImpl implements AssistantArticleService {


    /**
     *创建数据库操作接口对象
     */
    @Autowired
    AssistantArticleMapper assistantArticleMapper;

    /**
     * 获取所有文章
     */
    @Override
    public List<AssistantArticle> getAllArticle() {
        return assistantArticleMapper.getAllArticle();
    }

    /**
     * 获取头部文章
     */
    @Override
    public List<AssistantArticle> getHeadArticle() {
        return assistantArticleMapper.getHeadArticle();
    }

    /**
     * 通过标题查找文章
     */
    @Override
    public List<AssistantArticle> findArticleByTitle(String article_title) {
        return assistantArticleMapper.findArticleByTitle(article_title);
    }

    /**
     * 通过内容查找文章
     */
    @Override
    public List<AssistantArticle> findArticleByContent(String article_content) {
        return assistantArticleMapper.findArticleByContent(article_content);
    }

    /**
     * 删除文章
     */
    @Override
    public Boolean deleteArticle(int article_id) {
        int result = assistantArticleMapper.deleteArticle(article_id);
        return result==1;
    }

}