<script setup>
import { useI18n } from "vue-i18n";
import { onMounted, onBeforeMount, ref, handleError } from "vue";
import { useStore } from "vuex";
import Pageable from "../../../../commons/model/Pageable";
import { useHandlerMessage, useLoader } from "../../../../composables/commons";
import DialogCreditCardTransaction from "../components/DialogCreditCardTransaction.vue";
import CreditCardTransactionFilter from "../model/creditCardTransactionFilter";

import { eventBus } from "@/config/eventBus";
import { computed } from "vue";
import CreditCard from "../../../../administrator/creditCard/model/creditCard";
import CreditCardFilter from "../../../../administrator/creditCard/model/creditCardFilter";
import { XIcon } from "lucide-vue-next";
import FilterTags from "../../../../components/FilterTags.vue";

const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();

const listOfPageTransaction = computed(() => store.state.creditCardTransactionStore.listOfPageTransaction);
const creditCardTransactionFilter = ref(new CreditCardTransactionFilter());

const listOfFilters = ref([]);
const filterInformation = ref({
    keyword: '',
    filterType: '',
    filterName: ''
});

const breadCrumbItem = ref([
    { label: t("financial") },
    { label: t("creditCard") },
    { label: t("transaction") },
]);

const filterTypeSelected = ref({});
const filterTypes = ref([
    { code: 'creditCard', title: 'Filtro por Cartão de credito' },
    { code: 'dateFilter', title: 'Filtro por data' },
    { code: 'paymentStatus', title: 'Filtro por status' }
]);

const listPageCreditCard = computed(() => store.state.creditCardStore.listPageCreditCard);
const creditCardFilter = ref(new CreditCardFilter());
const creditCardSelected = ref(new CreditCard());

const home = ref({
    icon: "pi pi-home",
});

onMounted(() => {
    eventBus.on("refresh-transaction-credit-card", async () => {
        await findByFilter();
    });
});

onBeforeMount(async () => {
    await findByFilter();
    await findCreditCard(); //TODO: tem que ser chamado quando seleciona o filtro por cartão.
});

const openDialogPayment = async () => {
    await eventBus.emit("open-dialog-credit-card-transaction");
};

const findByFilter = async () => {
    try {
        showLoading();
        await store.dispatch('creditCardTransactionStore/findByFilter', creditCardTransactionFilter.value);
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const formatCurrency = (value) => {
    return value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
};

const findCreditCard = async () => {
    await store.dispatch("creditCardStore/findByFilter", creditCardFilter.value);
}

const addFilterType = async () => {
    try {
        showLoading();
        if (listOfFilters.value.length > 0) {
            listOfFilters.value.filter((item) => {
                if (item.filterType === filterTypeSelected.value.code) {
                    throw new Error("Tipo de filtro já foi adicionado");
                }
            });
        }

        console.log(filterTypeSelected.value);
        filterInformation.value.filterType = filterTypeSelected.value.code;
        filterInformation.value.filterName = filterTypeSelected.value.title;

        if (filterTypeSelected.value.code === 'creditCard') {
            filterInformation.value.keyword = creditCardSelected.value.name;
            creditCardSelected.value = new CreditCard();
        }

        listOfFilters.value.push({ ...filterInformation.value });
        filterTypeSelected.value = {};


    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
}

const removeFilterTag = async (index) => {
    // const filterInformationRemove = listOfFilters.value[index];
    console.log(filterInformationRemove);

    // listOfFilters.value.splice(index, 1);
};

</script>
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
                                @click="openDialogPayment()" />
                        </div>
                    </template>
                    <template v-slot:center>
                        <div class="my-2 mr-2">
                            <Dropdown v-model="filterTypeSelected" :options="filterTypes" optionLabel="title"
                                placeholder="Selecione o tipo de filtro" />
                        </div>
                        <div class="my-2" v-if="filterTypeSelected.code === 'creditCard'">
                            <Dropdown v-model="creditCardSelected" :options="listPageCreditCard.content"
                                optionLabel="name" :placeholder="$t('selectCreditCard')" class="w-full">
                                <template #value="slotProps">
                                    <div v-if="slotProps.value.name !== null" class="flex align-items-center">
                                        <div>
                                            {{
                                                slotProps.value.name +
                                                " - " +
                                                slotProps.value.bankAccountName
                                            }}
                                        </div>
                                    </div>
                                    <span v-else>{{ slotProps.placeholder }}</span>
                                </template>
                                <template #option="slotProps">
                                    <div class="flex align-items-center">
                                        <div>
                                            {{
                                                slotProps.option.name +
                                                " - " +
                                                slotProps.option.bankAccountName
                                            }}
                                        </div>
                                    </div>
                                </template>
                            </Dropdown>
                        </div>
                        <div class="ml-2 my-2" v-if="filterTypeSelected.code">
                            <Button icon="pi pi-plus" class="mr-2" severity="warning" rounded @click="addFilterType" />
                        </div>
                    </template>
                </Toolbar>
                <Toolbar class="mb-4" v-if="listOfFilters.length">
                    <template v-slot:start>
                        <FilterTags :filters="listOfFilters" @remove="removeFilterTag" />
                    </template>
                </Toolbar>
                <DataTable ref="ref" :value="listOfPageTransaction.resultList" dataKey="id" stripedRows showGridlines
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
                    <Column :header="$t('amount')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.amount) }}
                        </template>
                    </Column>
                    <Column :header="$t('date')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.dateCreate }}
                        </template>
                    </Column>
                    <Column :header="$t('installments')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.installments }}
                        </template>
                    </Column>
                </DataTable>
            </div>
        </div>
        <DialogCreditCardTransaction />
    </div>
</template>
