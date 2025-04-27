<script setup>
import { ref, computed, onBeforeMount } from "vue";
import { useCurrency, useHandlerMessage, useLoader } from "../../../composables/commons";
import { useI18n } from "vue-i18n";
import { useStore } from "vuex";

const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const { formatCurrency } = useCurrency();
const store = useStore();

const listOfBudgetPlanning = computed(() => store.state.budgetPlanningStore.listOfPageBudgetPlanning);

onBeforeMount(async () => {
    await findByFilter();
});

const findByFilter = async () => {
    try {
        showLoading();
        await store.dispatch("budgetPlanningStore/findByFilter", null);
        console.log("listOfBudgetPlanning", listOfBudgetPlanning.value);
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};
</script>

<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <Toolbar class="mb-4">
                    <template v-slot:start>
                        <div class="my-2">
                            <Button :label="$t('new')" icon="pi pi-plus" class="mr-2" severity="success" />
                        </div>
                    </template>
                </Toolbar>
                <!-- <DataTable ref="ref" :value="listOfBudgetPlanning.resultList" dataKey="id" stripedRows showGridlines
                    :paginator="true" :rows="30">
                    <Column :header="$t('code')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.id }}
                        </template>
                    </Column>
                    <Column :header="$t('description')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.description }}
                        </template>
                    </Column>
                    <Column :header="$t('mouthAndYearPlaning')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.mouthAndYearPlaning }}
                        </template>
                    </Column>
                    <Column :header="$t('expectedValueExpense')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.expectedValueExpense) }}
                        </template>
                    </Column>
                </DataTable>
            </div> -->
                <DataView :value="listOfBudgetPlanning.resultList">
                    <template #list="slotProps">
                        <div class="grid grid-nogutter">
                            <div v-for="(item, index) in slotProps.items" :key="index" class="col-12">
                                <div class=" card border-color flex flex-column sm:flex-row sm:align-items-center p-4 gap-3"
                                    :class="{ 'border-top-1 surface-border': index !== 0 }"
                                    style="border-left-width: 10px;">
                                    <div
                                        class="flex flex-column sm:flex-row justify-content-between sm:align-items-center flex-1 gap-4">
                                        <div
                                            class="flex flex-row md:flex-column justify-content-between align-items-start gap-2">
                                            <div>
                                                <span class="font-medium text-secondary text-sm">Descrição
                                                </span>
                                                <div class="text-lg font-medium text-900 mt-1">{{ item.description }}
                                                </div>
                                            </div>
                                            <div class="surface-100 p-1" style="border-radius: 30px">
                                                <div class="surface-0 flex align-items-center gap-2 justify-content-center py-1 px-2"
                                                    style="border-radius: 30px; box-shadow: 0px 1px 2px 0px rgba(0, 0, 0, 0.04), 0px 1px 2px 0px rgba(0, 0, 0, 0.06)">
                                                    <i class="pi pi-calendar text-yellow-500"></i>
                                                    <span class="text-900 font-medium text-sm">{{
                                                        item.mouthAndYearPlaning }}</span>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="flex flex-column md:align-items-end gap-2">
                                        <span class="font-medium text-secondary text-sm">Gasto planejado</span>
                                        <span class="text-xl  font-semibold text-teal-600">{{
                                            formatCurrency(item.expectedValueExpense) }}</span>
                                        <div class="flex flex-row-reverse md:flex-row gap-2">
                                            <Button icon="pi pi-pencil" outlined title="Editar" severity="info"
                                                class="flex-auto md:flex-initial white-space-nowrap"></Button>
                                            <Button icon="pi pi-list" outlined title="Categorias"
                                                severity="contrast"></Button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </template>
                </DataView>
            </div>
        </div>
    </div>
</template>
<style scoped>
.border-color {
    border-color: #7CB5E8FF;

}
</style>