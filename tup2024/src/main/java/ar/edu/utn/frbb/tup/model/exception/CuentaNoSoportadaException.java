package ar.edu.utn.frbb.tup.model.exception;

import ar.edu.utn.frbb.tup.model.Cuenta;

public class CuentaNoSoportadaException extends Exception {
    public CuentaNoSoportadaException(Cuenta cuenta) {
        super("El tipo de cuenta " + cuenta.getTipoCuenta() + " con " + cuenta.getMoneda() + " no esta soportada."); //imprimo el mensaje en la excepcion y solo paso por parametro la cuenta
    }
}