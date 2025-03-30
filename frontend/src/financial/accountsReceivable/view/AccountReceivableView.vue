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
import FilterTags from "../../../components/FilterTags.vue";


const listPageAccountReceivable = ref(new Pageable());
const accountReceivable = ref(new AccountReceivableFilter());
const accountReceivableFilter = ref(new AccountReceivableFilter());
const listPageBankAccount = ref(new Pageable());
const bankAccountSelected = ref({});
const bankAccountFilter = ref(new BankAccountFilter());
const filterBankAccountSelected = ref({});
const { t } = useI18n();

const accountReceivableDialog = ref(false);
const listOfCategory = ref([]);
const categorySelected = ref({});

const listOfFilters = ref([]);
const filterDate = ref(null);
const filterInformation = ref({
    keyword: '',
    filterType: '',
    filterName: ''
});

const filterTypeSelected = ref({});
const filterTypes = ref([
    { code: 'dateProcess', title: 'Filtro por data Recebido' },
    { code: 'accountBank', title: 'Filtro por Conta' }
]);

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
    await findBankAccount();
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

const addFilterType = async () => {
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
            filterInformation.value.keyword = formatDate(filterDate.value);
            accountReceivableFilter.value.dateProcessed = formatDate(filterDate.value);
        } else if (filterTypeSelected.value.code === 'accountBank') {
            filterInformation.value.keyword = filterBankAccountSelected.value.bankDescription;
            accountReceivableFilter.value.accountId = filterBankAccountSelected.value.id;
            filterBankAccountSelected.value = {};
        }


        await findAccountReceivable();

        listOfFilters.value.push({ ...filterInformation.value });
        filterTypeSelected.value = {};
        filterDate.value = null;

    } catch (error) {
        handlerError(error);
    }
};

const removeFilter = async (index) => {
    const filterInformationRemove = listOfFilters.value[index];

    if (filterInformationRemove.filterType === 'dateProcess') {
        accountReceivableFilter.value.dateProcessed = null;
    }

    if (filterInformationRemove.filterType === 'accountBank') {
        accountReceivableFilter.value.accountId = null;
    }

    listOfFilters.value.splice(index, 1);

    await findAccountReceivable();
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
                    <template v-slot:center>
                        <div class="my-2 mr-2">
                            <Dropdown v-model="filterTypeSelected" :options="filterTypes" optionLabel="title"
                                placeholder="Selecione o tipo de filtro" />
                        </div>
                        <div class="my-2" v-if="filterTypeSelected.code == 'dateProcess'">
                            <Calendar v-model="filterDate" showIcon iconDisplay="input" />
                        </div>
                        <div class="my-2" v-if="filterTypeSelected.code === 'accountBank'">
                            <Dropdown v-model="filterBankAccountSelected" :options="listPageBankAccount.content"
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
