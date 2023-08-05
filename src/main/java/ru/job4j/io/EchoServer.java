package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EchoServer {
    private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class.getName());

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    for (String str = in.readLine(); str != null && !str.isEmpty(); str = in.readLine()) {
                        Pattern pattern = Pattern.compile("[\\?|&]msg=\\w+");
                        Matcher matcher = pattern.matcher(str);
                        if (matcher.find()) {
                            String[] messageElems = matcher.group().split("=");
                            String message = messageElems[1];
                            switch (message) {
                                case "Hello":
                                    out.write("Hello, dear friend!".getBytes());
                                    break;
                                case "Exit":
                                    server.close();
                                    break;
                                default:
                                    out.write("What?".getBytes());
                                    break;
                            }
                        }
                        System.out.println(str);
                    }
                    out.flush();
                } catch (Exception e) {
                    LOG.error("Socket error", e);
                }
            }
        } catch (Exception e) {
            LOG.error("Echo server error", e);
        }
    }
}