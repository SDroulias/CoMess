package individual_0;

import java.io.IOException;
import java.util.Scanner;

public class Login {
    
    public void logIn() {
        boolean flag = true;
        
        UserDao aUserDao = new UserDao();
        while (flag) {
            System.out.println("Welcome to WannaBeMessagingApp.\n"
                    + "Please enter your username:");
            Scanner scan = new Scanner(System.in);
            String username = scan.next();
            System.out.println("Please enter your password:");
            String password = scan.next();
            
            if (aUserDao.checkUsernameAndPassword(username, password)) {
                System.out.println("Login attempt: Successful.");
                flag = false;
                String role = aUserDao.checkUserRole(username);
                
                switch (role) {
                    
                    case "user":
                        userMenu(username);
                        break;
                        
                    case "gold_user":
                        goldUserMenu(username);
                        break;
                        
                    case "admin":
                        adminMenu(username);
                        break;
                }
            }
            else {
                System.out.println("Login attempt: Failed");
            }
        }
    }    
    
    public void userMenu(String username) {
        boolean flag = true;
        
        while (flag) {
            UserDao aUserDao = new UserDao();
            User aUser = new User();
            aUser = aUserDao.findUserByUsername(username); //find the user by username and insert it's atributes in a new user object
            System.out.println("-Welcome "+username+"-");
            System.out.println("What would you like to do?");
            System.out.println("[1] Send Message.\n"
                    + "[2] View Inbox.\n"
                    + "[3] View Sent.\n"
                    + "[4] Upadte a Message.\n"
                    + "[5] Exit the Application.\n"
                    + "[6] Log Out.\n");
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            MessageDao aMessageDao = new MessageDao();
            switch(input) {
                case "1":
                    clearScreen();
                    aMessageDao.sendMessage(aUser.getId());
                    break;
                    
                case "2":
                    clearScreen();
                    aMessageDao.readInbox(aUser.getId());
                    break;
                
                case "3":
                    clearScreen();
                    aMessageDao.readSent(aUser.getId());
                    break;
                
                case "4":
                    clearScreen();
                    aMessageDao.updateMessage(aUser.getId());
                    break;
                    
                case "5":
                    flag = false;
                    break;
                  
                case "6":
                    clearScreen();
                    logIn();
                    break;
                
                default:
                    clearScreen();
                    System.out.println("Invalid Input\n");
                    break;
            }
        }
    }

    public void goldUserMenu(String username) {
        boolean flag = true;
        
        while (flag) {
            UserDao aUserDao = new UserDao();
            GoldUser aUser = new GoldUser();
            aUser = aUserDao.findGoldUserByUsername(username); //find the user by username and insert it's atributes in a new user object
            System.out.println("-Welcome "+username+"-");
            System.out.println("What would you like to do?");
            System.out.println("[1] Send Message.\n"
                    + "[2] View Inbox.\n"
                    + "[3] View Sent.\n"
                    + "[4] Update a Message.\n"
                    + "[5] Delete a Message.\n"
                    + "[6] Exit the Application.\n"
                    + "[7] Log Out.\n");
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            MessageDao aMessageDao = new MessageDao();
            switch(input) {
                case "1":
                    clearScreen();
                    aMessageDao.sendMessage(aUser.getId());
                    break;
                    
                case "2":
                    clearScreen();
                    aMessageDao.readInbox(aUser.getId());
                    break;
                
                case "3":
                    clearScreen();
                    aMessageDao.readSent(aUser.getId());
                    break;
                
                case "4":
                    clearScreen();
                    aMessageDao.updateMessage(aUser.getId());
                    break;
                    
                case "5":
                    clearScreen();
                    aMessageDao.deleteMessage(aUser.getId());
                    break;
                    
                case "6":
                    flag = false;
                    break;
                  
                case "7":
                    clearScreen();
                    logIn();
                    break;
                
                default:
                    clearScreen();
                    System.out.println("Invalid Input\n");
                    break;
            }
        }
    }

    public void adminMenu(String username) {
        boolean flag = true;
        
        while (flag) {
            UserDao aUserDao = new UserDao();
            UserDao nUserDao = new UserDao();
            Admin aUser = new Admin();
            aUser = aUserDao.findAdminUsername(username); //find the user by username and insert it's atributes in a new user object
            System.out.println("-Welcome "+username+"-");
            System.out.println("What would you like to do?");
            System.out.println("[1] Send Message.\n"
                    + "[2] View Inbox.\n"
                    + "[3] View Sent.\n"
                    + "[4] Create New User.\n"
                    + "[5] View All Users.\n"
                    + "[6] Edit User.\n"
                    + "[7] Delete User.\n"
                    + "[8] Exit the Application.\n"
                    + "[9] Log Out.\n");
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            MessageDao aMessageDao = new MessageDao();
            switch(input) {
                case "1":
                    clearScreen();
                    aMessageDao.sendMessage(aUser.getId());
                    break;
                    
                case "2":
                    clearScreen();
                    aMessageDao.readInbox(aUser.getId());
                    break;
                
                case "3":
                    clearScreen();
                    aMessageDao.readSent(aUser.getId());
                    break;
                
                case "4":
                    clearScreen();
                    nUserDao.createUser();
                    break;
                    
                case "5":
                    clearScreen();
                    nUserDao.getAllUsers();
                    break;
                    
                case "6":
                    clearScreen();
                    nUserDao.updateUser();
                    break;
                    
                case "7":
                    clearScreen();
                    nUserDao.deleteUser();
                    
                case "8":
                    flag = false;
                    break;
                  
                case "9":
                    clearScreen();
                    logIn();
                    break;
                
                default:
                    clearScreen();
                    System.out.println("Invalid Input\n");
                    break;
            }
        }
    }
    
    public void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
