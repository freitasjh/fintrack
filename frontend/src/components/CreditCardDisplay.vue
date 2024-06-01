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
    </div>
</template>

<script>
import { computed } from 'vue';

export default {
    name: 'CreditCardDisplay',
    props: {
        cardholderName: {
            type: String,
            required: true,
        },
        cardNumber: {
            type: String,
            required: true,
        },
        expiryDate: {
            type: String,
            required: true,
        },
        cvv: {
            type: String,
            required: true,
        },
    },
    setup(props) {
        const cardBrandImages = {
            visa: 'https://upload.wikimedia.org/wikipedia/commons/d/d6/Visa_2021.svg',
            mastercard: "https://upload.wikimedia.org/wikipedia/commons/a/a4/Mastercard_2019_logo.svg",
            amex: 'https://upload.wikimedia.org/wikipedia/commons/3/30/American_Express_logo_%282018%29.svg',
        };

        const cardBrand = computed(() => {
            const number = props.cardNumber;
            if (/^4[0-9]{12}(?:[0-9]{3})?$/.test(number)) {
                return cardBrandImages.visa;
            } else if (/^5[1-5][0-9]{14}$/.test(number)) {
                return cardBrandImages.mastercard;
            } else if (/^3[47][0-9]{13}$/.test(number)) {
                return cardBrandImages.amex;
            } else {
                return '';
            }
        });

        const formattedCardNumber = computed(() => {
            return props.cardNumber.replace(/(.{4})/g, '$1 ').trim();
        });

        return {
            cardBrand,
            formattedCardNumber,
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
    margin-bottom: 1rem;
    margin-top: 2.6rem;
    /* Adjust the margin to move the number closer to the name */
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
</style>