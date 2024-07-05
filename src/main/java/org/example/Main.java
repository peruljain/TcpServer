package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int port = 8080; // Port number
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(() -> {
                    try {
                        accept(socket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void accept(Socket socket) throws IOException {
        try (
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // Read the request (although we don't do anything with it)
            StringBuilder request = new StringBuilder();
            String line;
            while ((line = input.readLine()) != null && !line.isEmpty()) {
                request.append(line).append("\n");
            }
            System.out.println("Received request:\n" + request.toString());

            Thread.sleep(10000);

            // Write the response
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/plain");
            output.println("Content-Length: 13");
            output.println();
            output.println("Hello, World!");

        } catch (IOException e) {
            System.out.println("Exception handling socket: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the socket
            socket.close();
        }
    }

}