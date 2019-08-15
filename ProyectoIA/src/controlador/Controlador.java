/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.WallTracing;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.CargarDatos;
import modelo.EfectosSonido;
import modelo.WayPointNavigation;
import vista.Vista;

/**
 *
 * @author Konita
 */
public class Controlador implements ActionListener{
    Vista vista;
    WallTracing wall; 
    WayPointNavigation way;
    CargarDatos cd;
    int[][] tablero;
    String[][] tableroWay;
    ArrayList<ArrayList<String>> caminos;
    int direccion = -1;
    int arriba, abajo,derecha,izquierda = 0;
    int pasoAnterior[];
    EfectosSonido sonidoPasos;
    EfectosSonido sonidoRisa;
    boolean parar;
    
    public Controlador(Vista vista) throws IOException{
        this.vista = vista;
        
        wall = new WallTracing();
        way = new WayPointNavigation();
        cd = new CargarDatos();
        pasoAnterior = new int[2];
        pasoAnterior[0] = 0;
        pasoAnterior[1] = 0;
        sonidoPasos = new EfectosSonido(1);
        sonidoRisa = new EfectosSonido(3);
        parar = false;
       // tablero = cd.cargarTableroWallTracing("src\\archivos\\Inicial.txt");
        //vista.setPanel(tablero, direccion, abajo);
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.getBotonWall()){
            parar = false;
            wall.setGameOver(false);
            vista.getBotonWall().setEnabled(false);
            vista.getBotonParar().setEnabled(true);
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if(!wall.getGameOver()){
                        vista.getBotonSiguienteWall().doClick();
                    }else{
                        vista.getBotonWall().setEnabled(true);
                        if(!parar){
                            sonidoRisa.play();
                            tablero = wall.getTablero();
                            try {
                                vista.vistaRisa(tablero);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        timer.cancel();
                    }
                }
            };
        
        timer.schedule(task, 0, 600);
        
        }if(e.getSource() == vista.getBotonWay()){
            parar = false;
            way.setGameOver(false);
            vista.getBotonWay().setEnabled(false);
            vista.getBotonParar().setEnabled(true);
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if(!way.getGameOver()){
                        vista.getBotonSiguienteWay().doClick();
                    }else{
                        vista.getBotonWay().setEnabled(true);
                        if(!parar){
                            sonidoRisa.play();
                            tableroWay = way.getTablero();
                            try {
                                vista.vistaRisa(tableroWay);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        timer.cancel();
                    }
                }
            };
        
        timer.schedule(task, 0, 600);
        
        }else if(e.getSource() == vista.getBotonSiguienteWay()){
            int[] pasos;
            pasos = way.nextStep();
            tableroWay = way.getTablero();
            direccion = 2;
            direccion = generarDireccion(pasos, pasoAnterior);
            //vista.setPanel(tableroWay, 2, 0);
            sonidoPasos.play();
            if(direccion == 2){
                arriba++;
                vista.setPanel(tableroWay, direccion, arriba);
            }else if(direccion == 4){
                derecha++;
                vista.setPanel(tableroWay, direccion, derecha);
            }else if(direccion == 6){
                abajo++;
                vista.setPanel(tableroWay, direccion, abajo);
                
            }else{
                izquierda++;
                vista.setPanel(tableroWay, direccion, izquierda);
            }
            pasoAnterior = pasos;
            
        }else if(e.getSource() == vista.getBotonSiguienteWall()){
            int[] pasos;
            pasos = wall.nextStep();
            tablero = wall.getTablero();
            direccion = wall.getDireccion();
            sonidoPasos.play();
            if(direccion == 2){
                arriba++;
                vista.setPanel(tablero, direccion, arriba);
            }else if(direccion == 4){
                derecha++;
                vista.setPanel(tablero, direccion, derecha);
            }else if(direccion == 6){
                abajo++;
                vista.setPanel(tablero, direccion, abajo);
                
            }else{
                izquierda++;
                vista.setPanel(tablero, direccion, izquierda);
            }
            
        }else if(e.getSource() == vista.getBotonCargar()){
            File ruta = vista.getRutaArchivo();
            vista.getLabelInicio().setVisible(false);
            vista.getPanel().setVisible(true);
            try {
                tablero = cd.cargarTableroWallTracing(ruta.getAbsolutePath());
                tableroWay = cd.cargarTableroWaypoint(ruta.getAbsolutePath());
                caminos = cd.cargarCaminos(ruta.getParent()+"\\caminos-"+ruta.getName());
                wall.setGameOver(false);
                way.setGameOver(false);
                wall.setTablero(tablero);
                way.setTablero(tableroWay);
                way.setCaminos(caminos);
                
                int aux[] = way.getPosicionAgente();
                pasoAnterior[0] = aux[0];
                pasoAnterior[1] = aux[1];
                direccion = wall.getDireccion();
                vista.setPanel(tablero, direccion, abajo);
                vista.getBotonWall().setEnabled(true);
                vista.getBotonWay().setEnabled(true);
            } catch (IOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == vista.getBotonParar()){
            way.setGameOver(true);
            wall.setGameOver(true);
            vista.getBotonParar().setEnabled(false);
            parar = true;
            
        }
    }

    private int generarDireccion(int[] pasos, int[] pasoAnterior) {
        int resultado = 2;
        int resta1 = pasos[0] - pasoAnterior[0];
        int resta2 = pasos[1] - pasoAnterior[1];
        if(resta1 == 0 && resta2 > 0) resultado = 4;
        else if(resta1 == 0 && resta2 < 0) resultado = 8;
        else if(resta1 > 0 && resta2 == 0) resultado = 6;
        else if(resta1 < 0 && resta2 == 0) resultado = 2;
        else if(resta1 < 0 && resta2 < 0) resultado = 8;
        else if(resta1 > 0 && resta2 < 0) resultado = 8;
        else if(resta1 < 0 && resta2 > 0) resultado = 4;
        else if(resta1 > 0 && resta2 > 0) resultado = 4;
        return resultado;
    }
     
}
