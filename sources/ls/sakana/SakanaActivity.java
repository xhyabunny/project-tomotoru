package ls.sakana;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class SakanaActivity extends Activity implements SakanaViewListener {
    static SakanaView savedview;
    protected ViewGroup mainpanel;
    protected SakanaView view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("SakanaActivity.onCreate(). view=" + this.view + " save=" + savedInstanceState);
        if (savedview != null) {
            System.out.println("SakanaView restart.");
            this.view = savedview;
            savedview = null;
            if (this.mainpanel == null || this.mainpanel.getRootView() != getWindow().getDecorView().getRootView() || this.view.getParent() != this.mainpanel) {
                if (this.view.getParent() != null) {
                    ((ViewGroup) this.view.getParent()).removeView(this.view);
                }
                createMainPanel();
                return;
            }
            return;
        }
        this.view = new SakanaView(this);
        this.view.addSakanaViewListener(this);
        createMainPanel();
        this.view.startSakanaGL(getSakanaArguments());
    }

    /* access modifiers changed from: protected */
    public String[] getSakanaArguments() {
        return new String[0];
    }

    /* access modifiers changed from: protected */
    public void createMainPanel() {
        FrameLayout fl = new FrameLayout(getApplicationContext());
        setContentView(fl);
        fl.addView(this.view, new FrameLayout.LayoutParams(-1, -1));
        this.mainpanel = fl;
    }

    public void onDestroy() {
        System.out.println("SakanaActivity.onDestroy(). view=" + this.view);
        if (this.view != null) {
            if (savedview == this.view) {
                this.mainpanel.removeView(this.view);
            } else {
                this.view.stopSakanaGL();
                this.view = null;
                finish();
            }
        }
        super.onDestroy();
    }

    public void onPause() {
        System.out.println("SakanaActivity.onPause(). view=" + this.view);
        if (this.view != null) {
            this.view.onPause();
        }
        super.onPause();
    }

    public void onStop() {
        System.out.println("SakanaActivity.onStop(). view=" + this.view);
        if (this.view != null) {
            this.view.onStop();
        }
        super.onStop();
    }

    public void onRestart() {
        System.out.println("SakanaActivity.onRestart(). view=" + this.view + " ... clear savedview!");
        savedview = null;
        super.onRestart();
    }

    public void onResume() {
        System.out.println("SakanaActivity.onResume(). view=" + this.view);
        if (this.view != null) {
            this.view.onResume();
        }
        super.onResume();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("SakanaActivity.onSaveInstanceState(). view=" + this.view);
        savedview = this.view;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.view == null || !this.view.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        System.out.println("SakanaActivity.onConfigurationChanged(). view=" + this.view);
        if (this.view != null) {
            this.mainpanel.invalidate();
            this.view.onConfigurationChanged(newConfig);
        }
        super.onConfigurationChanged(newConfig);
    }

    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getAction() == 0) {
            switch (e.getKeyCode()) {
                case 4:
                    if (this.view != null) {
                        this.view.quitRequested();
                        return true;
                    }
                    break;
            }
        }
        return super.dispatchKeyEvent(e);
    }

    public void skFinished(SakanaView v) {
        runOnUiThread(new Runnable() {
            public void run() {
                SakanaActivity.this.finish();
            }
        });
    }

    public void skOnPause(SakanaView v) {
    }

    public void skOnResume(SakanaView v) {
    }
}
