package huangtx.article.read;

import android.app.Application;
import com.tencent.smtt.sdk.TbsDownloader;

import org.polaric.colorful.Colorful;


/**
 * Created by huangtongx on 2017/5/19.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TbsDownloader.needDownload(getApplicationContext(), false);
        Colorful.init(this);
    }
}
