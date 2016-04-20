package com.classic.simple.activity;

import android.view.MenuItem;
import android.widget.ListView;
import butterknife.Bind;
import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.classic.simple.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用适配器示例By ListView
 */
public class ListViewActivity extends AppBaseActivity {
    @Bind(R.id.listview_lv) ListView mListView;
    private List<Integer> mTypeList;

    @Override public int getLayoutResId() {
        return R.layout.activity_listview;
    }

    /**
     * 方法执行顺序：
     * initData() --> initView() --> register()
     */
    @Override public void initData() {
        super.initData();
        mTypeList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mTypeList.add(i % 3 == 0 ? 1 : 0);
        }
    }

    /**
     * 方法执行顺序：
     * initData() --> initView() --> register()
     */
    @Override public void initView() {
        super.initView();
        getSupportActionBar().setTitle("通用适配器示例");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView.setAdapter(
                new CommonAdapter<Integer>(activity, R.layout.activity_listview_item, mTypeList) {
                    @Override public int getLayoutResId(Integer item) {
                        return item == 1
                               ? R.layout.activity_listview_item2
                               : R.layout.activity_listview_item;
                    }


                    @Override public void onUpdate(BaseAdapterHelper helper, Integer item) {
                    }
                });
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
