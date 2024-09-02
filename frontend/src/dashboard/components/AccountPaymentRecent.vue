<script setup>
import { onBeforeMount, ref } from "vue";
import DashboardService from "../service/dashboardService";
import { useCurrency, useHandlerMessage } from "../../composables/commons";
import DialogPaymentCad from "../../financial/accountPayment/view/DialogPaymentCad.vue";
import { eventBus } from "@/config/eventBus";
import { onMounted } from "vue";

const listOfRecentPayment = ref([]);
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const { formatCurrency } = useCurrency();
onMounted(() => {
    eventBus.on('refresh-payment', async () => {
        window.location.reload(true);
    })
});

onBeforeMount(async () => {
    await findRecentPayment();
});

const listOfItemMenu = ([
    { label: 'Add New', icon: 'pi pi-fw pi-plus', command: async () => await openDialogPayment() },
])

const openDialogPayment = async () => {
    await eventBus.emit("open-dialog-payment");
};

const findRecentPayment = async () => {
    try {
        const service = new DashboardService();
        const response = await service.findRecentPayment();
        listOfRecentPayment.value = response.data;
    } catch (error) {
        console.log(error);
    }
};

const formatDate = (value) => {
    const formatter = new Intl.DateTimeFormat("pt-BR", { dateStyle: "short" });
    let date = new Date(value);
    return formatter.format(date);
};
</script>
<template>
    <div class="card">
        <div class="flex align-items-center justify-content-between mb-4">
            <h5>Pagamentos recentes</h5>
            <div>
                <Button icon="pi pi-ellipsis-v" class="p-button-text p-button-plain p-button-rounded"
                    @click="$refs.menu1.toggle($event)"></Button>
                <Menu ref="menu1" :popup="true" :model="listOfItemMenu"></Menu>
            </div>
        </div>

        <DataTable :value="listOfRecentPayment" :rows="5" :paginator="true" responsiveLayout="scroll">
            <Column field="description" header="Descrição" style="width: 35%"></Column>
            <Column field="category" header="Categoria" style="width: 35%"></Column>
            <Column field="amount" header="Valor" style="width: 35%">
                <template #body="slotProps">
                    {{ formatCurrency(slotProps.data.amount) }}
                </template>
            </Column>
            <Column field="dateRegister" header="Data pago" style="width: 35%">
                <template #body="slotProps">
                    {{ formatDate(slotProps.data.dateProcessed) }}
                </template>
            </Column>
            <!-- <Column style="width: 15%">
                <template #header> View </template>
                <template #body>
                    <Button icon="pi pi-search" type="button" class="p-button-text"></Button>
                </template>
            </Column> -->
        </DataTable>
        <DialogPaymentCad />
    </div>
</template>