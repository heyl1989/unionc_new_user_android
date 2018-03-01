package cn.v1.unionc_user.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by qy on 2018/2/26.
 */

public class ObserverScrollView extends ScrollView {

    private OnScrollChangedListener onScrollChangedListener;

    public ObserverScrollView(Context context) {
        super(context);
    }

    public ObserverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener){
        this.onScrollChangedListener = onScrollChangedListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(null != onScrollChangedListener){
            onScrollChangedListener.onScrollChanged(this,l,t,oldl,oldt);
        }
    }

    public interface OnScrollChangedListener{
        void onScrollChanged(ObserverScrollView scrollView ,int l, int t, int oldl, int oldt);
    }
}
