<script setup>
import { onBeforeMount, ref } from "vue";
import Pageable from "../../../commons/model/Pageable";
import { useHandlerMessage, useLoader } from "../../../composables/commons";
import { useStore } from "vuex";
import AccountReceivableFilter from "../model/AccountReceivableFilter";
import BankAccountFilter from "../../../administrator/bankAccount/model/BankAccountFilter";
import { useI18n } from "vue-i18n";
import { onMounted } from "vue";
import AccountReceivable from '../model/AccountReceivable';
import FilterCategory from "../../../administrator/category/model/FilterCategory";


const listPageAccountReceivable = ref(new Pageable());
const accountReceivable = ref(new AccountReceivableFilter());
const accountReceivableFilter = ref(new AccountReceivableFilter());
const listPageBankAccount = ref(new Pageable());
const bankAccountSelected = ref({});
const bankAccountFilter = ref(new BankAccountFilter());
const { t } = useI18n();
const accountReceivableDialog = ref(false);
const listOfCategory = ref([]);
const categorySelected = ref({});

const breadCrumbItem = ref([
    { label: t('financial') },
    { label: t('accountReceivable') },
]);
const home = ref({
    icon: 'pi pi-home'
});

const selectedRecurringFrequency = ref({});
const recurringFrequencyList = ref([
    { name: "Semanal", code: "WEEKLY" },
    { name: "Mensal", code: "MONTHLY" },
    { name: "Anual", code: "YEAR" },
]);

const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();

onMounted(() => { });
onBeforeMount(async () => {
    await findAccountReceivable();
});

const formatCurrency = (value) => {
    return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
};
const formatDate = (value) => {
    const formatter = new Intl.DateTimeFormat('pt-BR', { dateStyle: 'short' });
    let date = new Date(value);
    return formatter.format(date);
}
const findAccountReceivable = async () => {
    try {
        showLoading();
        await store.dispatch(
            "accountReceivableStore/findByFilter",
            accountReceivableFilter.value
        );
        listPageAccountReceivable.value =
            store.state.accountReceivableStore.listPageAccountReceivable;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const findBankAccount = async () => {
    try {
        showLoading();
        if (listPageBankAccount.value.size === 0) {
            await store.dispatch(
                "bankAccountStore/findBankAccountByFilter",
                bankAccountFilter.value
            );
            listPageBankAccount.value = store.state.bankAccountStore.pageOfBankAccount;
        }
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const newAccountReceivable = async () => {
    accountReceivableDialog.value = true;
    bankAccountSelected.value = {}
    accountReceivable.value = new AccountReceivable();
    selectedRecurringFrequency.value = {};
    await findBankAccount();


    await findCategory();
};

const editAccountReceivable = async (accountReceivableId) => {
    try {
        showLoading();
        await store.dispatch("accountReceivableStore/findById", accountReceivableId);
        accountReceivable.value = store.state.accountReceivableStore.accountReceivable;
        await findBankAccount();
        listPageBankAccount.value.content.filter((item) => {
            if (item.id === accountReceivable.value.bankAccountId) {
                bankAccountSelected.value = item;
            }
        });
        accountReceivableDialog.value = true;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};
const save = async () => {
    try {
        showLoading();
        accountReceivable.value.bankAccountId = bankAccountSelected.value.id;
        accountReceivable.value.frequencyType = selectedRecurringFrequency.code;

        await store.dispatch('accountReceivableStore/save', accountReceivable.value);
        handlerToastSuccess("Salvo com sucesso");
        accountReceivableDialog.value = false;
        accountReceivable.value = {};
        bankAccountSelected.value = {};
        await findAccountReceivable();
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const findCategory = async () => {
    try {
        let filterCategory = new FilterCategory();
        filterCategory.categoryType = 0;
        await store.dispatch('categoryStore/findCategoryByFilter', filterCategory);
        listOfCategory.value = store.state.categoryStore.listPageCategory;
    } catch (error) {
        console.log(error);
        handlerError(error);
    }
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
                                @click="newAccountReceivable()" />
                        </div>
                    </template>
                </Toolbar>
                <DataTable ref="ref" :value="listPageAccountReceivable.content" dataKey="id" stripedRows showGridlines
                    :paginator="true" :rows="30">
                    <Column :header="$t('code')" headerStyle="width:15%">
                        <template #body="slotProps">
                            {{ slotProps.data.id }}
                        </template>
                    </Column>
                    <Column :header="$t('bankAccount')" headerStyle="width:15%">
                        <template #body="slotProps">
                            {{ slotProps.data.bankAccountDescription }}
                        </template>
                    </Column>
                    <Column :header="$t('dateRegister')" headerStyle="width:15%">
                        <template #body="slotProps">
                            {{ formatDate(slotProps.data.dateRegister) }}
                        </template>
                    </Column>
                    <Column :header="$t('dateReceiver')" headerStyle="width:15%">
                        <template #body="slotProps">
                            {{ formatDate(slotProps.data.dateProcessed) }}
                        </template>
                    </Column>
                    <Column :header="$t('amount')" headerStyle="width:15%">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.amount) }}
                        </template>
                    </Column>
                    <Column headerStyle="width: 5%">
                        <template #body="slotProps">
                            <Button icon="pi pi-pencil" class="mr-2" severity="success" rounded
                                @click="editAccountReceivable(slotProps.data.id)" />
                            <!-- <Button icon="pi pi-trash" class="mt-2" severity="warning" rounded
                                @click="confirmDeleteProduct(slotProps.data)" /> -->
                        </template>
                    </Column>
                </DataTable>
                <Dialog v-model:visible="accountReceivableDialog" :style="{ width: '40%' }"
                    :header="$t('accountReceivable')" :modal="true">
                    <div class="p-fluid formgrid grid">
                        <div class="field col-12 md:col-12">
                            <label for="accountReceivable-description">{{ $t('description') }}</label>
                            <InputText v-model="accountReceivable.description" />
                        </div>
                        <div class="field col-12 md:col-12">
                            <label for="accountReceivable-bakAccount">{{ $t('bankAccount') }}</label>
                            <Dropdown v-model="bankAccountSelected" :options="listPageBankAccount.content"
                                optionLabel="description" :placeholder="$t('selectBankAccount')" class="w-full">
                                <template #value="slotProps">
                                    <div v-if="slotProps.value.description !== undefined"
                                        class="flex align-items-center">
                                        <div>{{ slotProps.value.bankDescription +
                                            " - " + slotProps.value.description }}</div>
                                    </div>
                                    <span v-else>
                                        {{ slotProps.placeholder }}
                                    </span>
                                </template>
                                <template #option="slotProps">
                                    <div class="flex align-items-center">
                                        <div>{{ slotProps.option.bankDescription +
                                            " - " + slotProps.option.description }}</div>
                                    </div>
                                </template>
                            </Dropdown>
                        </div>
                        <div class="field col-12 md:col-12">
                            <label>Categoria</label>
                            <Dropdown v-model="categorySelected" :options="listOfCategory.content"
                                optionLabel="description" placeholder="Selecione a categoria" class="w-full">
                            </Dropdown>
                        </div>

                        <div class="field col-12 md:col-12">
                            <label for="accountReceivable-amount">{{ $t('amount') }}</label>
                            <InputNumber v-model.lazy="accountReceivable.amount" :minFractionDigits="2"
                                :maxFractionDigits="5" />
                        </div>
                        <div class="field col-12 md:col-12">
                            <label for="accountReceivable-dateReceiver">{{ $t('dateReceiver') }}</label>
                            <Calendar v-model.lazy="accountReceivable.dateProcessed" showIcon iconDisplay="input"
                                dateFormat="dd/mm/yy" />
                        </div>
                        <div class="field col-12 md:col-12">
                            <label for="accountReceivable-recurring Transaction">Recorrente</label>
                            <div>
                                <InputSwitch v-model="accountReceivable.recurringTransaction" />
                            </div>
                        </div>
                        <div v-if="accountReceivable.recurringTransaction">
                            <div class="field col-12 md:col-12">
                                <label>Fixo</label>
                                <div>
                                    <InputSwitch v-model="accountReceivable.recurringTransactionFixed" />
                                </div>
                            </div>
                            <div class="field col-12 md:col-12" v-if="!accountReceivable.recurringTransactionFixed">
                                <label>Frequencia</label>
                                <Dropdown v-model="selectedRecurringFrequency" :options="recurringFrequencyList"
                                    optionLabel="name" placeholder="Selecione a frequencia" class="w-full" />
                            </div>
                        </div>
                    </div>
                    <template #footer>
                        <Button :label="$t('save')" text icon="pi pi-check" @click="save()" />
                        <Button :label="$t('cancel')" icon="pi pi-times" text
                            @click="accountReceivableDialog = false" />
                    </template>
                </Dialog>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
