package br.com.systec.fintrack.financial.received.impl.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class AccountReceivedMetrics {

    private final Counter accountReceivableCounter;

    public AccountReceivedMetrics(MeterRegistry meterRegistry) {
        this.accountReceivableCounter = Counter
                .builder("account_receivable_created")
                .description("Número total de contas a receber cadastradas")
                .tag("type", "receivable")
                .register(meterRegistry);
    }

    public void incrementAccountReceivable() {
        accountReceivableCounter.increment();
    }
}
