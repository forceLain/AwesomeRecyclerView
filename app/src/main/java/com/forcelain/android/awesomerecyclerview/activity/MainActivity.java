package com.forcelain.android.awesomerecyclerview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.forcelain.android.awesomerecyclerview.R;
import com.forcelain.android.awesomerecyclerview.data.DataProvider;
import com.forcelain.android.awesomerecyclerview.data.FakeDataProvider;
import com.forcelain.android.awesomerecyclerview.model.Article;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataProvider = new FakeDataProvider(this);
        List<Article> articles = dataProvider.getArticles();
    }

    @Override
    protected void onDestroy() {
        dataProvider = null;
        super.onDestroy();
    }
}
