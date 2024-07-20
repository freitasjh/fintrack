<script setup>
import { FilterMatchMode } from 'primevue/api';
import { inject, onBeforeMount, ref } from 'vue';
import { useStore } from 'vuex';
import FilterCategory from '../../category/model/FilterCategory';
import { useHandlerMessage, useLoader } from '../../../composables/commons';
import { useI18n } from 'vue-i18n';
import BankAccount from '../model/BankAccount';

const bankAccount = ref(new BankAccount());
const pageOfBankAccount = ref([]);
const listOfBank = ref(null);
const bankSelected = ref({});
const filters = ref({});
const store = useStore();
const bankAccountDialog = ref(false);
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const { t } = useI18n();

const breadCrumbItem = ref([
    { label: t('cadastre') },
    { label: t('bankAccount') },
]);
const home = ref({
    icon: 'pi pi-home'
});

const listAccountType = ref([
    { code: 1, description: t('currentAccount') },
    { code: 2, description: t('savingAccount') }
]);

const accountTypeSelected = ref({});

const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};

onBeforeMount(async () => {
    initFilters();
    await findBankAccount();
});

const findBank = async () => {
    try {
        if (!store.state.bankModuleStore.listOfBank.length) {
            await store.dispatch('bankModuleStore/findBank');
        }
        listOfBank.value = store.state.bankModuleStore.listOfBank;
    } catch (error) {
        handlerError(error);
    }
};

const findBankAccount = async () => {
    try {
        showLoading();
        await store.dispatch('bankAccountStore/findBankAccountByFilter', new FilterCategory());
        pageOfBankAccount.value = store.state.bankAccountStore.pageOfBankAccount;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const saveBankAccount = async () => {
    try {
        showLoading();
        bankAccount.value.accountType = accountTypeSelected.value.code;
        bankAccount.value.bankId = bankSelected.value.id;
        await store.dispatch("bankAccountStore/saveBankAccount", bankAccount.value);
        handlerToastSuccess("Conta bancaria salva com sucesso");
        bankAccount.value = new BankAccount();
        bankAccountDialog.value = false
        await findBankAccount();
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const editBankAccount = async (bankAccountId) => {
    try {
        showLoading();
        await findBank();
        await store.dispatch('bankAccountStore/findBankAccount', bankAccountId);
        bankAccount.value = store.state.bankAccountStore.bankAccount;
        listAccountType.value.filter((item) => {
            if (item.code == (bankAccount.value.accountType)) {
                accountTypeSelected.value = item;
            }
        });
        listOfBank.value.content.filter((item) => {
            if (item.id === bankAccount.value.bankId) {
                bankSelected.value = item;
            }
        });
        bankAccountDialog.value = true;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};
const formatCurrency = (value) => {
    return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
};

const newBankAccount = async () => {
    await findBank();
    bankAccount.value = new BankAccount();
    bankSelected.value = {};
    accountTypeSelected.value = {};
    bankAccountDialog.value = true;
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
                                @click="newBankAccount()" />
                        </div>
                    </template>
                </Toolbar>
                <DataTable ref="dt" :value="pageOfBankAccount.content" dataKey="id" stripedRows showGridlines
                    :paginator="true" :rows="30" :filters="filters" :rowsPerPageOptions="[10, 20, 30]">
                    <Column :header="$t('code')" :sortable="true" headerStyle="width:15%; min-width:10rem;">
                        <template #body="slotProps">
                            {{ slotProps.data.id }}
                        </template>
                    </Column>
                    <Column :header="$t('description')" :sortable="true" headerStyle="width:70%;">
                        <template #body="slotProps">
                            {{ slotProps.data.description }}
                        </template>
                    </Column>
                    <Column :header="$t('balance')" headerStyle="width:70%;">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.balance) }}
                        </template>
                    </Column>
                    <Column headerStyle="width: 10%">
                        <template #body="slotProps">
                            <Button icon="pi pi-pencil" class="mr-2" severity="success" rounded
                                @click="editBankAccount(slotProps.data.id)" />
                            <!-- <Button icon="pi pi-trash" class="mt-2" severity="warning" rounded
                                @click="confirmDeleteProduct(slotProps.data)" /> -->
                        </template>
                    </Column>
                </DataTable>
                <Dialog v-model:visible="bankAccountDialog" :style="{ width: '60%' }" :header="$t('bankAccount')"
                    :modal="true">
                    <div class="p-fluid formgrid grid">
                        <div class="field col-12 md:col-12">
                            <label for="bankAccount-description">{{ $t('description') }}</label>
                            <InputText v-model="bankAccount.description" />
                        </div>
                        <div class="field col-12 md:col-12">
                            <label for="bankAccount-accountType">{{ $t('accountType') }}</label>
                            <Dropdown v-model="accountTypeSelected" :options="listAccountType" optionLabel="description"
                                :placeholder="$t('selectAccountType')" class="w-full" />
                        </div>
                        <div class="field col-12 md:col-12">
                            <label for="bankAccount-bank">{{ $t('bank') }}</label>
                            <Dropdown filter v-model="bankSelected" :options="listOfBank.content" optionLabel="name"
                                :placeholder="$t('selectBank')" class="w-full" />
                        </div>
                        <div class="field col-12 md:col-12">
                            <label for="bankAccount-account">{{ $t('account') }}</label>
                            <InputText v-model="bankAccount.account" />
                        </div>
                        <div class="field col-12 md:col-12">
                            <label for="bankAccount-agency">{{ $t('agency') }}</label>
                            <InputText v-model="bankAccount.agency" />
                        </div>
                        <div v-if="bankAccount.id === null" class="field col-12 md:col-12">
                            <label for="bankAccount-initialValue">{{ $t('initialValue') }}</label>
                            <InputNumber v-model="bankAccount.initialValue" locale="pt-BR" :minFractionDigits="2" />
                        </div>
                        <div v-else class="field col-12 md:col-12">
                            <label for="bankAccount-initialValue">{{ $t('balance') }}</label>
                            <InputNumber v-model="bankAccount.balance" locale="pt-BR" :minFractionDigits="2" disabled />
                        </div>
                    </div>
                    <template #footer>
                        <Button :label="$t('save')" text icon="pi pi-check" @click="saveBankAccount()" />
                        <Button :label="$t('cancel')" icon="pi pi-times" text @click="bankAccountDialog = false" />
                    </template>
                </Dialog>
            </div>
        </div>
    </div>
</template>
