package br.com.systec.controle.financeiro.administrator.category.api.v1.dto;

import java.util.Objects;

public class CategoryDTO {

    private Long id;
    private String description;
    private String observation;
    private double spendingLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public double getSpendingLimit() {
        return spendingLimit;
    }

    public void setSpendingLimit(double spendingLimit) {
        this.spendingLimit = spendingLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryDTO that = (CategoryDTO) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", observation='" + observation + '\'' +
                ", spendingLimit=" + spendingLimit +
                '}';
    }
}
