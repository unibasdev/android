package it.unibas.mediapesataclient.modello.dto;

public class RichiestaLogin {

    private String email;
    private String password;

    public RichiestaLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
