/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Objetos.Comodines;
import Objetos.Dado;
import Objetos.Imagen;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author esveb
 */
public class Tablero extends javax.swing.JFrame {

    /**
     * Creates new form Tablero
     */
    private Object[] ordenJugadores;
    private Dado dados[] = new Dado[5];
    private int jugadores=0;
    private int turno=0;
    private String jugadorActual="0";
    private int lanzarMax=3;
    private int lanzarRondaMax=1;
    private int lanzarJugador=1;
    private Comodines jugada;
    
    public Tablero(Object[] ordenJugadores) {
        initComponents();
        Imagen imagen2 = new Imagen();
        jLKintaro.setIcon(imagen2.imagenAjustada(jLKintaro,"kintaro.gif"));
        this.ordenJugadores = ordenJugadores;
        this.jugadores = ordenJugadores.length;
        cargarTabla(jTablaRonda,"0");
        cargarTabla(jTablaGlobal,"0");
        for(int i=0; i<dados.length; i++)//Crea Objetos de tipo dado para el vector
            dados[i] = new Dado();
        turnoJugador();
        this.setTitle("Cubilete - Grupo Exploit");
        this.setLocationRelativeTo(null);
        this.repaint();
    }
    
    private void turnoJugador(){//Se debe llamar en evento boton siguiente
        turno++;
        jugadorActual = String.valueOf(ordenJugadores[turno-1]);
        jLJugador.setText(jugadorActual);
        jLPuntosGlobal.setText(consultarPuntajeJugador(jTablaGlobal, jugadorActual));
        jLPuntosRonda.setText("0");
        dado1.setText("--");
        dado1.setSelected(false);
        dado2.setText("--");
        dado2.setSelected(false);
        dado3.setText("--");
        dado3.setSelected(false);
        dado4.setText("--");
        dado4.setSelected(false);
        dado5.setText("--");
        dado5.setSelected(false);
    }
    
    private void cargarTabla(JTable tabla, String dato){
        DefaultTableModel modeloJugadores = (DefaultTableModel) tabla.getModel();
        for (int i = 0; i < jugadores; i++) {           
            modeloJugadores.addRow(new Object[] {ordenJugadores[i], dato});
        }
    }
    
    private void actualizarTabla(JTable tabla, String dato){
        for (int i = 0; i < jugadores; i++) {           
            tabla.setValueAt(ordenJugadores[i], i, 0);
            tabla.setValueAt(dato, i, 1);
        }
    }
    
    private String consultarPuntajeJugador(JTable tabla, String jugador){
        String resp = "vacio";
        for(int i=0; i<tabla.getRowCount();i++){
            if(jugador.equals(tabla.getValueAt(i, 0).toString()))
                resp = String.valueOf(tabla.getValueAt(i, 1));//int row, int col
        }
        return resp;
    }
    
    private void actualizarJugador(JTable tabla, String jugador, int puntaje){
        for(int i=0; i<tabla.getRowCount();i++){
            if(jugador.equals(tabla.getValueAt(i, 0).toString())){
                tabla.setValueAt(String.valueOf(puntaje+Integer.parseInt(tabla.getValueAt(i, 1).toString())),i, 1);                
            }
        }
    }
    
    private void ordenarTurnosRonda(){
        for(int i=0; i<jTablaRonda.getRowCount();i++){
            ordenJugadores[i]=jTablaRonda.getValueAt(i, 0);
            System.out.println(ordenJugadores[i]);
        }
    }
    
    private void ordenarJugadores(JTable tabla){
        DefaultTableModel tablaPedidos = (DefaultTableModel) tabla.getModel();
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tablaPedidos);
        sorter.toggleSortOrder(1);
        tabla.setRowSorter(sorter);
        tabla.getRowSorter().toggleSortOrder(1);
        System.out.println("Tabla Ordenada segun puntos ");
        for(int i=0; i<tabla.getRowCount(); i++){
            System.out.println("Jugador "+tabla.getValueAt(i, 0).toString());
            System.out.println("puntos "+tabla.getValueAt(i, 1).toString());
        }
    }
    
    private void Carabinas(){
        Comodines juego = new Comodines(dados);
        jLPuntosRonda.setText(String.valueOf(juego.puntuacion()));
        switch(juego.ganaRonda()){
            case 2:
                actualizarJugador(jTablaRonda, jugadorActual, juego.puntuacion());
                ordenarJugadores(jTablaRonda);
                actualizarJugador(jTablaGlobal, jTablaRonda.getValueAt(0,0).toString(),2);
                ordenarJugadores(jTablaGlobal);
                ordenarTurnosRonda();
                turno=0;
                lanzarJugador=1;
                actualizarTabla(jTablaRonda,"0");
                turnoJugador();
                new Puntos("Felicidades Ha hecho Carabina de Reyes Sucia, el Jugador "+jugadorActual+" Ganó ","2").setVisible(true);
                System.out.println("Ganó 2 puntos, termina ronda");
                break;
            case 5:
                actualizarJugador(jTablaRonda, jugadorActual, juego.puntuacion());
                ordenarJugadores(jTablaRonda);
                actualizarJugador(jTablaGlobal, jTablaRonda.getValueAt(0,0).toString(),5);
                ordenarJugadores(jTablaGlobal);
                ordenarTurnosRonda();
                turno=0;
                lanzarJugador=1;
                actualizarTabla(jTablaRonda,"0");
                turnoJugador();
                new Puntos("Felicidades Ha hecho Carabina de Reyes Natural,el Jugador "+jugadorActual+" Ganó ","5").setVisible(true);
                System.out.println("Gano 5 puntos, termina ronda");
                break;
            case 10:
                actualizarJugador(jTablaGlobal, jTablaRonda.getValueAt(0,0).toString(),10);
                ordenarJugadores(jTablaGlobal);
                terminarJuego();
                lanzarJugador=1;
                JOptionPane.showMessageDialog(this,"Felicidades Ha hecho Carabina de Ases ha Ganado el Juego!");
                new Ganador(jTablaGlobal.getValueAt(0,0).toString()).setVisible(true);
                System.out.println("Gano 10 puntos, termina juego");
                break;
            default:
                System.out.println("No ha ganado puntos por Carabinas");
                break;
        }
    }
    
    private void terminarJuego(){
        JOptionPane.showMessageDialog(this, "Felicidades, ha ganado el juego jugador "+jugadorActual);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBLanzar = new javax.swing.JButton();
        jBSiguiente = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        dado1 = new javax.swing.JToggleButton();
        dado2 = new javax.swing.JToggleButton();
        dado3 = new javax.swing.JToggleButton();
        dado4 = new javax.swing.JToggleButton();
        dado5 = new javax.swing.JToggleButton();
        jLJugador = new javax.swing.JLabel();
        jLPuntosGlobal = new javax.swing.JLabel();
        jLPuntosRonda = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaGlobal = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablaRonda = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLKintaro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Jugador");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Puntuación Global");

        jBLanzar.setText("Lanzar");
        jBLanzar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBLanzarMouseClicked(evt);
            }
        });

        jBSiguiente.setText("Siguiente Jugador");
        jBSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBSiguienteMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Puntuación Ronda");

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados!"));

        dado1.setBackground(new java.awt.Color(255, 255, 255));
        dado1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        dado1.setForeground(new java.awt.Color(255, 0, 0));
        dado1.setText("--");

        dado2.setBackground(new java.awt.Color(255, 255, 255));
        dado2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        dado2.setForeground(new java.awt.Color(255, 0, 0));
        dado2.setText("--");

        dado3.setBackground(new java.awt.Color(255, 255, 255));
        dado3.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        dado3.setForeground(new java.awt.Color(255, 0, 0));
        dado3.setText("--");

        dado4.setBackground(new java.awt.Color(255, 255, 255));
        dado4.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        dado4.setForeground(new java.awt.Color(255, 0, 0));
        dado4.setText("--");

        dado5.setBackground(new java.awt.Color(255, 255, 255));
        dado5.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        dado5.setForeground(new java.awt.Color(255, 0, 0));
        dado5.setText("--");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(dado4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(dado5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(dado1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(dado2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(dado3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dado1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dado2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dado3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dado5, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(dado4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLJugador.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLJugador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLJugador.setText("--");

        jLPuntosGlobal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLPuntosGlobal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLPuntosGlobal.setText("--");

        jLPuntosRonda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLPuntosRonda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLPuntosRonda.setText("--");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLJugador)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLPuntosGlobal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLPuntosRonda)
                        .addGap(34, 34, 34))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jBLanzar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBSiguiente))
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(12, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLJugador)
                    .addComponent(jLPuntosGlobal)
                    .addComponent(jLPuntosRonda))
                .addGap(14, 14, 14)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLanzar)
                    .addComponent(jBSiguiente))
                .addGap(0, 49, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tablero", jPanel1);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla Puntuación Global"));

        jTablaGlobal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Jugador", "Puntuación Global"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTablaGlobal);
        if (jTablaGlobal.getColumnModel().getColumnCount() > 0) {
            jTablaGlobal.getColumnModel().getColumn(0).setResizable(false);
            jTablaGlobal.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTablaGlobal.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla Puntuación Ronda"));

        jTablaRonda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Jugador", "Puntuación Ronda"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTablaRonda);
        if (jTablaRonda.getColumnModel().getColumnCount() > 0) {
            jTablaRonda.getColumnModel().getColumn(0).setResizable(false);
            jTablaRonda.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTablaRonda.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Puntuación", jPanel2);

        jLabel4.setFont(new java.awt.Font("Harlow Solid Italic", 0, 24)); // NOI18N
        jLabel4.setText("Grupo Exploit");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(157, 157, 157))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLKintaro, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLKintaro, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(21, 21, 21))
        );

        jLKintaro.getAccessibleContext().setAccessibleName("jLKintaro");

        jTabbedPane1.addTab("Acerca De...", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBLanzarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLanzarMouseClicked
        // TODO add your handling code here
        if(jugadorActual.equals(ordenJugadores[0].toString())&&lanzarRondaMax<lanzarMax)//permite que el primer jugador determine un maximo de 3 lances
                lanzarRondaMax=lanzarJugador;
        System.out.println("LanzarJugador = "+lanzarJugador);
        System.out.println("LanzarRondaMax = "+lanzarRondaMax);
        if(lanzarJugador <= lanzarRondaMax){//condicion que trabaja de acuerdo al turno del jugador
            if(lanzarJugador>1){
                if(!dado1.isSelected()){
                    dados[0].Lanzar();
                    dado1.setText(dados[0].getCara());}
                else{
                    dados[0].setValor();
                }
                if(!dado2.isSelected()){
                    dados[1].Lanzar();
                    dado2.setText(dados[1].getCara());}
                else{
                    dados[1].setValor();
                }
                if(!dado3.isSelected()){
                    dados[2].Lanzar();
                    dado3.setText(dados[2].getCara());}
                else{
                    dados[2].setValor();
                }
                if(!dado4.isSelected()){
                    dados[3].Lanzar();
                    dado4.setText(dados[3].getCara());}
                else{
                    dados[3].setValor();
                }
                if(!dado5.isSelected()){
                    dados[4].Lanzar();
                    dado5.setText(dados[4].getCara());}
                else{
                    dados[4].setValor();
                }
            }
            else{
                for(int i=0; i<dados.length; i++){
                    dados[i].Lanzar();}
                dado1.setText(dados[0].getCara());
                dado2.setText(dados[1].getCara());
                dado3.setText(dados[2].getCara());
                dado4.setText(dados[3].getCara());
                dado5.setText(dados[4].getCara());
            }
            //Muestra de puntajeRonda y validacion de carabinas
            Carabinas();
            System.out.println("turno = "+turno +", jugadores = "+jugadores+ ", jugadorActual = "+jugadorActual);
        }
        else{
            JOptionPane.showMessageDialog(null, "Ha terminado su cantidad de tiros permitidos en la ronda");
            jBLanzar.setVisible(false);
        }
        lanzarJugador++;
    }//GEN-LAST:event_jBLanzarMouseClicked

    private void jBSiguienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSiguienteMouseClicked
        // TODO add your handling code here:
        jBLanzar.setVisible(true);
        actualizarJugador(jTablaRonda, jugadorActual, Integer.parseInt(jLPuntosRonda.getText()));
        if(turno==jugadores){//Finaliza ronda;
            turno=0;
            ordenarJugadores(jTablaRonda);
            actualizarJugador(jTablaGlobal, jTablaRonda.getValueAt(0,0).toString(),1);
            new Puntos("Felicidades el Jugador "+jTablaRonda.getValueAt(0,0).toString()+" Ganó ","1").setVisible(true);
            //ordenarJugadores(jTablaGlobal);
            ordenarTurnosRonda();
            actualizarTabla(jTablaRonda,"0");
            if(jTablaGlobal.getValueAt(0, 1).toString().equals("10")){
                new Ganador(jTablaGlobal.getValueAt(0,0).toString()).setVisible(true);
                this.hide();
            }
        }
        lanzarJugador=1;
        turnoJugador();
    }//GEN-LAST:event_jBSiguienteMouseClicked

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton dado1;
    private javax.swing.JToggleButton dado2;
    private javax.swing.JToggleButton dado3;
    private javax.swing.JToggleButton dado4;
    private javax.swing.JToggleButton dado5;
    private javax.swing.JButton jBLanzar;
    private javax.swing.JButton jBSiguiente;
    private javax.swing.JLabel jLJugador;
    private javax.swing.JLabel jLKintaro;
    private javax.swing.JLabel jLPuntosGlobal;
    private javax.swing.JLabel jLPuntosRonda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTablaGlobal;
    private javax.swing.JTable jTablaRonda;
    // End of variables declaration//GEN-END:variables
}
