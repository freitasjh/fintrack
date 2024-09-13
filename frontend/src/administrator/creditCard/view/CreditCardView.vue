<script setup>
import { useI18n } from "vue-i18n";
import { onMounted, onBeforeMount, ref } from "vue";
import { useStore } from "vuex";
import Pageable from "../../../commons/model/Pageable";
import { useHandlerMessage, useLoader } from "../../../composables/commons";
import DialogCreditCard from "../component/DialogCreditCard.vue";
import { eventBus } from "@/config/eventBus";
import CreditCardFilter from "../model/creditCardFilter";

const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();
const creditCardFilter = ref(new CreditCardFilter())

const listPageCreditCard = ref(new Pageable());
const breadCrumbItem = ref([{ label: t("cadastre") }, { label: t("creditCard") }]);

const home = ref({
    icon: "pi pi-home",
});

const openDialogCad = async () => {
    await eventBus.emit("open-dialog-credit-card");
};
onBeforeMount(async () => {
    await findCreditCard();
})

const findCreditCard = async () => {
    try {
        await store.dispatch("creditCardStore/findByFilter", creditCardFilter.value);
        listPageCreditCard.value = store.state.creditCardStore.listPageCreditCard;
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
                                @click="openDialogCad()" />
                        </div>
                    </template>
                </Toolbar>
                <DataTable ref="ref" :value="listPageCreditCard.content" dataKey="id" stripedRows showGridlines
                    :paginator="true" :rows="30">
                    <Column :header="$t('code')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.id }}
                        </template>
                    </Column>
                    <Column :header="$t('name')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.name }}
                        </template>
                    </Column>
                    <Column :header="$t('totalLimit')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.totalLimit) }}
                        </template>
                    </Column>
                    <Column :header="$t('availableLimit')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.availableLimit) }}
                        </template>
                    </Column>
                </DataTable>
            </div>
        </div>

        <DialogCreditCard />
    </div>
</template>