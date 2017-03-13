/******************************************************************************
 *  Compilation:  javac LinkedQueue.java
 *  Execution:    java LinkedQueue < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/13stacks/tobe.txt  
 *
 *  A generic queue, implemented using a singly-linked list.
 *
 *  % java Queue < tobe.txt 
 *  to be or not to be (2 left on queue)
 *
 ******************************************************************************/

package com.notorious.visualization.graphing.collection;

import com.notorious.visualization.graphing.util.StdIn;
import com.notorious.visualization.graphing.util.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 *  The {@code LinkedQueue} class represents a first-in-first-out (FIFO)
 *  queue of generic items.
 *  It supports the usual <em>enqueue</em> and <em>dequeue</em>
 *  operations, along with methods for peeking at the first item,
 *  testing if the queue is empty, and iterating through
 *  the items in FIFO order.
 *  <p>
 *  This implementation uses a singly-linked list with a non-static nested class 
 *  for linked-list nodes.  See {@link Queue} for a version that uses a static nested class.
 *  The <em>enqueue</em>, <em>dequeue</em>, <em>peek</em>, <em>size</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
//@SuppressWarnings("ALL")
public class LinkedQueue<T> implements Iterable<T> {

    // helper linked list class
    private class Node {
        private T item;
        private Optional<Node> next;

        public Node(T item) {
            this.item = item;
            this.next = Optional.empty();
        }

        public Node(T item, Node next) {
            this.item = item;
            this.next = Optional.ofNullable(next);
        }

        public T getItem() {
            return item;
        }

        public Optional<Node> getNext() {
            return next;
        }

        public void setItem(T item) {
            this.item = item;
        }

        public void setNext(Node next) {
            this.next = Optional.ofNullable(next);
        }
    }

    private int n;         // number of elements on queue
    private Optional<Node> first;    // beginning of queue
    private Optional<Node> last;     // end of queue

    /**
     * Initializes an empty queue.
     */
    public LinkedQueue() {
        first = Optional.empty();
        last = Optional.empty();
        assert check();
    }

    /**
     * Is this queue empty?
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return !first.isPresent();
    }

    /**
     * Returns the number of items in this queue.
     * @return the number of items in this queue
     */
    public int size() {
        return n;     
    }

    /**
     * Returns the item least recently added to this queue.
     * @return the item least recently added to this queue
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public T peek() {
        if (isEmpty() || !first.isPresent()) throw new NoSuchElementException("Queue underflow!");
        return first.get().getItem();
    }

    /**
     * Adds the item to this queue.
     * @param item the item to add
     */
    public void enqueue(T item) {
        Optional<Node> original = last;
        last = Optional.of(new Node(item));
        if (isEmpty()) {
            first = last;
        } else {
            original.ifPresent(o -> last.ifPresent(o::setNext));
        }
        n++;
        assert check();
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     * @return the item on this queue that was least recently added
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public T dequeue() {
        if (isEmpty() || !first.isPresent()) throw new NoSuchElementException("Queue underflow");
        Node f = first.get();
        T item = f.getItem();
        first = f.getNext();
        n--;
        if (isEmpty()){
            last = Optional.empty();   // to avoid loitering
        }
        assert check();
        return item;
    }

    /**
     * Returns a string representation of this queue.
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T item : this) {
            s.append(item + " ");
        }
        return s.toString();
    } 

    // check internal invariants
    private boolean check() {
        if (n < 0) {
            return false;
        } else if (n == 0) {
            if (first.isPresent()) {
                return false;
            }
            if (last.isPresent()) {
                return false;
            }
        } else if (n == 1) {
            if (!first.isPresent() || !last.isPresent()) {
                return false;
            }
            if (!first.equals(last)) {
                return false;
            }
            if (first.get().getNext() != null) {
                return false;
            }
        } else {
            if (!first.isPresent() || !last.isPresent()) {
                return false;
            }
            if (first.equals(last)) {
                return false;
            }
            if (first.get().getNext().isPresent()) {
                return false;
            }
            if (last.get().getNext().isPresent()) {
                return false;
            }

            // check internal consistency of instance variable n
            int numberOfNodes = 0;
            for (Optional<Node> x = first; x.isPresent() && numberOfNodes <= n; x = x.get().getNext()) {
                numberOfNodes++;
            }
            if (numberOfNodes != n) return false;

            // check internal consistency of instance variable last
            Optional<Node> lastNode = first;
            while (lastNode.isPresent() && lastNode.get().getNext().isPresent()) {
                lastNode = lastNode.get().getNext();
            }
            if (last != lastNode) return false;
        }

        return true;
    } 
 

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<T> iterator()  {
        return new ListIterator();  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<T> {
        private Optional<Node> current = first;

        public boolean hasNext()  { return current.isPresent();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public T next() {
            if (!hasNext() || !current.isPresent()) throw new NoSuchElementException();
            T item = current.get().getItem();
            current = current.get().getNext();
            return item;
        }
    }


    /**
     * Unit tests the {@code LinkedQueue} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        LinkedQueue<String> queue = new LinkedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                queue.enqueue(item);
            else if (!queue.isEmpty())
                StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on queue)");
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