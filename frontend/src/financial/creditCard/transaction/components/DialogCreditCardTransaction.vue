<script setup>
import { onBeforeMount, onMounted, ref, onUnmounted, computed } from "vue";
import { eventBus } from "@/config/eventBus";
import { useI18n } from "vue-i18n";
import { useHandlerMessage, useLoader } from "../../../../composables/commons";
import { useStore } from "vuex";
import CreditCardFilter from "../../../../administrator/creditCard/model/creditCardFilter";
import CreditCard from "../../../../administrator/creditCard/model/creditCard";


const dialogVisible = ref(false);
const transactionStore = ref("creditCardTransactionStore");
const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();
const creditCardTransaction = computed(() => store.state.creditCardTransactionStore.creditCardTransaction);
const listPageCreditCard = computed(() => store.state.creditCardStore.listPageCreditCard);
const creditCardFilter = ref(new CreditCardFilter());
const creditCardSelected = ref(new CreditCard());


onMounted(() => {
    eventBus.on("open-dialog-credit-card-transaction", openDialogHandler);
});

onUnmounted(() => {
    eventBus.off("open-dialog-credit-card-transaction", openDialogHandler);
});


const findCreditCard = async () => {
    await store.dispatch("creditCardStore/findByFilter", creditCardFilter.value);
}

const openDialogHandler = async () => {
    try {
        showLoading();
        store.commit(`${transactionStore.value}/clearInformation`);
        creditCardSelected.value = new CreditCard();
        await findCreditCard();

        dialogVisible.value = true;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};


const save = async () => {
    try {
        showLoading()
        store.commit(`${transactionStore.value}/setCreditCard`, creditCardSelected.value.id);
        await store.dispatch(`${transactionStore.value}/save`);

        dialogVisible.value = false;

        eventBus.emit('refresh-transaction-credit-card');
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};
</script>
<template>
    <Dialog v-model:visible="dialogVisible" :style="{ width: '40%' }" :header="$t('transaction')" :modal="true">
        <div class="p-fluid formgrid grid">
            <div class="field col-12 md:col-12">
                <label for="accountPayment-description">{{ $t("description") }}</label>
                <InputText v-model.lazy="creditCardTransaction.description" />
            </div>
            <div class="field col-12 md:col-12">
                <label for="creditCardTransaction-creditCard">{{ $t("creditCard") }}</label>
                <Dropdown v-model="creditCardSelected" :options="listPageCreditCard.content" optionLabel="name"
                    :placeholder="$t('selectCreditCard')" class="w-full">
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
            <div class="field col-12 md:col-12">
                <label for="creditCardTransaction-amount">{{ $t("amount") }}</label>
                <InputNumber v-model.lazy="creditCardTransaction.amount" :minFractionDigits="2" :maxFractionDigits="5"
                    id="creditCardTransaction-amout" />
            </div>
            <div class="field col-12 md:col-12">
                <label for="creditCardTransaction-installments">{{ $t("installments") }}</label>
                <InputNumber v-model.lazy="creditCardTransaction.installments" inputId="minmax-buttons" mode="decimal"
                    showButtons :min="0" :max="100" id="creditCardTransaction-installments" />

            </div>
            <div class="field col-12 md:col-12">
                <label for="creditCardTransaction-installmentsDateTransaction">Data da transação</label>
                <Calendar v-model.lazy="creditCardTransaction.dateTransaction" showIcon iconDisplay="input"
                    dateFormat="dd/mm/yy" />
            </div>
        </div>
        <template #footer>
            <Button :label="$t('save')" text icon="pi pi-check" @click="save()" />
            <Button :label="$t('cancel')" icon="pi pi-times" text @click="dialogVisible = false" />
        </template>
    </Dialog>
</template>