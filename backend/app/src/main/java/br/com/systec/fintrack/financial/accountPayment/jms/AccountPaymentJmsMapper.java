package br.com.systec.fintrack.financial.accountPayment.jms;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountPaymentJmsMapper {

    public static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJson(AccountPaymentJmsVO jmsVO) throws Exception {
        return mapper.writeValueAsString(jmsVO);
    }

    public static AccountPaymentJmsVO toObject(String json) throws Exception {
        System.out.println(json);
        return mapper.readValue(json, AccountPaymentJmsVO.class);
    }
}
