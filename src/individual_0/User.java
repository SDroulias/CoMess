package individual_0;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;

//    Constructors

    public User() {
    }

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
//    Setters & Getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User: " + "id=" + id + ", username=" + username + ", password=" + password + ", role=" + role;
    }
    
//    public User UserLogin(String username, String password) {
//        try (Connection con = Database.getConnection()) {
//            String query = "SELECT * FROM `users` WHERE `username`=? AND `password`=?;";
//            
//            try (PreparedStatement ps = con.prepareStatement(query)) {
//                ps.setString(1, username);
//                ps.setString(2, password);
//                
//                ResultSet rs = ps.executeQuery();
//                if (rs.next()) {
//                    User user = new User();
//                    user.setId(rs.getInt(1));
//                    user.setUsername(rs.getString(2));
//                    user.setPassword(rs.getString(3));
//                    user.setRole(rs.getString(4));
//                
//                    System.out.println("User "+ username + " Found");
//                    return user;
//                }
//            }
//        }catch (SQLException ex) {
//            System.out.println("Error: " + ex);
//        }
//        System.out.println("Not Found");
//        return null;        
//    }
    
    

}