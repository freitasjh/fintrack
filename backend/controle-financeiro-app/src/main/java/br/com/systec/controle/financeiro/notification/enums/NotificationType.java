package br.com.systec.controle.financeiro.notification.enums;

import br.com.systec.controle.financeiro.commons.exception.BaseException;

public enum NotificationType {
    NOTIFICATION_RECEIVE(1), NOTIFICATION_PAYMENT(2), NOTIFICATION_TRANSFER(3);

    int code;

    NotificationType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static NotificationType valueOfCode(int code) {
        for(NotificationType item : NotificationType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }

        throw new BaseException("Tipo de notificação não encontrado");
    }
}
