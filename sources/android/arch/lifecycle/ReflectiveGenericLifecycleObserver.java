package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ReflectiveGenericLifecycleObserver implements GenericLifecycleObserver {
    private static final int CALL_TYPE_NO_ARG = 0;
    private static final int CALL_TYPE_PROVIDER = 1;
    private static final int CALL_TYPE_PROVIDER_WITH_EVENT = 2;
    static final Map<Class, CallbackInfo> sInfoCache = new HashMap();
    private final CallbackInfo mInfo = getInfo(this.mWrapped.getClass());
    private final Object mWrapped;

    ReflectiveGenericLifecycleObserver(Object wrapped) {
        this.mWrapped = wrapped;
    }

    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        invokeCallbacks(this.mInfo, source, event);
    }

    private void invokeMethodsForEvent(List<MethodReference> handlers, LifecycleOwner source, Lifecycle.Event event) {
        if (handlers != null) {
            for (int i = handlers.size() - 1; i >= 0; i--) {
                invokeCallback(handlers.get(i), source, event);
            }
        }
    }

    private void invokeCallbacks(CallbackInfo info, LifecycleOwner source, Lifecycle.Event event) {
        invokeMethodsForEvent(info.mEventToHandlers.get(event), source, event);
        invokeMethodsForEvent(info.mEventToHandlers.get(Lifecycle.Event.ON_ANY), source, event);
    }

    private void invokeCallback(MethodReference reference, LifecycleOwner source, Lifecycle.Event event) {
        try {
            switch (reference.mCallType) {
                case 0:
                    reference.mMethod.invoke(this.mWrapped, new Object[0]);
                    return;
                case 1:
                    reference.mMethod.invoke(this.mWrapped, new Object[]{source});
                    return;
                case 2:
                    reference.mMethod.invoke(this.mWrapped, new Object[]{source, event});
                    return;
                default:
                    return;
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to call observer method", e.getCause());
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static CallbackInfo getInfo(Class klass) {
        CallbackInfo existing = sInfoCache.get(klass);
        if (existing != null) {
            return existing;
        }
        return createInfo(klass);
    }

    private static void verifyAndPutHandler(Map<MethodReference, Lifecycle.Event> handlers, MethodReference newHandler, Lifecycle.Event newEvent, Class klass) {
        Lifecycle.Event event = handlers.get(newHandler);
        if (event != null && newEvent != event) {
            throw new IllegalArgumentException("Method " + newHandler.mMethod.getName() + " in " + klass.getName() + " already declared with different @OnLifecycleEvent value: previous" + " value " + event + ", new value " + newEvent);
        } else if (event == null) {
            handlers.put(newHandler, newEvent);
        }
    }

    private static CallbackInfo createInfo(Class klass) {
        CallbackInfo superInfo;
        Class superclass = klass.getSuperclass();
        Map<MethodReference, Lifecycle.Event> handlerToEvent = new HashMap<>();
        if (!(superclass == null || (superInfo = getInfo(superclass)) == null)) {
            handlerToEvent.putAll(superInfo.mHandlerToEvent);
        }
        Method[] methods = klass.getDeclaredMethods();
        Class[] interfaces = klass.getInterfaces();
        int length = interfaces.length;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= length) {
                break;
            }
            for (Map.Entry<MethodReference, Lifecycle.Event> entry : getInfo(interfaces[i2]).mHandlerToEvent.entrySet()) {
                verifyAndPutHandler(handlerToEvent, entry.getKey(), entry.getValue(), klass);
            }
            i = i2 + 1;
        }
        int length2 = methods.length;
        for (int i3 = 0; i3 < length2; i3++) {
            Method method = methods[i3];
            OnLifecycleEvent annotation = (OnLifecycleEvent) method.getAnnotation(OnLifecycleEvent.class);
            if (annotation != null) {
                Class<?>[] params = method.getParameterTypes();
                int callType = 0;
                if (params.length > 0) {
                    callType = 1;
                    if (!params[0].isAssignableFrom(LifecycleOwner.class)) {
                        throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                    }
                }
                Lifecycle.Event event = annotation.value();
                if (params.length > 1) {
                    callType = 2;
                    if (!params[1].isAssignableFrom(Lifecycle.Event.class)) {
                        throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                    } else if (event != Lifecycle.Event.ON_ANY) {
                        throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
                    }
                }
                if (params.length > 2) {
                    throw new IllegalArgumentException("cannot have more than 2 params");
                }
                verifyAndPutHandler(handlerToEvent, new MethodReference(callType, method), event, klass);
            }
        }
        CallbackInfo info = new CallbackInfo(handlerToEvent);
        sInfoCache.put(klass, info);
        return info;
    }

    static class CallbackInfo {
        final Map<Lifecycle.Event, List<MethodReference>> mEventToHandlers = new HashMap();
        final Map<MethodReference, Lifecycle.Event> mHandlerToEvent;

        CallbackInfo(Map<MethodReference, Lifecycle.Event> handlerToEvent) {
            this.mHandlerToEvent = handlerToEvent;
            for (Map.Entry<MethodReference, Lifecycle.Event> entry : handlerToEvent.entrySet()) {
                Lifecycle.Event event = entry.getValue();
                List<MethodReference> methodReferences = this.mEventToHandlers.get(event);
                if (methodReferences == null) {
                    methodReferences = new ArrayList<>();
                    this.mEventToHandlers.put(event, methodReferences);
                }
                methodReferences.add(entry.getKey());
            }
        }
    }

    static class MethodReference {
        final int mCallType;
        final Method mMethod;

        MethodReference(int callType, Method method) {
            this.mCallType = callType;
            this.mMethod = method;
            this.mMethod.setAccessible(true);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MethodReference that = (MethodReference) o;
            if (this.mCallType != that.mCallType || !this.mMethod.getName().equals(that.mMethod.getName())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.mCallType * 31) + this.mMethod.getName().hashCode();
        }
    }
}
