package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class Cliente2 {
    public static void main(String[] args) throws IOException {
        Socket clienteSocket = new Socket("localhost", 1250);
        DataOutputStream out = new DataOutputStream(clienteSocket.getOutputStream());
        DataInputStream in = new DataInputStream(clienteSocket.getInputStream());

        Thread threadOuvinte = new Thread(() -> {
            try {
                String mensagemDoServidor;
                while ((mensagemDoServidor = in.readUTF()) != null) {
                    System.out.println("\n" + mensagemDoServidor);
                }
            } catch (IOException e) {
                System.out.println("Conexão com o servidor perdida.");
            }
        });
        threadOuvinte.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            out.writeUTF(linha);
        }

        scanner.close();
        clienteSocket.close();
    }
}