package ournio;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class Server extends Application {
    final static int HEIGHT = 800;
    final static int WIDTH = 800;
    static Group root = new Group();
    static Scene scene = new Scene(root, HEIGHT, WIDTH);

    @Override
    public void start(Stage primaryStage) {
        Stage stage = new Stage();

        stage.setTitle("NIO Hell");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) throws IOException {

        InetAddress ip = InetAddress.getLocalHost();
        int port = 1;
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(ip, port);
        serverSocket.bind(address);

        serverSocketChannel.configureBlocking(false);
        int options = serverSocketChannel.validOps();
        serverSocketChannel.register(selector, options, null);
        launch();
        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    SocketChannel clientSocketChannel = serverSocketChannel.accept();
                    clientSocketChannel.configureBlocking(false);
                    clientSocketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    SocketChannel clientSocketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int size = clientSocketChannel.read(byteBuffer);

                    String data = new String(byteBuffer.array(), 0, size, StandardCharsets.UTF_8);
                    if (data.length() > 0) {
                        System.out.println("Done!");
                        System.out.println(data);
                    }
                    clientSocketChannel.close();
                }
                iterator.remove();
            }
        }
    }
}