package com.ecole;

public class Authentification {

		
	    private String adminUsername;
	    private String adminPassword;

	    public Authentification(String username, String password) {
	        this.adminUsername = username;
	        this.adminPassword = password;
	    }

	    public boolean seConnecter(String username, String password) {
	        return adminUsername.equals(username) && adminPassword.equals(password);
	    }


}
