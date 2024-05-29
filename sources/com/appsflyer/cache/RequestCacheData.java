package com.appsflyer.cache;

import java.util.Scanner;

public class RequestCacheData {

    /* renamed from: ˊ  reason: contains not printable characters */
    private String f109;

    /* renamed from: ˋ  reason: contains not printable characters */
    private String f110;

    /* renamed from: ˏ  reason: contains not printable characters */
    private String f111;

    /* renamed from: ॱ  reason: contains not printable characters */
    private String f112;

    public RequestCacheData(String str, String str2, String str3) {
        this.f111 = str;
        this.f109 = str2;
        this.f110 = str3;
    }

    public RequestCacheData(char[] cArr) {
        Scanner scanner = new Scanner(new String(cArr));
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (nextLine.startsWith("url=")) {
                this.f111 = nextLine.substring(4).trim();
            } else if (nextLine.startsWith("version=")) {
                this.f110 = nextLine.substring(8).trim();
            } else if (nextLine.startsWith("data=")) {
                this.f109 = nextLine.substring(5).trim();
            }
        }
        scanner.close();
    }

    public String getVersion() {
        return this.f110;
    }

    public void setVersion(String str) {
        this.f110 = str;
    }

    public String getPostData() {
        return this.f109;
    }

    public void setPostData(String str) {
        this.f109 = str;
    }

    public String getRequestURL() {
        return this.f111;
    }

    public void setRequestURL(String str) {
        this.f111 = str;
    }

    public String getCacheKey() {
        return this.f112;
    }

    public void setCacheKey(String str) {
        this.f112 = str;
    }
}
