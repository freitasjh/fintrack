<script setup>
import { onBeforeMount, onMounted, ref, onUnmounted, handleError } from "vue";
import { eventBus } from "@/config/eventBus";
import { useStore } from "vuex";
import { useI18n } from "vue-i18n";
import { useHandlerMessage, useLoader } from "../../../composables/commons";
import CreditCard from "../model/creditCard";
import BankAccountFilter from "../../bankAccount/model/BankAccountFilter";


const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();

const store = useStore();
const dialogVisible = ref(false);
const listPageBankAccount = ref([])
const creditCard = ref(new CreditCard());
const bankAccountSelected = ref({});
const bankAccountFilter = ref(new BankAccountFilter());

onMounted(() => {
    eventBus.on("open-dialog-credit-card", openDialogHandler);
});

onUnmounted(() => {
    eventBus.off("open-dialog-credit-card", openDialogHandler);
});

const openDialogHandler = async () => {
    try {
        showLoading();
        await findBankAccount();
        dialogVisible.value = true;
    } catch (error) {
        handleError(error);
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
};

const save = async () => {
    try {
        showLoading();
        creditCard.value.bankAccountId = bankAccountSelected.value.id;
        await store.dispatch("creditCardStore/save", creditCard.value);
        handlerToastSuccess("Salvo com sucesso")
        dialogVisible.value = false
    } catch (error) {
        handlerError(error)
    } finally {
        hideLoading();
    }
};

</script>
<template>
    <Dialog v-model:visible="dialogVisible" :style="{ width: '40%' }" :header="$t('creditCard')" :modal="true">

        <div class="p-fluid formgrid grid">
            <div class="field col-12 md:col-12">
                <label for="credit-card-name">{{ $t("name") }}</label>
                <InputText v-model.lazy="creditCard.name" id="credit-card-name" />
            </div>
            <div class="field col-12 md:col-12">
                <label for="credit-card-number">Numero Cartão</label>
                <InputMask v-model.lazy="creditCard.number" id="credit-card-number" mask="9999 9999 9999 9999"
                    placeholder="9999 9999 9999 9999" />
            </div>
            <div class="field col-12 md:col-12">
                <label for="credit-card-nameCreditCard">Nome no Cartão</label>
                <InputText v-model.lazy="creditCard.nameCreditCard" id="credit-card-nameCreditCard" />
            </div>
            <div class="field col-12 md:col-12">
                <label>Limit total</label>
                <InputNumber v-model.lazy="creditCard.totalLimit" />
            </div>
            <div class="field col-12 md:col-12">
                <label>Dia Fechamento</label>
                <InputMask v-model.lazy="creditCard.closingDate" mask="99" />
            </div>
            <div class="field col-12 md:col-12">
                <label>Dia Vencimento</label>
                <InputMask v-model.lazy="creditCard.dueDay" mask="99" />
            </div>
            <div class="field col-12 md:col-12">
                <label>Conta bancaria</label>
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
        </div>
        <template #footer>
            <Button :label="$t('save')" text icon="pi pi-check" @click="save()" />
            <Button :label="$t('cancel')" icon="pi pi-times" text @click="dialogVisible = false" />
        </template>
    </Dialog>
</template>