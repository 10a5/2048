package model;

import java.io.*;
import java.net.*;

import view.DuoGameFrame;
import view.MainFrame;
import view.duoFrame;

import static model.GridNumberDuo.random;

public class ServerConnection implements Runnable {
    private static final int UDP_PORT = 10945;
    private static final int TCP_PORT = 10113;
    private static final int BROADCAST_TIMEOUT = 500;
    private boolean started;
    private MainFrame mainFrame;
    private String currentUser;
    private volatile boolean running = true;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private duoFrame DuoFrame;

    public ServerConnection(MainFrame mainFrame, String currentUser , duoFrame DuoFrame) {
        this.DuoFrame = DuoFrame;
        this.mainFrame = mainFrame;
        this.currentUser = currentUser;
    }

    @Override
    public void run() {
        String serverIP = null;
        int tcpPort = TCP_PORT;

        try {
            System.out.println("Waiting for broadcast...");
            String message = NetworkUtils.receiveBroadcast(UDP_PORT, BROADCAST_TIMEOUT);
            if (message != null) {
                System.out.println("Received UDP broadcast: " + message);
                String[] parts = message.split(":");
                if (parts.length == 3 && "ServerIP".equals(parts[0])) {
                    serverIP = parts[1];
                    tcpPort = Integer.parseInt(parts[2]);
                }
            } else {
                System.out.println("No broadcast received within the timeout period.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (serverIP == null) {
            startServer();
        } else {
            startClient(serverIP, tcpPort);
        }
    }

    private void startServer() {
        System.out.println("Starting server...");
        String message = "ServerIP:" + NetworkUtils.getLocalIPAddress() + ":" + TCP_PORT;
        started = false;
        new Thread(() -> {
            try {
                while (!started) {
                    NetworkUtils.sendBroadcast(message, UDP_PORT);
                    Thread.sleep(200);
                }
            } catch (IOException | InterruptedException e) {
                if (running) e.printStackTrace();
            }
        }).start();

        try {
            serverSocket = new ServerSocket(TCP_PORT);
            System.out.println("Server is listening on port " + TCP_PORT);
            clientSocket = serverSocket.accept();
            System.out.println("New client connected");
            started = true;
            handleClient(clientSocket);
        } catch (IOException ex) {
            if (running) ex.printStackTrace();
        } finally {
            stop();  // Ensure resources are closed
        }
    }

    private void startClient(String serverIP, int tcpPort) {
        DuoFrame.dispose();
        DuoGameFrame duoGameFrame = new DuoGameFrame(1000,800,currentUser,mainFrame.getMainController(),1);
        duoGameFrame.setVisible(true);
        System.out.println("Connecting to server " + serverIP + " on port " + tcpPort);
        try {
            clientSocket = new Socket(serverIP, tcpPort);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            int[][] numbers = new int[4][4];
//            for(int i = 0 ; i < numbers.length ; i++){
//                for (int j = 0 ; j < numbers[i].length ; j++){
//                    numbers[i][j] = 0;
//                }
//            }
//            int randomNum = random.nextInt(16);
//            numbers[randomNum/4][randomNum%4] = 2;
//            int randomNum2 = random.nextInt(16);
//            while(randomNum2 == randomNum){
//                randomNum2 = random.nextInt(16);
//            }
//            numbers[randomNum2/4][randomNum2%4] = 4;
//            int[] temp = new int[16];
//            for(int i = 0 ; i < numbers.length ; i++){
//                for (int j = 0 ; j < numbers[i].length ; j++){
//                    temp[i+4*j] = numbers[i][j];
//                }
//            }
            int[] temp = new int[16];
            for (int i = 0; i < 16; i++) {
                String response = reader.readLine();
                System.out.println(response);
                temp[i] = Integer.parseInt(response);
            }
            duoGameFrame.setSame(temp);
            while (running) {
                temp = duoGameFrame.YourTurn(temp);
//                for (int i = 0; i < 16; i++) {
                    writer.println(temp[0]);
//                }
                while(!reader.ready()){

                }
                if (reader.ready()) {
                    for (int i = 0; i < 16; i++) {
                        String response = reader.readLine();
                        temp[i] = Integer.parseInt(response);
                    }
                }
            }
        } catch (IOException ex) {
            if (running) ex.printStackTrace();
        } finally {
            stop();  // Ensure resources are closed
        }
    }

    private void handleClient(Socket socket) {
        DuoFrame.dispose();
        DuoGameFrame duoGameFrame = new DuoGameFrame(1000,800,currentUser,mainFrame.getMainController(),0);
        duoGameFrame.setVisible(true);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            String text;
            int[] temp = new int[16];
            duoGameFrame.initialNumber();
            temp = duoGameFrame.getNumber();
            System.out.println();
            int tag = 0;
            for (int i = 0; i < 16; i++) {
                System.out.println(temp[i]);
//                        System.out.println("Received from client: " + temp[i]);
//                        writer.println("Server: " + temp[i]);
                writer.println(temp[i]);
            }
            duoGameFrame.setSame(temp);
            while (running && (text = reader.readLine()) != null) {
                temp[0] = Integer.parseInt(text);
                temp[1] = 7;
//                if (tag == 15) {
                    temp = duoGameFrame.YourTurn(temp);
                    for (int i = 0; i < 16; i++) {
//                        System.out.println("Received from client: " + temp[i]);
//                        writer.println("Server: " + temp[i]);
                        writer.println(temp[i]);
                    }
                    tag = 0;
//                }
//                tag++;
            }
        } catch (IOException ex) {
            if (running) ex.printStackTrace();
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
