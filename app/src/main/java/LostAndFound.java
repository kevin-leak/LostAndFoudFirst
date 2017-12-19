import android.app.Application;
import android.service.autofill.SaveCallback;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;

import java.security.Key;

/**
 * Created by Administrator on 2017/12/19.
 */

public class LostAndFound extends Application{
    private final String ID = "H5AUPg4bLDOxftm6K3rbbuta-gzGzoHsz";
    private final String key = "p7bDIpEGEPHR4VDEQaSgLUWY";
    @Override
    public void onCreate() {
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,ID, key);
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new com.avos.avoscloud.SaveCallback() {
            @Override
            public void done(AVException e) {
                Log.d("LostAndFound","success");

            }
        });
        super.onCreate();
    }
}
