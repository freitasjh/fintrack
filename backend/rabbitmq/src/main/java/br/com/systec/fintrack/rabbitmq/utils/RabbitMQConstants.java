package br.com.systec.fintrack.rabbitmq.utils;

public class RabbitMQConstants {
    public static final String NEW_ACCOUNT_CATEGORY_QUEUE = "new.account.category";
    public static final String CREDIT_CARD = "credit.card";

    public static final String CREDIT_CARD_JOB = "credit.card.job";
    public static final String JOB_NEW_ACCOUNT = "job.new.account";
    public static final String ACCOUNT_PAYMENT = "account.payment";
    public static final String CREDIT_CARD_NEW_INVOICE = "credit.card.new.invoice";
    public static final String FINANCIAL_EXCHANGE = "fx.financial";
    public static final String RECURRING_FINANCIAL_JOB = "recurring.financial.job.queue";
    public static final String CREDIT_CARD_INVOICE_PENDING = "credit.card.invoice.pending";
    public static final String NOTIFICATION = "notification";

    //Bank account constants
    public static final String ROUTING_KEY_NEW_ACCOUNT = "rt.new.account";
    public static final String ROUTING_KEY_NEW_BANK_ACCOUNT = "rt.new.bank.account";
    public static final String ROUTING_KEY_UPDATE_BALANCE_ACCOUNT = "rt.update.balance.account";
    public static final String FINANCIAL_EXCHANGE_BANK_ACCOUNT = "fx.financial.bank.account";

    public static final String NEW_ACCOUNT_BANK_ACCOUNT_QUEUE = "new.account.bank.account";
    public static final String BANK_ACCOUNT_BALANCE_UPDATE = "bank.account.balance.update";
    public static final String BANK_ACCOUNT_NEW = "bank.account.new";

}
