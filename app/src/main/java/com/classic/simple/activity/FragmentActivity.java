package com.classic.simple.activity;

import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Bind;
import com.classic.simple.R;
import com.classic.simple.fragment.Image2Fragment;
import com.classic.simple.fragment.ImageFragment;

public class FragmentActivity extends AppBaseActivity {

  @Bind(R.id.fragment_layout) RelativeLayout fragmentLayout;

  @Override public int getLayoutResId() {
    return R.layout.activity_fragment;
  }

  private ImageFragment imageFragment;
  private Image2Fragment image2Fragment;
  @Override public void initView() {
    super.initView();
    fragmentLayout.setOnClickListener(this);
    //这里偷懒，使用默认的。实际项目中建议使用ToolBar
    getSupportActionBar().setTitle("Fragment示例");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    imageFragment = new ImageFragment();
    image2Fragment = new Image2Fragment();
    changeFragment(R.id.fragment_layout, imageFragment);
  }

  private boolean isImageFragment = true;
  @Override public void viewClick(View v) {
    if(v.getId() == R.id.fragment_layout){
      isImageFragment = !isImageFragment;
      changeFragment(R.id.fragment_layout,isImageFragment? imageFragment : image2Fragment);
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
