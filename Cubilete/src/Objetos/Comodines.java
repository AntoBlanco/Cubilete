/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

/**
 *
 * @author esveb
 */
public class Comodines {
    //enviar dados al constructor para trabajar desde alli y retornar datos de juego
    private Dado[] dados;
    public Comodines(Dado[] dados){
        this.dados = dados;
    }
    
    public int puntuacion(){
        //Los Ases sirven como comodines y pueden tomar el lugar de cualquier cara en un grupo. 
        String listaPuntos="";
        int puntaje = 0;
        int s=0,o=0,j=0,q=0,k=0;
        int dadoMayor = dadoMayor();
        System.out.println("dado mayor "+dadoMayor);
        for(int i=0; i<dados.length; i++){
            if(dados[i].getCara().equals("As")){
                dados[i].setValor(dadoMayor);
                System.out.println("dados "+i+" "+dados[i].getValor());
            }
        }
        for(int i=0; i<dados.length; i++){
            switch(dados[i].getValor()){
                case 1:
                    s++;
                    break;
                case 2:
                    o++;
                    break;
                case 3:
                    j++;
                    break;
                case 4:
                    q++;
                    break;
                case 5:
                    k++;
                    break;
                default:
                    System.out.println("Hay una variable Rebelde");
                    break;
            }
        }
        if(s>1)
            puntaje+=(1*s);
        if(o>1)
            puntaje+=(2*o);
        if(j>1)
            puntaje+=(3*j);
        if(q>1)
            puntaje+=(4*q);
        if(k>1)
            puntaje+=(5*k);
        System.out.println("puntaje "+puntaje);
        return puntaje;
    }
    
    public int ganaRonda(){
        if(carabinaReyesSucia())
            return 2;
        if(carabinaReyesNatural())
            return 5;
        if(carabinaAses())
            return 10;
        return 0;
    }
    
    //Retorna el dado con mayor puntaje
    private int dadoMayor(){
        int iNumeroMayor=0;
        //Presuponemos que el numero mayor es el primero
        iNumeroMayor = (dados[0].getValor()>=6)?-1:dados[0].getValor();
        for (int i=1;i<dados.length;i++){
            if ((dados[i].getValor()>iNumeroMayor)&&(!dados[i].getCara().equals("As"))){
                iNumeroMayor = dados[i].getValor();
            } 
        }
        return (iNumeroMayor==-1)?5:iNumeroMayor;
    }
    
    //Se le llama Carabina a la tirada 5 Ases o Reyes
    
    private boolean carabinaReyesSucia(){
        int contador = 0;
        //Una Carabina de Reyes "sucia" (con Ases) vale 2 puntos y termina la ronda.
        for(int i=0; i<dados.length; i++)
            if(dados[i].getCara().equals("As")||dados[i].getCara().equals("K"))
                contador++;
        return contador==dados.length;
    }
    
    private boolean carabinaReyesNatural(){
        int contador = 0;
        //Una Carabina de Reyes Natural (sin Ases) vale 5 puntos y termina la ronda 
        for(int i=0; i<dados.length; i++)
            if(dados[i].getCara().equals("K"))
                contador++;
        return contador==dados.length;
    }
    
    private boolean carabinaAses(){
        int contador = 0;
        //Una Carabina de Ases vale 10 punto y termina el juego.
        for(int i=0; i<dados.length; i++)
            if(dados[i].getCara().equals("As"))
                contador++;
        return contador==dados.length;
    }
}
