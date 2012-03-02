package com.od.swing.util;

/**
 * Created by IntelliJ IDEA.
 * User: Nick Ebbutt
 * Date: 02/03/12
 * Time: 19:37
 *
 * An interface which can be used to decouple dependency cycles by passing
 * in a child object a reference to the containing class as a source for a given type of data
 */
public interface Source<E> {

    E get();
}
