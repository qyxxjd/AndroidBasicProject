package com.classic.simple.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import butterknife.BindView;
import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.classic.simple.R;
import com.classic.simple.model.News;
import com.classic.simple.utils.GlideImageLoad;
import com.classic.simple.utils.NewsDataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 通用适配器示例By ListView
 */
public class ListViewActivity extends AppBaseActivity {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    public static final String URL_SEPARATOR = ";";
    public static final String FORMAT_AUTHOR = "报道人：%s";

    @BindView(R.id.listview_lv) ListView mListView;

    @Override public int getLayoutResId() {
        return R.layout.activity_listview;
    }

    @Override public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        getSupportActionBar().setTitle("通用适配器示例");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView.setAdapter(new MultipleLayoutAdapter(this, R.layout.item_none_picture, NewsDataSource.getNewsList()));
    }

    private final class MultipleLayoutAdapter extends CommonAdapter<News> {

        public MultipleLayoutAdapter(Context context, int layoutResId, List<News> data) {
            super(context, layoutResId, data);
        }

        @Override public int getLayoutResId(News item, int position) {
            int layoutResId = -1;
            switch (item.getNewsType()){
                case News.TYPE_NONE_PICTURE:
                    layoutResId = R.layout.item_none_picture;
                    break;
                case News.TYPE_SINGLE_PICTURE:
                    layoutResId = R.layout.item_single_picture;
                    break;
                case News.TYPE_MULTIPLE_PICTURE:
                    layoutResId = R.layout.item_multiple_picture;
                    break;
            }
            return layoutResId;
        }

        @Override public void onUpdate(BaseAdapterHelper helper, News item, int position) {
            switch (item.getNewsType()){
                case News.TYPE_NONE_PICTURE:
                    helper.setText(R.id.item_none_picture_title, item.getTitle())
                          .setText(R.id.item_none_picture_author,
                                   String.format(Locale.CHINA, FORMAT_AUTHOR, item.getAuthor()))
                          .setText(R.id.item_none_picture_date,
                                   DATE_FORMAT.format(new Date(item.getReleaseTime())))
                          .setText(R.id.item_none_picture_intro, item.getIntro());
                    break;
                case News.TYPE_SINGLE_PICTURE:
                    helper.setText(R.id.item_single_picture_title, item.getTitle())
                          .setText(R.id.item_single_picture_author,
                                   String.format(Locale.CHINA, FORMAT_AUTHOR, item.getAuthor()))
                          .setText(R.id.item_single_picture_date,
                                   DATE_FORMAT.format(new Date(item.getReleaseTime())))
                          .setImageLoad(new GlideImageLoad())
                          .setImageUrl(R.id.item_single_picture_cover,item.getCoverUrl());
                    break;
                case News.TYPE_MULTIPLE_PICTURE:
                    String[] urls = item.getCoverUrl().split(URL_SEPARATOR);
                    helper.setText(R.id.item_multiple_picture_intro, item.getIntro())
                          .setImageLoad(new GlideImageLoad())
                          .setImageUrl(R.id.item_multiple_picture_cover_left,urls[0])
                          .setImageUrl(R.id.item_multiple_picture_cover_right, urls[1]);
                    break;
            }
        }
    }
}
