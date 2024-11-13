import { createRouter, createWebHistory } from 'vue-router';
import AppLayout from '@/layout/AppLayout.vue';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            component: AppLayout,
            children: [
                {
                    path: '/',
                    name: 'dashboard',
                    component: () => import('@/dashboard/view/Dashboard.vue')
                },
                {
                    path: '/category',
                    name: 'category',
                    component: () => import('@/administrator/category/view/CategoryView.vue')
                },
                {
                    path: '/creditCard',
                    name: 'creditCard',
                    component: () => import('@/administrator/creditCard/view/CreditCardView.vue')
                },
                {
                    path: '/bankAccount',
                    name: 'bankAccount',
                    component: () => import('../administrator/bankAccount/view/BankAccountView.vue')
                },
                {
                    path: '/accountReceivable',
                    name: 'accountReceivable',
                    component: () => import('@/financial/accountsReceivable/view/AccountReceivableView.vue')
                },
                {
                    path: '/accountPayment',
                    name: 'accountPayment',
                    component: () => import('@/financial/accountPayment/view/AccountPaymentView.vue')
                },
                {
                    path: '/accountTransfer',
                    name: 'accountTransfer',
                    component: () => import('@/financial/accountTransfer/view/AccountTransferView.vue')
                },
                {
                    path: '/creditCard/transactions',
                    name: 'creditCardTransaction',
                    component: () => import('@/financial/creditCard/transaction/view/CreditCardTransactionView.vue')
                }
            ]
        },
        {
            path: '/pages/notfound',
            name: 'notfound',
            component: () => import('@/views/pages/NotFound.vue')
        },

        {
            path: '/auth/login',
            name: 'login',
            component: () => import('@/login/view/Login.vue')
        },
        {
            path: '/auth/access',
            name: 'accessDenied',
            component: () => import('@/views/pages/auth/Access.vue')
        },
        {
            path: '/auth/error',
            name: 'error',
            component: () => import('@/views/pages/auth/Error.vue')
        },
        {
            path: '/newAccount',
            name: 'newAccount',
            component: () => import('@/administrator/user/view/AccountNew.vue')
        }
    ]
});

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token');

    if (to.name === 'error') {
        next();
    }
    if (to.name === 'newAccount') {
        next();
    } else if (to.name != 'login' && token === null) {
        next({ name: 'login' });
    } else {
        next();
    }
});

export default router;
