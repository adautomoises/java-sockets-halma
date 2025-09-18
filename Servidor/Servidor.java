package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Servidor {
    private static final String MSG_SERVIDOR = "Mensagem do Servidor.Servidor Socket";
    private static final int NUMERO_MAX_JOGADORES = 2;

    public static void main(String[] args) throws IOException {
        ArrayList<Socket> jogadores = new ArrayList<>();
        int turno = 1;

        try (ServerSocket servidorSocket = new ServerSocket(1250)) {
            System.out.println("Aguardando Conexoes ... ");
            while (jogadores.size() != NUMERO_MAX_JOGADORES) {
                jogadores.add(servidorSocket.accept());
            }

            do {
                while (turno == 1) {
                    DataInputStream in = new DataInputStream(jogadores.getFirst().getInputStream());
                    DataOutputStream out = new DataOutputStream(jogadores.getFirst().getOutputStream());
                    String msg = in.readUTF();
                    System.out.println("Stream Recebida: " + msg);
                    out.writeUTF(MSG_SERVIDOR);
                    turno = 2;
                }

                while (turno == 2) {
                    DataInputStream in = new DataInputStream(jogadores.getLast().getInputStream());
                    DataOutputStream out = new DataOutputStream(jogadores.getLast().getOutputStream());
                    String msg = in.readUTF();
                    System.out.println("Stream Recebida: " + msg);
                    out.writeUTF(MSG_SERVIDOR);
                    turno = 1;
                }
            } while (true);
        }
    }
}