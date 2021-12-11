package com.example.proyectofintrimestre_alejandromoles.Controlador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConexionMagic {

    //creo un string el cual va a tener la url de donde se encuentra la api
    private static String URl = "https://api.magicthegathering.io/v1/cards?";

    //creo un metodo para realizar una peticion get
    public static String PeticionGet( ){
        HttpURLConnection http = null;
        String content = null;
        try {
            //creo la url pasandole como string la "URL" que habia creado antes
            URL url = new URL( URl);
            http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");

            // compruebo el codigo que me ha devuelto el servidor al realizar la peticion
            //al ser un codigo de OK, quiere decir que he recogido los datos perfectamente

            if( http.getResponseCode() == HttpURLConnection.HTTP_OK ) {

                //se codifican los datos
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader( http.getInputStream() ));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
                reader.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {

            //Se desconecta la conexión.
            if( http != null ) http.disconnect();
        }
        return content;
    }


}
