package com.forcelain.android.awesomerecyclerview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.forcelain.android.awesomerecyclerview.R;
import com.forcelain.android.awesomerecyclerview.data.DataProvider;
import com.forcelain.android.awesomerecyclerview.data.FakeDataProvider;
import com.forcelain.android.awesomerecyclerview.model.Article;
import com.forcelain.android.awesomerecyclerview.view.ArticleAdapter;
import com.forcelain.android.awesomerecyclerview.view.AwesomeLayoutManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DataProvider dataProvider;
    private AwesomeLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new AwesomeLayoutManager(this);
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
        adapter.setItemClickListener(new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int pos) {
                layoutManager.openItem(pos);
            }
        });
        findViewById(R.id.test_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(4);
            }
        });
    }

    @Override
    protected void onDestroy() {
        dataProvider = null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (layoutManager.getOrientation() == AwesomeLayoutManager.Orientation.HORIZONTAL){
            layoutManager.setOrientation(AwesomeLayoutManager.Orientation.VERTICAL);
        } else {
            super.onBackPressed();
        }
    }
}
