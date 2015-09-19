package com.forcelain.android.awesomerecyclerview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.forcelain.android.awesomerecyclerview.R;
import com.forcelain.android.awesomerecyclerview.data.DataProvider;
import com.forcelain.android.awesomerecyclerview.data.FakeDataProvider;
import com.forcelain.android.awesomerecyclerview.model.Article;
import com.forcelain.android.awesomerecyclerview.view.ArticleAdapter;
import com.forcelain.android.awesomerecyclerview.view.AwesomeLayoutManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        AwesomeLayoutManager layoutManager = new AwesomeLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ArticleAdapter adapter = new ArticleAdapter();
        recyclerView.setAdapter(adapter);
        dataProvider = new FakeDataProvider(this);
        List<Article> articles = dataProvider.getArticles();
        adapter.setArticles(articles);
        recyclerView.setChildDrawingOrderCallback(new RecyclerView.ChildDrawingOrderCallback() {
            @Override
            public int onGetChildDrawingOrder(int childCount, int i) {
                return childCount - i - 1;
            }
        });
    }

    @Override
    protected void onDestroy() {
        dataProvider = null;
        super.onDestroy();
    }
}
