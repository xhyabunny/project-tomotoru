package ls.addon.appsflyer;

import android.app.Application;
import android.view.View;
import com.appsflyer.AppsFlyerLib;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SKAppsFlyer {
    public static void startInit(View view, String devkey) {
        AppsFlyerLib.getInstance().startTracking((Application) view.getContext().getApplicationContext(), devkey);
    }

    static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static boolean trackEvent(View view, String name, String params) {
        try {
            AppsFlyerLib.getInstance().trackEvent(view.getContext(), name, toMap(new JSONObject(params)));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static String getAppsFlyerUID(View view) {
        return AppsFlyerLib.getInstance().getAppsFlyerUID(view.getContext());
    }

    public static void setCurrencyCode(String code) {
        AppsFlyerLib.getInstance().setCurrencyCode(code);
    }
}
