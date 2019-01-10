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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDao {
    public void createUser(User user) {
        try (Connection con = Database.getConnection()) {
            String query = "INSERT INTO `users`"
                    + "(`username`, `password`, `role`)"
                    + "VALUES (?, ?, ?);";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getRole());
                ps.executeUpdate();
            }        
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }
    
    public void createUser() {
        boolean flag = true;
        System.out.println("Enter Username for the new User (should not be above 25 characters): \n");
        Scanner sc = new Scanner(System.in);
        String username = sc.next();
        while (searchUserByUsername(username) || username.length()>25) {
            System.out.println("Username already exists or too long. Please Enter another Username.\n");
            username = sc.next();
        }
        System.out.println("Enter Password (more than 6 characters less than 25) for the new User: \n");
        String password = sc.next();
        while (password.length()<6 || password.length()>25) {
            System.out.println("Password not in range. Try a new password.\n");
            password = sc.next();
        }
        System.out.println("Choose Role for the User:\n"
                + "[1] User (plain user)\n"
                + "[2] Gold User\n"
                + "[3] Admin\n");
        String role = sc.next();
        int parserole = Integer.parseInt(role);
        while (flag) {
            switch (parserole){
                case 1:
                    role = "user";
                    flag = false;
                    break;
                case 2:
                    role = "gold_user";
                    flag = false;
                    break;
                case 3:
                    role = "admin";
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid Input. Try again.\n");
                    role = sc.next();
                    parserole = Integer.parseInt(role);
                    break;
            }
        }
        try (Connection con = Database.getConnection()) {
            String query = "INSERT INTO `users` (username, password, role)"
                    + "VALUES (?, ?, ?);";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, role);
                ps.executeUpdate();
                System.out.println("New User Created");
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        log("admin", "", " created user " + username, "");
    }
    
    public void updateUser(User user) {
        try (Connection con = Database.getConnection()) {
            String query = "UPDATE `users` SET"
                    + " `username`=?, `password`=?, `role`=? "
                    + "WHERE `id`=?;";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getRole());
                ps.executeUpdate();
            }
        }catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }
    
    public void deleteUserById(int id) {
        try (Connection con = Database.getConnection()) {
            String query = "DELETE FROM `users` WHERE `id`=? ;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void deleteUserByUsername(String username) {
        try (Connection con = Database.getConnection()) {
            String query = "DELETE * FROM `users` WHERE `username`= ? ;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, username);
                ps.executeUpdate();
            }
        }catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }
    
    public void deleteUser () {
        boolean flag = true;
        while (flag) {
            System.out.println("Select the user ID of the User you want to Delete.\n");
            getAllUsers();
            Scanner sc = new Scanner (System.in);
            String parseid = sc.next();
            String deletedUser;
            try {
                int usrdlt = Integer.parseInt(parseid);
                if (searchUserByUsername(getUsermameFromId(usrdlt))) {
                    deletedUser = getUsermameFromId(usrdlt);
                    try (Connection con = Database.getConnection()) {
                        String query = "DELETE FROM `users` WHERE `id`=?;";
                        try (PreparedStatement ps = con.prepareStatement(query)) {
                            ps.setInt(1, usrdlt);
                            ps.executeUpdate();
                            System.out.println("Deletion Successful.");
                            getAllUsers();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    log(deletedUser, checkUserRole(deletedUser), " has been deleted ", "");
                    flag = false;
                } else {
                    System.out.println("User Does Not Exist. Try Again.\n");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
    
    public User findUserById(int id) {
        try (Connection con = Database.getConnection()) {
            String query = "SELECT * FROM `users` WHERE `id`=? ;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setUsername(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setRole(rs.getString(4));
                
                return user;
                }
            }
        }catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
        return null;
    }
    
    public GoldUser findGoldUserByUsername(String username) {
        try (Connection con = Database.getConnection()) {
            String query = "SELECT * FROM `users` WHERE `username`=? ;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, username);
                
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    GoldUser user = new GoldUser();
                    user.setId(rs.getInt(1));
                    user.setUsername(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setRole(rs.getString(4));
                
                return user;
                }
            }
        }catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
        return null;
    }
    
    public Admin findAdminUsername(String username) {
        try (Connection con = Database.getConnection()) {
            String query = "SELECT * FROM `users` WHERE `username`=? ;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, username);
                
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Admin user = new Admin();
                    user.setId(rs.getInt(1));
                    user.setUsername(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setRole(rs.getString(4));
                
                return user;
                }
            }
        }catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
        return null;
    }
    
    public User findUserByUsername(String username) {
        try (Connection con = Database.getConnection()) {
            String query = "SELECT * FROM `users` WHERE `username`=? ;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, username);
                
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setUsername(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setRole(rs.getString(4));
                
                return user;
                }
            }
        }catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
        return null;
    }
    
    public boolean searchUserByUsername(String username) {
        try (Connection con = Database.getConnection()) {
            String query = "SELECT * FROM `users` WHERE `username`=? ;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, username);
                
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
    
    public int searchUserId(String username) {
        try (Connection con = Database.getConnection()) {
            String query = "SELECT `id` FROM `users` WHERE `username`=? ;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, username);
                
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int userid = rs.getInt(1);
                    return userid;
                }
            }
        }catch (SQLException ex) {
            System.out.println("Search failed " + ex.getMessage());
        }
        return -1;        
    }
    
    public User findUserByUsernameAndPassword(String username, String password) {
            try (Connection con = Database.getConnection()) {
                String query = "SELECT * FROM `users` WHERE `username`=? AND `password`=? ;";

                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, username);
                    ps.setString(2, password);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt(1));
                        user.setUsername(rs.getString(2));
                        user.setPassword(rs.getString(3));
                        user.setRole(rs.getString(4));

                        System.out.println("User "+ username + " Found");
                        return user;
                    }
                }
            }catch (SQLException ex) {
                System.out.println("Error: " + ex);
            }
            System.out.println("Not Found");
            return null;
    }

    public boolean checkUsernameAndPassword(String username, String password) {
        boolean flag = false;
        try (Connection con = Database.getConnection()) {
            String query = "SELECT * FROM `users` WHERE `username`=? AND `password`=? ;";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        } 
        return flag;
    }
    
    public String checkUserRole (String username) { //returns the role of given user by the username
        
        try (Connection connection = Database.getConnection()) {
            String userrole = "";
            String query = "SELECT `role` FROM `users` WHERE `username`=?";
//            System.out.println("MPIKA");
            try (PreparedStatement pstm = connection.prepareStatement(query)) {
//                System.out.println("MPIKA ps");
                pstm.setString(1, username);
                
                ResultSet rs = pstm.executeQuery();
                if (rs.next()) {
                  userrole = rs.getString("role");
//                    System.out.println("pira to role " + userrole);
                  return userrole;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
//            System.out.println("Error: " + ex);
        }
        return "could not find role";
    }

    public String getUsermameFromId (int id) {
        
        try (Connection connection = Database.getConnection()) {
            String username = "";
            String query = "SELECT `username` FROM `users` WHERE `id`=?";
            try (PreparedStatement pstm = connection.prepareStatement(query)) {
                pstm.setInt(1, id);
                
                ResultSet rs = pstm.executeQuery();
                if (rs.next()) {
                  username = rs.getString("username");
                  return username;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "Username Not Found";
    }
    
    public List<User> findAllUsers() {
        List<User> users = null;
        try (Connection con = Database.getConnection()) {
            String query = "SELECT * FROM `users`;";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (users == null)
                        users = new ArrayList<>();
                    
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setUsername(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setRole(rs.getString(4));
                    
                    users.add(user);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
        return users;
    }
    
    public void getAllUsers() {
        try (Connection con = Database.getConnection()) {
            String query = "SELECT * FROM `users`;";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String username = rs.getString(2);
                    String password = rs.getString(3);
                    String role = rs.getString(4);
                    System.out.format("%s|, %s|, %s|, %s|\n", id, username, password, role);
                }
                System.out.println();
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void updateUser() {
        boolean flag = true;
        while (flag) {
            getAllUsers();
            System.out.println("Select the User you want to update by ID.");
            Scanner sc = new Scanner(System.in);
            String parseid = sc.next();
            try {
                int userid = Integer.parseInt(parseid);
                if (searchUserByUsername(getUsermameFromId(userid))) {
                    System.out.println("Press 1 to edit username.\n"
                            + "Press 2 to edit password.\n"
                            + "Press 3 to edit role.\n");
                    int editchoice = sc.nextInt();
                    switch (editchoice) {

                        case 1:
                            System.out.println("Enter new username.\n");
                            String newusername = sc.next();
                            try (Connection con = Database.getConnection()) {
                                String query = ("UPDATE `users` SET username=? WHERE `id` = ?;");
                                try (PreparedStatement ps = con.prepareStatement(query)) {
                                    ps.setString(1, newusername);
                                    ps.setInt(2, userid);
                                    ps.executeUpdate();
                                    System.out.println("User update Successful.\n");
                                    getAllUsers();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            log(getUsermameFromId(userid), checkUserRole(getUsermameFromId(userid)),
                                    " has been edited. New username: " + newusername, "");
                            flag = false;
                            break;

                        case 2:
                            System.out.println("Enter new password.\n");
                            String newpassword = sc.next();
                            try (Connection con = Database.getConnection()) {
                                String query = ("UPDATE `users` SET password=? WHERE `id` = ?;");
                                try (PreparedStatement ps = con.prepareStatement(query)) {
                                    ps.setString(1, newpassword);
                                    ps.setInt(2, userid);
                                    ps.executeUpdate();
                                    System.out.println("User update Successful.\n");
                                    getAllUsers();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            log(getUsermameFromId(userid), checkUserRole(getUsermameFromId(userid)),
                                    " has been edited. New password: " + newpassword, "");
                            flag = false;
                            break;

                        case 3:
                            boolean flag2 = true;
                            while (flag2) {
                                System.out.println("Please enter new role (Choose Between: user, gold_user, admin).\n");
                                String newrole = sc.next();
                                switch (newrole) {
                                    case "user":
                                        flag2 = false;
                                        try (Connection con = Database.getConnection()) {
                                            String query = ("UPDATE `users` SET role=? WHERE `id` = ?;");
                                            try (PreparedStatement ps = con.prepareStatement(query)) {
                                                ps.setString(1, newrole);
                                                ps.setInt(2, userid);
                                                ps.executeUpdate();
                                                System.out.println("User update Successful.\n");
                                                getAllUsers();
                                            }
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        log(getUsermameFromId(userid), checkUserRole(getUsermameFromId(userid)), " role has been changed ", "");
                                        flag = false;
                                        break;
                                    case "gold_user":
                                        flag2 = false;
                                        try (Connection con = Database.getConnection()) {
                                            String query = ("UPDATE `users` SET role=? WHERE `id` = ?;");
                                            try (PreparedStatement ps = con.prepareStatement(query)) {
                                                ps.setString(1, newrole);
                                                ps.setInt(2, userid);
                                                ps.executeUpdate();
                                                System.out.println("User update Successful.\n");
                                                getAllUsers();
                                            }
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        log(getUsermameFromId(userid), checkUserRole(getUsermameFromId(userid)), " role has been changed ", "");
                                        flag = false;
                                        break;
                                    case "admin":
                                        flag2 = false;
                                        try (Connection con = Database.getConnection()) {
                                            String query = ("UPDATE `users` SET role=? WHERE `id` = ?;");
                                            try (PreparedStatement ps = con.prepareStatement(query)) {
                                                ps.setString(1, newrole);
                                                ps.setInt(2, userid);
                                                ps.executeUpdate();
                                                System.out.println("User update Successful.\n");
                                                getAllUsers();
                                            }
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        log(getUsermameFromId(userid), checkUserRole(getUsermameFromId(userid)), " role has been changed ", "");
                                        flag = false;
                                        break;
                                    default:
                                        System.out.println("Invalid role. Please try again.\n");
                                        break;
                                }
                            }
                    }
                } else {
                    System.out.println("User Not Found. Try again.\n");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
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
}

