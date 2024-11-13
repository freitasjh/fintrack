<script setup>
import { onBeforeMount, ref } from 'vue';
import BankAccountService from '../../administrator/bankAccount/service/BankAccountService';
import FilterCategory from '../../administrator/category/model/FilterCategory';
import { useCurrency } from '../../composables/commons';
import Card from 'primevue/card';

const listBankAccount = ref([]);
const { formatCurrency } = useCurrency();

const responsiveOptions = ref([
    {
        breakpoint: '1400px',
        numVisible: 2,
        numScroll: 1
    },
    {
        breakpoint: '1199px',
        numVisible: 3,
        numScroll: 1
    },
    {
        breakpoint: '767px',
        numVisible: 2,
        numScroll: 1
    },
    {
        breakpoint: '575px',
        numVisible: 1,
        numScroll: 1
    }
]);
onBeforeMount(async () => {
    await findBankAccount();
});

const findBankAccount = async () => {
    try {
        const bankAccountService = new BankAccountService();
        const response = await bankAccountService.findByFilter(new FilterCategory());

        listBankAccount.value = response.data.content;
    } catch (error) {
        console.log(error);
    }
};
</script>
<template>
    <Card style="height: 40rem; overflow: hidden">
        <template #content>
            <div class="flex align-items-center justify-content-between mb-0">
                <h5>Contas</h5>
                <Button class="p-button-plain p-button-rounded">Adicionar</Button>
            </div>
            <Carousel :value="listBankAccount" :numVisible="1" :numScroll="1" :responsiveOptions="responsiveOptions">
                <template #item="slotProps">
                    <div class="border-2 surface-border border-round m-3 p-4 shadow-4 hover:shadow-6">
                        <div class="flex align-items-center justify-content-between mb-4">
                            <div class="mb-0 font-bold">{{ slotProps.data.description }}</div>
                            <div>
                                <Button icon="pi pi-ellipsis-v" class="p-button-text p-button-plain p-button-rounded"
                                    @click="$refs.menu1.toggle($event)"></Button>

                            </div>
                        </div>
                        <div class="mb-4">
                            <img src="https://img.freepik.com/vetores-premium/construcao-de-banco-e-financiamento-de-banco-de-dinheiro-servicos-financeiros-de-cambio-de-dinheiro_625536-425.jpg"
                                alt="Image" class="centered-image" />
                        </div>
                        <div class="flex justify-content-between">
                            <div class="mt-0 font-semibold">
                                <span>Saldo</span>
                                <div class="text-4xl">{{
                                    formatCurrency(slotProps.data.balance) }}</div>
                            </div>
                        </div>
                    </div>
                </template>
            </Carousel>
        </template>
    </Card>
</template>
<style scoped>
.centered-image {
    display: block;
    margin: 0 auto;
    /* Centraliza horizontalmente */
    width: 200px;
    /* Largura fixa da imagem */
    height: auto;
    /* Mantém a proporção da imagem */
}
</style>