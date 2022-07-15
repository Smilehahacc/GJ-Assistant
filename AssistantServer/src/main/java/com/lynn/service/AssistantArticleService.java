package com.lynn.service;

import com.lynn.pojo.AssistantArticle;
import com.lynn.pojo.AssistantUser;

import java.util.List;

/**
 * 〈实现service类的接口〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/23
 * @since 1.0.0
 */

public interface AssistantArticleService {

    List<AssistantArticle> getAllArticle();
    List<AssistantArticle> getHeadArticle();
    List<AssistantArticle> findArticleByTitle(String article_title);
    List<AssistantArticle> findArticleByContent(String article_content);
    Boolean deleteArticle(int article_id);
}