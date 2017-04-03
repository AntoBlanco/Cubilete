/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.applet.AudioClip;

/**
 *
 * @author esveb
 */
public class Imagen {
    
    private ImageIcon cuadro;
    private Clip clip;
    Image imagen;
    
    public Icon imagenAjustada(JLabel label, String url){
        URL resUrl = this.getClass().getResource("/Multimedia/Imagenes/"+url);
        cuadro = new ImageIcon(resUrl);
        Icon icono = new ImageIcon(cuadro.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
        return icono;
    }
    
    public void daleplay(String url)
    {        
        try
        {
            URL resUrl = this.getClass().getResource("/Multimedia/Sonidos/"+url);
            System.out.println(resUrl.toString());
            clip=AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream( getClass().getResourceAsStream( "Multimedia/Sonidos/"+url ) ) );
            clip.start();
        }catch(Exception ex){
            System.err.println( ex.getMessage() );
        }
    }
}
