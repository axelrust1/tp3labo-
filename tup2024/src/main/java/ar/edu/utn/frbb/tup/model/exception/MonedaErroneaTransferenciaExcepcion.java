package ar.edu.utn.frbb.tup.model.exception;

public class MonedaErroneaTransferenciaExcepcion extends Exception {
    public MonedaErroneaTransferenciaExcepcion(){
        super("Error en la moneda seleccionada para la transferencia.");
    }
}//modificamos las excepciones con exception en ves de throwle ya que utilizamos el mensaje para mostrarlo luego de la trasnferencia