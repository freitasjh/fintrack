<!-- src/components/CreditCardForm.vue -->
<template>
    <div class="credit-card-container">
        <div class="credit-card">
            <div class="credit-card-header">
                <img v-if="cardBrand" :src="cardBrand" class="card-brand" />
            </div>
            <div class="credit-card-body">
                <div class="card-number">{{ formattedCardNumber }}</div>
                <div class="card-details">
                    <div class="card-holder">
                        <label>Nome no Cartão</label>
                        <div>{{ cardholderName || 'NOME COMPLETO' }}</div>
                    </div>
                    <div class="card-expiry">
                        <label>Validade</label>
                        <div>{{ expiryDate || 'MM/AA' }}</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-form">
            <div class="p-fluid">
                <div class="p-field">
                    <label for="cardholderName">Nome no Cartão</label>
                    <InputText id="cardholderName" v-model="cardholderName" />
                </div>
                <div class="p-field">
                    <label for="cardNumber">Número do Cartão</label>
                    <InputText id="cardNumber" v-model="cardNumber" maxlength="16" @input="detectCardBrand" />
                </div>
                <div class="p-field p-grid">
                    <div class="p-col-6">
                        <label for="expiryDate">Validade</label>
                        <InputText id="expiryDate" v-model="expiryDate" placeholder="MM/AA" />
                    </div>
                    <div class="p-col-6">
                        <label for="cvv">CVV</label>
                        <InputText id="cvv" v-model="cvv" maxlength="3" />
                    </div>
                </div>
                <div class="p-field">
                    <Button label="Enviar" icon="pi pi-check" @click="submitForm" />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, computed } from 'vue';
import InputText from 'primevue/inputtext';
import Button from 'primevue/button';

export default {
    name: 'CreditCardForm',
    components: {
        InputText,
        Button,
    },
    setup() {
        const cardholderName = ref('');
        const cardNumber = ref('');
        const expiryDate = ref('');
        const cvv = ref('');
        const cardBrand = ref('');

        const cardBrandImages = {
            visa: 'https://upload.wikimedia.org/wikipedia/commons/4/41/Visa_Logo.png',
            mastercard: 'https://upload.wikimedia.org/wikipedia/commons/a/a4/Mastercard_2019_logo.svg',
            amex: 'https://upload.wikimedia.org/wikipedia/commons/3/30/American_Express_logo_%282018%29.svg',
        };

        const formattedCardNumber = computed(() => {
            return cardNumber.value.replace(/(.{4})/g, '$1 ').trim();
        });

        const detectCardBrand = () => {
            const number = cardNumber.value;
            if (/^4[0-9]{12}(?:[0-9]{3})?$/.test(number)) {
                cardBrand.value = cardBrandImages.visa;
            } else if (/^5[1-5][0-9]{14}$/.test(number)) {
                cardBrand.value = cardBrandImages.mastercard;
            } else if (/^3[47][0-9]{13}$/.test(number)) {
                cardBrand.value = cardBrandImages.amex;
            } else {
                cardBrand.value = '';
            }
        };

        const submitForm = () => {
            if (!cardholderName.value || !cardNumber.value || !expiryDate.value || !cvv.value) {
                alert('Por favor, preencha todos os campos.');
                return;
            }
            alert('Formulário enviado com sucesso!');
        };

        return {
            cardholderName,
            cardNumber,
            expiryDate,
            cvv,
            cardBrand,
            formattedCardNumber,
            detectCardBrand,
            submitForm,
        };
    },
};
</script>

<style scoped>
.credit-card-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2rem;
}

.credit-card {
    width: 320px;
    height: 200px;
    background: linear-gradient(135deg, #667eea, #764ba2);
    border-radius: 15px;
    color: white;
    padding: 1.5rem;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.credit-card-header {
    display: flex;
    justify-content: flex-end;
}

.card-brand {
    height: 30px;
}

.credit-card-body {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100%;
}

.card-number {
    font-size: 1.2rem;
    letter-spacing: 2px;
}

.card-details {
    display: flex;
    justify-content: space-between;
}

.card-holder,
.card-expiry {
    display: flex;
    flex-direction: column;
}

.card-holder label,
.card-expiry label {
    font-size: 0.75rem;
    color: #dcdcdc;
}

.card-form {
    width: 100%;
    max-width: 400px;
}
</style>