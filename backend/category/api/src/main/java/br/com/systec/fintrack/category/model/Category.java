package br.com.systec.fintrack.category.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Long id;
    @Column(name = "description")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String description;
    @Column(name = "observation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String observation;
    @Column(name = "spendingLimit")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Double spendingLimit;
    @Column(name = "tenant_id", nullable = false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Long tenantId;
    @Column(name = "category_type")
    private CategoryType categoryType;

    public Category(){}

    public Category(Long id){
        this.id = id;
    }

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

    public Double getSpendingLimit() {
        return spendingLimit;
    }

    public void setSpendingLimit(Double spendingLimit) {
        this.spendingLimit = spendingLimit;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", observation='" + observation + '\'' +
                ", spendingLimit=" + spendingLimit +
                ", tenantId=" + tenantId +
                ", categotyType=" + categoryType +
                '}';
    }
}

