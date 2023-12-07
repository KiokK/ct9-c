package by.kihtenkoolga;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    public final ExecutorService serverExecutor;

    private AtomicInteger remainedCount = new AtomicInteger(0);

    public Server(ExecutorService executor) {
        this.serverExecutor = executor;
    }

    public Response handleRequest(Request request) {
        System.out.println("Значение запроса - " + request.getRequestValue());
        Future<Response> submit = serverExecutor.submit(new ServerCaller(request));
        try {
            Response response = submit.get();
            System.out.println("Выполнилось значение - " + request.getRequestValue());

            return response;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

     private class ServerCaller implements Callable<Response> {
        private final Request request;

        ServerCaller(Request request) {
            this.request = request;
        }

        @Override
        public Response call() {
            System.out.println("(call) Значение запроса - " + request.getRequestValue());

            sleep();

            return new Response(remainedCount.incrementAndGet());
        }

         /**
          * Усыпляет поток на время от 100 до 1000 мс.
          */
         private void sleep() {
             try {
                 Thread.sleep(ThreadLocalRandom.current().nextLong(100, 1001));
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
         }
    }

}
