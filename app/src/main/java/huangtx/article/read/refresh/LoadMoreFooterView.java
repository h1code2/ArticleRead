package huangtx.article.read.refresh;

import android.content.Context;
import android.util.AttributeSet;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;


/**
 * Created by huangtongx on 2017/5/11.
 */

public class LoadMoreFooterView extends android.support.v7.widget.AppCompatTextView implements SwipeTrigger, SwipeLoadMoreTrigger {
    public LoadMoreFooterView(Context context) {
        super(context);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLoadMore() {
        setText("正在加载数据中..");
    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled <= -getHeight()) {
                setText("松开加载更多..");
            } else {
                setText("刷新加载更多..");
            }
        } else {
            setText("正在加载更多..");
        }
    }

    @Override
    public void onRelease() {
        setText("正在加载数据中..");
    }

    @Override
    public void onComplete() {
        setText("加载更多完成");
    }

    @Override
    public void onReset() {
        setText("");
    }
}