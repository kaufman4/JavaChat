import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

public class Server {

    ArrayList Connections;
    ArrayList Namen;
    
   
    public class ClientHandler implements Runnable {

        // char-streams als Reader/Writer oeffnen
        BufferedReader sockIN;
        Socket client = null;

        //Konstruktor für ClientHandler-Klasse
        //Gibt die Daten des Clients in der Konsole aus
        //Erstellt die Streams
        public ClientHandler(Socket clientSocket) {
            try {
                client = clientSocket;

                // Informationen ueber Client ausgeben
                InetAddress clientAddr = client.getInetAddress();
                System.out.println("Client IP: " + clientAddr.getHostAddress() + " - Port: " + client.getPort());

                // char-stream als Reader oeffnen
                sockIN = new BufferedReader(new InputStreamReader(client.getInputStream()));

            } catch (IOException e) {
                System.err.println(e);
            }
        }
        
        //wird bei start des Threads ausgeführt durch Methode start()
        //Wartet auf eingehende Nachrichten des Clients
        public void run() {
            String nachricht;
            try {
                // Zeile von client einlesen
                // Schleifenabbruch bei Datenende
                while ((nachricht = sockIN.readLine()) != null) {
                    System.out.println(nachricht);
                    Broadcast(nachricht);
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public static void main(String[] args) {
        new Server().los();
    }
    
    //Öffnet einen Sockel unter dem Port
    //Erzeugt die Liste Connections zur Verwaltung der Verbindungen
    public void los() {
        Connections = new ArrayList();
        int port = 8080;

        try {
            // ServerSocket zum Warten auf die Clients
            ServerSocket server = new ServerSocket(port);

            while (true) {
                // warten auf client
                Socket clientSocket = server.accept();
                //öffnet Ausgabe-Stream 
                BufferedWriter sockOut = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                //Speichert den neuen Ausgabestream in der Liste 
                Connections.add(sockOut);
                //Neuer Thread für neuen Client, der auf Nachrichten dieses Clients wartet
                Thread connect = new Thread(new ClientHandler(clientSocket));
                //run() wird ausgeführt 
                connect.start();

            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    //Sendet die Nachricht an alle Clients 
    public void Broadcast(String nachricht) {
        Iterator it = Connections.iterator();
        while (it.hasNext()) {
            try {
                //Typcasting: Listenelement wird wieder zu einem BufferedWriter
                BufferedWriter sockOut = (BufferedWriter) it.next();
                // eingelesene zeile in den Socket
                sockOut.write(nachricht + "\n");
                // puffer leeren (sofort ausgeben)
                sockOut.flush();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}