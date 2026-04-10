package ru.aston.hometask.finalproject.services;

import ru.aston.hometask.finalproject.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserCounter {

    private static final int THREAD_COUNT = 2;

    public static int countUsers(List<User> users, User targetUser) {

        int size = users.size();

        if (size == 0) {
            return 0;
        }

        int chunkSize = (int) Math.ceil((double) size / THREAD_COUNT);

        List<Worker> workers = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, size);

            if (start >= size) {
                break;
            }

            Worker worker = new Worker(users, start, end, targetUser);
            workers.add(worker);
            worker.start();
        }

        int total = 0;

        for (Worker worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            total += worker.getCount();
        }

        System.out.printf("\nКоличество вхождений элемента User{%s}  в коллекцию: %d.\n", targetUser.toString(), total);

        return total;
    }

    private static class Worker extends Thread {
        private final List<User> users;
        private final int start;
        private final int end;
        private final User targetUser;
        private int count;

        public Worker(List<User> users, int start, int end, User targetUser) {
            this.users = users;
            this.start = start;
            this.end = end;
            this.targetUser = targetUser;
        }

        @Override
        public void run() {
            int localCount = 0;

            for (int i = start; i < end; i++) {
                if (users.get(i).equals(targetUser)) {
                    localCount++;
                }
            }

            this.count = localCount;
        }

        public int getCount() {
            return count;
        }
    }


}