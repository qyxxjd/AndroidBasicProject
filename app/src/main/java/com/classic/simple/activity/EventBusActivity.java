package com.classic.simple.activity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import com.classic.core.utils.DataUtil;
import com.classic.core.utils.DateUtil;
import com.classic.simple.R;
import com.classic.simple.event.EventUtil;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

/**
 * AndroidEventBus示例
 */
public class EventBusActivity extends AppBaseActivity {
  public static final String EVENT_TAG = "classic";

  @Bind(R.id.eventbus_button) Button eventbusButton;
  @Bind(R.id.eventbus_content) TextView eventbusContent;

  @Override public boolean configEventBus() {
    return true;
  }

  @Override public int getLayoutResId() {
    return R.layout.activity_eventbus;
  }

  @Override public void initView() {
    super.initView();
    eventbusButton.setOnClickListener(this);
    //这里偷懒，使用默认的。实际项目中建议使用ToolBar
    getSupportActionBar().setTitle("AndroidEventBus示例");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override public void viewClick(View v) {
    if(v.getId() == R.id.eventbus_button){
      //发布一个事件
      EventUtil.post(DateUtil.formatDate("HH:mm:ss.SSS", System.currentTimeMillis()), EVENT_TAG);
    }
  }

  /**
   * 接收事件
   * 当用户post事件时,只有指定了EVENT_TAG的事件才会触发该函数,
   * ThreadMode.MAIN : 默认方法，执行在UI线程，可省略不写
   * ThreadMode.ASYNC: 执行在一个独立的线程
   * ThreadMode.POST : post函数在哪个线程执行,该函数就执行在哪个线程
   */
  @Subscriber(tag = EVENT_TAG,mode = ThreadMode.MAIN)
  public void updateUI(String params){
    final StringBuilder sb = new StringBuilder(DataUtil.isEmpty(eventbusContent.getText().toString()) ? "" : eventbusContent.getText().toString());
    sb.append("\n").append("收到事件 --> ").append(params);
    eventbusContent.setText(sb.toString());
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
