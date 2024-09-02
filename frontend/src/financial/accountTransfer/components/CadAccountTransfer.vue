<script setup>
import { onBeforeMount, onMounted, ref, onUnmounted } from "vue";
import { eventBus } from "@/config/eventBus";
import { useI18n } from "vue-i18n";
import { useHandlerMessage, useLoader } from "../../../composables/commons";
import { useStore } from "vuex";
import BankAccountFilter from "../../../administrator/bankAccount/model/BankAccountFilter";
import AccountTransfer from '../../accountTransfer/model/accountTransfer';

const dialogVisible = ref(false);
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();

const listPageBankAccount = ref([]);
const bankAccountSelectTo = ref({});
const bankAccountSelectFrom = ref({})
const bankAccountFilter = ref(new BankAccountFilter());
const accountTransfer = ref(new AccountTransfer())


onMounted(() => {
    eventBus.on("open-dialog-transfer", openDialogTransfer);
});

onUnmounted(() => {
    eventBus.off("open-dialog-transfer", openDialogTransfer);
});

const openDialogTransfer = async (payment) => {
    try {
        showLoading();
        await clear();

        await findBankAccount();
        dialogVisible.value = true;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const clear = async () => {
    bankAccountSelectTo.value = {};
    bankAccountSelectFrom.value = {};
}

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
        accountTransfer.value.bankAccountToId = bankAccountSelectTo.value.id;
        accountTransfer.value.bankAccountFromId = bankAccountSelectFrom.value.id;
        await store.dispatch('accountTransferStore/save', accountTransfer.value);
        dialogVisible.value = false;
        await eventBus.emit("refresh-find-transfer")
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
}
</script>

<template>
    <Dialog v-model:visible="dialogVisible" :style="{ width: '40%' }" :header="$t('accountTransfer')" :modal="true">
        <div class="p-fluid formgrid grid">
            <div class="field col-12 md:col-12">
                <label>{{ $t("description") }}</label>
                <InputText v-model.lazy="accountTransfer.description" id="accountTransfer-description" />
            </div>
            <div class="field col-12 md:col-12">
                <label>Conta de</label>
                <Dropdown v-model="bankAccountSelectFrom" :options="listPageBankAccount.content"
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
                <label>Conta para</label>
                <Dropdown v-model="bankAccountSelectTo" :options="listPageBankAccount.content" optionLabel="description"
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
                <label for="accountPayment-amount">{{ $t("amount") }}</label>
                <InputNumber v-model.lazy="accountTransfer.amount" :minFractionDigits="2" :maxFractionDigits="5" />
            </div>
        </div>
        <template #footer>
            <Button :label="$t('save')" text icon="pi pi-check" @click="save()" />
            <Button :label="$t('cancel')" icon="pi pi-times" text @click="dialogVisible = false" />
        </template>
    </Dialog>
</template>