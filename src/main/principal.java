
package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import static main.Tokens.Error;

public class principal extends javax.swing.JFrame {
    
    private int tipoValor;
    private LineaNum numlinea;
    private SistemaArch archivo;
    Lexer lexico;
    TablaSimbolos tablaSimbolos;
    public boolean band = true, siRegistra, chkv;
    public Stack<String> pilaPrincipal = new Stack();
    public ArrayList<String> simbolosTerm = new ArrayList<>(Arrays.asList("id", "num", "int", "float", "char",
            ",", ";", "+", "-", "*", "/", "=", "(", ")", "$")); //Simbolos terminales o tokens
    public ArrayList<String> columnas = new ArrayList<>(Arrays.asList("id", "num", "int", "float", "char",
            ",", ";", "+", "-", "*", "/", "=", "(", ")", "$", "P", "Tipo",
            "V", "A", "S", "E", "T", "F")); //Columnas de la tabla sintactica
    public String produccionesP[][] = {
        {"P'", "P"}, {"P", "Tipo id V"}, {"P", "A"}, {"Tipo", "int"}, {"Tipo", "float"}, {"Tipo", "char"},
        {"V", ", id V"}, {"V", "; P"}, {"A", "id = S ;"}, {"S", "+ E"}, {"S", "- E"}, {"S", "E"},
        {"E", "E + T"}, {"E", "E - T"}, {"E", "T"}, {"T", "T * F"}, {"T", "T / F"}, {"T", "F"},
        {"F", "( E )"}, {"F", "id"}, {"F", "num"}}; //Producciones de la gramatica 
    public String[][] tablaSint = {
        {"I7", "err", "I4", "I5", "I6", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I1", "I2", "err", "I3", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "P0", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"I8", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "P2", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"P3", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"P4", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"P5", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I9", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "I11", "I12", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I10", "err", "err", "err", "err", "err"},
        {"I20", "I21", "err", "err", "err", "err", "err", "I14", "I15", "err", "err", "err", "I19", "err", "err", "err", "err", "err", "err", "I13", "I16", "I17", "I18"},
        {"err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "P1", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"I22", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"I7", "err", "I4", "I5", "I6", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I23", "I2", "err", "I3", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "I24", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"I20", "I21", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I19", "err", "err", "err", "err", "err", "err", "err", "I25", "I17", "I18"},
        {"I20", "I21", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I19", "err", "err", "err", "err", "err", "err", "err", "I26", "I17", "I18"},
        {"err", "err", "err", "err", "err", "err", "P11", "I27", "I28", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "P14", "P14", "P14", "I29", "I30", "err", "err", "P14", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "P17", "P17", "P17", "P17", "P17", "err", "err", "P17", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"I20", "I21", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I19", "err", "err", "err", "err", "err", "err", "err", "I31", "I17", "I18"},
        {"err", "err", "err", "err", "err", "err", "P19", "P19", "P19", "P19", "P19", "err", "err", "P19", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "P20", "P20", "P20", "P20", "P20", "err", "err", "P20", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "I11", "I12", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I32", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "P7", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "P8", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "P9", "I27", "I28", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "P10", "I27", "I28", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"I20", "I21", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I19", "err", "err", "err", "err", "err", "err", "err", "err", "I33", "I18"},
        {"I20", "I21", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I19", "err", "err", "err", "err", "err", "err", "err", "err", "I34", "I18"},
        {"I20", "I21", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I19", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I35"},
        {"I20", "I21", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I19", "err", "err", "err", "err", "err", "err", "err", "err", "err", "I36"},
        {"err", "err", "err", "err", "err", "err", "err", "I27", "I28", "err", "err", "err", "err", "I37", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "P6", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "P12", "P12", "P12", "I29", "I30", "err", "err", "P12", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "P13", "P13", "P13", "I29", "I30", "err", "err", "P13", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "P15", "P15", "P15", "P15", "P15", "err", "err", "P15", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "P16", "P16", "P16", "P16", "P16", "err", "err", "P16", "err", "err", "err", "err", "err", "err", "err", "err", "err"},
        {"err", "err", "err", "err", "err", "err", "P18", "P18", "P18", "P18", "P18", "err", "err", "P18", "err", "err", "err", "err", "err", "err", "err", "err", "err"},};

    public principal() {
        initComponents();
        Inicio();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    
    private void procesoComp(){
        AnalisisLexico();
    }
        
    private void RegistroTablaSimb(String valor){
        ChecarSiRegistra(valor);
        String nombreSimbolo = lexico.lexema;
        InfoSimbolo info;
        if(valor.equals("id") && siRegistra==true){ //Este if es para ver si se registra el id
            System.out.println(nombreSimbolo);
            //InfoSimbolo infoSimbolo = new InfoSimbolo(nombreSimbolo, tipoSimbolo, valorSimbolo, lexico.posLinea+1);
            InfoSimbolo infoSimbolo = new InfoSimbolo(nombreSimbolo, tipoValor, null, lexico.posLinea+1);
            tablaSimbolos.agregarSimbolo(nombreSimbolo, infoSimbolo);
        }else if(valor.equals("id") && siRegistra==false){ //este if es para ver si estamos declarando
            if(tablaSimbolos.contieneSimbolo(nombreSimbolo)){ //Este if sirve checar si un id si se declaro
                info = tablaSimbolos.obtenerSimbolo(nombreSimbolo);
                System.out.println(info.getTipo());
            }else{
                String errorID = "Error detectado: La variable \""+nombreSimbolo+"\" no se declaro" + "\n"
                + "COMPILACION INTERRUMPIDA DEBIDO AL ERROR DE IDENTIFICADOR DETECTADO" + "\n";
                txtSemantico.append(errorID);
                txtAreaTerminal.append(errorID);
                band = false;
            }
        }
    }
    
    private void ChecarSiRegistra(String valor){
        if(valor.equals(";")){
            siRegistra = false;
        }else if(valor.equals("int")||valor.equals("float")||valor.equals("char")){
            siRegistra = true;
            switch (valor) {
                case "int":
                    tipoValor = 0;
                    break;
                case "float":
                    tipoValor = 1;
                    break;
                case "char":
                    tipoValor = 2;
                    break;
            }
        }
    }

    private void AnalisisLexico() {
        tablaSimbolos = new TablaSimbolos();
        File codigo = new File("archivo.txt");
        try (FileOutputStream output = new FileOutputStream(codigo);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF-8"))) {
            byte[] bytes = txtCodigoBase.getText().getBytes();
            output.write(bytes);

            lexico = new Lexer(entrada);
            String lexRec = "";
            String errorLex = "";

            while (!lexico.yyatEOF() && band) {
                Tokens token = lexico.yylex();
                int lineaActual = lexico.posLinea + 1;

                if (token == null) {
                    AnalisisSintactico("$", lineaActual);
                    lexRec += "Análisis léxico finalizado";
                    txtLexico.setText(lexRec);
                    return;
                }

                switch (token) {
                    case Error -> {
                        lexRec += "Error léxico en la línea " + lineaActual +
                                " debido a la detección de un símbolo inapropiado: " + lexico.lexema + "\n";
                        errorLex += "Error léxico en la línea " + lineaActual +
                                " debido a la detección de un símbolo inapropiado: " + lexico.lexema +
                                "\nCOMPILACIÓN INTERRUMPIDA DEBIDO AL ERROR LÉXICO DETECTADO";
                        txtLexico.setText(lexRec);
                        txtAreaTerminal.setText(errorLex);
                        //break;
                        band = false;
                        return;
                    }
                    default -> {
                        String valorToken = (token.getValor() == null) ? token.toString() : token.getValor();
                        lexRec += valorToken + "\n";
                        AnalisisSintactico(valorToken, lineaActual);
                        txtLexico.setText(lexRec);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void AnalisisSintactico(String token, int nlinea) {
        String elementoPilaP, accionTabla;
        RegistroTablaSimb(token);
        int numEstado, columnaTabla;
        while (true) {
            elementoPilaP = pilaPrincipal.peek();
            numEstado = Integer.parseInt(elementoPilaP.substring(1));
            columnaTabla = columnas.indexOf(token);
            accionTabla = tablaSint[numEstado][columnaTabla];
            if (accionTabla.equals("err")) {
                ErrorSint(nlinea, numEstado, token);
                return;
            }
            if (accionTabla.equals("P0")) {
                ProduccionCero(accionTabla);
                return;
            }
            if (accionTabla.substring(0, 1).equals("I")) {
                EstadoI(accionTabla, token);
                return;
            } else {
                EstadoProd(accionTabla);
            }
        }
    }
    
    private void EstadoI(String accionTabla, String token){
        txtSintactico.append(pilaPrincipal + "\t Desplaza "+token+" a "+accionTabla+"\n");
        pilaPrincipal.push(token);
        pilaPrincipal.push(accionTabla);
    }
    
    private void EstadoProd(String accionTabla){
        String prod, prodRedux;
        int nuevoEstado;
        prod = produccionesP[Integer.parseInt(accionTabla.substring(1))][0];
        prodRedux = produccionesP[Integer.parseInt(accionTabla.substring(1))][1];
        String pr[] = prodRedux.split(" ");
        txtSintactico.append(pilaPrincipal + "\t Se  genera "+accionTabla+" "+prod+"->"+prodRedux+"\n");
        for(int i = 0; i<pr.length*2; i++) {
            pilaPrincipal.pop();
        }
        nuevoEstado = Integer.parseInt(pilaPrincipal.peek().substring(1));
        pilaPrincipal.push(prod);
        pilaPrincipal.push(tablaSint[nuevoEstado][columnas.indexOf(prod)]);
    }
    
    private void ProduccionCero(String accionTabla){
        String fin ="COMPILACION FINALIZADA CON EXITO";
        String sintFin = "Analisis Sintactico Finalizado";
        txtSintactico.append(pilaPrincipal +  "\t Se genera " + accionTabla + ". Se acepta la cadena \n");
        txtSintactico.append(sintFin);
        txtAreaTerminal.append(fin);
    }
    
    private void ErrorSint(int nlinea, int numEstado, String token){
        txtSintactico.append(pilaPrincipal + "\t Se genera un error \n");
        String errorSint = "Error sintactico en la linea " + (nlinea) 
        + SimbEsperado(token, numEstado) + "\n"
        + "COMPILACION INTERRUMPIDA DEBIDO AL ERROR SINTACTICO DETECTADO" + "\n";
        txtSintactico.append(errorSint);
        txtAreaTerminal.append(errorSint);
        band = false;
    }
    
    private String SimbEsperado(String tok, int numEstado){
        String error;
        if(tok.equals("$"))
            error = " se acabo la cadena de texto pero aun se esperaba: ";
        else
            error = " no se esperaba "+ tok +" y lo que se esperaba era: ";
        for(int i=0; i<15; i++){
            if(!tablaSint[numEstado][i].equals("err")){
                error += simbolosTerm.get(i)+", ";
            }
        }
        return error;
    }

    private void InicializarPilas() {
        pilaPrincipal.clear();
        pilaPrincipal.push("$");
        pilaPrincipal.push("I0");
    }

    private void Inicio() {
        archivo = new SistemaArch();
        setTitle("Compilador LR");
        numlinea = new LineaNum(txtCodigoBase);
        jScrollPane2.setRowHeaderView(numlinea);
    }

    private void CierreProg() {
        String opciones[] = {"Cerrar", "Cancelar"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿Estas seguro de cerrar el programa? Toda accion sin guardar se perderá", "Cerrar programa", 0, 0, null, opciones, EXIT_ON_CLOSE);
        if (eleccion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCodigoBase = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtLexico = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        btnNuevo = new javax.swing.JButton();
        btnArchivo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtSintactico = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaTerminal = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtSemantico = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(222, 220, 220));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCodigoBase.setColumns(20);
        txtCodigoBase.setRows(5);
        txtCodigoBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoBaseKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(txtCodigoBase);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 73, 500, 250));

        txtLexico.setEditable(false);
        txtLexico.setColumns(20);
        txtLexico.setRows(5);
        jScrollPane3.setViewportView(txtLexico);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 70, 506, 110));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setRollover(true);

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo-arch_24.png"))); // NOI18N
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNuevo);

        btnArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/abrir-documento-24.png"))); // NOI18N
        btnArchivo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnArchivo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchivoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnArchivo);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar-24.png"))); // NOI18N
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGuardar);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/compilar-24.png"))); // NOI18N
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jPanel1.add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1116, 35));

        txtSintactico.setEditable(false);
        txtSintactico.setColumns(20);
        txtSintactico.setRows(5);
        jScrollPane4.setViewportView(txtSintactico);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 210, 506, 120));

        txtAreaTerminal.setEditable(false);
        txtAreaTerminal.setColumns(20);
        txtAreaTerminal.setRows(5);
        jScrollPane1.setViewportView(txtAreaTerminal);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 500, 120));

        jLabel1.setText("Terminal");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 100, -1));

        jLabel2.setText("Panel Principal");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 100, -1));

        jLabel3.setText("Análisis Sintáctico");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 190, 100, -1));

        jLabel4.setText("Análisis Léxico");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 100, -1));

        jLabel5.setText("Análisis Semántico");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 340, 150, -1));

        txtSemantico.setEditable(false);
        txtSemantico.setColumns(20);
        txtSemantico.setRows(5);
        jScrollPane5.setViewportView(txtSemantico);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 360, 506, 120));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 510));

        jMenu1.setText("Archivo");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo-arch_24.png"))); // NOI18N
        jMenuItem1.setText("Nuevo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/abrir-documento-24.png"))); // NOI18N
        jMenuItem2.setText("Abrir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar-24.png"))); // NOI18N
        jMenuItem3.setText("Guardar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarComo-24.png"))); // NOI18N
        jMenuItem4.setText("Guardar como");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        archivo.Nuevo(this);
        Limpiar();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        CierreProg();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        archivo.Guardar(this);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        archivo.Abrir(this);
        Limpiar();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        archivo.guardarC(this);
        Limpiar();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void txtCodigoBaseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBaseKeyReleased
        int key = evt.getKeyCode();
        //if (KeyEvent.getKeyText(key).length() > 0) {
        if ((key >= 65 && key <= 90) || (key >= 48 && key <= 59) || (key >= 97 && key <= 122) || (key != 27 && (key >= 37
                && key <= 40) && !(key >= 16 && key <= 18) && key != 524 && key != 20) || 
                (key == 40) || (key == 41) || (key == 91) || (key == 93) || (key == 61) || (key == 45)
                || (key == 56) || (key == 47) || (key == 61) || (key == 44) || (key == 39) || (key == 34)
                || (key == 127) || (key == 46)) {
            if (!getTitle().contains("*")) {
                setTitle(getTitle() + "*");
            }
        }
    }//GEN-LAST:event_txtCodigoBaseKeyReleased

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        txtLexico.setText("");
        archivo.Nuevo(this);
        Limpiar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchivoActionPerformed
        txtLexico.setText("");
        archivo.Abrir(this);
        Limpiar();
    }//GEN-LAST:event_btnArchivoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        archivo.Guardar(this);
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void Limpiar() {
        txtAreaTerminal.setText("");
        txtLexico.setText("");
        txtSintactico.setText("");
        txtSemantico.setText("");
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        InicializarPilas();
        Limpiar();
        band = true;
        procesoComp();
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnArchivo;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea txtAreaTerminal;
    public javax.swing.JTextArea txtCodigoBase;
    private javax.swing.JTextArea txtLexico;
    private javax.swing.JTextArea txtSemantico;
    private javax.swing.JTextArea txtSintactico;
    // End of variables declaration//GEN-END:variables
}
