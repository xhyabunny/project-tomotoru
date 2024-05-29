package ls.sakana;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.widget.ExploreByTouchHelper;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import java.net.URLEncoder;

public class SakanaWebView {
    private static final String SK_TAG = "SK";
    int h = SakanaView.ACTION_PERFORMED;
    Handler handler;
    JSInterface jsif = new JSInterface();
    int loading;
    SakanaView skview = null;
    int type;
    int w = SakanaView.ACTION_PERFORMED;
    WebView webview;
    int x = 0;
    int y = 0;

    public SakanaWebView(SakanaView v) {
        this.skview = v;
        this.handler = v.handler;
    }

    private int setType(int t) {
        return new IRunnable() {
            public int irun() {
                if (SakanaWebView.this.webview != null) {
                    ((ViewGroup) SakanaWebView.this.webview.getParent()).removeView(SakanaWebView.this.webview);
                }
                SakanaWebView.this.webview = new WebView(SakanaWebView.this.skview.getContext());
                SakanaWebView.this.webview.setWebViewClient(new MyWebViewClient());
                SakanaWebView.this.webview.getSettings().setJavaScriptEnabled(true);
                SakanaWebView.this.webview.setWebChromeClient(new MyWebChromeClient());
                return 0;
            }
        }.postAndWait();
    }

    private int show() {
        return new IRunnable() {
            public int irun() {
                if (SakanaWebView.this.webview == null) {
                    return 0;
                }
                if (SakanaWebView.this.webview.getParent() != null) {
                    ((ViewGroup) SakanaWebView.this.webview.getParent()).removeView(SakanaWebView.this.webview);
                }
                SakanaWebView.this.webview.setTranslationX((float) SakanaWebView.this.x);
                SakanaWebView.this.webview.setTranslationY((float) SakanaWebView.this.y);
                ((ViewGroup) SakanaWebView.this.skview.getParent()).addView(SakanaWebView.this.webview, new FrameLayout.LayoutParams(SakanaWebView.this.w, SakanaWebView.this.h));
                return 0;
            }
        }.postAndWait();
    }

    private int hide() {
        return new IRunnable() {
            public int irun() {
                if (SakanaWebView.this.webview == null) {
                    return 0;
                }
                SakanaWebView.this.webview.setVisibility(4);
                return 0;
            }
        }.postAndWait();
    }

    private int close() {
        return new IRunnable() {
            public int irun() {
                if (SakanaWebView.this.webview == null) {
                    return 0;
                }
                ((ViewGroup) SakanaWebView.this.webview.getParent()).removeView(SakanaWebView.this.webview);
                SakanaWebView.this.webview = null;
                return 0;
            }
        }.postAndWait();
    }

    private int setRect(int _x, int _y, int _w, int _h) {
        final int i = _x;
        final int i2 = _y;
        final int i3 = _w;
        final int i4 = _h;
        return new IRunnable() {
            public int irun() {
                SakanaWebView.this.x = i;
                SakanaWebView.this.y = i2;
                SakanaWebView.this.w = i3;
                SakanaWebView.this.h = i4;
                return 0;
            }
        }.postAndWait();
    }

    private int openURL(final String url, String headers) {
        return new IRunnable() {
            public int irun() {
                if (SakanaWebView.this.webview == null) {
                    return 0;
                }
                SakanaWebView.this.loading = 1;
                SakanaWebView.this.webview.loadUrl(url);
                return 0;
            }
        }.postAndWait();
    }

    private int isLoading() {
        return this.loading;
    }

    private String eval(final String jscode) {
        new IRunnable() {
            public int irun() {
                SakanaWebView.this.jsif.retobj = null;
                try {
                    SakanaWebView.this.webview.loadUrl("javascript:setResult(" + URLEncoder.encode(jscode) + ")");
                    return 0;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SakanaWebView.this.jsif.retobj = null;
                    return 0;
                }
            }
        }.postAndWait();
        Object o = this.jsif.retobj;
        this.jsif.retobj = null;
        if (o == null) {
            return "null";
        }
        if ((o instanceof Integer) || (o instanceof Float) || (o instanceof Boolean)) {
            return o.toString();
        }
        try {
            return "\"" + URLEncoder.encode(o.toString(), "UTF-8") + "\"";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "\"URLEncoder error\"";
        }
    }

    abstract class IRunnable implements Runnable {
        boolean end = false;
        int ret;

        public abstract int irun();

        IRunnable() {
        }

        public final void run() {
            try {
                this.ret = irun();
            } catch (Exception ex) {
                ex.printStackTrace();
                this.ret = ExploreByTouchHelper.INVALID_ID;
            }
            synchronized (this) {
                this.end = true;
                notifyAll();
            }
        }

        public synchronized int postAndWait() {
            int i;
            SakanaWebView.this.handler.post(this);
            while (!this.end) {
                try {
                    wait();
                } catch (Exception e) {
                    e.printStackTrace();
                    i = -1;
                }
            }
            i = this.ret;
            return i;
        }
    }

    class JSInterface {
        Object retobj;

        JSInterface() {
        }

        public void setResult(Object o) {
            this.retobj = o;
        }
    }

    class MyWebViewClient extends WebViewClient {
        MyWebViewClient() {
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            SakanaWebView.this.webview.addJavascriptInterface(SakanaWebView.this.jsif, "android");
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            SakanaWebView.this.loading = 0;
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        MyWebChromeClient() {
        }

        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            new AlertDialog.Builder(SakanaWebView.this.skview.getContext()).setTitle("confirm").setMessage(message).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            }).create().show();
            return true;
        }
    }
}
