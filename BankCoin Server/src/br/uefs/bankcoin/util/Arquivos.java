/**
 * Componente Curricular: Módulo Integrado de Concorrecia e Conectividade
 * Autor: <Jhone Mendes>
 * Data:  <04/09/2014>
 *
 */
package br.uefs.bankcoin.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Responsavel manipulacao de arquivos no disco rigido.
 *
 * @author Jhone Mendes
 */
public class Arquivos {
    
    /**
     * Salva um objeto como um arquivo (.data).
     *
     * @param o Objeto
     * @param nome
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void salvar (Object o, String nome) throws FileNotFoundException, IOException{
        String t = nome+".data";
        FileOutputStream f = new FileOutputStream(t);
        ObjectOutputStream ob = new ObjectOutputStream(f);
        ob.writeObject(o);
        f.close();
        ob.close();
    }
    
    /**
     * Carrega um arquivo (.data) do disco rigido
     *
     * @param nome
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object carregar(String nome) throws IOException, ClassNotFoundException{
        String t =  nome+".data";
        FileInputStream f = new FileInputStream(t);
        ObjectInputStream ob = new ObjectInputStream(f);
        Object x = ob.readObject();
        f.close();
        ob.close();   //Verificar a exception que da quando nao acha o arquivo
        return x;
    } 
}
//criar directorio
//(new File("Nome do Diretório")).mkdir();