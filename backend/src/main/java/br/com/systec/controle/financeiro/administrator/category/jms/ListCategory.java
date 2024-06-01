package br.com.systec.controle.financeiro.administrator.category.jms;

import br.com.systec.controle.financeiro.administrator.category.model.Category;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
