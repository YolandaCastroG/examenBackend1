package dh.backend.dao.impl;

import dh.backend.dao.IDao;
import dh.backend.db.H2Connection;
import dh.backend.model.Cuenta;
import org.apache.log4j.Logger;

import java.security.PrivilegedAction;
import java.sql.*;
import java.util.List;

public class CuentaIDaoH2 implements IDao<Cuenta> {
    private static Logger LOGGER = Logger.getLogger(CuentaIDaoH2.class);
    public static String SQL_INSERT = "INSERT INTO CUENTAS VALUES (DEFAULT,?,?,?)";
    public static String SQL_SELECT_ID = "SELECT * FROM CUENTAS where ID = ?";
    public static String SQL_UPDATE = "UPDATE CUENTAS SET SALDO=? WHERE NROCUENTA=?";
    @Override
    public Cuenta registrar(Cuenta cuenta) {
        Connection connection = null;
        Cuenta cuentaARetornar = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,cuenta.getNroCuenta());
            preparedStatement.setString(2, cuenta.getNombre());
            preparedStatement.setDouble(3, cuenta.getSaldo());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                Integer id = resultSet.getInt(1);
                cuentaARetornar = new Cuenta(id, cuenta.getNroCuenta(), cuenta.getNombre(),
                        cuenta.getSaldo());
                LOGGER.info("Cuenta persisitida: "+ cuentaARetornar);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.info(ex.getMessage());
                    ex.printStackTrace();
                }
            }
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }
        return cuentaARetornar;
}

    @Override
    public Cuenta buscarPorId(Integer id) {
        Connection connection = null;
        try {
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ID);
            preparedStatement.setInt(1, id);

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<Cuenta> buscarTodos() {
        return null;
    }
}
