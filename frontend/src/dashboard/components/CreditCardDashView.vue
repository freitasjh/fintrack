<script setup>
import { onBeforeMount, ref } from 'vue';
import CreditCardService from '../../administrator/creditCard/service/creditCardService';
import CreditCardFilter from '../../administrator/creditCard/model/creditCardFilter';
import { useCurrency } from '../../composables/commons';

const listCreditCard = ref([]);
const creditCardFilter = ref(new CreditCardFilter())
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
    await findCreditCard();
});

const findCreditCard = async () => {
    try {
        const creditCardService = new CreditCardService();
        const response = await creditCardService.findByFilter(creditCardFilter.value);

        listCreditCard.value = response.data.content;

    } catch (error) {
        console.log(error);
    }
}

const calculatePercentUsed = (item) => {
    let totalUsed = item.totalLimit - item.availableLimit;

    return (totalUsed / item.totalLimit) * 100;
}

</script>
<template>
    <Card style="height: 40rem; overflow: hidden">
        <template #content>
            <div class="flex align-items-center justify-content-between mb-0">
                <h5>Cartão de Credito</h5>
                <Button class="p-button-plain p-button-rounded">Adicionar</Button>
            </div>
            <Carousel :value="listCreditCard" :numVisible="1" :numScroll="1" :responsiveOptions="responsiveOptions">
                <template #item="slotProps">
                    <div class="border-2 surface-border border-round m-3 p-4 shadow-4 hover:shadow-6">
                        <div class="flex align-items-center justify-content-between mb-4">
                            <div class="mb-0 font-bold">{{ slotProps.data.name }}</div>
                            <div>
                                <Button icon="pi pi-ellipsis-v" class="p-button-text p-button-plain p-button-rounded"
                                    @click="$refs.menu1.toggle($event)"></Button>

                            </div>
                        </div>
                        <div class="mb-0">
                            <img src="https://img.freepik.com/vetores-premium/icone-de-cartao-de-credito-vetor-sinal-de-pagamento-icone-em-moderno-estilo-plano-isolado-no-fundo-branco_554867-22.jpg"
                                alt="Image" class="centered-image" />
                        </div>
                        <div class="mb-3">
                            <MeterGroup :value="[{ label: 'Utilizado', value: calculatePercentUsed(slotProps.data) }]"
                                labelPosition="start">
                            </MeterGroup>
                        </div>
                        <div class="flex justify-content-between">
                            <div class="mt-0 font-semibold">
                                <span>Limite Total</span>
                                <div>{{
                                    formatCurrency(slotProps.data.totalLimit) }}</div>
                            </div>
                            <div class="font-semibold">
                                <span>Disponivel</span>
                                <div>{{
                                    formatCurrency(slotProps.data.availableLimit) }}</div>
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
