package com.example.q_studentcommunity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Server {
    public static HashMap<String, Socket> hs = new HashMap<>();
    public static ArrayList<String> userLIst = new ArrayList<>();
    public static void main(String[] args)  {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String data = "INSERT INTO chat_user_list (username) VALUES (?)";
            PreparedStatement statement = connectDB.prepareStatement(data);
            Statement statement1 = connectDB.createStatement();
            statement1.execute("DELETE FROM chat_user_list WHERE 1");




            try (ServerSocket server = new ServerSocket(8080)){
                DataInputStream dis;
                DataOutputStream dos;
                Socket socket;
                while (true) {
                    socket = server.accept();
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream());
                    String user = dis.readUTF();

                    if(hs.containsKey(user)){
                        hs.get(user).close();
                        hs.remove(user);
                        userLIst.remove(user);

                        hs.put(user, socket);
                        userLIst.add(user);
                        new InputOutput(dis,dos).start();
                    }else {
                        hs.put(user, socket);
                        userLIst.add(user);
                        PreparedStatement state = connectDB.prepareStatement("SELECT * FROM chat_user_list WHERE username = ?");
                        state.setString(1,user);
                        ResultSet resultSet = state.executeQuery();
                        if(!resultSet.next()){
                            statement.setString(1,user);
                            statement.execute();
                        }

                        System.out.println(socket + user);
                        new InputOutput(dis,dos).start();
                    }

////reboooo

                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
/*
class serverThread extends Thread{
    Socket socket ;
    DataInputStream dis;
    DataOutputStream dos;
    public serverThread(Socket soc) {
        try {
            socket=soc;
            dis=new DataInputStream(socket.getInputStream());
            dos=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void run() {
        new InputOutput(dis,dos).start();

    }
}
*/
class InputOutput extends Thread{
    DataInputStream dis;
    String msg = null;
    DataOutputStream dos;
    public InputOutput(DataInputStream i , DataOutputStream o){
        this.dis = i;
        this.dos=o;
    }
    public void run() {
        Thread t1 = new Thread(() -> {
            try {
                while (true){
                    String ms = null;
                    try {
                        ms = dis.readUTF();

                        if(ms.contains("<@>")){
                            String []array=ms.split("<@>");
                            String msg = array[1];
                            String user = array[0];
                            sendTouser(msg,user);
                        } else if (ms.contains("<c>")) {
                            String []array=ms.split("<c>");
                            //String msg = array[1];
                            String user = array[0];
                            sendToCommunity(ms,user);
                        }


                    }catch (ArrayIndexOutOfBoundsException e){

                    }



                    //System.out.println("hello " + ms);
                }

            } catch (IOException e) {
                System.out.println("input not found");

            }
        });
        t1.start();
    }

    void sendTouser(String ms , String user){
        try {
            DataOutputStream dos = new DataOutputStream(Server.hs.get(user).getOutputStream());
            dos.writeUTF(ms);
        } catch (IOException e) {
            System.out.println(Server.hs.size());
            System.out.println(Server.userLIst.size());
        }
    }
    void sendToCommunity(String ms,String user){

        System.out.println(Server.userLIst.size());
        for (String use:Server.userLIst) {
            try {
               if(!use.equals(user)){
                   System.out.println("send from comunity");
                   DataOutputStream dos = new DataOutputStream(Server.hs.get(use).getOutputStream());
                   dos.writeUTF(ms);
               }
            } catch (IOException e) {
//                                Server.hs.remove(user);
//                                Server.userLIst.remove(user);
            }
        }
    }
}
