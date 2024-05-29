package ls.sakana;

public class SakanaVM {
    private int vm;

    public static native float sq_getfloat_n(int i, int i2);

    public static native int sq_getinteger_n(int i, int i2);

    public static native String sq_getstring_n(int i, int i2);

    public static native int sq_gettop_n(int i);

    public static native int sq_gettype_n(int i, int i2);

    public static native void sq_pushfloat_n(int i, float f);

    public static native void sq_pushinteger_n(int i, int i2);

    public static native void sq_pushnull_n(int i);

    public static native void sq_pushstring_n(int i, String str);

    static int execkglfunc(int v, SakanaFunction f) {
        SakanaVM sqvm = new SakanaVM();
        sqvm.vm = v;
        int ret = f.exec(sqvm);
        sqvm.vm = 0;
        return ret;
    }

    private void checkvm() {
        if (this.vm == 0) {
            throw new IllegalStateException("vm is not running");
        }
    }

    public int sq_gettop() {
        checkvm();
        return sq_gettop_n(this.vm);
    }

    public int sq_gettype(int i) {
        checkvm();
        return sq_gettype_n(this.vm, i);
    }

    public int sq_getinteger(int i) {
        checkvm();
        return sq_getinteger_n(this.vm, i);
    }

    public float sq_getfloat(int i) {
        checkvm();
        return sq_getfloat_n(this.vm, i);
    }

    public String sq_getstring(int i) {
        checkvm();
        return sq_getstring_n(this.vm, i);
    }

    public void sq_pushinteger(int val) {
        checkvm();
        sq_pushinteger_n(this.vm, val);
    }

    public void sq_pushfloat(float val) {
        checkvm();
        sq_pushfloat_n(this.vm, val);
    }

    public void sq_pushstring(String val) {
        checkvm();
        sq_pushstring_n(this.vm, val);
    }

    public void sq_pushnull() {
        checkvm();
        sq_pushnull_n(this.vm);
    }
}
