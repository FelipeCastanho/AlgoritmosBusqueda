package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class CargarDatos {
    File archivo; 
    FileReader fr;
    BufferedReader br;
    String linea;

    public CargarDatos() {
        
    }
    
    public int[][] cargarTableroWallTracing(String direccion) throws FileNotFoundException, IOException{
        int tablero[][] = new int[20][20];
        archivo = new File (direccion);
        fr = new FileReader(archivo);
        br = new BufferedReader(fr);
        String linea;
        String[] respuesta;
        for(int i = 0; i < 20; i++){ 
            linea = br.readLine();
            respuesta = linea.split(" ");
            for(int j = 0; j < 20; j++){
                try{
                    tablero[i][j] = Integer.parseInt(respuesta[j]);
                }
                catch(NumberFormatException a){
                    tablero[i][j] = 0;
                }
                
            }     
        }
       
        return tablero;
    }
    
     public String[][] cargarTableroWaypoint(String direccion) throws FileNotFoundException, IOException{
        String tablero[][] = new String[20][20];
        archivo = new File (direccion);
        fr = new FileReader(archivo);
        br = new BufferedReader(fr);
        String linea;
        String[] respuesta;
        for(int i = 0; i < 20; i++){ 
            linea = br.readLine();
            respuesta = linea.split(" ");
            for(int j = 0; j < 20; j++){
                tablero[i][j] = respuesta[j];
            }     
        }
       
        return tablero;
    }
    
    public ArrayList<ArrayList<String>> cargarCaminos(String direccion) throws FileNotFoundException, IOException{
        ArrayList<ArrayList<String>> caminos = new ArrayList<ArrayList<String>>();
        archivo = new File (direccion);
        fr = new FileReader(archivo);
        br = new BufferedReader(fr);
        String linea;
        String[] respuesta;
        linea = br.readLine();
        while(linea != null){
            respuesta = linea.split(" ");
            ArrayList<String> fila = new ArrayList<String>();
            for(int j = 0; j < respuesta.length; j++){
                fila.add(respuesta[j]);
            }     
            caminos.add(fila);
            linea = br.readLine();
        }
        return caminos;
    }
}
