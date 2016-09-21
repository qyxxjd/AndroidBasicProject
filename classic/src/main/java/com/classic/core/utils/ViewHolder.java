package com.classic.core.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * 比较规范独立的的ViewHolder.
 *
 * @author Jack Tony see {http://www.cnblogs.com/tianzhijiexian/p/4157889.html}
 */
public final class ViewHolder {
    private ViewHolder(){}
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        //SparseArray<View>在代码理解上等价于HashMap<Interger, View>
        //SparseArray是Android提供的一个数据结构，旨在提高查询的效率。
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

	/**
	 * 替代findviewById方法
	 */
	public static <T extends View> T find(View view, int id)
	{
		return (T) view.findViewById(id);
	}
}
