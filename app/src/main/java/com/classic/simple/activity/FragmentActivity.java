package com.classic.simple.activity;

import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Bind;
import com.classic.core.activity.BaseActivity;
import com.classic.simple.R;
import com.classic.simple.fragment.Image2Fragment;
import com.classic.simple.fragment.ImageFragment;

public class FragmentActivity extends BaseActivity {

  @Bind(R.id.fragment_layout) RelativeLayout fragmentLayout;

  @Override protected boolean configButterKnife() {
    return true;
  }

  @Override protected boolean configEventBus() {
    return false;
  }

  @Override public int setLayoutResId() {
    return R.layout.activity_fragment;
  }

  @Override public void initView() {
    fragmentLayout.setOnClickListener(this);
    //这里偷懒，使用默认的。实际项目中建议使用ToolBar
    getSupportActionBar().setTitle("Fragment示例");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    changeFragment(R.id.fragment_layout, new ImageFragment());
  }

  private boolean isImageFragment = true;
  @Override public void viewClick(View v) {
    if(v.getId() == R.id.fragment_layout){
      isImageFragment = !isImageFragment;
      changeFragment(R.id.fragment_layout,isImageFragment? new ImageFragment() : new Image2Fragment());
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    if(item.getItemId() == android.R.id.home)
    {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
