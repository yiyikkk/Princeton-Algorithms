/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;
    // private int nextlast;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[8];
        size = 0;
    }

    private void resize(int length) {
        Item[] newitems = (Item[]) new Object[length];
        System.arraycopy(items, 0, newitems, 0, size);
        this.items = newitems;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[size] = item;
        size += 1;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("NoSuchElementException ");
        }
        // 找到需要随机删除的item
        int temp = StdRandom.uniform(0, size);
        Item i = items[temp];
        items[temp] = items[size - 1];
        items[size - 1] = null;
        size -= 1;
        // resize数组
        if (items.length >= 16 && size < (items.length / 4)) {
            resize(items.length / 2);
        }
        return i;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("NoSuchElementException ");
        }
        int temp = StdRandom.uniform(0, size);
        return items[temp];
    }

    private class RQIterator implements Iterator<Item> {
        private final int[] random;
        private int p;

        RQIterator() {
            random = StdRandom.permutation(size);
            p = 0;
        }

        public boolean hasNext() {
            return p < random.length;
        }

        public Item next() {
            if (p >= random.length) {
                throw new NoSuchElementException("NoSuchElementException");
            }
            Item i = items[random[p]];
            p += 1;
            return i;
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> r = new RandomizedQueue<>();
        System.out.println(r.isEmpty());
        for (int i = 0; i < 20; i++) {
            r.enqueue(i);
        }
        for (int i : r) {
            System.out.print(i + " ");
        }
        System.out.println("\n" + r.size());
        System.out.println(r.isEmpty());
        System.out.println(r.sample());
        System.out.println(r.sample());
        for (int i = 0; i < 20; i++) {
            r.dequeue();
        }
        for (int i : r) {
            System.out.print(i + " ");
        }
        System.out.println("\n" + r.size());
        for (int i = 0; i < 10; i++) {
            r.enqueue(i);
        }
        for (int i : r) {
            System.out.print(i + " ");
        }
        RandomizedQueue<Integer> r1 = new RandomizedQueue<>();
        r1.enqueue(101);
        System.out.println(r1.dequeue());
    }
}
