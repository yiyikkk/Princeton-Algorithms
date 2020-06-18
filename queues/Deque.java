/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;
    private int nextfirst;
    private int nextlast;

    public Deque() {
        items = (Item[]) new Object[8];
        nextfirst = 0;
        nextlast = 1;
        size = 0;
    }

    /**
     * 将双端队列中数的下一个数的位置映射到实际的数组中。
     * 例如，这一个数索引为items.length-1（即数组最后），则下一个数
     * 应该位于0位置.若索引还不到最后一位，则下一个数的位置
     * 就是下一个数的索引，正常+1即可。
     * <p>
     * 这个trick应该很常用于循环。每次将索引+1，一到某个阈值
     * 就从头开始。
     *
     * @param a
     * @return
     */
    private int addOne(int a) {
        return (a + 1) % items.length;
    }

    private int subOne(int a) {
        return (a - 1 + items.length) % items.length;
    }

    /**
     * resize the array.
     * 主要思想是，将原数组从first到end复制新数组的0到size中。
     * 则新数组的nextfirst为新数组的最后一个，新数组的nextlast为新数组的size索引。
     *
     * @param length
     */
    private void resize(int length) {
        Item[] newitems = (Item[]) new Object[length];
        int oldindex = addOne(nextfirst);
        for (int i = 0; i < size; i++) {
            newitems[i] = items[oldindex];
            oldindex = addOne(oldindex);
        }
        this.items = newitems;
        nextfirst = items.length - 1;
        nextlast = size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextfirst] = item;
        nextfirst = subOne(nextfirst);
        size += 1;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextlast] = item;
        nextlast = addOne(nextlast);
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /*
    public void printDeque() {
        int i = addOne(nextfirst);
        for (int j = 0; j < size; j++) {
            System.out.print(items[i] + " ");
            i = addOne(i);
        }
    } */

    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("NoSuchElementException");
        }
        Item a = items[addOne(nextfirst)];
        items[addOne(nextfirst)] = null;
        nextfirst = addOne(nextfirst);
        size -= 1;
        if (items.length >= 16 && size < (items.length / 4)) {
            resize(items.length / 2);
        }
        return a;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("NoSuchElementException ");
        }
        Item a = items[subOne(nextlast)];
        items[subOne(nextlast)] = null;
        nextlast = subOne(nextlast);
        size -= 1;
        if (items.length >= 16 && size < (items.length / 4)) {
            resize(items.length / 2);
        }
        return a;
    }

    // 这里不要写DequeIterator<Item>,否则相当于新声明了一个Item，该迭代器内部会认为此Item非Deque的Item。
    private class DequeIterator implements Iterator<Item> {
        private int p;
        private boolean first;

        DequeIterator() {
            p = addOne(nextfirst);
            first = true;
        }

        public boolean hasNext() {
            // p is the first one and size == length,
            // p == nextlast means p has the next|| p != nextlast
            if (first) {
                return items[p] != null;
            } else {
                return p != nextlast;
            }
        }

        public Item next() {
            if ((first && items[p] == null)
                    || (!first && p == nextlast)) {
                throw new NoSuchElementException("NoSuchElementException");
            }
            Item i = items[p];
            p = addOne(p);
            first = false;
            return i;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> a = new Deque<>();

        boolean isNull = a.isEmpty();
        System.out.println("create, and the deque is null, " + isNull);

        a.addLast("c");
        a.addLast("d");
        a.addLast("e");
        a.addLast("f");
        a.addLast("g");
        a.addLast("h");
        a.addFirst("b");
        a.addFirst("a");

        // Iterator<String> iterator = a.iterator();
        for (String i : a) {
            System.out.print(i + " ");
        }

        boolean isNull2 = a.isEmpty();
        System.out.println("\nadd, and the deque is null, " + isNull2);
        System.out.println("size is " + a.size());

        a.removeFirst();
        a.removeFirst();
        a.removeFirst();
        a.removeLast();
        a.removeLast();

        for (String i : a) {
            System.out.print(i + " ");
        }
        System.out.println("size is " + a.size());
    }

}


