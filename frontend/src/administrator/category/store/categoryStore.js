import CategoryService from '../service/CategoryService';
import Pageable from '../../../commons/model/Pageable';
import Category from '../model/Category';

export const categoryStore = {
    namespaced: true,
    state: {
        category: null,
        listPageCategory: new Pageable(),
    },
    mutations: {
        RETURN_LIST_CATEGORY(state, response) {
            state.listPageCategory = response.data;
        },
        RETURN_CATEGORY(state, response) {
            state.category = response.data;
        },
        CLEAR_CATEGORY_STATE(state) {
            state.category = new Category();
        }
    },
    actions: {
        async findCategoryByFilter({ commit }, filterCategory) {
            const categoryService = new CategoryService();
            const response = await categoryService.findByFilter(filterCategory);

            commit('RETURN_LIST_CATEGORY', response);
        },
        async findCategoryById({ commit }, categoryId) {
            const categoryService = new CategoryService();
            const response = await categoryService.findById(categoryId);

            commit('RETURN_CATEGORY', response);
        },
        async saveCategory({ commit }, categorySave) {
            const categoryService = new CategoryService();
            await categoryService.save(categorySave);
            commit('CLEAR_CATEGORY_STATE');
        }
    },
};

export default categoryStore;