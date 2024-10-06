package br.com.systec.fintrack.financial.payment.jms;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

//TODO analisar se realmente e necessario essa logica
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
        return mapper.readValue(json, AccountPaymentJmsVO.class);
    }
}
