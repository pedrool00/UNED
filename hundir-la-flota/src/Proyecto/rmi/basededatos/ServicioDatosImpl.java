/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.rmi.basededatos;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class ServicioDatosImpl {
    private static final String FILE_PATH = "./src/Proyecto/rmi/basededatos/ficheros/usuariosRegistrados.txt";
    private static final String SECRET_KEY = "MySecretKey12345"; // Clave secreta para el cifrado AES

    public static void escribirUsuariosRegistrados(Map<String, String> usuariosRegistrados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, String> entry : usuariosRegistrados.entrySet()) {
                String usuario = entry.getKey();
                String contrasena = entry.getValue();

                // Encriptar la contraseña
                String contrasenaEncriptada = encriptar(contrasena);

                writer.write(usuario + " " + contrasenaEncriptada); // Separar usuario y contraseña encriptada con un espacio
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo: " + ex.getMessage());
        }
    }

    public static Map<String, String> leerUsuariosRegistrados() {
        Map<String, String> usuariosRegistrados = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(" "); // Separar la línea en usuario y contraseña encriptada usando un espacio
                if (partes.length == 2) {
                    String usuario = partes[0];
                    String contrasenaEncriptada = partes[1];

                    // Desencriptar la contraseña
                    String contrasena = desencriptar(contrasenaEncriptada);

                    usuariosRegistrados.put(usuario, contrasena);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo: " + ex.getMessage());
        }

        return usuariosRegistrados;
    }

    private static String encriptar(String texto) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] textoEncriptado = cipher.doFinal(texto.getBytes());
            return Base64.getEncoder().encodeToString(textoEncriptado);
        } catch (Exception ex) {
            System.out.println("Error al encriptar: " + ex.getMessage());
        }
        return null;
    }

    private static String desencriptar(String textoEncriptado) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] textoDesencriptado = cipher.doFinal(Base64.getDecoder().decode(textoEncriptado));
            return new String(textoDesencriptado);
        } catch (Exception ex) {
            System.out.println("Error al desencriptar: " + ex.getMessage());
        }
        return null;
    }

    //Todo: Implementar los metodos para anadir la lista de puntuaciones
    //asociadas a cada usuario cuando pueda implementarlas.
}
