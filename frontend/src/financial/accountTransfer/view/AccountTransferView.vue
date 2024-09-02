<script setup>
import { useI18n } from "vue-i18n";
import { onMounted, onBeforeMount, ref } from "vue";
import { useStore } from "vuex";
import Pageable from "../../../commons/model/Pageable";
import { useCurrency, useHandlerMessage, useLoader } from "../../../composables/commons";
import { eventBus } from "@/config/eventBus";
import CadTransfer from '../components/CadAccountTransfer.vue';
import AccountPaymentFilter from "../../accountPayment/model/accountPaymentFilter";

const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const { formatCurrency } = useCurrency();
const store = useStore();

const breadCrumbItem = ref([{ label: t("financial") }, { label: t("accountTransfer") }]);
const home = ref({
    icon: "pi pi-home",
});
const listPageAccountTransfer = ref(new Pageable());
const accountPaymentFilter = ref(new AccountPaymentFilter());

const openDialogPayment = async () => {
    await eventBus.emit("open-dialog-transfer");
};

onMounted(() => {
    eventBus.on("refresh-find-transfer", async () => {
        await findAccountTransfer();
    });
});

onBeforeMount(async () => {
    await findAccountTransfer();
});

const findAccountTransfer = async () => {
    try {
        showLoading();
        await store.dispatch('accountTransferStore/findByFilter', accountPaymentFilter.value);
        listPageAccountTransfer.value = store.state.accountTransferStore.listPageAccountTransfer;
    } catch (error) {
        handlerError(error);
    } finally {
        hideLoading();
    }
}
const formatDate = (value) => {
    const formatter = new Intl.DateTimeFormat("pt-BR", { dateStyle: "short" });
    let date = new Date(value);
    return formatter.format(date);
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
                <DataTable ref="ref" :value="listPageAccountTransfer.content" dataKey="id" stripedRows showGridlines
                    :paginator="true" :rows="30">
                    <!-- <template #header>
                        <div class="flex justify-content-between flex-column sm:flex-row">
                            <Dropdown v-model="filterPaymentOpenSelected" :options="filterPaymentOpen"
                                optionLabel="label" @change="findAccountPayment()">
                            </Dropdown>
                            <IconField iconPosition="left">
                                <InputIcon class="pi pi-search" />
                                <InputText placeholder="Keyword Search" style="width: 100%" />
                            </IconField>
                        </div>
                    </template> -->
                    <Column :header="$t('code')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.id }}
                        </template>
                    </Column>
                    <Column :header="$t('description')" headerStyle="width:25%">
                        <template #body="slotProps">
                            {{ slotProps.data.description }}
                        </template>
                    </Column>
                    <Column :header="$t('bankAccount')" headerStyle="width:25%">
                        <template #body="slotProps">
                            {{ slotProps.data.bankToDescription }}
                        </template>
                    </Column>
                    <Column :header="$t('bankAccount')" headerStyle="width:25%">
                        <template #body="slotProps">
                            {{ slotProps.data.bankFromDescription }}
                        </template>
                    </Column>
                    <Column header="Data transferido" style="width: 35%">
                        <template #body="slotProps">
                            {{ formatDate(slotProps.data.transferDate) }}
                        </template>
                    </Column>

                    <Column :header="$t('amount')" headerStyle="width:15%">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.amount) }}
                        </template>
                    </Column>
                </DataTable>
                <CadTransfer />
            </div>
        </div>
    </div>
</template>
