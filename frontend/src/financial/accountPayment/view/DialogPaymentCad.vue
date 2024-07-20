<script setup>
import { onBeforeMount, onMounted, ref } from "vue";
import { eventBus } from "@/config/eventBus";
import AccountPayment from "../model/accountPayment";
import { useI18n } from "vue-i18n";
import { useHandlerMessage, useLoader } from "../../../composables/commons";
import { useStore } from "vuex";
import BankAccountFilter from "../../../administrator/bankAccount/model/BankAccountFilter";

const dialogVisible = ref(false);
const accountPayment = ref(new AccountPayment());
const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();
const bankAccountFilter = ref(new BankAccountFilter());
const listPageBankAccount = ref([]);
const bankAccountSelected = ref({});

onMounted(() => {
    eventBus.on("open-dialog", async (payment) => {
        dialogVisible.value = true;
        await findBankAccount();
    });
});

const findAccountPayment = async () => {};

const findBankAccount = async () => {
    try {
        showLoading();
        await store.dispatch(
            "bankAccountStore/findBankAccountByFilter",
            bankAccountFilter.value
        );

        listPageBankAccount.value = store.state.bankAccountStore.pageOfBankAccount;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const savePayment = async () => {
    try {
        showLoading();
        accountPayment.value.bankAccountId = bankAccountSelected.value.id;
        await store.dispatch("accountPaymentStore/save", accountPayment.value);
        dialogVisible.value = false;
        eventBus.emit("refresh-payment");
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};
</script>

<template>
    <Dialog
        v-model:visible="dialogVisible"
        :style="{ width: '40%' }"
        :header="$t('accountPayment')"
        :modal="true"
    >
        <div class="p-fluid formgrid grid">
            <div class="field col-12 md:col-12">
                <label for="accountPayment-description">{{ $t("description") }}</label>
                <InputText v-model.lazy="accountPayment.description" />
            </div>
            <div class="field col-12 md:col-12">
                <label for="accountPayment-bakAccount">{{ $t("bankAccount") }}</label>
                <Dropdown
                    v-model="bankAccountSelected"
                    :options="listPageBankAccount.content"
                    optionLabel="description"
                    :placeholder="$t('selectBankAccount')"
                    class="w-full"
                >
                    <template #value="slotProps">
                        <div
                            v-if="slotProps.value.description !== undefined"
                            class="flex align-items-center"
                        >
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
                <InputNumber
                    v-model.lazy="accountPayment.amount"
                    :minFractionDigits="2"
                    :maxFractionDigits="5"
                />
            </div>
            <div class="field col-12 md:col-12">
                <label for="accountPayment-dateReceiver">
                    {{ $t("dateReceiver") }}
                </label>
                <Calendar
                    v-model.lazy="accountPayment.dateProcessed"
                    showIcon
                    iconDisplay="input"
                    dateFormat="dd/mm/yy"
                />
            </div>
        </div>

        <template #footer>
            <Button :label="$t('save')" text icon="pi pi-check" @click="savePayment()" />
            <Button
                :label="$t('cancel')"
                icon="pi pi-times"
                text
                @click="dialogVisible = false"
            />
        </template>
    </Dialog>
</template>
