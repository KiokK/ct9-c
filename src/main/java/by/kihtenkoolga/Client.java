package by.kihtenkoolga;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class Client implements Runnable {

    public final ExecutorService clientExecutor;

    /** Общий ресурс - данные клиента */
    public List<Integer> dataList;

    public List<Response> responseList = new ArrayList<>();

    private final Lock locker = new ReentrantLock();

    private final Server server;
    private int accumulator;

    public Client(ExecutorService clientExecutor, Server server, List<Integer> dataList) {
        this.clientExecutor = clientExecutor;
        this.server = server;
        this.dataList = dataList;

        Collections.shuffle(this.dataList);
    }

    @Override
    public void run() {
        AtomicInteger i = new AtomicInteger(dataList.size() - 1);

        for (int k = 0; k < dataList.size(); k++) {
            clientExecutor.submit(() -> {
                Request request = getAndDeleteData(i);

                System.out.println("Начато Thread name " + Thread.currentThread().getName());
                Response response = server.handleRequest(request);
                addToResponses(response);
                System.out.println("Завершение Thread name " + Thread.currentThread().getName());
            });

        }
    }

    private Request getAndDeleteData(AtomicInteger i) {
        locker.lock();
        try {
            return new Request(dataList.remove(i.getAndDecrement()));
        } finally {
            locker.unlock();
        }
    }

    private void addToResponses(Response response) {
        locker.lock();
        try {
            responseList.add(response);
            accumulator += response.getResponseValue();
            System.out.println(" accumulator = " + accumulator);
        } finally {
            locker.unlock();
        }
    }

}
