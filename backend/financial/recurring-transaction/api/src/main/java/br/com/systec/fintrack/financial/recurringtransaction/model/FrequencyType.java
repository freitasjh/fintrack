package br.com.systec.fintrack.financial.recurringtransaction.model;

public enum FrequencyType {
    MONTHLY("0 0 0 1 * ? *"),
    YEARLY("0 0 0 1 {month} ? *"),
    WEEKLY("0 0 0 ? * SUN *");

    private final String cronJob;

    FrequencyType(String cronJob) {
        this.cronJob = cronJob;
    }

    public String getCronJob() {
        return cronJob;
    }
}
