package com.pmrodrigues.ellasa.factory;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.pagamentos.entity.Payer;
import com.pmrodrigues.ellasa.pagamentos.entity.Phone;

/**
 * Created by Marceloo on 11/11/2014.
 */
public abstract class PayerFactory {

    public static PayerFactory getInstance(final OrdemPagamento ordemPagamento ){
        if (ordemPagamento.getContrato() == null) {
            return new ClienteFactory(ordemPagamento);
        } else {
            return new FranqueadoFactory(ordemPagamento);
        }
    }

    protected Phone getPhone(final Phone.Type type, final String number) {
        final Phone phone = new Phone();
        phone.setNumber(number);
        phone.setType(type);
        return phone;
    }

    public abstract Payer create();
}
