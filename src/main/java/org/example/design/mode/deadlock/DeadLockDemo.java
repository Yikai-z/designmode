package org.example.design.mode.deadlock;

public class DeadLockDemo {
    public static class A{

    }

    public static class B {

    }

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        new Thread(()-> {
            synchronized(a) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized(b) {
                    
                }
            }

        }).start();
        
        new Thread(()-> {
            synchronized (b) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a){}
            }
        }).start();
    }
}
