package org.example.design.mode.pubsub;

import java.util.LinkedList;
import java.util.Random;

public class PubSubDemo {

    public static class StackDemo {
        int capacity = 10;
        LinkedList<Integer> linkedList = new LinkedList<>();
        // 生产者push
        public synchronized void push(String name,Integer index) {
            while (linkedList.size() >= capacity) {
                System.out.println("队列满了！！！");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            linkedList.add(index);
            System.out.println("生产者： "+ name + "，生产了："+index);
            notifyAll();
        }

        // 消费者pop
        public synchronized void pop(String name) {
            while (linkedList.size() == 0) {
                System.out.println("队列空了！！！");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int value = linkedList.removeFirst();
            System.out.println("消费者："+name +", 消费了" + value);
            notifyAll();
        }
    }



    public static class Consumer implements Runnable {
        private String name;
        private StackDemo stack;
        public Consumer(String name, StackDemo stack) {
            this.name = name;
            this.stack = stack;
        }

        @Override
        public void run() {
            while (true) {
                stack.pop(name);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static class Producter implements Runnable {

        private String name;
        private StackDemo stack;
        public Producter(String name, StackDemo stack) {
            this.name = name;
            this.stack = stack;
        }
        @Override
        public void run() {
            while (true) {
                int value = new Random().nextInt(1000);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stack.push(name, value);
            }
        }
    }

    public static void main(String[] args) {
        StackDemo stackDemo = new StackDemo();
        Producter producer1 = new Producter("producer1",stackDemo);
        Producter producer2 = new Producter("producer2",stackDemo);
        Producter producer3 = new Producter("producer3",stackDemo);
        new Thread(producer1).start();
        new Thread(producer2).start();
        new Thread(producer3).start();

        Consumer consumer1 = new Consumer("consumer1",stackDemo);
        Consumer consumer2 = new Consumer("consumer2",stackDemo);
        Consumer consumer3 = new Consumer("consumer3",stackDemo);
        new Thread(consumer1).start();
        new Thread(consumer2).start();
        new Thread(consumer3).start();


    }
}
