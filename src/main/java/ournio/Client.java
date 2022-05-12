package ournio;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client extends Application {
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

    public static void main(String[] args) throws java.io.IOException {
        InetAddress ip = InetAddress.getLocalHost();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, 1);
        SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);
        String message = "Hello there. It's a hard live to love and live together...";
        ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes());
        socketChannel.write(byteBuffer);
    }
}
