package com.aryuuu.basic;

import java.util.NoSuchElementException;

public class Queue {
    public Queue() {
        head = null;
        length = 0;
    }

    public boolean isEmpty() {
        if (length == 0)
            return true;
        else
            return false;
    }

    public long queueSize() {
        return length;
    }

    public Job dequeue() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();

        List ptr = head;
        head = head.next;

        length--;

        return ptr.work;
    }

    public void enqueue(Job toadd) {
        if (toadd == null)
            return;

        List ptr = head;

        if (isEmpty()) {
            head = new List();
            ptr = head;
        } else {
            while (ptr.next != null)
                ptr = ptr.next;

            ptr.next = new List();
            ptr = ptr.next;
        }

        ptr.next = null;
        ptr.work = toadd;
        length++;
    }

    private List head;

    private long length;
}

/* This is the queue on which Jobs are placed before they are used. */

class List {
    public List() {
        work = null;
        next = null;
    }

    public Job work;

    public List next;
}
