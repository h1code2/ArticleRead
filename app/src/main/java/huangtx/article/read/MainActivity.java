package huangtx.article.read;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import org.polaric.colorful.CActivity;

import java.util.ArrayList;
import java.util.List;

import huangtx.article.read.adapters.TabAdapter;
import huangtx.article.read.fragments.CharacterAnecdotesFragment;
import huangtx.article.read.fragments.ClassicArticleFragment;
import huangtx.article.read.fragments.ClassicQuotationsFragment;
import huangtx.article.read.fragments.EmotionalArticleFragment;
import huangtx.article.read.fragments.EssayEssayFragment;
import huangtx.article.read.fragments.FamilyArticlesFragment;
import huangtx.article.read.fragments.FriendshipArticlesFragment;
import huangtx.article.read.fragments.HealthyHealthFragment;
import huangtx.article.read.fragments.HorrorStoryFragment;
import huangtx.article.read.fragments.InspirationalArticleFragment;
import huangtx.article.read.fragments.LifeTipsFragment;
import huangtx.article.read.fragments.LoveArticlesFragment;
import huangtx.article.read.fragments.OtherArticlesFragment;
import huangtx.article.read.fragments.PhilosophyArticleFragment;

public class MainActivity extends CActivity {

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    private TabLayout tabs;
    private ViewPager viewPager;
    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), mTitle, mFragment);
        viewPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        tabs.setupWithViewPager(viewPager);
        //使用ViewPager的适配器
        tabs.setTabsFromPagerAdapter(adapter);

    }

    //初始化V
    private void initView() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mTitle.add("爱情文章");
        mTitle.add("情感恋爱");
        mTitle.add("亲情文章");
        mTitle.add("友情文章");
        mTitle.add("哲理人生");
        mTitle.add("励志文章");
        mTitle.add("经典语录");
        mTitle.add("经典文章");
        mTitle.add("杂文随笔");
        mTitle.add("人物轶事");
        mTitle.add("恐怖故事");
        mTitle.add("生活小窍门");
        mTitle.add("健康养生");
        mTitle.add("其他文章");

        mFragment.add(new LoveArticlesFragment());
        mFragment.add(new EmotionalArticleFragment());
        mFragment.add(new FamilyArticlesFragment());
        mFragment.add(new FriendshipArticlesFragment());
        mFragment.add(new PhilosophyArticleFragment());
        mFragment.add(new InspirationalArticleFragment());
        mFragment.add(new ClassicQuotationsFragment());
        mFragment.add(new ClassicArticleFragment());
        mFragment.add(new EssayEssayFragment());
        mFragment.add(new CharacterAnecdotesFragment());
        mFragment.add(new HorrorStoryFragment());
        mFragment.add(new LifeTipsFragment());
        mFragment.add(new HealthyHealthFragment());
        mFragment.add(new OtherArticlesFragment());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Snackbar.make(viewPager, "再次点击返回将退出", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
