package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.DepositoRetiroDto;
import ar.edu.utn.frbb.tup.model.Cuenta;

public class DepositoRetiro {

    private long cuenta;
    private int monto;
    private String tipoMoneda;

    public DepositoRetiro(DepositoRetiroDto DepositoRetirodto){
        this.cuenta = DepositoRetirodto.getCuenta();
        this.monto = DepositoRetirodto.getMonto();
        this.tipoMoneda = DepositoRetirodto.getMoneda();
    }

    public long getCuenta() {
        return cuenta;
    }

    public void setCuenta(long cuenta) {
        this.cuenta = cuenta;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

}
