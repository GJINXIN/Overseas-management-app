package com.example.liuxuetong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class nsearch extends AppCompatActivity {
    private String[] mStrs = {"房屋", "车", "求合租", "哪里有中国饭菜","哈佛"};
    private SearchView mSearchView;
    private ListView lListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nsearch);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        lListView = (ListView) findViewById(R.id.listView);
        lListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrs));
        lListView.setTextFilterEnabled(true);
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new Intent(nsearch.this,searchlist.class);
                String search=query;
                intent.putExtra("search",search);
                startActivity(intent);
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {

                if (!TextUtils.isEmpty(newText)){
                    lListView.setFilterText(newText);

                }else{

                    lListView.clearTextFilter();
                }
                return false;
            }

        });

    }


}
