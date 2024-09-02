<script setup>
import { onBeforeMount, ref } from "vue";
import DashboardService from "../service/dashboardService";
import { useCurrency, useHandlerMessage } from "../../composables/commons";

const listOfRecentPayment = ref([]);
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const { formatCurrency } = useCurrency();


onBeforeMount(async () => {
    await findPaymentOpen();
});


const findPaymentOpen = async () => {
    try {
        const service = new DashboardService();
        const response = await service.findPaymentOpen();
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
            <h5>Pagamentos Abertos</h5>
            <div>
                <!-- <Button icon="pi pi-ellipsis-v" class="p-button-text p-button-plain p-button-rounded"
                    @click="$refs.menu1.toggle($event)"></Button>
                <Menu ref="menu1" :popup="true" :model="listOfItemMenu"></Menu> -->
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
            <Column field="dateRegister" header="Data Vencimento" style="width: 35%">
                <template #body="slotProps">
                    {{ formatDate(slotProps.data.paymentDueDate) }}
                </template>
            </Column>
            <!-- <Column style="width: 15%">
                <template #header> View </template>
                <template #body>
                    <Button icon="pi pi-search" type="button" class="p-button-text"></Button>
                </template>
            </Column> -->
        </DataTable>
    </div>
</template>