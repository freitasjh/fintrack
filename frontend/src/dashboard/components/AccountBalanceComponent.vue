<script setup>
import { onBeforeMount, onMounted, ref } from "vue";
import DashboardService from '../service/dashboardService'

const accountBalance = ref(0);
onMounted(() => {
    
});

onBeforeMount(async () => {
    await findAccountBalance();
});

const findAccountBalance = async () => {
    try {
        const service = new DashboardService();

        const response = await service.findBalanceCurrentAccount();
        accountBalance.value = response.data;
    } catch (error) {
        console.log(error);
    }
};
const formatCurrency = (value) => {
    return value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
};

</script>

<template>
    <div class="card mb-0">
        <div class="flex justify-content-between mb-3">
            <div>
                <span class="block text-500 font-medium mb-3">Saldo</span>
                <div class="text-900 font-medium text-xl">{{formatCurrency(accountBalance)}}</div>
            </div>
            <div class="flex align-items-center justify-content-center bg-blue-100 border-round" style="width: 2.5rem; height: 2.5rem">
                <i class="pi pi-dollar text-blue-500 text-xl"></i>
            </div>
        </div>
    </div>
</template>
