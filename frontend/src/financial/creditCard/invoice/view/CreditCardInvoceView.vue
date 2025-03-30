<script setup>
import { useI18n } from "vue-i18n";
import { onMounted, onBeforeMount, ref } from "vue";
import { useStore } from "vuex";
import Pageable from "../../../../commons/model/Pageable";
import { useHandlerMessage, useLoader } from "../../../../composables/commons";
import CreditCardInvoiceService from "../service/creditCardInvoiceService";
import CreditCardInvoicePayment from '../model/creditCardInvoicePayment'
import BankAccountFilter from "../../../../administrator/bankAccount/model/BankAccountFilter";
import { XIcon } from "lucide-vue-next";
import FilterTags from "../../../../components/FilterTags.vue";
import CreditCardTransactionFilter from "../../transaction/model/creditCardTransactionFilter";
import { computed } from "vue";
import CreditCardFilter from "../../../../administrator/creditCard/model/creditCardFilter";
import CreditCard from "../../../../administrator/creditCard/model/creditCard";
import CreditCardInvoiceFilter from "../model/creditCardInvoiceFilter";

const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();
const registerPaymentVisible = ref(false);

const listPageCreditCardInvoice = ref([]);
const breadCrumbItem = ref([{ label: t("creditCard") }, { label: t("invoice") }]);
const home = ref({
    icon: "pi pi-home",
});

const rowLimit = ref(30);

const listPageBankAccount = ref([]);
const bankAccountSelected = ref({});
const creditCardInvoiceSelected = ref({});
const expandInstallmentRow = ref({});
const creditCardInvoiceFilter = ref(new CreditCardInvoiceFilter())

const service = new CreditCardInvoiceService();
const creditCardInvoicePayment = ref(new CreditCardInvoicePayment());
const bankAccountFilter = ref(new BankAccountFilter());

const listPageCreditCard = computed(() => store.state.creditCardStore.listPageCreditCard);
const creditCardFilter = ref(new CreditCardFilter());
const creditCardSelected = ref(new CreditCard());
const responseFindInvoice = ref({});

const listOfFilters = ref([]);
const filterInformation = ref({
    keyword: '',
    filterType: '',
    filterName: ''
});


const filterTypeSelected = ref({});
const filterTypes = ref([
    { code: 'creditCard', title: 'Filtro por Cartão de credito' },
    { code: 'dateFilter', title: 'Filtro por data' },
    { code: 'paymentStatus', title: 'Filtro por status' }
]);

const statusType = ref([
    { code: 'OPEN', title: 'Aberto' },
    { code: 'PENDING', title: 'Pendente' },
    { code: 'PAID', title: 'Pago' }
]);
const statusTypeSelected = ref({});

onBeforeMount(async () => {
    await findByFilter();
    await findCreditCard();
});

const findByFilter = async () => {
    try {
        showLoading();
        const response = await service.findByFilter(creditCardInvoiceFilter.value);
        responseFindInvoice.value = response.data;
        console.log(responseFindInvoice.value.totalResults);

        listPageCreditCardInvoice.value = response.data.resultList;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
}

const formatCurrency = (value) => {
    return value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
};

const findBankAccount = async () => {
    await store.dispatch(
        "bankAccountStore/findBankAccountByFilter",
        bankAccountFilter.value
    );

    listPageBankAccount.value = store.state.bankAccountStore.pageOfBankAccount;
};

const registerPayment = async () => {
    try {
        showLoading();

        creditCardInvoicePayment.value.creditCardInvoiceId = creditCardInvoiceSelected.value.id;
        creditCardInvoicePayment.value.bankAccountId = bankAccountSelected.value.id;
        creditCardInvoicePayment.value.datePay = new Date();
        creditCardInvoicePayment.value.amount = creditCardInvoiceSelected.value.amount;

        await service.registerPayment(creditCardInvoicePayment.value);

        handlerToastSuccess("Fatura paga com sucesso");

        registerPaymentVisible.value = false;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
}

const openRegisterPayment = async (creditCardInvoice) => {
    try {
        showLoading();
        await findBankAccount();

        creditCardInvoiceSelected.value = creditCardInvoice;
        registerPaymentVisible.value = true;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
}

const findInstallmentOfInvoice = async (event) => {
    try {
        showLoading();
        const invoiceId = event.data.id;

        const response = await service.findInstallmentByInvoiceId(invoiceId);

        // Cria uma nova lista atualizando apenas o item correspondente
        listPageCreditCardInvoice.value = listPageCreditCardInvoice.value.map(item =>
            item.id === invoiceId
                ? { ...item, installments: response.data }
                : item
        );
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
}

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

        filterInformation.value.filterType = filterTypeSelected.value.code;
        filterInformation.value.filterName = filterTypeSelected.value.title;

        if (filterTypeSelected.value.code === 'creditCard') {
            filterInformation.value.keyword = creditCardSelected.value.name;
            creditCardInvoiceFilter.value.creditCardId = creditCardSelected.value.id;
            creditCardSelected.value = new CreditCard();
        } else if (filterTypeSelected.value.code === 'paymentStatus') {
            filterInformation.value.keyword = statusTypeSelected.value.title;
            creditCardInvoiceFilter.value.statusType = statusTypeSelected.value.code;
            statusTypeSelected.value = {};
        }

        listOfFilters.value.push({ ...filterInformation.value });
        filterTypeSelected.value = {};

        await findByFilter();

    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
}

const removeFilter = async (index) => {
    const filterInformationRemove = listOfFilters.value[index];

    if (filterInformationRemove.filterType === 'creditCard') {
        creditCardInvoiceFilter.value.creditCardId = null;
    } else if (filterInformationRemove.filterType === 'paymentStatus') {
        creditCardInvoiceFilter.value.statusType = null;
    }

    listOfFilters.value.splice(index, 1);

    await findByFilter();
};

const pageEvent = async (event) => {
    console.log(event);
    rowLimit.value = event.rows;
    creditCardInvoiceFilter.value.limit = rowLimit.value;
    creditCardInvoiceFilter.value.page = ++event.page;
    await findByFilter();
};

const sortEvent = async (event) => {
    console.log(event);
    creditCardInvoiceFilter.value.sortField = event.sortField;
    if (event.sortOrder === -1) {
        creditCardInvoiceFilter.value.sortOrder = 'asc';
    } else {
        creditCardInvoiceFilter.value.sortOrder = 'desc';
    }

    await findByFilter();
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
                            <Button :label="$t('new')" icon="pi pi-plus" class="mr-2" severity="success" />
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
                        <div class="my-2" v-if="filterTypeSelected.code === 'paymentStatus'">
                            <Dropdown v-model="statusTypeSelected" :options="statusType" optionLabel="title"
                                class="w-full" placeholder="Selecione o Status" />
                        </div>
                        <div class="ml-2 my-2" v-if="filterTypeSelected.code">
                            <Button icon="pi pi-plus" class="mr-2" severity="warning" rounded @click="addFilterType" />
                        </div>
                    </template>
                </Toolbar>
                <Toolbar class="mb-4" v-if="listOfFilters.length">
                    <template v-slot:start>
                        <FilterTags :filters="listOfFilters" @remove="removeFilter" />
                    </template>
                </Toolbar>
                <Paginator :rows="rowLimit" :totalRecords="responseFindInvoice.totalResults"
                    :rowsPerPageOptions="[10, 20, 30]" @page="pageEvent">
                </Paginator>
                <DataTable v-model:expandedRows="expandInstallmentRow" ref="ref" :value="listPageCreditCardInvoice"
                    dataKey="id" stripedRows showGridlines :rows="rowLimit" @rowExpand="findInstallmentOfInvoice"
                    :lazy="true" @sort="sortEvent">
                    <Column expander style="width: 5rem" />
                    <Column field="id" :header="$t('code')" headerStyle="5%" sortable />
                    <Column field="dueDate" :header="$t('dueDate')" headerStyle="5%" sortable>

                    </Column>
                    <Column :header="$t('description')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.creditCardDescription }}
                        </template>
                    </Column>
                    <Column field="amount" :header="$t('amount')" headerStyle="5%" sortable>
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.amount) }}
                        </template>
                    </Column>
                    <Column header="Status" headerStyle="5%">
                        <template #body="slotProps">
                            <Tag icon="pi pi-info-circle" severity="info" :value="$t('open')"
                                v-if="slotProps.data.statusType === 'OPEN'" />
                            <Tag icon="pi pi-exclamation-triangle" severity="warning" :value="$t('pending')"
                                v-if="slotProps.data.statusType === 'PENDING'" />
                            <Tag icon="pi pi-check" severity="success" :value="$t('paid')"
                                v-if="slotProps.data.statusType === 'PAID'" />

                        </template>
                    </Column>
                    <Column headerStyle="width: 5%">
                        <template #body="slotProps">
                            <div v-if="slotProps.data.statusType !== 'PAID'">
                                <Button icon="pi pi-pencil" class="mr-2" severity="success" rounded
                                    @click="openRegisterPayment(slotProps.data)" />
                            </div>
                        </template>
                    </Column>
                    <template #expansion="slotProps">
                        <div class="p-3">
                            <h5>Demonstrativo </h5>
                            <DataTable :value="slotProps.data.installments">
                                <Column field="description" header="Descrição" />
                                <Column field="dateCreate" header="Date" />
                                <Column field="amount" header="Valor" sortable>
                                    <template #body="slotProps">
                                        {{ formatCurrency(slotProps.data.amount) }}
                                    </template>
                                </Column>
                            </DataTable>
                        </div>
                    </template>
                </DataTable>
            </div>
        </div>
        <Dialog v-model:visible="registerPaymentVisible" :style="{ width: '40%' }" :modal="true">
            <div class="p-fluid formgrid grid">
                <div class="field col-12 md:col-12">
                    <label for="accountPayment-description">{{ $t("description") }}</label>
                    <InputText v-model.lazy="creditCardInvoiceSelected.creditCardDescription" />
                </div>
                <div class="field col-12 md:col-12">
                    <label for="accountPayment-bakAccount">{{ $t("bankAccount") }}</label>
                    <Dropdown v-model="bankAccountSelected" :options="listPageBankAccount.content"
                        optionLabel="description" :placeholder="$t('selectBankAccount')" class="w-full">
                        <template #value="slotProps">
                            <div v-if="slotProps.value.description !== undefined" class="flex align-items-center">
                                <div>
                                    {{
                                        slotProps.value.bankDescription +
                                        " - " +
                                        slotProps.value.description
                                    }}
                                </div>
                            </div>
                            <span v-else>{{ slotProps.placeholder }}</span>
                        </template>
                        <template #option="slotProps">
                            <div class="flex align-items-center">
                                <div>
                                    {{
                                        slotProps.option.bankDescription +
                                        " - " +
                                        slotProps.option.description
                                    }}
                                </div>
                            </div>
                        </template>
                    </Dropdown>
                </div>
                <div class="field col-12 md:col-12">
                    <label>Data Vencimento</label>
                    <Calendar v-model="creditCardInvoiceSelected.dueDate" dateFormat="dd/mm/yy" disabled />
                </div>
                <div class="field col-12 md:col-12">
                    <label for="accountPayment-amount">{{ $t("amount") }}</label>
                    <InputNumber v-model.lazy="creditCardInvoiceSelected.amount" :minFractionDigits="2"
                        :maxFractionDigits="5" disabled />
                </div>
            </div>
            <template #footer>
                <Button :label="$t('save')" text icon="pi pi-check" @click="registerPayment()" />
                <Button :label="$t('cancel')" icon="pi pi-times" text @click="registerPaymentVisible = false" />
            </template>
        </Dialog>
    </div>
</template>