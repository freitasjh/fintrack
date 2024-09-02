<script setup>
import { onBeforeMount, ref } from "vue";
import { useCurrency } from "../../composables/commons";
import DashboardService from "../service/dashboardService";

const expensesMonthly = ref(0.0);
const { formatCurrency } = useCurrency();

onBeforeMount(async () => {
    await findMonthlyExpenses();
});


const findMonthlyExpenses = async () => {
    try {
        const service = new DashboardService();
        const response = await service.findMonthlyExpenses();

        expensesMonthly.value = response.data;
    } catch (error) {
        console.log(error);
    }
};
</script>
<template>
    <div class="card mb-0">
        <div class="flex justify-content-between mb-3">
            <div>
                <span class="block text-500 font-medium mb-3">Despesas</span>
                <div class="text-900 font-medium text-xl">
                    {{ formatCurrency(expensesMonthly) }}
                </div>
            </div>
            <div class="flex align-items-center justify-content-center bg-red-100 border-round"
                style="width: 2.5rem; height: 2.5rem">
                <i class="pi pi-dollar text-red-500 text-xl"></i>
            </div>
        </div>
    </div>
</template>
