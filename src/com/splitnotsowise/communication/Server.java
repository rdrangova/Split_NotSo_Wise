package com.splitnotsowise.communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import com.splitnotsowise.utilities.User;

public class Server {

    private static final int PORT = 8080;

    private static HashSet<User> registeredUsers = new HashSet<>();
    private static ConcurrentHashMap<String, HashSet<String>> contactLists = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, HashSet<String>> groups = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.printf("server is running on localhost:%d%n", PORT);

            updateRegisteredUsersInFile();
            updateContactListsInFile();
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("com.splitnotsowise.communication.Client " + socket.getInetAddress() + " connected to server");

                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String line = reader.readLine();
                String[] splitLine = line.split(" ");

                int index = 0;
                String command = splitLine[index++];

                if (command.equals("connect")) {
                    String username = splitLine[index];

                    if (containsUser(username)) {
                        writer.println("Connection successful. You must login now");
                    } else {
                        writer.println("Connection successful. You are connected as a guest, you must register to use SplitWise");
                    }

                    ClientConnectionRunnable runnable = new ClientConnectionRunnable(socket, username, this);
                    new Thread(runnable).start();
                }
            }

        } catch (IOException e) {
            System.out.println("another server is running on port 8080");
        }

    }

    private void updateContactListsInFile() {
        for (User user:registeredUsers){
            String path = "resources\\com.splitnotsowise.communication.Server\\"+user.getUsername();

            try {
                Files.createDirectories(Paths.get(path));
            } catch (IOException e1) {
                e1.getMessage();
            }

            File file = new File("resources\\com.splitnotsowise.communication.Server\\"+user.getUsername()+"\\contactList.txt");

            if (file.exists()) {
                try (FileReader fr = new FileReader(file.getAbsoluteFile());
                     BufferedReader bufferedReader = new BufferedReader(fr)) {

                    if (!file.exists()) {
                        file.createNewFile();

                    } else {
                        String line;

                        while ((line = bufferedReader.readLine()) != null) {
                            String[] tokens = line.split(" ");

                            String contactUsername = tokens[1];

                            addContact(user.getUsername(),contactUsername);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }



        }
    }

    private void updateRegisteredUsersInFile() {
        String path = "resources\\com.splitnotsowise.communication.Server";

        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e1) {
            e1.getMessage();
        }

        File file = new File("resources\\com.splitnotsowise.communication.Server\\registeredUsers.txt");

        if (file.exists()) {
            try (FileReader fr = new FileReader(file.getAbsoluteFile());
                 BufferedReader bufferedReader = new BufferedReader(fr)) {

                if (!file.exists()) {
                    file.createNewFile();

                } else {
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        String[] tokens = line.split(" ");

                        String username = tokens[1];
                        String password = tokens[3];

                        registeredUsers.add(new User(username, password));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }



    public void addRegisteredUser(User newUser) {
        registeredUsers.add(newUser);
    }

    public boolean containsUser(String username) {
        for (User user : registeredUsers) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    public boolean isAccountValid(String username, String password) {
        return getRegisteredUser(username).isPasswordCorrect(password);
    }

    public User getRegisteredUser(String username) {
        for (User user : registeredUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        System.out.println("User with this username doesn't exist");
        return null;
    }

    public void createContactList(String username) {
        contactLists.put(username, new HashSet<>());
    }

    public boolean areFriends(String username, String friend) {
        if (contactLists.isEmpty()){
            return false;
        }
        try {
            for (String friendUsername : contactLists.get(username)) {
                if (friendUsername.equals(friend)) {
                    return true;
                }
            }

            return false;

        } catch (NullPointerException e){
            return false;
        }

    }

    public void addContact(String username, String friend) {

        try {
            HashSet<String> contactList1 = contactLists.get(username);
            contactList1.add(friend);
            contactLists.put(username, contactList1);
        }catch (NullPointerException e){
            HashSet<String> newContactList = new HashSet<>();
            newContactList.add(friend);
            contactLists.put(username,newContactList);
        }

    }

    public void addUsernameToFile(String path, String username) {
        File file = new File(path);
        try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
             BufferedWriter bufferedWriter = new BufferedWriter(fw)) {

            if (!file.exists()) {
                file.createNewFile();
            }

            bufferedWriter.write("username: " + username);
            bufferedWriter.newLine();

        } catch (IOException e) {
            e.getMessage();
        }
    }

    public boolean containsGroup(String name) {
        return groups.containsKey(name);
    }

    public void addGroup(String name, HashSet<String> members) {
        groups.put(name, members);
    }

    public HashSet<String> getGroup(String groupName) {
        return groups.get(groupName);
    }

    public HashSet<String> getContactList(String username) {
        return contactLists.get(username);
    }
}
