import http from '@/config/axios';

export default class CategoryService {
    save(category) {
        console.log(category)
        if (category.id === null || category.id === undefined) {
            return http.post("/api/v1/categories", category).then((response) => response);
        }

        return this.#update(category);
    }
    #update(category) {
        return http.put("/api/v1/categories", category).then((response) => response);
    }
    findByFilter(filterCategory) {
        return http.get(`/api/v1/categories/filter${filterCategory.toString()}`).then((response) => response);
    }
    findById(categoryId) {
        return http.get(`/api/v1/categories/${categoryId}`).then((response) => response);
    }
}