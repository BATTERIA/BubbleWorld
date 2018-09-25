package batteria.bubbleworld.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import batteria.bubbleworld.entity.User;

public class SharedHelper {
    private  static  final String TAG = "SharedHelper";
    Context mContext;
    public SharedHelper() {
    }

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void clear(){
        SharedPreferences sp = mContext.getSharedPreferences("UserRecord", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nickname", "");
        editor.putString("password", "");
        editor.putString("uid", "");
        editor.putString("portrait", "");
        editor.putString("motto", "");
        editor.putString("isLogin", "0");
        editor.apply();
    }

    //定义一个保存数据的方法
    public void save(User user) {
        SharedPreferences sp = mContext.getSharedPreferences("UserRecord", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Log.d(TAG, "save: " + user.getUid());
        editor.putString("nickname", user.getNickname());
        editor.putString("password", user.getPassword());
        editor.putString("uid", String.valueOf(user.getUid()));
        editor.putString("portrait", user.getPortrait());
        editor.putString("motto", user.getMotto());
        editor.putString("isLogin", "1");
        editor.apply();
    }

    //修改
    public void revise(User user){
        save(user);
    }

    //定义一个读取SP文件的方法
    public Map<String, String> read() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("UserRecord", Context.MODE_PRIVATE);
        data.put("nickname", sp.getString("nickname", ""));
        data.put("password", sp.getString("password", ""));
        data.put("uid", sp.getString("uid", ""));
        data.put("portrait", sp.getString("portrait", ""));
        data.put("motto", sp.getString("motto", ""));
        data.put("isLogin", sp.getString("isLogin", ""));
        return data;
    }
}
