package individual_0;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MessageDao {
    
    public void sendMessage(Message message) {
        try (Connection con = Database.getConnection()) {
            String query = "INSERT INTO `messages`"
                    + "(`data`, `sender_id`, `receiver_id`, `date`)"
                    + "VALUES (?, ?, ?, ?);";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, message.getData());
                ps.setInt(2, message.getSender_id());
                ps.setInt(3, message.getReceiver_id());
                ps.setString(4, message.getDate());
                ps.executeUpdate();
            }        
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }
    
    public boolean searchMessage(int id) {
        try (Connection con = Database.getConnection()) {
            String query = "SELECT * FROM `messages` WHERE `id`=? ;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        }catch (SQLException ex) {
            System.out.println("Search failed " + ex.getMessage());
        }
        return false;
    }
    
    public void updateMessage(int userId) {
        boolean flag = true;
        while (flag){
            System.out.println("[1] Edit message from Inbox.\n");
            System.out.println("[2] Edit message from Sent.\n");
            Scanner sc = new Scanner(System.in);
            int edt = sc.nextInt();
            switch (edt) {
                case 1:
                    readInbox(userId);
                    break;
                case 2:
                    readSent(userId);
                    break;
                default:
                    System.out.println("Invalid Input. Try Again\n");
                    break;
            }
            System.out.println("\n Which message do you want to edit?\n");
            String parseid = sc.next();
            try {
                int editmessage = Integer.parseInt(parseid);
                if (searchMessage(editmessage)) {
                    try (Connection con = Database.getConnection()) {
                        String firstquery = "SELECT `id`, `sender_id`, `receiver_id` FROM `messages` WHERE `id`=?;";
                        try (PreparedStatement firstps = con.prepareStatement(firstquery)) {
                            firstps.setInt(1, editmessage);
                            ResultSet rs = firstps.executeQuery();
                            int sendid=0;
                            int recid=0;
                            while (rs.next()){
                                int id = rs.getInt("id");
                                sendid = rs.getInt("sender_id");
                                recid = rs.getInt("receiver_id");
                            }
                            if ((sendid==userId) || (recid==userId)) {
                                System.out.println("Enter new message: ");
                                String newmsg = sc.nextLine();
                                newmsg += sc.nextLine();
                                String query = "UPDATE `messages` SET data=? WHERE id=?;";
                                try (PreparedStatement ps = con.prepareStatement(query)){
                                    ps.setString(1, newmsg);
                                    ps.setInt(2, editmessage);
                                    ps.executeUpdate();
                                    System.out.println("Message Edit Successful.");
                                    if (edt == 1) {
                                        readInbox(userId);
                                    } else {
                                        readSent(userId);
                                    }
                                }
                                flag = false;
                                UserDao aUserDao = new UserDao();
                                String un = aUserDao.getUsermameFromId(userId);
                                log(un, aUserDao.checkUserRole(un), " updated a message " + newmsg, "");
                            }
                        }
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Message does not exist. Try again.");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

        
    public void deleteMessageById(int id) {
        try (Connection con = Database.getConnection()) {
            String query = "DELETE FROM `messages` WHERE `id`=? ;";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
            
    }

    public void deleteMessageBySenderId(int sender_id) {
        try (Connection con = Database.getConnection()) {
            String query = "DELETE * FROM `messages` WHERE `sender_id`= ? ;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, sender_id);
                ps.executeUpdate();
            }
        }catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }
    
    public void deleteMessageByReceiverId(int receiver_id) {
        try (Connection con = Database.getConnection()) {
            String query = "DELETE * FROM `messages` WHERE `receiver_id`= ? ;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, receiver_id);
                ps.executeUpdate();
            }
        }catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }
    
    public void deleteMessage (int userId){
        boolean flag = true;
        while (flag) {
            System.out.println("[1] Delete message from Inbox.\n");
            System.out.println("[2] Delete message from Sent.\n");
            Scanner sc = new Scanner(System.in);
            int dlt = sc.nextInt();
            switch (dlt) {
                case 1:
                    readInbox(userId);
                    break;
                case 2:
                    readSent(userId);
                    break;
                default:
                    System.out.println("Invalid Input. Try Again\n");
                    break;
            }
            System.out.println("\n Which message do you want to delete?\n");
            String parseid = sc.next();
            try {
                int delete = Integer.parseInt(parseid);
                if (searchMessage(delete)) {
                    try (Connection con = Database.getConnection()) {
                        String firstquery = "SELECT `id`, `sender_id`, `receiver_id` FROM `messages` WHERE `id`=?;";
                        try (PreparedStatement firstps = con.prepareStatement(firstquery)) {
                            firstps.setInt(1, delete);
                            ResultSet rs = firstps.executeQuery();
                            int sendid=0;
                            int recid=0;
                            while (rs.next()){
                                int id = rs.getInt("id");
                                sendid = rs.getInt("sender_id");
                                recid = rs.getInt("receiver_id");
                            }
                            if ((sendid==userId) || (recid==userId)) {
                                String query = "DELETE FROM `messages` WHERE id=?;";
                                try (PreparedStatement ps = con.prepareStatement(query)){
                                    ps.setInt(1, delete);
                                    ps.executeUpdate();
                                    System.out.println("Message Deletion Successful.");
                                    if (dlt == 1) {
                                        readInbox(userId);
                                    } else {
                                        readSent(userId);
                                    }
                                }
                                flag = false;
                                UserDao aUserDao = new UserDao();
                                String un = aUserDao.getUsermameFromId(userId);
                                log(un, aUserDao.checkUserRole(un), " deleted a message ", "");
                            }
                        }
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Message does not exist. Try again.");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }            
        }
    }
    
    public boolean sendMessage(int sender_id) {
        try (Connection con = Database.getConnection()) {
            Message message = new Message();    
            message.setSender_id(sender_id);
            System.out.println("Who is the receiver?");
            Scanner sc = new Scanner(System.in);
            String name = sc.next();
            UserDao aUserDao = new UserDao();
            while (!aUserDao.searchUserByUsername(name)) {
                System.out.println("User does not exist.\n"
                        + "Select user: ");
                name = sc.next();
            }
            int receiver = aUserDao.searchUserId(name);
            message.setReceiver_id(receiver);
            System.out.println("Enter your Message (Limited to 250 characters).");
            String data = sc.next();
            data +=sc.nextLine();
            message.setData(data);
            while (data.length() > 250) {
                System.out.println("Message has more than 250 characters. Please try again: \n");
                data = sc.next();
                data += sc.nextLine();
                message.setData(data);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"); //stackoverflow grabs
            LocalDateTime datetime = LocalDateTime.now();
            String date = datetime.format(formatter);
            message.setDate(date);
            String query = "INSERT INTO `messages`"
                    + "(`data`, `sender_id`, `receiver_id`, `date`)"
                    + "VALUES (?, ?, ?, ?);";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, data);
                ps.setInt(2, sender_id);
                ps.setInt(3, receiver);
                ps.setString(4, date);
                ps.executeUpdate();
                System.out.println("Message Sent");
                String un = aUserDao.getUsermameFromId(sender_id);
                log(un, aUserDao.checkUserRole(un), " sent a meessage to ", name);
                return true;
            }
        }catch (SQLException ex) {
            System.out.println("Error: " + ex);
            ex.printStackTrace();
        }
        return false;
    }
    
    public void readInbox(int receiver_id) {
        try (Connection con = Database.getConnection()) {
            String query = "SELECT `id`, `data`, `sender_id`, `date` FROM `messages` WHERE `receiver_id`=?;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, receiver_id);
                ResultSet rs = ps.executeQuery();
                UserDao aUserDao = new UserDao();
                String sender = "";
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String data = rs.getString("data");
                    int sender_id = rs.getInt("sender_id");
                    sender = aUserDao.getUsermameFromId(sender_id);
                    String time = rs.getString("date");
                    System.out.format("message id %s| %s|sender %s| %s \n", id, data, sender, time);
                }
                String receiver = aUserDao.getUsermameFromId(receiver_id);
                log(receiver, aUserDao.checkUserRole(receiver), " read his inbox", "");
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void readSent(int sender_id) {
        try (Connection con = Database.getConnection()) {
            String query = "SELECT `id`, `data`, `receiver_id`, `date` FROM `messages` WHERE `sender_id`=?;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, sender_id);
                ResultSet rs = ps.executeQuery();
                UserDao aUserDao = new UserDao();
                String receiver = "";
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String data = rs.getString("data");
                    int receiver_id = rs.getInt("receiver_id");
                    receiver = aUserDao.getUsermameFromId(receiver_id);
                    String time = rs.getString("date");
                    System.out.format("message id %s| %s|receiver %s| %s \n", id, data, receiver, time);
                }
                String sender = aUserDao.getUsermameFromId(sender_id);
                log(sender, aUserDao.checkUserRole(sender), " read his Sent", "");
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void log(String username, String role, String action, String target) {
        try (
            FileWriter fw = new FileWriter(username + ".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"); //stackoverflow grabs
            LocalDateTime datetime = LocalDateTime.now();
            String date = datetime.format(formatter);
            pw.println(role + " " +username +" "+ action +" "+ target +" "+ date + ".");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
//    not used
//    public Message findMessageById(int id) {
//        try (Connection con = Database.getConnection()) {
//            String query = "SELECT * FROM `messages` WHERE `id`=? ;";
//            
//            try (PreparedStatement ps = con.prepareStatement(query)) {
//                ps.setInt(1, id);
//                
//                ResultSet rs = ps.executeQuery();
//                if (rs.next()) {
//                    Message message = new Message();
//                    message.setId(rs.getInt(1));
//                    message.setData(rs.getString(2));
//                    message.setSender_id(rs.getInt(3));
//                    message.setReceiver_id(rs.getInt(4));
//                    message.setDate(rs.getString(5));
//                
//                return message;
//                }
//            }
//        }catch (SQLException ex) {
//            System.out.println("Error: " + ex);
//        }
//        return null;
//    }
//    

}
