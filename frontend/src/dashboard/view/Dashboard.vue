<script setup>
import { onMounted, reactive, ref, watch } from 'vue';
import { ProductService } from '@/service/ProductService';
import { useLayout } from '@/layout/composables/layout';
import AccountBalanceComponent from '@/dashboard/components/AccountBalanceComponent.vue'
import AccountExpendBalance from '@/dashboard/components/AccountExpendBalanceComponent.vue'
import AccountPaymentRecent from '@/dashboard/components/AccountPaymentRecent.vue';
import CategoryExpenseComponent from '@/dashboard/components/CategoryExpenseComponent.vue';
import AccountPaymentNotProcessedComponent from '@/dashboard/components/AccountPaymentNotProcessedComponent.vue';
import AccountPaymentRecentOpen from '@/dashboard/components/AccountPaymentRecentOpen.vue';
import CreditCardInvoiceAmountOpen from '@/dashboard/components/CreditCardInvoiceAmountOpen.vue';
import CreditCardDashView from '../components/CreditCardDashView.vue';
import BankAccountDashView from '../components/BankAccountDashView.vue';

const { isDarkTheme } = useLayout();
</script>

<template>
    <div class="grid">
        <div class="col-12 lg:col-6 xl:col-3">
            <AccountBalanceComponent />
        </div>
        <div class="col-12 lg:col-6 xl:col-3">
            <AccountExpendBalance />
        </div>
        <div class="col-12 lg:col-6 xl:col-3">
            <AccountPaymentNotProcessedComponent />
        </div>
        <div class="col-12 lg:col-6 xl:col-3">
            <CreditCardInvoiceAmountOpen />
        </div>
        <div class="col-12 lg:col-6 xl:col-6">
            <BankAccountDashView />
        </div>
        <div class="col-12 lg:col-6 xl:col-6">
            <CreditCardDashView />
        </div>
        <div class="col-12 xl:col-12">
            <div class="dashboard">
                <div class="col" :class="{ 'full-width': true }">
                    <AccountPaymentRecent />
                </div>
                <div class="col" :class="{ 'full-width': true }">
                    <CategoryExpenseComponent />
                </div>
            </div>
        </div>
        <div class="col-12 xl:col-12">
            <div class="dashboard">
                <div class="col" :class="{ 'full-width': false }">
                    <AccountPaymentRecentOpen />
                </div>

            </div>
        </div>
        <div class="col-12 xl:col-6">
            <!-- <div class="card">
                <h5>Sales Overview</h5>
                <Chart type="line" :data="lineData" :options="lineOptions" />
            </div> -->
            <!-- <div class="card">
                <div class="flex align-items-center justify-content-between mb-4">
                    <h5>Notifications</h5>
                    <div>
                        <Button icon="pi pi-ellipsis-v" class="p-button-text p-button-plain p-button-rounded"
                            @click="$refs.menu1.toggle($event)"></Button>
                        <Menu ref="menu1" :popup="true" :model="items"></Menu>
                    </div>
                </div>

                <span class="block text-600 font-medium mb-3">TODAY</span>
                <ul class="p-0 mx-0 mt-0 mb-4 list-none">
                    <li class="flex align-items-center py-2 border-bottom-1 surface-border">
                        <div
                            class="w-3rem h-3rem flex align-items-center justify-content-center bg-blue-100 border-circle mr-3 flex-shrink-0">
                            <i class="pi pi-dollar text-xl text-blue-500"></i>
                        </div>
                        <span class="text-900 line-height-3">Richard Jones
                            <span class="text-700">has purchased a blue t-shirt for <span
                                    class="text-blue-500">79$</span></span>
                        </span>
                    </li>
                    <li class="flex align-items-center py-2">
                        <div
                            class="w-3rem h-3rem flex align-items-center justify-content-center bg-orange-100 border-circle mr-3 flex-shrink-0">
                            <i class="pi pi-download text-xl text-orange-500"></i>
                        </div>
                        <span class="text-700 line-height-3">Your request for withdrawal of <span
                                class="text-blue-500 font-medium">2500$</span> has been initiated.</span>
                    </li>
                </ul>

                <span class="block text-600 font-medium mb-3">YESTERDAY</span>
                <ul class="p-0 m-0 list-none">
                    <li class="flex align-items-center py-2 border-bottom-1 surface-border">
                        <div
                            class="w-3rem h-3rem flex align-items-center justify-content-center bg-blue-100 border-circle mr-3 flex-shrink-0">
                            <i class="pi pi-dollar text-xl text-blue-500"></i>
                        </div>
                        <span class="text-900 line-height-3">Keyser Wick
                            <span class="text-700">has purchased a black jacket for <span
                                    class="text-blue-500">59$</span></span>
                        </span>
                    </li>
                    <li class="flex align-items-center py-2 border-bottom-1 surface-border">
                        <div
                            class="w-3rem h-3rem flex align-items-center justify-content-center bg-pink-100 border-circle mr-3 flex-shrink-0">
                            <i class="pi pi-question text-xl text-pink-500"></i>
                        </div>
                        <span class="text-900 line-height-3">Jane Davis
                            <span class="text-700">has posted a new questions about your product.</span>
                        </span>
                    </li>
                </ul>
            </div> -->
        </div>
    </div>
</template>
<style scoped>
.dashboard {
    display: flex;
    flex-wrap: wrap;
}

.col {
    flex: 1;
    padding: 10px;
}

.full-width {
    flex: 0 0 100%;
}
</style>