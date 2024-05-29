package com.appsflyer;

import java.util.Map;

public interface ConversionDataListener {
    void onConversionDataLoaded(Map<String, String> map);

    void onConversionFailure(String str);
}
