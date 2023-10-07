
package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import jflex.exceptions.SilentExit;

public class Lexico {
    
    public static void main(String[] args) {
        String archivoLexer = System.getProperty("user.dir")+"\\src\\main\\Lexer.flex";
        generararchivoLexer(archivoLexer);
    }
    
    public static void generararchivoLexer(String ruta){
        try {
            jflex.Main.generate(new String[] {ruta});
        } catch (SilentExit ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
