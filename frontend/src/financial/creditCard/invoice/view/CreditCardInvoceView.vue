<script setup>
import { useI18n } from "vue-i18n";
import { onMounted, onBeforeMount, ref } from "vue";
import { useStore } from "vuex";
import Pageable from "../../../../commons/model/Pageable";
import { useHandlerMessage, useLoader } from "../../../../composables/commons";
import CreditCardInvoiceService from "../service/creditCardInvoiceService";

const { t } = useI18n();
const { showLoading, hideLoading } = useLoader();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const store = useStore();

const listPageCreditCardInvoice = ref([]);
const breadCrumbItem = ref([{ label: t("creditCard") }, { label: t("invoice") }]);
const service = new CreditCardInvoiceService();

const home = ref({
    icon: "pi pi-home",
});


onBeforeMount(async () => {
    await findByFilter();
});


const findByFilter = async () => {
    try {
        const response = await service.findByFilter(null);

        listPageCreditCardInvoice.value = response.data;
        console.log(listPageCreditCardInvoice)
    } catch (error) {
        console.log(error);
    }
}

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
                            <Button :label="$t('new')" icon="pi pi-plus" class="mr-2" severity="success" />
                        </div>
                    </template>
                </Toolbar>
                <DataTable ref="ref" :value="listPageCreditCardInvoice" dataKey="id" stripedRows showGridlines
                    :paginator="true" :rows="30">
                    <Column :header="$t('code')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.id }}
                        </template>
                    </Column>
                    <Column :header="$t('dueDate')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.dueDate }}
                        </template>
                    </Column>
                    <Column :header="$t('description')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ slotProps.data.creditCardDescription }}
                        </template>
                    </Column>
                    <Column :header="$t('amount')" headerStyle="5%">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.amount) }}
                        </template>
                    </Column>
                </DataTable>
            </div>
        </div>
    </div>
</template>