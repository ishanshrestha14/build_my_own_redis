import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible
    // when running tests.
    System.out.println("Logs from your program will appear here!");

    int port = 6379;
    try (ServerSocket serverSocket = new ServerSocket(port);
  ) {
      serverSocket.setReuseAddress(true);
      // Wait for connection from client.
      while (true) {
        Socket client = serverSocket.accept();

        new Thread(() -> handleClient(client)).start();
      }
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }

  private static void handleClient(Socket clientSocket) {
    try (Socket client = clientSocket) {

      InputStream inputStream = client.getInputStream();

      byte[] buffer = new byte[1024];
      int bytesRead;

      while ((bytesRead = inputStream.read(buffer)) != -1) {
        client.getOutputStream().write("+PONG\r\n".getBytes());
      }
    } catch (IOException e) {
              System.out.println("Client connection error: " + e.getMessage());

    }
  }
}
