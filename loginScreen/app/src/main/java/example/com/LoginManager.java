package example.com;

public class LoginManager
{
    private String username;
    private String password;

    LoginManager(String incomingUsername, String incomingPassword)
    {
        this.username = incomingUsername;
        this.password = incomingPassword;

    }

    public boolean authenticated()
    {
        return username.equals("admin") && password.equals("admin");
    }
}
