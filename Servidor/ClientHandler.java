package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket socket;
    private List<ClientHandler> jogadores;
    private DataOutputStream out;
    private DataInputStream in;

    public ClientHandler(Socket socket, List<ClientHandler> jogadores) {
        try {
            this.socket = socket;
            this.jogadores = jogadores;
            this.out = new DataOutputStream(socket.getOutputStream());
            this.in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String mensagemRecebida;
            while ((mensagemRecebida = in.readUTF()) != null) {
                System.out.println("Mensagem recebida de " + socket.getInetAddress() + ": " + mensagemRecebida);

                broadcast(mensagemRecebida);

                String[] partes = mensagemRecebida.split(";", 2);
                String comando = partes[0];

                switch (comando) {
                    case "MOVE":
                        validarMovimento(partes[1]);
                        break;
                    case "CHAT":
                        broadcast("CHAT;" + partes[1]);
                        break;
                    case "GIVEUP":
                        break;
                    default:
                        System.out.println("Comando desconhecido: " + comando);
                }
            }
        } catch (IOException e) {
            System.out.println("Jogador desconectado: " + socket.getInetAddress());
        }
    }

    private void broadcast(String message) {
        for (ClientHandler jogador : jogadores) {
            if (jogador != this) {
                try {
                    jogador.out.writeUTF("Oponente disse: " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void validarMovimento(String[] movimentos) {

    }
}