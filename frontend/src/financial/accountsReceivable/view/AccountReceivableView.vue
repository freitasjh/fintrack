<script setup>
import { onBeforeMount, ref } from "vue";
import Pageable from "../../../commons/model/Pageable";
import { useHandlerMessage, useLoader } from "../../../composables/commons";
import { useStore } from "vuex";
import AccountReceivableFilter from "../model/AccountReceivableFilter";
import BankAccountFilter from "../../../administrator/bankAccount/model/BankAccountFilter";

const listPageAccountReceivable = ref(new Pageable());
const accountReceivable = ref(null);
const accountReceivableFilter = ref(new AccountReceivableFilter());
const listPageBankAccount = ref(new Pageable());
const bankAccountFilter = ref(new BankAccountFilter());
const { t } = useI18n();

const breadCrumbItem = ref([
    { label: t('financial') },
    { label: t('accountReceivable') },
]);

const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();

onMounted(() => { });
onBeforeMount(async () => {
    await findAccountReceivable();
});

const findAccountReceivable = async () => {
    try {
        showLoading();
        await store.dispatch(
            "accountReceivableStore/findByFilter",
            accountReceivableFilter.value
        );
        listPageAccountReceivable.value =
            store.accountReceivableStore.listPageAccountReceivable;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const findBankAccount = async () => {
    try {
        showLoading();
        await store.dispatch(
            "bankAccountStore/findBankAccountByFilter",
            bankAccountFilter.value
        );
        listPageBankAccount.value = store.bankAccountStore.listPageBankAccount;
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
                <div class="flex flex-row-reverse flex-wrap">
                    <Breadcrumb :home="home" :model="breadCrumbItem" />
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
