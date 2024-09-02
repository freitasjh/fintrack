<script setup>
import { onBeforeMount, ref } from "vue";
import DashboardService from "../service/dashboardService";
import { onMounted } from "vue";
import { useCurrency } from "../../composables/commons";

let documentStyle = getComputedStyle(document.documentElement);
let textColor = documentStyle.getPropertyValue('--text-color');
let textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
let surfaceBorder = documentStyle.getPropertyValue('--surface-border');
const pieData = ref([]);
const pieLabel = ref([]);
const pieChart = ref();
const pieOptions = ref();
const isDatable = ref(false);
const isError = ref(false);
const { formatCurrency } = useCurrency();
const listOfItemMenu = ([
    { label: `Alterar para Tabela`, command: () => alternateDataTableOrChart() },
]);

const data = ref([

]);

onBeforeMount(() => {
    findCategoryExpense();
});


const alternateDataTableOrChart = () => {
    isDatable.value = !isDatable.value;
    listOfItemMenu[0].label = (isDatable.value ? "Alterar para Chart Pie" : "Alterar para Tabela");
}

const findCategoryExpense = async () => {
    try {
        const service = new DashboardService();
        const response = await service.findExpenseByCategory();
        data.value = response.data;
        data.value.forEach((item) => {
            pieLabel.value.push(item.description);
            pieData.value.push(item.totalExpense);
        });
        mountedChartPie();
    } catch (error) {
        console.log(error);
    }
};

const mountedChartPie = () => {
    pieChart.value = {
        labels: pieLabel,
        datasets: [
            {
                data: pieData,
                backgroundColor: [
                    documentStyle.getPropertyValue("--cyan-500"),
                    documentStyle.getPropertyValue("--orange-500"),
                    documentStyle.getPropertyValue("--gray-500"),
                    documentStyle.getPropertyValue("--red-500"),
                ],
                hoverBackgroundColor: [
                    documentStyle.getPropertyValue("--cyan-400"),
                    documentStyle.getPropertyValue("--orange-400"),
                    documentStyle.getPropertyValue("--gray-400"),
                    documentStyle.getPropertyValue("--red-400"),
                ],
            },
        ],
    };

    pieOptions.value = {
        plugins: {
            legend: {
                labels: {
                    usePointStyle: true,
                    color: textColor,
                },
            },
        },
    };
};
</script>
<template>
    <div class="card ">
        <div class="flex align-items-center justify-content-between mb-4">
            <h5>Gastos por Categoria</h5>
            <div>
                <Button icon="pi pi-ellipsis-v" class="p-button-text p-button-plain p-button-rounded"
                    @click="$refs.menu1.toggle($event)"></Button>
                <Menu ref="menu1" :popup="true" :model="listOfItemMenu"></Menu>
            </div>
        </div>

        <div v-if="isError">

        </div>
        <div v-else-if="!isDatable">
            <div class="flex flex-column align-items-center">
                <Chart type="pie" :data="pieChart" :options="pieOptions" />
            </div>
        </div>
        <div v-else-if="isDatable">
            <div class="flex flex-column">
                <DataTable :value="data">
                    <Column field="description" header="Descrição" style="width: 35%"></Column>
                    <Column field="totalExpense" header="Total gasto" style="width: 35%">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.totalExpense) }}
                        </template>
                    </Column>
                </DataTable>
            </div>
        </div>
    </div>
</template>
