/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.util.Random;

/**
 *
 * @author esveb
 */
public class Dado {
    private int valorTiro=0;
    private String cara="vacio";
    
    public void Lanzar(){
        Random Generar = new Random();    
        valorTiro = Generar.nextInt(6)+1;
        setCara();
    }
    
    private void setCara(){
        switch(valorTiro)
        {
            case 0:
                cara = "vacio";
                break;
            case 1:
                cara = "7";
                break;
            case 2:
                cara = "8";
                break;
            case 3:
                cara = "J";
                break;
            case 4:
                cara = "Q";
                break;
            case 5:
                cara = "K";
                break;
            case 6:
                cara = "As";
                break;
        }
    }
    
    public void setValor(){
        switch(cara)
        {
            case "vacio":
                valorTiro = 0;
                break;
            case "7":
                valorTiro = 1;
                break;
            case "8":
                valorTiro = 2;
                break;
            case "J":
                valorTiro = 3;
                break;
            case "Q":
                valorTiro = 4;
                break;
            case "K":
                valorTiro = 5;
                break;
            case "As":
                valorTiro = 6;
                break;
        }
    }
    
    public int getValor(){
        return this.valorTiro;
    }
    
    public void setValor(int valor){
        this.valorTiro = valor;
        setCara();
    }
    
    public String getCara(){
        return this.cara;
    }
}
