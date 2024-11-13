<script setup>
import { useI18n } from "vue-i18n";
import { onMounted, onBeforeMount, ref, handleError } from "vue";
import { useStore } from "vuex";
import Pageable from "../../../../commons/model/Pageable";
import { useHandlerMessage, useLoader } from "../../../../composables/commons";
import DialogCreditCardTransaction from "../components/DialogCreditCardTransaction.vue";
import CreditCardTransactionFilter from "../model/creditCardTransactionFilter";

import { eventBus } from "@/config/eventBus";
import { computed } from "vue";

const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();

const listOfPageTransaction = computed(() => store.state.creditCardTransactionStore.listOfPageTransaction);
const creditCardTransactionFilter = ref(new CreditCardTransactionFilter());

const breadCrumbItem = ref([
    { label: t("financial") },
    { label: t("creditCard") },
    { label: t("transaction") },
]);

const home = ref({
    icon: "pi pi-home",
});

onMounted(() => {
    eventBus.on("refresh-transaction-credit-card", async () => {
        await findByFilter();
    });
});

onBeforeMount(async () => {
    await findByFilter();
});

const openDialogPayment = async () => {
    await eventBus.emit("open-dialog-credit-card-transaction");
};

const findByFilter = async () => {
    try {
        showLoading();
        await store.dispatch('creditCardTransactionStore/findByFilter', creditCardTransactionFilter.value);
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const formatCurrency = (value) => {
    return value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
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
                                @click="openDialogPayment()" />
                        </div>
                    </template>
                </Toolbar>
                <DataTable ref="ref" :value="listOfPageTransaction.content" dataKey="id" stripedRows showGridlines
                    :paginator="true" :rows="30">
                    <Column :header="$t('code')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.id }}
                        </template>
                    </Column>
                    <Column :header="$t('description')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.description }}
                        </template>
                    </Column>
                    <Column :header="$t('amount')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.amount) }}
                        </template>
                    </Column>
                    <Column :header="$t('date')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.dateCreate }}
                        </template>
                    </Column>
                    <Column :header="$t('installments')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.installments }}
                        </template>
                    </Column>
                </DataTable>
            </div>
        </div>
        <DialogCreditCardTransaction />
    </div>
</template>
