

package edu.eci.cvds.sampleprj.jdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCExample {
    
    private static final String SQL_INSERT_REGISTRAR_NUEVO_PRODUCTO = "INSERT INTO ORD_PRODUCTOS(codigo, nombre, precio) VALUES (?,?,?)";
    private static final String SQL_SELECT_NOMBRES_PRODUCTOS_PEDIDOS = "SELECT nombre FROM ORD_PRODUCTOS WHERE codigo = ?";
    private static final String SQL_SELECT_VALOR_TOTAL_PEDIDO = "SELECT SUM(cantidad*ORD_PRODUCTOS.precio) FROM ORD_DETALLE_PEDIDO,ORD_PRODUCTOS WHERE producto_fk = ORD_PRODUCTOS.codigo && pedido_fk = ?;";
    
    public static void main(String args[]){
        try {
            String url="jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
            String driver="com.mysql.jdbc.Driver";
            String user="bdprueba";
            String pwd="prueba2020";
                        
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url,user,pwd);
            con.setAutoCommit(false);
                 
            
            System.out.println("Valor total pedido 1: "+valorTotalPedido(con, 1));
            
            List<String> prodsPedido=nombresProductosPedido(con, 214);
            
            
            System.out.println("Productos del pedido 1: ");
            System.out.println("-----------------------");
            for (String nomprod:prodsPedido){
                System.out.println(nomprod);
            }
            System.out.println("-----------------------");
            
            
            int suCodigoECI = 545646444;
            registrarNuevoProducto(con, suCodigoECI, "Juanpis", 45);   
            
            
            con.commit();
                        
            
            con.close();
                                   
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JDBCExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * Agregar un nuevo producto con los parámetros dados
     * @param con la conexión JDBC
     * @param codigo
     * @param nombre
     * @param precio
     * @throws SQLException 
     */
    public static void registrarNuevoProducto(Connection con, int codigo, String nombre,int precio) throws SQLException{
        
        PreparedStatement st = null;
        
        int filas = 0;
        
        //con.getConnection();
        st = con.prepareStatement(SQL_INSERT_REGISTRAR_NUEVO_PRODUCTO);
        int index = 1; //Contador de columnas
        
        st.setInt(index++, codigo);
        st.setString(index++, nombre);
        st.setInt(index++, precio);
        System.out.println("Ejecutando query: " + SQL_INSERT_REGISTRAR_NUEVO_PRODUCTO);

        filas = st.executeUpdate(); //Numero de filas afectadas
        System.out.println("Número de filas insertadas: " + filas);
        
       
        
        //Crear preparedStatement
        //Asignar parámetros
        //usar 'execute'

        
        con.commit();
        //con.close();
    }
    
    /**
     * Consultar los nombres de los productos asociados a un pedido
     * @param con la conexión JDBC
     * @param codigoPedido el código del pedido
     * @return 
     */
    public static List<String> nombresProductosPedido(Connection con, int codigoPedido){
                
        //Crear prepared statement
        //asignar parámetros
        //usar executeQuery
        //Sacar resultados del ResultSet
        //Llenar la lista y retornarla
        
        List<String> np=new LinkedList<>();
        PreparedStatement st = null;
        
        ResultSet rs = null;
        String nombre = null;
        
        try{
            
            //con.getConnection();
            st = con.prepareStatement(SQL_SELECT_NOMBRES_PRODUCTOS_PEDIDOS);
            st.setInt(1, codigoPedido);
            rs = st.executeQuery();
            
            while(rs.next()){
                nombre = rs.getString(1);
                np.add(nombre);
            }

            
            //con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }        
        
        return np;
    }

    
    /**
     * Calcular el costo total de un pedido
     * @param con
     * @param codigoPedido código del pedido cuyo total se calculará
     * @return el costo total del pedido (suma de: cantidades*precios)
     */
    public static int valorTotalPedido(Connection con, int codigoPedido){
        //Crear prepared statement
        //asignar parámetros
        //usar executeQuery
        //Sacar resultado del ResultSet
        
        PreparedStatement st = null;
        
        ResultSet rs = null;
        int valorTotal = 0;        

        
        try{
            
            //con.getConnection();
            st = con.prepareStatement(SQL_SELECT_VALOR_TOTAL_PEDIDO);
            st.setInt(1, codigoPedido);
            rs = st.executeQuery();
            
            while(rs.next()){
                valorTotal = rs.getInt(1);                
            }

            
            //con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }              
        
        return valorTotal;
    }
    

    
    
    
}
