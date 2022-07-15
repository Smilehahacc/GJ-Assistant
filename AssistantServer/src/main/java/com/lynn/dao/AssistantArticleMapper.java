package com.lynn.dao;

import com.lynn.pojo.AssistantArticle;

import java.util.List;

public interface AssistantArticleMapper {
    /**
     * 获取所有文章
     * @return Result<List>
     */
    List<AssistantArticle> getAllArticle();

    /**
     * 获取头部（轮播图）文章
     * @return Result<List>
     */
    List<AssistantArticle> getHeadArticle();

    /**
     * 通过标题查找文章
     * @return Result<List>
     */
    List<AssistantArticle> findArticleByTitle(String article_title);

    /**
     * 通过内容查找文章
     * @return Result<List>
     */
    List<AssistantArticle> findArticleByContent(String article_content);

    /**
     * 删除文章
     * @return Result<int>
     */
    int deleteArticle(int article_id);
}
