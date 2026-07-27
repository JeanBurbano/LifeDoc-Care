package model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Hashed {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String contrasena) {
        return encoder.encode(contrasena);
    }

    public static boolean verifyPassword(String contrasenaPlana, String contrasenaHasheada) {
        return encoder.matches(contrasenaPlana, contrasenaHasheada);
    }
}
