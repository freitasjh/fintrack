<script setup>
import { useI18n } from "vue-i18n";
import { onMounted, onBeforeMount, ref } from "vue";
import { useStore } from "vuex";
import Pageable from "../../../commons/model/Pageable";
import AccountPayment from "../model/accountPayment";
import AccountPaymentFilter from "../model/accountPaymentFilter";
import { useHandlerMessage, useLoader } from "../../../composables/commons";
import { eventBus } from "@/config/eventBus";
import DialogPayment from './DialogPaymentCad.vue';

const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();

const breadCrumbItem = ref([{ label: t("financial") }, { label: t("accountPayment") }]);
const home = ref({
    icon: "pi pi-home",
});

const listPageAccountPayment = ref(new Pageable());
const accountPayment = ref(new AccountPayment());
const accountPaymentSelected = ref({});
const accountPaymentFilter = ref(new AccountPaymentFilter());

onMounted(() => {
   eventBus.on('refresh-payment', async () => {
        await findAccountPayment();
   }) 
});

onBeforeMount(async () => {
    await findAccountPayment();
});

const findAccountPayment = async () => {
    try {
        showLoading();
        await store.dispatch(
            "accountPaymentStore/findByFilter",
            accountPaymentFilter.value
        );
        listPageAccountPayment.value =
            store.state.accountPaymentStore.listPageAccountPayment;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const formatCurrency = (value) => {
    return value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
};

const formatDate = (value) => {
    const formatter = new Intl.DateTimeFormat("pt-BR", { dateStyle: "short" });
    let date = new Date(value);
    return formatter.format(date);
};

const openDialogPayment = () => {
    eventBus.emit("open-dialog", null);
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
                            <Button
                                :label="$t('new')"
                                icon="pi pi-plus"
                                class="mr-2"
                                severity="success"
                                @click="openDialogPayment()"
                            />
                        </div>
                    </template>
                </Toolbar>
                <DataTable
                    ref="ref"
                    :value="listPageAccountPayment.content"
                    dataKey="id"
                    stripedRows
                    showGridlines
                    :paginator="true"
                    :rows="30"
                >
                    <Column :header="$t('code')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.id }}
                        </template>
                    </Column>
                    <Column :header="$t('bankAccount')" headerStyle="width:25%">
                        <template #body="slotProps">
                            {{ slotProps.data.bankAccount }}
                        </template>
                    </Column>
                    <Column :header="$t('dateRegister')" headerStyle="width:25%">
                        <template #body="slotProps">
                            {{ formatDate(slotProps.data.dateRegister) }}
                        </template>
                    </Column>
                    <Column :header="$t('dateReceiver')" headerStyle="width:25%">
                        <template #body="slotProps">
                            {{ formatDate(slotProps.data.dateProcessed) }}
                        </template>
                    </Column>
                    <Column :header="$t('amount')" headerStyle="width:15%">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.amount) }}
                        </template>
                    </Column>
                </DataTable>
                <DialogPayment />
            </div>
        </div>
    </div>
</template>
