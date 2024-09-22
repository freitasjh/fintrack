package br.com.systec.controle.financeiro.fake;

import br.com.systec.controle.financeiro.administrator.bank.model.Bank;

public class BankFake {

    public static Bank toFake() {
        Bank bank = new Bank();
        bank.setId(79L);
        bank.setName("Itau");
        bank.setCode(341);
        bank.setWebsite("www.itau.com.br");

        return bank;
    }
}
