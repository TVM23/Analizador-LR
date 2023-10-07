
package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SistemaArch {
    JFileChooser selectFile = new JFileChooser();
    File file;
    String[] botones = new String[]{"Guardar y continuar", "Continuar"};
    FileNameExtensionFilter filtro = new FileNameExtensionFilter("TXT", "txt");
    
    
    public String getTextFile(File file){
        String text = "";
        try {
            
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            while(true){
            int b = entrada.read();
                if(b!=-1)
                    text += (char)b;
                else
                    break;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no se ha encontrado o no existe"+ex.getMessage());
            return null;
        } catch (IOException ex) {
            System.out.println("Error de lectura del archivo"+ex.getMessage());
            return null;
        }
        return text;   
    }

    
    public boolean saveFile(File archivo, String text){
        try {
            FileOutputStream output = new FileOutputStream(archivo);
            byte[] bytesText = text.getBytes();
            output.write(bytesText);
        } catch (FileNotFoundException ex) {
            System.out.println("Error, el archivo no se encontro"+ex.getMessage());
            return false;
        } catch (IOException ex) {
            System.out.println("Error de escritura del archivo"+ex.getMessage());
            return false;
        } 
        return true;
    }
    
    
    public boolean guardarEditNuevo(File fileG, JFileChooser selecFileG, principal compF){
        selecFileG.setFileFilter(filtro);
        int x;
        if(compF.getTitle().equals("Compilador LR*"))
            x = 0;
        else
            x = JOptionPane.showOptionDialog(compF, "Se ha detectado cambios en el archivo actual, ¿desea guardarlos?",
                                            "Cambios detectados", -1, 3, null, botones, botones[0]);
        if(x==0){
            if(selecFileG.getSelectedFile() != null){
                boolean save = saveFile(fileG, compF.txtCodigoBase.getText());
                if(save)
                    compF.setTitle(fileG.getName());
                    
            }
            else if(compF.getTitle().equals("Compilador LR*")){
                int y = JOptionPane.showOptionDialog(compF, "¿Desea guardar el archivo actual?",
                                                    "Edición de nuevo archivo", -1, 3, null, botones, botones[0]);
                if(y==0){
                    if(selecFileG.showDialog(compF, "Guardar") == JFileChooser.APPROVE_OPTION){
                        fileG = selecFileG.getSelectedFile();
                        String fileGname = fileG.getName();
                
                        if(fileGname.endsWith(".txt")){
                            if(!fileGname.split("[.]")[0].replace(" ","").equals("")){
                                System.out.println(!fileG.exists());
                                if(!fileG.exists())
                                    guardarArch(fileG, compF);  
                                else{
                                    int z = JOptionPane.showConfirmDialog(compF, "Se ha encontrado un archivo existente con este mismo nombre, ¿desea "
                                                                         +"sobreescribirlo?", "Archivo existente detectado", 2);
                                    if(z == 0)
                                        guardarArch(fileG, compF);                                        
                                }
                            }else{
                                JOptionPane.showMessageDialog(compF, "Escriba un nombre válido para el archivo",
                                                              "Nombre inválido de archivo", 2);
                                return false;
                            }
                        }else{
                            JOptionPane.showMessageDialog(compF, "El archivo debe contener la extensión '.txt'",
                                                          "Extensión de archivo inválida", 2); 
                            return false;
                        }
                    }
                    
                }else{
                     return true;
                }
            }else{
            int z = JOptionPane.showConfirmDialog(compF, "Ya existe un archivo con este nombre, ¿desea "
                                                                         +"sobreescribirlo?", "Nombre de archivo en uso", 2);
                if(z == 0)
                guardarArch(fileG, compF); 
            }
               
        }
        return true;
    }
    
    
    public boolean guardarEditAbrir(File fileG, JFileChooser selecFileG, principal compF){
        selecFileG.setFileFilter(filtro);
        int x;
        if(compF.getTitle().equals("Compilador LR*"))
            x = 0;
        else
            x = JOptionPane.showOptionDialog(compF, "El archivo actual ha recibido cambios, ¿desea guardarlos?",
                                            "Cambios de edición", -1, 3, null, botones, botones[0]);
        if(x==0){
            if(selecFileG.getSelectedFile() != null){
                boolean save = saveFile(fileG, compF.txtCodigoBase.getText());
                if(save)
                    compF.setTitle(fileG.getName());
                    
            }
            else if(compF.getTitle().equals("Compilador LR*")){
                int y = JOptionPane.showOptionDialog(compF, "¿Desea guardar el archivo actual?",
                                                    "Edición de archivo nuevo", -1, 3, null, botones, botones[0]);
               
                if(y==0){
                    if(selecFileG.showDialog(compF, "Guardar") == JFileChooser.APPROVE_OPTION){
                        fileG = selecFileG.getSelectedFile();
                        String fileGname = fileG.getName();
                
                        if(fileGname.endsWith(".txt")){
                            if(!fileGname.split("[.]")[0].replace(" ","").equals("")){
                                if(!fileG.exists())
                                    guardarArch(fileG, compF);  
                                else{
                                    int z = JOptionPane.showConfirmDialog(compF, "Ya existe un archivo con este nombre, ¿desea "
                                                                         +"sobreescribirlo?", "Nombre de archivo en uso", 2);
                                    if(z == 0)
                                        guardarArch(fileG, compF);  
                                    else{
                                    
                                    }
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(compF, "Debe escribir un nombre válido para el archivo",
                                                              "Nombre inválido detectado", 2);
                                return false;
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(compF, "El archivo debe contener la extensión '.txt'",
                                                          "Extensión de archivo inválida", 2); 
                            return false;
                        }
                    }
                    
                }
                else{
                     compF.txtCodigoBase.setText("");
                     compF.setTitle("Compilador LR");
                }
            }
            else{
                int z = JOptionPane.showConfirmDialog(compF, "Ya hay un archivo con este nombre, ¿desea "
                                                                         +"sobreescribirlo?", "Archivo existente", 2);
                if(z == 0)
                guardarArch(fileG, compF); 
            }
               
        }
        else{
            compF.txtCodigoBase.setText("");
            compF.setTitle("Compilador LR");
        }
        return true;
    }
    
    
    public void guardarArch(File file, principal compF){
        boolean save = saveFile(file, compF.txtCodigoBase.getText());   
        if(save)
            compF.setTitle(file.getName());
        else
            JOptionPane.showMessageDialog(compF, "No se ha podido guardar el archivo",
                                         "Error desconocido", 2); 
    }
        
    public void Nuevo(principal compF){ 
        file = selectFile.getSelectedFile();
        
        if(compF.getTitle().contains("*")){
            if(guardarEditNuevo(file, selectFile, compF)){
                compF.setTitle("Compilador LR");
                compF.txtCodigoBase.setText("");
                selectFile = new JFileChooser();
                file = null;
            }
        }
        else{
            compF.setTitle("Compilador LR");
            compF.txtCodigoBase.setText("");
            selectFile = new JFileChooser();
            file = null;
        }
    }
    
    
    public boolean Abrir(principal compF){
               
        if(compF.getTitle().contains("*")){
            if(guardarEditAbrir(file, selectFile, compF)){
                selectFile = new JFileChooser();
                file = null;
            }
        }
        JFileChooser tSelecFile = new JFileChooser();
        File tFile;
        tSelecFile.setFileFilter(filtro);
        
        if(tSelecFile.showDialog(compF, "Abrir") == JFileChooser.APPROVE_OPTION){
            tFile = tSelecFile.getSelectedFile();
            String filename = tFile.getName();
             

            if(filename.endsWith(".txt")){
                if(!filename.split("[.]")[0].replace(" ","").equals("")){
                    if(!tFile.exists())
                        JOptionPane.showMessageDialog(compF, "El archivo que sea desea abrir no existe en la direccion especificada",
                                                     "Archivo no encontrado", 2);      
                    else{
                        String t = getTextFile(tFile);
                           
                        if(t!=null){
                            compF.txtCodigoBase.setText(t);
                            compF.setTitle(tFile.getName());
                            compF.LimpiarComp();
                            selectFile = tSelecFile;
                            file = tFile;
                        }
                        else{
                            JOptionPane.showMessageDialog(compF, "Error encontrado al leer el archivo seleccionado",
                                                         "Error desconocido", 2);
                            return false;
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(compF, "Debe ingresar un nombre válido para el archivo",
                                                  "Nombre de archivo inválido", 2); 
                    return false;
                }
            }
            else{
                JOptionPane.showMessageDialog(compF, "El archivo debe de contener la extensión '.txt'",
                                             "Extensión de archivo inválida", 2);  
                return false;
            }
        }
        else
            return false;
        return true;
    }
    
    public boolean Guardar(principal compF){
        if(file != null)
            guardarArch(file, compF);
        else{
            JFileChooser tSelectFile = new JFileChooser();
            File tFile;
            tSelectFile.setFileFilter(filtro);
            //FileNameExtensionFilter ext = new FileNameExtensionFilter("Archivos txt", "txt");
            //tSelecFile.setFileFilter(ext);
            
            if(tSelectFile.showDialog(compF, "Guardar") == JFileChooser.APPROVE_OPTION){
                tFile = tSelectFile.getSelectedFile();
                String filename = tFile.getName();
                
                if(true){
                    if(!filename.split("[.]")[0].replace(" ","").equals("")){
                        if(!tFile.exists()){
                            guardarArch(tFile, compF);  
                            file = tFile;
                            System.out.println(tFile);
                            selectFile = tSelectFile;
                        }
                        else{
                            int x = JOptionPane.showConfirmDialog(compF, "Ya existe un archivo que emplea este nombre, ¿desea "
                                                                +"sobreescribirlo?", "Sobreescribir archivo", 2);
                            if(x == 0){
                                guardarArch(tFile, compF); 
                                file = tFile;
                                selectFile = tSelectFile;
                            }
                            else{
                                selectFile = new JFileChooser();
                                file = null;
                            }
                            
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(compF, "Escriba un nombre válido para el archivo",
                                                  "Nombre inválido", 2);
                        selectFile = new JFileChooser();
                        file = null;
                        return false;
                    }
                }
                else{
                    JOptionPane.showMessageDialog(compF, "El archivo debe de contener la extensión '.txt'",
                                                  "Extensión inválida", 2); 
                    selectFile = new JFileChooser();
                    file = null;
                    return false;
                }
            }
            else 
                return false;
        }
        return true;
    }
    
    
    public void guardarC(principal compF){
        JFileChooser tSelecFile = new JFileChooser();
        tSelecFile.setFileFilter(filtro);
        
        if(tSelecFile.showDialog(compF, "Guardar como") == JFileChooser.APPROVE_OPTION){
            File tFile;
            tFile = tSelecFile.getSelectedFile();
            String filename = tFile.getName();
                
            if(filename.endsWith(".txt")){
                if(!filename.split("[.]")[0].replace(" ","").equals("")){
                    guardarArch(tFile, compF);  
                    file = tFile;
                    selectFile = tSelecFile;
                }
                else
                    JOptionPane.showMessageDialog(compF, "Debe ingresar un nombre válido para el archivo",
                                                 "Nombre de archivo inválido", 2); 
            }
            else
                JOptionPane.showMessageDialog(compF, "El archivo debe contener la extensión '.txt'",
                                             "Extensión inválida", 2);  
        }
    }

}
