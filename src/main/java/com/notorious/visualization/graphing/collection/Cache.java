package com.notorious.visualization.graphing.collection;

import com.notorious.visualization.graphing.util.StdIn;
import com.notorious.visualization.graphing.util.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * A modern adaptation of the Bag.java written by Robert Sedgewick and Kevin Wayne.
 * Following a much stricter OOP structure, adding encapsulation, removing literal null
 * values, and introducing some of the features given by Java 8.
 *
 * <p>
 * ORIGINAL DOCUMENTATION:
 * -------------------------------------------------------------------------------
 * The {@code Bag} class represents a bag (or multiset) of
 * generic items. It supports insertion and iterating over the
 * items in arbitrary order.
 * <p>
 * This implementation uses a singly-linked list with a static nested class Node.
 * See {@link LinkedCache} for the version from the
 * textbook that uses a non-static nested class.
 * See {@link ResizableArrayCache} for a version that uses a resizing array.
 * The <em>add</em>, <em>isEmpty</em>, and <em>size</em> operations
 * take constant time. Iteration takes time proportional to the number of items.
 * <p>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * -------------------------------------------------------------------------------
 *
 * @param <T> the type of value to be stored
 * @author Notorious
 * @version 0.0.1
 * @since 3 /12/2017
 */
public class Cache<T> implements Iterable<T> {

    // helper linked list class
    //kind of overkill, but I rather follow OOP structure as it fits my preference
    private static class Node<T> {
        private T item;
        private Node<T> next;

        /**
         * Instantiates a new Node.
         *
         * @param item the item
         * @param next the next
         */
        Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }

        /**
         * Gets next node in chain.
         *
         * @return the next node
         */
        Node<T> getNext() {
            return next;
        }

        /**
         * Gets item stored within this node.
         *
         * @return the item
         */
        T getItem() {
            return item;
        }
    }

    private Node<T> root;
    private int nSize;

    /**
     * Instantiates a new empty Cache instance.
     */
    public Cache() {
        //default
    }

    /**
     * Checks if the cache is empty, with no added values.
     *
     * @return true if the cache is empty; otherwise false
     */
    public boolean isEmpty() {
        return !Optional.ofNullable(root).isPresent();
    }

    /**
     * Gets the count of the items stored in the cache.
     *
     * @return the size of the cache
     */
    public int size(){
        return nSize;
    }


    /**
     * Adds the given item to the cache.
     *
     * @param t the item to be added
     */
    public void add(T t) {
        root = new Node<>(t, root);
        nSize++;
    }

    public Iterator<T> iterator() {
        return new ListIterator<>(root);
    }


    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<V> implements Iterator<V> {
        private Node<V> current;

        /**
         * Instantiates a new List iterator to be used within this class.
         *
         * @param first the first item of the iterator
         */
        ListIterator(Node<V> first) {
            current = first;
        }

        public boolean hasNext() {
            return Optional.ofNullable(current).isPresent();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public V next() {
            if (!hasNext()) throw new NoSuchElementException();
            V item = current.getItem();
            current = current.getNext();
            return item;
        }
    }

    /**
     * Unit tests the {@code Bag} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Cache<String> bag = new Cache<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.add(item);
        }

        StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.println(s);
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
