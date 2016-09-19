package com.classic.simple.activity;

import android.os.Bundle;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.classic.core.activity.BaseActivity;

public abstract class AppBaseActivity extends BaseActivity {

    @Override public void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
