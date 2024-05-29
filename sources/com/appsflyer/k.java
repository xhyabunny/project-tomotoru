package com.appsflyer;

final class k {

    /* renamed from: ˋ  reason: contains not printable characters */
    private boolean f167;

    /* renamed from: ˏ  reason: contains not printable characters */
    private b f168;

    /* renamed from: ॱ  reason: contains not printable characters */
    private String f169;

    k(b bVar, String str, boolean z) {
        this.f168 = bVar;
        this.f169 = str;
        this.f167 = z;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final String m155() {
        return this.f169;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final boolean m154() {
        return this.f167;
    }

    public final String toString() {
        return String.format("%s,%s", new Object[]{this.f169, Boolean.valueOf(this.f167)});
    }

    enum b {
        GOOGLE(0),
        AMAZON(1);
        

        /* renamed from: ˋ  reason: contains not printable characters */
        private int f173;

        private b(int i) {
            this.f173 = i;
        }

        public final String toString() {
            return String.valueOf(this.f173);
        }
    }
}
