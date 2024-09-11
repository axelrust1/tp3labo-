package ar.edu.utn.frbb.tup.controller;


public class TransferenciaDto {
    private long cuentaOrigen;
    private long cuentaDestino;
    int monto;
    String moneda;

    public long getCuentaOrigen(){
        return cuentaOrigen;
    }

    public void setCuentaOrigen(long cuentaOrigen){
        this.cuentaOrigen=cuentaOrigen;
    }

    public long getCuentaDestino(){
        return cuentaDestino;
    }

    public void setCuentaDestino(long cuentaDestino){
        this.cuentaDestino=cuentaDestino;
    }

    public int getMonto(){
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public String toString() {
        return "TransferenciaDto [cuentaOrigen=" + cuentaOrigen + ", cuentaDestino=" + cuentaDestino + ", monto="
                + monto + ", moneda=" + moneda + "]";
    }
    
}
