package huangtx.article.read.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import huangtx.article.read.R;
import huangtx.article.read.adapters.PublicInfoAdapter;
import huangtx.article.read.customize.ListInfo;

/**
 * Created by huangtongx on 2017/5/19.
 */

public class LifeTipsFragment extends Fragment {
//    生活小窍门
private View view;
    private RecyclerView mRecyclerView;
    private String URL = "http://www.idduu.com/category/shenghuo";
    private SwipeToLoadLayout swipeToLoadLayout;
    private String NextPage = "";
    private PublicInfoAdapter mAdapter;

    private List<ListInfo> list = new ArrayList<>();//item的信息

    private int lastPosition = 0;
    private int lastOffset = 0;
    private boolean flag = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //防止加载两次..
        if (view == null) {
            view = inflater.inflate(R.layout.main_fragment, null);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));//万能分割线
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//这里用线性显示 类似于listview

        //监听RecyclerView滚动状态
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeToLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setRefreshing(false);
                    }
                }, 2000);
                if (flag) {
                    loadData(URL);
                }
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                swipeToLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                }, 2000);
                flag = true;
                loadData(NextPage);
            }
        });

        autoRefresh();

    }

    /**
     * 记录RecyclerView当前位置
     */
    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    private void loadData(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements ele1 = doc.select("div.idduuList ul li");
                    Element ele2 = doc.select("nav.pagination div.nav-links a").last();
//                    System.out.print("成功打印：" + doc);
                    NextPage = ele2.absUrl("href");
                    for (Element e : ele1) {
                        ListInfo info = new ListInfo();
                        info.setType(e.select("a span.cap").text());
                        info.setTitle(e.select("a h2").text());
                        info.setSummary(e.select("a p").text());
                        info.setUrl(e.select("a").attr("href"));
                        list.add(info);
                    }
                } catch (Exception e) {
                    System.out.print("错误：" + e.toString());
                }

                handler.sendMessage(msg);
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                mAdapter = new PublicInfoAdapter(getContext(), list);
                mRecyclerView.setAdapter(mAdapter);
                if (flag) {
                    scrollToPosition();
                }
            } catch (Exception e) {
                Toasty.warning(getContext(), "异常：" + e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    };

    private void autoRefresh() {//自动更新
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        loadData(URL);
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if (mRecyclerView.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }
}

