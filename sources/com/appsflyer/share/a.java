package com.appsflyer.share;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

final class a {

    /* renamed from: ˋ  reason: contains not printable characters */
    private String f252;

    a() {
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final void m217(String str) {
        this.f252 = str;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final void m216(Context context) {
        if (this.f252 != null) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.f252)).setFlags(268435456));
        }
    }
}
