<script setup>
import { FilterMatchMode } from "primevue/api";
import { inject, onBeforeMount, ref } from "vue";
import Pageable from "@/commons/model/Pageable";
import { useStore } from "vuex";
import FilterCategory from "../model/FilterCategory";
import Category from "../model/Category";
import { useToast } from "primevue/usetoast";
import { useLoader } from "@/composables/commons";
import { useHandlerMessage } from "../../../composables/commons";
import { useI18n } from "vue-i18n";

const category = ref(new Category());
const pageOfCategory = ref(new Pageable());
const filters = ref({});
const store = useStore();
const filterCategory = ref(new FilterCategory());
const categoryDialog = ref(false);
const toast = useToast();
const { showLoading, hideLoading } = useLoader();
const { handlerError } = useHandlerMessage();
const { t } = useI18n();

const breadCrumbItem = ref([{ label: t("cadastre") }, { label: t("category") }]);

const home = ref({
    icon: "pi pi-home",
});

const listOfCategoryType = ref([
    { code: 0, description: "Receber" },
    { code: 1, description: "Pagar" },
]);
const categoryTypeSelected = ref({});

onBeforeMount(async () => {
    await findCategoryByFilter();
    initFilters();
});

const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    };
};

const findCategoryByFilter = async () => {
    try {
        showLoading();
        await store.dispatch("categoryStore/findCategoryByFilter", filterCategory.value);
        pageOfCategory.value = store.state.categoryStore.listPageCategory;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const editCategory = async (categoryId) => {
    try {
        showLoading();
        await store.dispatch("categoryStore/findCategoryById", categoryId);
        category.value = store.state.categoryStore.category;
        categoryTypeSelected.value = await getCategoryTypeOfCode(category.value.categoryType)
        categoryDialog.value = true;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const newCategory = async () => {
    category.value = new Category();
    categoryTypeSelected.value = await getCategoryTypeOfCode(1);
    categoryDialog.value = true;
};

const saveCategory = async () => {
    try {
        showLoading();
        category.value.categoryType = categoryTypeSelected.value.code;

        await store.dispatch("categoryStore/saveCategory", category.value);
        toast.add({
            severity: "success",
            summary: "Successful",
            detail: "Categoria salva com sucesso",
            life: 3000,
        });
        categoryDialog.value = false;
        category.value = new Category();
        await findCategoryByFilter();
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const getCategoryTypeOfCode = async (categoryTypeCode) => {
    let categoryTypeFounded = {};
    listOfCategoryType.value.filter((item) => {
        if (item.code === categoryTypeCode) {
            categoryTypeFounded = item;
        }
    });
    return categoryTypeFounded;
};
</script>
<!-- eslint-disable prettier/prettier -->
<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <div class="flex flex-row-reverse flex-wrap">
                    <Breadcrumb :home="home" :model="breadCrumbItem" />
                </div>
                <Toolbar class="mb-4">
                    <template v-slot:start>
                        <div class="my-2">
                            <Button :label="$t('new')" icon="pi pi-plus" class="mr-2" severity="success"
                                @click="newCategory" />
                            <!-- <Button label="Delete" icon="pi pi-trash" severity="danger"
                                :disabled="!selectedProducts || !selectedProducts.length" /> -->
                        </div>
                    </template>
                </Toolbar>
                <DataTable ref="dt" :value="pageOfCategory.content" dataKey="id" stripedRows showGridlines
                    :paginator="true" :rows="10" :filters="filters" :rowsPerPageOptions="[5, 10, 25]">
                    <Column field="id" :header="$t('code')" :sortable="true" headerStyle="width:15%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Code</span>
                            {{ slotProps.data.id }}
                        </template>
                    </Column>
                    <Column field="description" :header="$t('description')" :sortable="true" headerStyle="width:70%;">
                        <template #body="slotProps">
                            {{ slotProps.data.description }}
                        </template>
                    </Column>
                    <Column headerStyle="width: 10%">
                        <template #body="slotProps">
                            <Button icon="pi pi-pencil" class="mr-2" severity="success" rounded
                                @click="editCategory(slotProps.data.id)" />
                            <Button icon="pi pi-trash" class="mt-2" severity="warning" rounded
                                @click="confirmDeleteProduct(slotProps.data)" />
                        </template>
                    </Column>
                </DataTable>

                <Dialog v-model:visible="categoryDialog" :style="{ width: '450px' }" header="Categoria" :modal="true">
                    <div class="p-fluid formgrid grid">
                        <div class="field col-12 md:col-12">
                            <label for="category.description">{{
                                $t("description")
                                }}</label>
                            <InputText id="category.description" v-model="category.description" required="true" rows="3"
                                cols="20" />
                        </div>
                        <div class="field col-12 md:col-12">
                            <label for="category_type">{{ $t("categoryType") }}</label>
                            <Dropdown id="category_type" v-model="categoryTypeSelected" :options="listOfCategoryType"
                                optionLabel="description" />
                        </div>
                        <div class="field col-12 md:col-12">
                            <label for="category.spendinglimit">{{
                                $t("spendingLimit")
                                }}</label>
                            <InputNumber id="category.description" v-model="category.spendingLimit" required="true"
                                locale="pt-BR" :minFractionDigits="2" />
                        </div>
                        <div class="field col-12 md:col-12">
                            <label for="category.observation">{{
                                $t("observation")
                                }}</label>
                            <Textarea id="category.observation" v-model="category.observation" required="true" rows="3"
                                cols="20" />
                        </div>
                    </div>
                    <template #footer>
                        <Button :label="$t('save')" icon="pi pi-check" @click="saveCategory()" />
                        <Button :label="$t('cancel')" icon="pi pi-times" text @click="categoryDialog = false" />
                    </template>
                </Dialog>
            </div>
        </div>
    </div>
</template>
