<script setup>
import { useI18n } from "vue-i18n";
import { onMounted, onBeforeMount, ref } from "vue";
import { useStore } from "vuex";
import Pageable from "../../../../commons/model/Pageable";
import { useHandlerMessage, useLoader } from "../../../../composables/commons";
import CreditCardInvoiceService from "../service/creditCardInvoiceService";
import CreditCardInvoicePayment from '../model/creditCardInvoicePayment'
import BankAccountFilter from "../../../../administrator/bankAccount/model/BankAccountFilter";

const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();
const registerPaymentVisible = ref(false);

const listPageCreditCardInvoice = ref([]);
const breadCrumbItem = ref([{ label: t("creditCard") }, { label: t("invoice") }]);

const listPageBankAccount = ref([]);
const bankAccountSelected = ref({});
const creditCardInvoiceSelected = ref({});
const expandInstallmentRow = ref({});

const service = new CreditCardInvoiceService();
const creditCardInvoicePayment = ref(new CreditCardInvoicePayment());
const bankAccountFilter = ref(new BankAccountFilter());

const home = ref({
    icon: "pi pi-home",
});

onBeforeMount(async () => {
    await findByFilter();
});

const findByFilter = async () => {
    try {
        const response = await service.findByFilter(null);

        listPageCreditCardInvoice.value = response.data;
        console.log(listPageCreditCardInvoice)
    } catch (error) {
        console.log(error);
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
                </Toolbar>
                <DataTable v-model:expandedRows="expandInstallmentRow" ref="ref" :value="listPageCreditCardInvoice"
                    dataKey="id" stripedRows showGridlines :paginator="true" :rows="30"
                    @rowExpand="findInstallmentOfInvoice">
                    <Column expander style="width: 5rem" />
                    <Column :header="$t('code')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.id }}
                        </template>
                    </Column>
                    <Column :header="$t('dueDate')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.dueDate }}
                        </template>
                    </Column>
                    <Column :header="$t('description')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.creditCardDescription }}
                        </template>
                    </Column>
                    <Column :header="$t('amount')" headerStyle="5%">
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