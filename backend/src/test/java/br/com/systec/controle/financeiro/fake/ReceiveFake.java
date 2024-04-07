package br.com.systec.controle.financeiro.fake;

import br.com.systec.controle.financeiro.receive.model.Receive;

import java.time.LocalDateTime;

public class ReceiveFake {

    public static Receive fake() {
        Receive receive = new Receive();
        receive.setDescription("Salario");
        receive.setCategory(CategoryFake.fakeCategory());
        receive.setAccountId(1L);
        receive.setAmount(50000);
        receive.setDateReceiver(LocalDateTime.now());
        receive.setDateRegister(LocalDateTime.now());

        return receive;
    }
}
