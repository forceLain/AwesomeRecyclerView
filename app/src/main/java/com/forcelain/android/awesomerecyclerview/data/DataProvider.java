package com.forcelain.android.awesomerecyclerview.data;

import com.forcelain.android.awesomerecyclerview.model.Article;

import java.util.List;

public interface DataProvider {
    List<Article> getArticles();
}
