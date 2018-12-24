import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public final class CheckerClient {
    private static final int PORT = 8090;
    private static final InetSocketAddress SOCKET_ADDRESS = new InetSocketAddress("localhost", PORT);

    private static boolean read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        int numRead;
        numRead = channel.read(buffer);

        if (numRead == -1) {
            Socket socket = channel.socket();
            SocketAddress remoteAddress = socket.getRemoteSocketAddress();
            System.out.println("Connection closed by server: " + remoteAddress);
            channel.close();
            key.cancel();
            return false;
        }
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        byte type = buffer.get(0);
        int quality = buffer.getInt(1);
        boolean next = buffer.get(5) == 1;
        System.out.printf("Type: %d, quality: %d, next: %b\n", type, quality, next);
        return true;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new CheckerClient()
                .image(".\\tests_data\\6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B.jpg");
    }

    private void image(String filename) throws IOException {
        SocketChannel client = SocketChannel.open();
        client.configureBlocking(false);
        Selector selector = Selector.open();
        client.register(selector, SelectionKey.OP_CONNECT);
        client.connect(SOCKET_ADDRESS);
        boolean isRunning = true;
        while (isRunning) {
            selector.select();
            Iterator keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();
                keys.remove();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isReadable()) {
                    if (read(key)) {
                        isRunning = false;
                    }
                } else if (key.isConnectable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.finishConnect();
                    key.interestOps(SelectionKey.OP_WRITE);
                } else if (key.isWritable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    byte[] bytes = Files.readAllBytes(
                            Paths.get(filename));
                    ByteBuffer buf = ByteBuffer.allocate(bytes.length + 500);
                    buf.clear();
                    ByteOrder order = buf.order();
                    buf = buf.put((byte) 1)
                            .order(ByteOrder.LITTLE_ENDIAN)
                            .putInt(bytes.length)
                            .order(order)
                            .put(bytes)
                            .put((byte) 0);
                    buf.flip();
                    socketChannel.write(buf);
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }
        client.close();
    }
}
