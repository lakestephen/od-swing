package com.od.swing.util;

import javax.swing.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by IntelliJ IDEA.
 * User: Nick Ebbutt
 * Date: 17/12/10
 * Time: 16:21
 *
 */
public class AwtSafeListener {

    /**
     * @return return an AWT safe listener which wraps a swing listener and ensures events are propagated on the awt thread
     */
    public static <E> E getAwtSafeListener(final E listener, final Class<E> listenerClass) {

        final InvocationHandler handler = new InvocationHandler() {

            public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
                Class<?>[] paramTypes = method.getParameterTypes();
                if (method.getName().equals("equals") && paramTypes.length == 1 && paramTypes[0] == Object.class) {
                    //this is the equals method being called on the proxy, we need to handle it locally
                    return args[0] == proxy;
                } else {
                    SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                try {
                                    method.invoke(listener, args);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    );
                    return null;
                }
            }
        };

        return (E)Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class[] { listenerClass }, handler);
    }

}
