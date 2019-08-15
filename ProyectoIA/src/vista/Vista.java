package vista;

import controlador.Controlador;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Konita
 */

public class Vista extends javax.swing.JFrame {
    
    private final JPanel panel;
    private JLabel labelInicio;
    private int[][] tablero;
    private final ImageIcon pared, suelo,meta,personajeArribaD,personajeArribaI, personajeIzquierda,
                            personajeIzquierdaD,personajeIzquierdaI,personajeDerecha,personajeDerechaD,
                            personajeDerechaI,personajeAbajo,personajeAbajoD,personajeAbajoI, personajeRisaI, personajeRisaII;
    private final JButton botonWay, botonWall, botonSiguienteWall,botonCargar, botonSiguienteWay, botonParar;
    private final Controlador controlador;
    
    public Vista() throws IOException{
        panel = new JPanel();
        panel.setLayout(new GridLayout(20,20));
        
        
        pared = new ImageIcon(getClass().getResource("/icons/pared.png"));
        suelo = new ImageIcon(getClass().getResource("/icons/suelo.png"));
        meta = new ImageIcon(getClass().getResource("/icons/meta.png"));
        personajeArribaD = new ImageIcon(getClass().getResource("/icons/personajeArribaD.png"));
        personajeArribaI = new ImageIcon(getClass().getResource("/icons/personajeArribaI.png"));
        personajeIzquierda = new ImageIcon(getClass().getResource("/icons/personajeIzquierda.png"));
        personajeIzquierdaD = new ImageIcon(getClass().getResource("/icons/personajeIzquierdaD.png"));
        personajeIzquierdaI = new ImageIcon(getClass().getResource("/icons/personajeIzquierdaI.png"));
        personajeDerecha = new ImageIcon(getClass().getResource("/icons/personajeDerecha.png"));
        personajeDerechaD = new ImageIcon(getClass().getResource("/icons/personajeDerechaD.png"));
        personajeDerechaI = new ImageIcon(getClass().getResource("/icons/personajeDerechaI.png"));
        personajeAbajo = new ImageIcon(getClass().getResource("/icons/personajeAbajo.png"));
        personajeAbajoD = new ImageIcon(getClass().getResource("/icons/personajeAbajoD.png"));
        personajeAbajoI = new ImageIcon(getClass().getResource("/icons/personajeAbajoI.png"));
        personajeRisaI = new ImageIcon(getClass().getResource("/icons/personajeRisa1.png"));
        personajeRisaII = new ImageIcon(getClass().getResource("/icons/personajeRisa2.png"));

        panel.setBounds(95,50,500,500);
        panel.setVisible(true);
        add(panel);
        panel.setVisible(false);
        controlador = new Controlador(this);
        
        labelInicio = new JLabel();
        labelInicio.setBounds(95,50,500,500);
        labelInicio.setIcon(new ImageIcon("src\\icons\\Fondo.png"));
        labelInicio.setVisible(true);
        add(labelInicio);
        
        botonCargar = new JButton("Cargar Mapa");
        botonCargar.setBounds(95, 600, 100, 25);
        botonCargar.addActionListener(controlador);
        add(botonCargar);
        botonWall = new JButton("Iniciar Wall");
        botonWall.setBounds(205, 600, 100, 25);
        botonWall.addActionListener(controlador);
        botonWall.setEnabled(false);
        add(botonWall);
        botonWay = new JButton("Iniciar Way");
        botonWay.setBounds(315, 600, 100, 25);
        botonWay.addActionListener(controlador);
        botonWay.setEnabled(false);
        add(botonWay);
        botonSiguienteWall = new JButton("Siguiente");
        botonSiguienteWall.setBounds(310, 600, 100, 25);
        botonSiguienteWall.addActionListener(controlador);
        botonSiguienteWall.setVisible(false);
        add(botonSiguienteWall);
        
        botonSiguienteWay = new JButton("Siguiente");
        botonSiguienteWay.setBounds(410, 600, 100, 25);
        botonSiguienteWay.addActionListener(controlador);
        botonSiguienteWay.setVisible(false);
        add(botonSiguienteWay);
       
        botonParar = new JButton("Parar");
        botonParar.setBounds(425, 600, 100, 25);
        botonParar.addActionListener(controlador);
        botonParar.setEnabled(false);
        add(botonParar);
        
        initComponents();
        
        setSize(700,700);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
    }
    
    public File getRutaArchivo(){
        String ruta = "";
        JFileChooser fc  = new JFileChooser("src\\archivos");
        int opcion = fc.showOpenDialog(panel);
        if(opcion == JFileChooser.APPROVE_OPTION){
            File archivo = fc.getSelectedFile();
            //ruta = archivo.getAbsolutePath(); 
            return archivo;
        }
        
        return null;
    }
    
    
    public void setPanel(int[][] tablero, int direccion, int pasos){
        panel.removeAll();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                JLabel label = new JLabel();
                if(tablero[i][j] == 1){
                    label.setIcon(pared);
                    panel.add(label);
                }else if(tablero[i][j] == 2 && direccion == 2){
                    if(pasos%2 == 0){
                        label.setIcon(personajeArribaD);
                    }else{
                        label.setIcon(personajeArribaI);
                    }
                    panel.add(label);
                }else if(tablero[i][j] == 2 && direccion == 4){
                    if(pasos%2 == 0){
                        label.setIcon(personajeDerechaD);
                    }else{
                        label.setIcon(personajeDerechaI);
                    }
                    panel.add(label);
                }else if(tablero[i][j] == 2 && direccion == 6){
                    if(pasos == 0){
                        label.setIcon(personajeAbajo);
                    }else if(pasos%2 == 0){
                        label.setIcon(personajeAbajoD);
                    }else{
                        label.setIcon(personajeAbajoI);
                    }
                    panel.add(label);
                }else if(tablero[i][j] == 2 && direccion == 8){
                    if(pasos%2 == 0){
                        label.setIcon(personajeIzquierdaD);
                    }else{
                        label.setIcon(personajeIzquierdaI);
                    }
                    panel.add(label);
                }else if(tablero[i][j] == 2 && direccion == 0){
                    label.setIcon(personajeRisaI);
                    panel.add(label);
                }else if(tablero[i][j] == 2 && direccion == 1){
                    label.setIcon(personajeRisaII);
                    panel.add(label);
                }else if(tablero[i][j] == 3){
                    label.setIcon(meta);
                    panel.add(label);
                }
                else {
                    label.setIcon(suelo);
                    panel.add(label);
                }
            }
        }
        
        this.validate();
        
    }
    
     public void setPanel(String[][] tablero, int direccion, int pasos){
        panel.removeAll();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                JLabel label = new JLabel();
                if(tablero[i][j].equals("1")){
                    label.setIcon(pared);
                    panel.add(label);
                }else if(tablero[i][j].equals("2") && direccion == 2){
                    if(pasos%2 == 0){
                        label.setIcon(personajeArribaD);
                    }else{
                        label.setIcon(personajeArribaI);
                    }
                    panel.add(label);
                }else if(tablero[i][j].equals("2") && direccion == 4){
                    if(pasos%2 == 0){
                        label.setIcon(personajeDerechaD);
                    }else{
                        label.setIcon(personajeDerechaI);
                    }
                    panel.add(label);
                }else if(tablero[i][j].equals("2") && direccion == 6){
                    if(pasos == 0){
                        label.setIcon(personajeAbajo);
                    }else if(pasos%2 == 0){
                        label.setIcon(personajeAbajoD);
                    }else{
                        label.setIcon(personajeAbajoI);
                    }
                    panel.add(label);
                }else if(tablero[i][j].equals("2") && direccion == 8){
                    if(pasos%2 == 0){
                        label.setIcon(personajeIzquierdaD);
                    }else{
                        label.setIcon(personajeIzquierdaI);
                    }
                    panel.add(label);
                }else if(tablero[i][j].equals("2") && direccion == 0){
                    label.setIcon(personajeRisaI);
                    panel.add(label);
                }else if(tablero[i][j].equals("2") && direccion == 1){
                    label.setIcon(personajeRisaII);
                    panel.add(label);
                }else if(tablero[i][j].equals("3")){
                    label.setIcon(meta);
                    panel.add(label);
                }
                else {
                    label.setIcon(suelo);
                    panel.add(label);
                }
            }
        }
        
        this.validate();
        
    }
    
    public void vistaRisa(int[][] tablero) throws InterruptedException{
        int contador = 0; 
        while(contador < 6){
            setPanel(tablero, 0, 2);
            Thread.sleep(300);
            setPanel(tablero, 1, 2);
            Thread.sleep(300);
            contador++;
        }
    }
    
    public void vistaRisa(String[][] tablero) throws InterruptedException{
        int contador = 0; 
        while(contador < 6){
            setPanel(tablero, 0, 2);
            Thread.sleep(300);
            setPanel(tablero, 1, 2);
            Thread.sleep(300);
            contador++;
        }
    }
      
    public JButton getBotonWay(){
        return botonWay;
    }
    public JButton getBotonWall(){
        return botonWall;
    }
    public JButton getBotonParar(){
        return botonParar;
    }
    public JButton getBotonSiguienteWay(){
        return botonSiguienteWay;
    }
    public JButton getBotonSiguienteWall(){
        return botonSiguienteWall;
    }
    public JButton getBotonCargar(){
        return botonCargar;
    }
     public JLabel getLabelInicio(){
        return labelInicio;
    }

     public JPanel getPanel(){
         return panel;
     }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Vista().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
