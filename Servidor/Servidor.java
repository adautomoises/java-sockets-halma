package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class Servidor {
    private static final int NUMERO_MAX_JOGADORES = 2;
    private static List<ClientHandler> jogadores = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (ServerSocket servidorSocket = new ServerSocket(1250)) {
            System.out.println("Aguardando Conexoes ... ");
            while (jogadores.size() < NUMERO_MAX_JOGADORES) {
                Socket socketCliente = servidorSocket.accept();
                System.out.println("Jogador conectado: " + socketCliente.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(socketCliente, jogadores);
                jogadores.add(clientHandler);

                new Thread(clientHandler).start();
            }
        }
    }
}