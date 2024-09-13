<script setup>
import { onBeforeMount, onMounted, ref } from "vue";
import { eventBus } from "@/config/eventBus";
import AccountPayment from "../model/accountPayment";
import { useI18n } from "vue-i18n";
import { useHandlerMessage, useLoader } from "../../../composables/commons";
import { useStore } from "vuex";
import BankAccountFilter from "../../../administrator/bankAccount/model/BankAccountFilter";
import FilterCategory from "../../../administrator/category/model/FilterCategory";
import { onUnmounted } from "vue";

const dialogVisible = ref(false);
const accountPayment = ref(new AccountPayment());
const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();
const bankAccountFilter = ref(new BankAccountFilter());
const listPageBankAccount = ref([]);
const bankAccountSelected = ref({});
const listOfCategory = ref([]);
const categorySelected = ref({});

onMounted(() => {
    eventBus.on("open-dialog-payment", openDialogHandler);
});

onUnmounted(() => {
    eventBus.off("open-dialog-payment", openDialogHandler);
});

const openDialogHandler = async () => {
    try {
        showLoading();
        await findBankAccount();
        dialogVisible.value = true;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }

};

const findBankAccount = async () => {
    await store.dispatch(
        "bankAccountStore/findBankAccountByFilter",
        bankAccountFilter.value
    );

    listPageBankAccount.value = store.state.bankAccountStore.pageOfBankAccount;
    await findCategory();
};

const savePayment = async () => {
    try {
        showLoading();
        accountPayment.value.bankAccountId = bankAccountSelected.value.id;
        accountPayment.value.categoryId = categorySelected.value.id;
        await store.dispatch("accountPaymentStore/save", accountPayment.value);
        dialogVisible.value = false;
        eventBus.emit("refresh-payment");
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }


};
const findCategory = async () => {
    try {
        let filterCategory = new FilterCategory();
        filterCategory.categoryType = 1;
        await store.dispatch('categoryStore/findCategoryByFilter', filterCategory);
        listOfCategory.value = store.state.categoryStore.listPageCategory;
    } catch (error) {
        handlerError(error);
    }
};
</script>

<template>
    <Dialog v-model:visible="dialogVisible" :style="{ width: '40%' }" :header="$t('accountPayment')" :modal="true">
        <div class="p-fluid formgrid grid">
            <div class="field col-12 md:col-12">
                <label for="accountPayment-description">{{ $t("description") }}</label>
                <InputText v-model.lazy="accountPayment.description" />
            </div>
            <div class="field col-12 md:col-12">
                <label for="accountPayment-bakAccount">{{ $t("bankAccount") }}</label>
                <Dropdown v-model="bankAccountSelected" :options="listPageBankAccount.content" optionLabel="description"
                    :placeholder="$t('selectBankAccount')" class="w-full">
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
                <label>Categoria</label>
                <Dropdown v-model="categorySelected" :options="listOfCategory.content" optionLabel="description"
                    placeholder="Selecione a categoria" class="w-full">
                </Dropdown>
            </div>
            <div class="field col-12 md:col-12">
                <label for="accountPayment-amount">{{ $t("amount") }}</label>
                <InputNumber v-model.lazy="accountPayment.amount" :minFractionDigits="2" :maxFractionDigits="5" />
            </div>
            <div class="field col-12 md:col-12">
                <label for="accountPayment-dateReceiver">
                    {{ $t("dateIncoming") }}
                </label>
                <Calendar v-model.lazy="accountPayment.dateProcessed" showIcon iconDisplay="input"
                    dateFormat="dd/mm/yy" />
            </div>
            <div class="field col-12 md:col-12">
                <label for="accountPayment-paymentDueDate">
                    {{ $t("dueDate") }}
                </label>
                <Calendar v-model.lazy="accountPayment.paymentDueDate" showIcon iconDisplay="input"
                    dateFormat="dd/mm/yy" />
            </div>
        </div>

        <template #footer>
            <Button :label="$t('save')" text icon="pi pi-check" @click="savePayment()" />
            <Button :label="$t('cancel')" icon="pi pi-times" text @click="dialogVisible = false" />
        </template>
    </Dialog>
</template>
