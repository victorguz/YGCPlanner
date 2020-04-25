/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.SQLException;

/**
 * @author Victor
 */
public class SimpleException extends SQLException {

    public SimpleException() {
    }

    public SimpleException(String message) {
        super(message);
    }

    public SimpleException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimpleException(Throwable cause) {
        super(cause);
    }

}
