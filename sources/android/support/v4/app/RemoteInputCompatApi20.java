package android.support.v4.app;

import android.app.RemoteInput;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.RemoteInputCompatBase;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RequiresApi(20)
class RemoteInputCompatApi20 {
    private static final String EXTRA_DATA_TYPE_RESULTS_DATA = "android.remoteinput.dataTypeResultsData";

    RemoteInputCompatApi20() {
    }

    static RemoteInputCompatBase.RemoteInput[] toCompat(RemoteInput[] srcArray, RemoteInputCompatBase.RemoteInput.Factory factory) {
        if (srcArray == null) {
            return null;
        }
        RemoteInputCompatBase.RemoteInput[] result = factory.newArray(srcArray.length);
        for (int i = 0; i < srcArray.length; i++) {
            RemoteInput src = srcArray[i];
            result[i] = factory.build(src.getResultKey(), src.getLabel(), src.getChoices(), src.getAllowFreeFormInput(), src.getExtras(), (Set<String>) null);
        }
        return result;
    }

    static RemoteInput[] fromCompat(RemoteInputCompatBase.RemoteInput[] srcArray) {
        if (srcArray == null) {
            return null;
        }
        RemoteInput[] result = new RemoteInput[srcArray.length];
        for (int i = 0; i < srcArray.length; i++) {
            RemoteInputCompatBase.RemoteInput src = srcArray[i];
            result[i] = new RemoteInput.Builder(src.getResultKey()).setLabel(src.getLabel()).setChoices(src.getChoices()).setAllowFreeFormInput(src.getAllowFreeFormInput()).addExtras(src.getExtras()).build();
        }
        return result;
    }

    static Bundle getResultsFromIntent(Intent intent) {
        return RemoteInput.getResultsFromIntent(intent);
    }

    static Map<String, Uri> getDataResultsFromIntent(Intent intent, String remoteInputResultKey) {
        String mimeType;
        String uriStr;
        Intent clipDataIntent = getClipDataIntentFromIntent(intent);
        if (clipDataIntent == null) {
            return null;
        }
        Map<String, Uri> results = new HashMap<>();
        for (String key : clipDataIntent.getExtras().keySet()) {
            if (key.startsWith(EXTRA_DATA_TYPE_RESULTS_DATA) && (mimeType = key.substring(EXTRA_DATA_TYPE_RESULTS_DATA.length())) != null && !mimeType.isEmpty() && (uriStr = clipDataIntent.getBundleExtra(key).getString(remoteInputResultKey)) != null && !uriStr.isEmpty()) {
                results.put(mimeType, Uri.parse(uriStr));
            }
        }
        if (results.isEmpty()) {
            results = null;
        }
        return results;
    }

    static void addResultsToIntent(RemoteInputCompatBase.RemoteInput[] remoteInputs, Intent intent, Bundle results) {
        Bundle existingTextResults = getResultsFromIntent(intent);
        if (existingTextResults == null) {
            existingTextResults = results;
        } else {
            existingTextResults.putAll(results);
        }
        for (RemoteInputCompatBase.RemoteInput input : remoteInputs) {
            Map<String, Uri> existingDataResults = getDataResultsFromIntent(intent, input.getResultKey());
            RemoteInput.addResultsToIntent(fromCompat(new RemoteInputCompatBase.RemoteInput[]{input}), intent, existingTextResults);
            if (existingDataResults != null) {
                addDataResultToIntent(input, intent, existingDataResults);
            }
        }
    }

    public static void addDataResultToIntent(RemoteInputCompatBase.RemoteInput remoteInput, Intent intent, Map<String, Uri> results) {
        Intent clipDataIntent = getClipDataIntentFromIntent(intent);
        if (clipDataIntent == null) {
            clipDataIntent = new Intent();
        }
        for (Map.Entry<String, Uri> entry : results.entrySet()) {
            String mimeType = entry.getKey();
            Uri uri = entry.getValue();
            if (mimeType != null) {
                Bundle resultsBundle = clipDataIntent.getBundleExtra(getExtraResultsKeyForData(mimeType));
                if (resultsBundle == null) {
                    resultsBundle = new Bundle();
                }
                resultsBundle.putString(remoteInput.getResultKey(), uri.toString());
                clipDataIntent.putExtra(getExtraResultsKeyForData(mimeType), resultsBundle);
            }
        }
        intent.setClipData(ClipData.newIntent(RemoteInput.RESULTS_CLIP_LABEL, clipDataIntent));
    }

    private static String getExtraResultsKeyForData(String mimeType) {
        return EXTRA_DATA_TYPE_RESULTS_DATA + mimeType;
    }

    private static Intent getClipDataIntentFromIntent(Intent intent) {
        ClipData clipData = intent.getClipData();
        if (clipData == null) {
            return null;
        }
        ClipDescription clipDescription = clipData.getDescription();
        if (!clipDescription.hasMimeType("text/vnd.android.intent") || !clipDescription.getLabel().equals(RemoteInput.RESULTS_CLIP_LABEL)) {
            return null;
        }
        return clipData.getItemAt(0).getIntent();
    }
}
