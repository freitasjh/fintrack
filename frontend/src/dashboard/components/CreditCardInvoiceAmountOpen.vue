<script setup>
import { onBeforeMount, ref } from 'vue';
import DashboardService from '../service/dashboardService';
import { useCurrency } from '../../composables/commons';

const amount = ref(0);
const { formatCurrency } = useCurrency();

onBeforeMount(async () => {
    await find();
});

const find = async () => {
    try {
        const service = new DashboardService();

        const response = await service.findCreditCardInvoiceAmountOpen();
        amount.value = response.data.amount;
    } catch (error) {
        console.log(error);
    }
}
</script>

<template>
    <div class="card mb-0">
        <div class="flex justify-content-between mb-3">
            <div>
                <span class="block text-500 font-medium mb-3">Cartão de Credito Fatura</span>
                <div class="text-900 font-medium text-xl">
                    {{ formatCurrency(amount) }}
                </div>
            </div>
            <div class="flex align-items-center justify-content-center bg-red-100 border-round"
                style="width: 2.5rem; height: 2.5rem">
                <i class="pi pi-dollar text-red-500 text-xl"></i>
            </div>
        </div>
    </div>
</template>