/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.cliente;

import DAO.DAO;
import java.time.LocalDate;
import modelo.cliente.Medida;


public interface MedidasDAO extends DAO<Medida, String> {
public abstract Medida obtener(int clientekey, LocalDate fecha) throws Exception;
}
