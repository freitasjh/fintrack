package br.com.systec.fintrack.category.jms;

import br.com.systec.fintrack.category.model.Category;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "categories")
public class ListCategory {

    @XmlElement(name = "category")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
