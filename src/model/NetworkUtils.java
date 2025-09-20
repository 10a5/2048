package model;

import java.net.*;
import java.io.*;

public class NetworkUtils {
    public static String getLocalIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "UnknownHost";
        }
    }

    public static void sendBroadcast(String message, int port) throws IOException {
        try (DatagramSocket udpSocket = new DatagramSocket()) {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), port);
            udpSocket.send(packet);
        }
    }

    public static String receiveBroadcast(int port, int timeout) throws IOException {
        try (DatagramSocket udpSocket = new DatagramSocket(port)) {
            udpSocket.setSoTimeout(timeout); // Set the socket timeout
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(packet);
            return new String(packet.getData(), 0, packet.getLength());
        } catch (SocketTimeoutException e) {
            return null; // Return null if timeout occurs
        }
    }
}
