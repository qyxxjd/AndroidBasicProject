package com.classic.simple.activity;

import android.view.MenuItem;
import butterknife.Bind;
import com.classic.core.activity.BaseActivity;
import com.classic.core.download.core.DownloadManagerPro;
import com.classic.core.download.report.listener.DownloadManagerListener;
import com.classic.core.log.L;
import com.classic.core.utils.NetworkUtil;
import com.classic.core.utils.SDcardUtil;
import com.classic.core.utils.ToastUtil;
import com.classic.simple.R;
import is.arontibo.library.ElasticDownloadView;
import java.io.IOException;

/**
 * 下载管理器示例
 */
public class DownloadActivity extends BaseActivity {
  @Bind(R.id.download_view)
  ElasticDownloadView downloadView;
  private DownloadManagerPro downloadManagerPro;
  private int currTaskId;

  @Override protected boolean configButterKnife() {
    return true;
  }

  @Override protected boolean configEventBus() {
    return true;
  }

  @Override public int setLayoutResId() {
    return R.layout.activity_download;
  }

  @Override public void initView() {
    //这里偷懒，使用默认的。实际项目中建议使用ToolBar
    getSupportActionBar().setTitle("下载管理器示例");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

  }

  @Override public void initData() {
    final String imgUrl = "http://www.hy345.com/data/attachment/album/201504/04/231626t7tgggmm7a2oudfv.jpg";
    downloadManagerPro = new DownloadManagerPro(this);
    downloadManagerPro.init(SDcardUtil.getImageDir(), 3, new DownloadManagerListener() {
      @Override public void OnDownloadStarted(long taskId) {
        if(taskId == currTaskId){
          runOnUiThread(new Runnable() {
            @Override public void run() {
              downloadView.startIntro();
            }
          });
        }
      }

      @Override public void OnDownloadPaused(long taskId) {

      }

      @Override public void onDownloadProcess(long taskId,final double percent, long downloadedLength) {
        if(taskId == currTaskId){
          runOnUiThread(new Runnable() {
            @Override public void run() {
              downloadView.setProgress((int) percent);
            }
          });
        }
      }

      @Override public void OnDownloadFinished(long taskId) {
        if(taskId == currTaskId){
          runOnUiThread(new Runnable() {
            @Override public void run() {
              downloadView.success();
              L.d("下载完成");
            }
          });
        }
      }

      @Override public void OnDownloadRebuildStart(long taskId) {

      }

      @Override public void OnDownloadRebuildFinished(long taskId) {

      }

      @Override public void OnDownloadCompleted(long taskId) {

      }

      @Override public void connectionLost(long taskId) {
        if(taskId == currTaskId){
          runOnUiThread(new Runnable() {
            @Override public void run() {
              downloadView.fail();
            }
          });
        }
      }
    });
    currTaskId = downloadManagerPro.addTask("temp.jpg",imgUrl,true,false);
    if(NetworkUtil.isNetworkConnected(this)){
      try {
        downloadManagerPro.startDownload(currTaskId);
      } catch (IOException e) {
        e.printStackTrace();
        L.e(e.getMessage());
      }
    }else{
      ToastUtil.showToast(this,"请检查网络连接");
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
