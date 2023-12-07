package by.kihtenkoolga;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        List<Integer> dataList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        ExecutorService clientExecutor = Executors.newFixedThreadPool(6);
        ExecutorService serverExecutor = Executors.newFixedThreadPool(3);

        Server server = new Server(serverExecutor);
        Client client = new Client(clientExecutor, server, dataList);

        runTask(server, client, dataList.size());
    }

    public static void runTask(Server server, Client client, int length) {
        client.run();

        while (client.getAccumulator() != (1 + length) * (length / 2)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        client.clientExecutor.shutdown();
        server.serverExecutor.shutdown();
    }

}
