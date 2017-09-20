package huangtx.article.read;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import org.polaric.colorful.CActivity;
import org.polaric.colorful.ColorPickerDialog;
import org.polaric.colorful.Colorful;

/**
 * Created by huangtongx on 2017/5/19.
 */

public class SettingActivity extends CActivity {

    private Switch mSwitch;
    private LinearLayout mLinear;
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "感谢您的使用", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initView();

    }

    private void initView() {
        final SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        flag = sp.getBoolean("check", false);
        mSwitch = (Switch) findViewById(R.id.mSwitch);
        if (flag) {
            mSwitch.setChecked(true);
        } else {
            mSwitch.setChecked(false);
        }
        mLinear = (LinearLayout) findViewById(R.id.mLinear1);
        mLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog dialog = new ColorPickerDialog(SettingActivity.this);
                dialog.setOnColorSelectedListener(new ColorPickerDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(Colorful.ThemeColor color) {
                        //TODO: Do something with the color
                        Colorful.config(SettingActivity.this)
                                .primaryColor(color)
                                .accentColor(color)
                                .translucent(false)
//                                .dark(flag)
                                .apply();
                    }
                });
                dialog.show();
            }
        });
        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Colorful.config(SettingActivity.this)
                        .translucent(false)
                        .dark(!flag)
                        .apply();
                SharedPreferences.Editor ed = sp.edit();
                ed.putBoolean("check", !flag);
                ed.commit();
            }
        });

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
