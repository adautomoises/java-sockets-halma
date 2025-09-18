package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class Cliente {

    private static final String CLIENTE = "(Cliente 1):";

    public static void main(String[] args) throws IOException {

        try (Socket clienteSocket = new Socket("localhost", 1250)) {
            Scanner scanner = new Scanner(System.in);
            DataOutputStream out = new DataOutputStream(clienteSocket.getOutputStream());
            DataInputStream in = new DataInputStream(clienteSocket.getInputStream());

            while (true) {
                System.out.println("Digite uma mensagem para o servidor:");
                out.writeUTF(CLIENTE + scanner.nextLine());

                String msg = in.readUTF();
                System.out.println("Stream Recebida: " + msg + "\n");
            }
        }
    }
}