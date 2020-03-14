
package edu.eci.cvds.samples.services.client;

import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;



public class MyBatisExample {

    /**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejempo de uso de MyBATIS
     *
     * @param args
     * @throws SQLException
     */
    public static void main(String args[]) throws SQLException {
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        //Crear el mapper y usarlo: 
        ClienteMapper cm = sqlss.getMapper(ClienteMapper.class);
        imprimirEspacios("Clientes");
        System.out.println(cm.consultarClientes());
        imprimirEspacios("Clientes por ID");
        System.out.println(cm.consultarCliente(3));
        //Para la ejecucion final de las pruebas quitar el comentario de la siguiente instruccion
        //cm.agregarItemRentadoACliente(5, 1, parseDate("2019-03-12"), parseDate("2019-04-12"));
        ItemMapper im= sqlss.getMapper(ItemMapper.class);
        TipoItem tipoIt= new TipoItem(3,"Peliculas");
        Item it = new Item(tipoIt,9999,"NuevoItemxDv4_0","Este es el nuevo Item v4_0",parseDate("2020-03-12"),1234, "formatoxD4_0","genero4_0 final"); 
        //Para la ejecucion final de las pruebas quitar el comentario de la siguiente instruccion
        //im.insertarItem(it);
        //cm...
        imprimirEspacios("Items");
        System.out.println(im.consultarItems());
        imprimirEspacios("Items Por ID");
        System.out.println(im.consultarItem(888));
        

        sqlss.commit();
        sqlss.close();
    }
    
    /**
     * Parse string to date
     * @param date
     * @return 
     */
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static void imprimirEspacios(String tipo){
        System.out.println("");
        System.out.println("");
        System.out.println("----------.Imprimiendo CONSULTA de "+ tipo+ "--------------");
        System.out.println("");
        System.out.println("");

    }

}
