package by.kihtenkoolga;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;

class MainTest {

    @Test
    void testRunTaskClientServer() {
        final int expected = 55;
        List<Integer> dataList = new ArrayList<>(10);
        dataList.addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        ExecutorService clientExecutor = Executors.newFixedThreadPool(3);
        ExecutorService serverExecutor = Executors.newFixedThreadPool(4);
        Server server = new Server(serverExecutor);
        Client client = new Client(clientExecutor, server, dataList);

        Main.runTask(server, client, dataList.size());

        assertAll(
                () -> assertThat(client.getAccumulator()).isEqualTo(expected),
                () -> assertThat(client.dataList).isEmpty()
        );
    }

    @Test
    @Disabled("~15 sec")
    void testRunTaskShouldReturn5050() {
        final int expected = 5050;
        List<Integer> dataList = new ArrayList<>(100);
        for (int i = 1; i <= 100; i++)
            dataList.add(i);

        ExecutorService clientExecutor = Executors.newFixedThreadPool(6);
        ExecutorService serverExecutor = Executors.newFixedThreadPool(4);
        Server server = new Server(serverExecutor);
        Client client = new Client(clientExecutor, server, dataList);

        Main.runTask(server, client, dataList.size());

        assertThat(client.getAccumulator()).isEqualTo(expected);
    }
}
