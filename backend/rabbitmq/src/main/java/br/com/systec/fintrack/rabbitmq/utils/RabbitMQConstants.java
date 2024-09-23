package br.com.systec.fintrack.rabbitmq.utils;

public class RabbitMQConstants {
    public static final String NEW_ACCOUNT_CATEGORY_QUEUE = "new-account-category";
    public static final String NEW_ACCOUNT_BANK_ACCOUNT_QUEUE = "new-account-bank-account";
    public static final String BANK_ACCOUNT_BALANCE_UPDATE = "bank-account-balance-update";
    public static final String BANK_ACCOUNT_NEW = "bank-account-new";
    public static final String CREDIT_CARD = "credit-card";
    public static final String CREDIT_CARD_JOB = "credit-card-job";
    public static final String JOB_NEW_ACCOUNT = "job-new-account";
    public static final String ACCOUNT_PAYMENT = "account-payment";
    public static final String ROUTING_KEY_NEW_ACCOUNT = "rt-newaccount";
    public static final String ROUTING_KEY_NEW_BANK_ACCOUNT = "rt-new-bank-account";
    public static final String ROUTING_KEY_NEW_BALANCE_ACCOUNT = "rt-newbalance-account";
    public static final String FINANCIAL_EXCHANGE = "fx-financial";
}
