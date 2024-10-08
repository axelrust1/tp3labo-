package ar.edu.utn.frbb.tup.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controller.DepositoRetiroDto;
import ar.edu.utn.frbb.tup.controller.MovimientoDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.DepositoRetiro;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.CuentaNulaExcepcion;
import ar.edu.utn.frbb.tup.model.exception.CuentaOrigenNoExisteExcepcion;
import ar.edu.utn.frbb.tup.model.exception.MonedaErroneaTransferenciaExcepcion;
import ar.edu.utn.frbb.tup.model.exception.MonedaVaciaExcepcion;
import ar.edu.utn.frbb.tup.model.exception.MontoMenorIgualQueCero;
import ar.edu.utn.frbb.tup.model.exception.SaldoInsuficienteExcepcion;
import ar.edu.utn.frbb.tup.model.exception.TipoDeMonedaIncorrectoExcepcion;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;

@Component
public class DepositoRetiroService {
    @Autowired
    CuentaDao cuentaDao;
    @Autowired
    CuentaService cuentaService;
    @Autowired
    ClienteDao clienteDao;

    public DepositoRetiro realizarDeposito(DepositoRetiroDto DepositoRetirodto) throws MontoMenorIgualQueCero, MonedaVaciaExcepcion, TipoDeMonedaIncorrectoExcepcion, CuentaNulaExcepcion, CuentaOrigenNoExisteExcepcion, MonedaErroneaTransferenciaExcepcion {
        Cuenta cuenta = cuentaDao.find(DepositoRetirodto.getCuenta()); //clono la cuenta en un tipo cuenta nuevo
        DepositoRetiro deposito = new DepositoRetiro(DepositoRetirodto); //creo un nuevo deposito que sera el que retorna
        if (deposito.getTipoMoneda() == null || deposito.getTipoMoneda().isEmpty()) {
            throw new MonedaVaciaExcepcion("La moneda no puede ser vacia");
        }
        if (deposito.getMonto()<=0){
            throw new MontoMenorIgualQueCero("El monto debe ser mayor a 0");
        }
        if (deposito.getCuenta() == 0 || deposito.getCuenta() == 0) {
            throw new CuentaNulaExcepcion("La cuenta no puede ser nula.");
        }
        if (cuenta==null){
            throw new CuentaOrigenNoExisteExcepcion("La cuenta en la que quiere depositar no existe."); //excepcion por si no exixste la cuenta
        }

        if (!"PESOS".equals(deposito.getTipoMoneda()) && !"DOLARES".equals(deposito.getTipoMoneda())) {
            throw new TipoDeMonedaIncorrectoExcepcion("Tipo de moneda "+ deposito.getTipoMoneda() + "  es incorrecto");
        }
        if (!(TipoMoneda.valueOf(deposito.getTipoMoneda()).equals(cuenta.getMoneda()))){ //CONVIERTO EL STRING EN UN ENUM PARA LA COMPARACION CON LA CUENTA
                throw new MonedaErroneaTransferenciaExcepcion("Error en la moneda seleccionada para el deposito"); //excepcion por si es distinta moneda
        }
        
        cuenta.setBalance(cuenta.getBalance()+deposito.getMonto()); //si todo sale bien seteamos el balance de la cuenta creada aca
        MovimientoDto movimientoDto = new MovimientoDto(LocalDate.now(), "Credito", "Deposito", deposito.getMonto()); //creamos el movimiento para guardarlo
        cuentaDao.updateBalance(cuenta.getNumeroCuenta(), cuenta.getBalance()); //actualizamos el balance en la cuenta original
        cuentaDao.guardarMovimiento(cuenta.getNumeroCuenta(), movimientoDto); //guardamos el movimiento
        return deposito;
    }

    public DepositoRetiro realizarRetiro(DepositoRetiroDto DepositoRetirodto) throws SaldoInsuficienteExcepcion, MontoMenorIgualQueCero, MonedaVaciaExcepcion, TipoDeMonedaIncorrectoExcepcion, CuentaOrigenNoExisteExcepcion, MonedaErroneaTransferenciaExcepcion {
        Cuenta cuenta = cuentaDao.find(DepositoRetirodto.getCuenta());
        DepositoRetiro retiro = new DepositoRetiro(DepositoRetirodto);
        if (retiro.getTipoMoneda() == null || retiro.getTipoMoneda().isEmpty()) {
            throw new MonedaVaciaExcepcion("La moneda no puede ser vacia");
        }

        if (retiro.getMonto()<=0){
            throw new MontoMenorIgualQueCero("El monto debe ser mayor a 0");
        }
        if (cuenta==null){
            throw new CuentaOrigenNoExisteExcepcion("La cuenta de la que quiere hacer el retiro no existe."); 
        }
        if (!"PESOS".equals(retiro.getTipoMoneda()) && !"DOLARES".equals(retiro.getTipoMoneda())) {
            throw new TipoDeMonedaIncorrectoExcepcion("Tipo de moneda "+ retiro.getTipoMoneda() + "  es incorrecto");
        }
        if (!(TipoMoneda.valueOf(retiro.getTipoMoneda()).equals(cuenta.getMoneda()))){ //CONVIERTO EL STRING EN UN ENUM PARA LA COMPARACION CON LA CUENTA
                throw new MonedaErroneaTransferenciaExcepcion("Error en la moneda seleccionada para el retiro");
        }
        if (cuenta.getBalance()<retiro.getMonto()){
            throw new SaldoInsuficienteExcepcion("No hay saldo suficiente para realizar el retiro."); //verifico si el monto alcanza
        }

        cuenta.setBalance(cuenta.getBalance()-retiro.getMonto());
        MovimientoDto movimientoDto = new MovimientoDto(LocalDate.now(), "DEBITO", "Retiro", retiro.getMonto());
        cuentaDao.updateBalance(cuenta.getNumeroCuenta(), cuenta.getBalance());
        cuentaDao.guardarMovimiento(cuenta.getNumeroCuenta(), movimientoDto);
        return retiro;
    }
}
