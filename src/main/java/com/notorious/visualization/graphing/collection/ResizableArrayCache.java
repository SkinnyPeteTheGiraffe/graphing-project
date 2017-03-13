package com.notorious.visualization.graphing.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A modern adaptation of the ResizingArrayBag.java written by Robert Sedgewick and Kevin Wayne.
 * Following a much stricter OOP structure, adding encapsulation, removing literal null
 * values, and introducing some of the features given by Java 8.
 *
 * <p>
 * ORIGINAL DOCUMENTATION:
 * -------------------------------------------------------------------------------
 *  The {@code ResizableArrayCache} class represents a bag (or multiset) of
 *  generic items. It supports insertion and iterating over the
 *  items in arbitrary order.
 *  <p>
 *  This implementation uses a resizing array.
 *  See {@link LinkedCache} for a version that uses a singly-linked list.
 *  The <em>add</em> operation takes constant amortized time; the
 *  <em>isEmpty</em>, and <em>size</em> operations
 *  take constant time. Iteration takes time proportional to the number of items.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * -------------------------------------------------------------------------------
 *
 * @author Notorious
 * @version 0.0.1
 * @since 3/12/2017
 */
public class ResizableArrayCache<T> implements Iterable<T> {

    private T[] array;
    private int nSize;

    @SuppressWarnings("unchecked")
    public ResizableArrayCache() {
        array = (T[]) new Object[2]; //generic array initialization is gross
        nSize = 0;
    }

    /**
     * Checks if the cache is empty, with no added values.
     *
     * @return true if the cache is empty; otherwise false
     */
    public boolean isEmpty() {
        return nSize == 0;
    }

    /**
     * Gets the count of the items stored in the cache.
     *
     * @return the size of the cache
     */
    public int size() {
        return nSize;
    }

    /**
     * Adds the given item to the cache.
     *
     * @param item the item to be added
     */
    public void add(T item) {
        if (nSize == array.length) {
            resize(2 * array.length);   // double size of array if necessary
        }
        array[nSize++] = item;  // add item
    }

    // resize the underlying array holding the elements
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        assert capacity >= nSize;
        T[] temp = (T[]) new Object[capacity];
        System.arraycopy(array, 0, temp, 0, nSize);
        array = temp;
    }


    /**
     * Returns an iterator that iterates over the items in the bag in arbitrary order.
     *
     * @return an iterator that iterates over the items in the bag in arbitrary order
     */
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<T> {
        private int i = 0;

        public boolean hasNext() {
            return i < nSize;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            return array[i++];
        }
    }
}


/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
