<script setup>
import { useI18n } from "vue-i18n";
import { onMounted, onBeforeMount, ref } from "vue";
import { useStore } from "vuex";
import Pageable from "../../../commons/model/Pageable";
import AccountPayment from "../model/accountPayment";
import AccountPaymentFilter from "../model/accountPaymentFilter";
import { useHandlerMessage, useLoader } from "../../../composables/commons";
import { eventBus } from "@/config/eventBus";
import DialogPayment from "./DialogPaymentCad.vue";
import FilterTags from "../../../components/FilterTags.vue";

const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();

const breadCrumbItem = ref([{ label: t("financial") }, { label: t("accountPayment") }]);
const home = ref({
    icon: "pi pi-home",
});

const listPageAccountPayment = ref(new Pageable());
const accountPayment = ref(new AccountPayment());
const accountPaymentSelected = ref({});
const accountPaymentFilter = ref(new AccountPaymentFilter());
const filterPaymentOpen = ref([
    { code: 0, label: "Aberto" },
    { code: 1, label: "Fechado" },
    { code: 2, label: "Todos" },
]);

const filterPaymentOpenSelected = ref({ code: 2, label: "Todos" });

const listOfFilters = ref([])
const filterTypeSelected = ref({});
const filterTypes = ref([
    { code: 'dateProcess', title: 'Filtro por data Recebido' },
    { code: 'accountBank', title: 'Filtro por Conta' },
    { code: 'category', title: 'Filtro por categoria' }
]);
const filterDateInitial = ref(null);
const filterDateFinal = ref(null);
const filterInformation = ref({
    keyword: '',
    filterType: '',
    filterName: '',
    rangeDate: false
});


onMounted(() => {
    eventBus.on("refresh-payment", async () => {
        await findAccountPayment();
    });
});

onBeforeMount(async () => {
    await findAccountPayment();
});

const findAccountPayment = async () => {
    try {
        showLoading();
        accountPaymentFilter.value.paymentFilterType = filterPaymentOpenSelected.value.code;
        await store.dispatch(
            "accountPaymentStore/findByFilter",
            accountPaymentFilter.value
        );
        listPageAccountPayment.value =
            store.state.accountPaymentStore.listPageAccountPayment;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const formatCurrency = (value) => {
    return value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
};

const formatDate = (value) => {
    if (value === null) {
        return "";
    }

    const formatter = new Intl.DateTimeFormat("pt-BR", { dateStyle: "short" });
    let date = new Date(value);
    return formatter.format(date);
};

const openDialogPayment = async () => {
    await eventBus.emit("open-dialog-payment");
};

const registerPayment = async (id) => {
    try {
        showLoading();
        const registerPayment = {
            id: id,
            dateRegister: new Date()
        }

        await store.dispatch("accountPaymentStore/registerPayment", registerPayment);
        handlerToastSuccess("Data de pagamento registrada com sucesso");
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const addFilter = async () => {
    try {
        if (listOfFilters.value.length > 0) {
            listOfFilters.value.filter((item) => {
                if (item.filterType === filterTypeSelected.value.code) {
                    throw new Error("Tipo de filtro já foi adicionado");
                }
            });
        }

        filterInformation.value.filterType = filterTypeSelected.value.code;
        filterInformation.value.filterName = filterTypeSelected.value.title;

        if (filterTypeSelected.value.code === 'dateProcess') {
            if (filterDateInitial.value === null) {
                throw new Error("Informe a data inicial");
            }

            filterInformation.value.keyword = formatDate(filterDateInitial.value);

            if (filterDateFinal.value) {
                filterInformation.value.keyword = filterInformation.value.keyword + " até " + formatDate(filterDateFinal.value);
            }

            accountPaymentFilter.value.dateProcessedInitial = formatDate(filterDateInitial.value);
        }

        await findAccountPayment();

        listOfFilters.value.push({ ...filterInformation.value });
        filterTypeSelected.value = {};
        filterDateInitial.value = null;
        filterDateFinal.value = null;

    } catch (error) {
        handlerError(error);
    }
};

const removeFilter = async (index) => {
    const filterInformationRemove = listOfFilters.value[index];
    if (filterInformationRemove.filterType === 'dateProcess') {
        accountPaymentFilter.value.dateProcessedInitial = null;
    }

    listOfFilters.value.splice(index, 1);

    await findAccountPayment();
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
                        <div class="flex items-center gap-2 my-2" v-if="filterTypeSelected.code == 'dateProcess'">
                            <FloatLabel>
                                <Calendar v-model="filterDateInitial" showIcon iconDisplay="input" aria-label="Date" />
                                <label>Data inicio</label>
                            </FloatLabel>
                            <FloatLabel>
                                <Calendar v-model="filterDateFinal" showIcon iconDisplay="input" aria-label="Date" />
                                <label>Data Fim</label>
                            </FloatLabel>
                        </div>
                        <div class="ml-2 my-2" v-if="filterTypeSelected.code">
                            <Button icon="pi pi-plus" class="mr-2" severity="warning" rounded @click="addFilter" />
                        </div>
                    </template>
                </Toolbar>
                <Toolbar class="mb-4" v-if="listOfFilters.length">
                    <template v-slot:start>
                        <FilterTags :filters="listOfFilters" @remove="removeFilter" />
                    </template>
                </Toolbar>
                <DataTable ref="ref" :value="listPageAccountPayment.content" dataKey="id" stripedRows showGridlines
                    :paginator="true" :rows="30">
                    <template #header>
                        <div class="flex justify-content-between flex-column sm:flex-row">
                            <Dropdown v-model="filterPaymentOpenSelected" :options="filterPaymentOpen"
                                optionLabel="label" @change="findAccountPayment()">
                            </Dropdown>
                            <IconField iconPosition="left">
                                <InputIcon class="pi pi-search" />
                                <InputText placeholder="Keyword Search" style="width: 100%" />
                            </IconField>
                        </div>
                    </template>
                    <Column :header="$t('code')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.id }}
                        </template>
                    </Column>
                    <Column :header="$t('description')" headerStyle="width:25%">
                        <template #body="slotProps">
                            {{ slotProps.data.description }}
                        </template>
                    </Column>
                    <Column :header="$t('bankAccount')" headerStyle="width:25%">
                        <template #body="slotProps">
                            {{ slotProps.data.bankAccount }}
                        </template>
                    </Column>
                    <Column :header="$t('dateRegister')" headerStyle="width:25%">
                        <template #body="slotProps">
                            {{ formatDate(slotProps.data.dateRegister) }}
                        </template>
                    </Column>
                    <Column :header="$t('dateReceiver')" headerStyle="width:25%">
                        <template #body="slotProps">
                            {{ formatDate(slotProps.data.dateProcessed) }}
                        </template>
                    </Column>
                    <Column :header="$t('amount')" headerStyle="width:15%">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.amount) }}
                        </template>
                    </Column>
                    <Column headerStyle="width: 10%">
                        <template #body="slotProps">
                            <div v-if="slotProps.data.dateProcessed === null">
                                <Button icon="pi pi-check" class="mr-2" severity="success" rounded
                                    @click="registerPayment(slotProps.data.id)" />
                            </div>
                        </template>
                    </Column>
                </DataTable>
                <DialogPayment />
            </div>
        </div>
    </div>
</template>
