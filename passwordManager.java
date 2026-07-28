import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

// ================= USER CLASS =================
/* This class stores basic information such as username and password and use getters and setters to 
    implement the use of Encapsulation.
 */

class User
{
    private String username;
    private String password;

    User(String username,String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }
    
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
// ================= PASSWORD CHECKER CLASS =================
/* This class is basically responsible for checking the strength of the password 
It analyze different conditions such as:
- presence of uppercase letters
- presence of lowercase letters
- presence of digits
- presence of special characters
- minimum length requirement 
- also checks if there is space in the password or not.

It helps us to make sure that the user password is strong and implements the use of abstraction
*/
class PasswordChecker
{
    public static boolean hasUpperCase(String password)
    {

        for (char c:password.toCharArray())
        {
            if (Character.isUpperCase(c))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean hasLowerCase(String password)
    {

        for (char c:password.toCharArray())
        {
            if (Character.isLowerCase(c))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean hasDigits(String password)
    {
        for (char c:password.toCharArray())
        {
            if (Character.isDigit(c))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSpecialCharacter(String password)
    {
        for (char c:password.toCharArray())
        {
            if (!Character.isLetterOrDigit(c))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSpace(String password)
    {
        return password.contains(" ");
    }

    public static String checkStrength(String password)
    {
        int score = 0;

        if (password.length()>=8)
        {
            score++;
        }
        
        if (hasUpperCase(password))
        {
            score++;
        }

        if (hasLowerCase(password))
        {
            score++;
        }

        if (hasDigits(password))
        {
            score++;
        }

        if (hasSpecialCharacter(password))
        {
            score++;
        }



        if (score <= 2)
        {
            return "Weak";
        }
        else if (score == 3 || score == 4)
        {
            return "Medium";
        }
        else
        {
            return "Strong";
        }

    }
}

// ================= AUTHENTICATION SYSTEM =================
/* This class handles all user authentication operations such as
   registration, login, password change, and displaying users.
   It acts as the core controller of the system by managing user data
   and applying password validation rules before storing or updating data.
   It also uses file handling to store user information permanently.
*/
class AuthSystem
{
    private String fileName = "users.txt";
    private ArrayList <User> Users;
    public ArrayList<User> getUsers()
    {
    return Users;
    }
    public AuthSystem()
    {
        Users = new ArrayList<>();
        loadUsersFromFile();

    }
    /*This method uses file handling to load users from the file. The basic idea behind it is that we will save
    username and password in a file and read them back line by line.
    */
    public void loadUsersFromFile()
    {
        try 
        {
            File file = new File(fileName);

            if (!file.exists())
            {
                file.createNewFile();
                return;
            }

            Scanner sc = new Scanner(file);


            while (sc.hasNextLine())
            {
                String line = sc.nextLine();
                String [] data = line.split(",");

                if (data.length == 2)
                {
                    Users.add(new User(data[0], data[1]));
                } 
            }
            
            sc.close();
        }

        catch (Exception e) 
        {
            System.out.println("Error Loading File!");
        }
    }

    // This method will save data to the file

    public void saveUserToFile()
    {
        try 
        {
            FileWriter fw = new FileWriter(fileName);

            for (User u : Users)
            {
                fw.write(u.getUsername()+","+u.getPassword()+"\n");
            }

            fw.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Error Saving File!");
        }
    }


    
    public void register(String username,String password)
    {
        for (User u : Users)
        {
            if (u.getUsername().equals(username))
            {
                System.out.println("Username Already Exits! Try another Username");
                return;
            }
        }

        if (PasswordChecker.hasSpace(password))
        {
            System.out.println("Password cannot contain spaces!");
            return;

        }

        // Checking password Strength if the password is strong we continue and if not than we won't continue....
        String strength = PasswordChecker.checkStrength(password);

        System.out.println("Strength = "+strength);

        if (strength.equals("Weak"))
        {
            System.out.println("Weak Password! Try Another Password");
            return;
        }

        else if (strength.equals("Medium"))
        {
            System.out.println("Medium Password! Make a Stronger Password");
            return;
        }

        else
        {
            Users.add(new User(username, password));
            saveUserToFile();
            System.out.println("Registration Successful");
        }

    
    }
    // Function for login in for user
    public boolean login(String username, String password)
    {
        for (User u : Users)
        {
            if (u.getUsername().equals(username))
            {
                if (u.getPassword().equals(password))
                {
                    System.out.println("Login Successful!");
                    return true;
                }
                else 
                {
                    System.out.println("Password is Incorrect!");
                    return false;
                }
            }
        }
            System.out.println("Username Not Found!");
            return false;
        }


    // This method changes the password of the user if it obeys the conditions.

    public void changePassword(String username,String oldPassword, String newPassword)
    {
        for (User u:Users)
        {
            if (u.getUsername().equals(username))
            {
                if (!u.getPassword().equals(oldPassword))
                {
                    System.out.println("Incorrect Old Password!");
                    return;
                }

                if (u.getPassword().equals(newPassword))
                {
                    System.out.println("New Password cannnot be same as Old Password!");
                    return;
                }

                if (PasswordChecker.hasSpace(newPassword))
                {
                    System.out.println("The New Password Cannot Contain Spaces");
                    return;
                }

                String strength = PasswordChecker.checkStrength(newPassword);

                if (strength.equals("Weak"))
                {
                    System.out.println("Weak Password! Try Another Password");
                    return;
                }

                else if (strength.equals("Medium"))
                {
                    System.out.println("Medium Password! Try Another Password");
                    return;
                }
                
                else
                {
                    u.setPassword(newPassword);
                    saveUserToFile();
                    System.out.println("Password has been Successfully changed!");
                    return;
                }


            }
        }
        System.out.println("Username is Incorrect!");
    }


    // This method shows all users that are registered.

    public void showUsers()
    {
        System.out.println("|=============Registered Users============|");
        for(User u:Users)
        {
            System.out.println(u.getUsername());
        }
    }
}

    
