package individual_0;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Individual_0 {
    
    public static void main(String[] args) {
        System.out.println("Welcome To My Individual project\n");
        System.out.println("[1] Show the README.txt (info regarding the app).\n"
                + "[2] Run the App.\n"
                + "~Other input exits the main~");
        Scanner sc = new Scanner (System.in);
        String input = sc.next();
        switch (input){
            case "1":
                readTxt();
                break;
            case "2":
                Login login = new Login();
                login.logIn();
                break;
            default:
                System.out.println("Invalid Input. Try Again");
        }
        
    }
    
    static void readTxt() {
        String fileName = "README.txt";
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }   
            bufferedReader.close();         
        }catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");    
        }
    }
    
}
