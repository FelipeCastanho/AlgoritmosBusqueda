/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Jennifer
 */
public class EfectosSonido {

    AudioClip clip;
    int opcion;
    public EfectosSonido(int opc)
    {	
        URL url;
        opcion = opc;
        switch (opc)
        {
        case 1:
                url = EfectosSonido.class.getResource("/sonidos/Pasos.wav");
                clip = Applet.newAudioClip(url);
                break;
        case 2:
                //url = EfectosSonido.class.getResource("/sonidos/Fondo.wav");
                //clip = Applet.newAudioClip(url);
                break;
        case 3:
                url = EfectosSonido.class.getResource("/Sonidos/Risa.wav");
                clip = Applet.newAudioClip(url);
                break;
        }

    }

    public void reproducir()
    {
            this.clip.loop();

    }

    public void play()
    {
        if(opcion == 1){
            this.clip.play();
            try {
                Thread.sleep(450);
            } catch (InterruptedException ex) {
                Logger.getLogger(EfectosSonido.class.getName()).log(Level.SEVERE, null, ex);
            }
            clip.stop();
        }
        else{
            this.clip.play();
        }

            
    }

    public void parar(){
        this.clip.stop();
    }
}
