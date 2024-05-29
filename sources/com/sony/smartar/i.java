package com.sony.smartar;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: GuReflectionUtil */
final class i {
    public static Class<?> a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new h(e);
        }
    }

    public static Class<?> a(String str, boolean z, ClassLoader classLoader) {
        try {
            return Class.forName(str, z, classLoader);
        } catch (ClassNotFoundException e) {
            throw new h(e);
        }
    }

    public static Method a(Class<?> cls, String str, Class<?>... clsArr) {
        try {
            Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (NoSuchMethodException e) {
            Class<? super Object> superclass = cls.getSuperclass();
            if (superclass != null) {
                return a((Class<?>) superclass, str, clsArr);
            }
            throw new h(e);
        }
    }

    public static Field a(Class<?> cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (NoSuchFieldException e) {
            Class<? super Object> superclass = cls.getSuperclass();
            if (superclass != null) {
                return a((Class<?>) superclass, str);
            }
            throw new h(e);
        }
    }

    public static <E> Constructor<E> a(Class<E> cls, Class<?>... clsArr) {
        try {
            Constructor<E> declaredConstructor = cls.getDeclaredConstructor(clsArr);
            declaredConstructor.setAccessible(true);
            return declaredConstructor;
        } catch (NoSuchMethodException e) {
            throw new h(e);
        }
    }

    public static void a(Object obj, Field field, Object obj2) {
        try {
            field.set(obj, obj2);
        } catch (IllegalAccessException e) {
            throw new h(e);
        }
    }

    public static Object a(Object obj, Field field) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new h(e);
        }
    }

    public static Object a(Object obj, Method method, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new h(e);
        } catch (InvocationTargetException e2) {
            throw new h(e2);
        }
    }

    public static Object a(Method method, Object... objArr) {
        return a((Object) null, method, objArr);
    }

    public static <E> E a(Constructor<E> constructor, Object... objArr) {
        try {
            return constructor.newInstance(objArr);
        } catch (InstantiationException e) {
            throw new h(e);
        } catch (IllegalAccessException e2) {
            throw new h(e2);
        } catch (InvocationTargetException e3) {
            throw new h(e3);
        }
    }

    public static Object b(Class<?> cls, String str) {
        return a((Object) null, a(cls, str));
    }
}
