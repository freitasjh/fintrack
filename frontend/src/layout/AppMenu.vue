<script setup>
import { onBeforeMount, ref } from 'vue';

import AppMenuItem from './AppMenuItem.vue';
import { useStore } from 'vuex';

const store = useStore();
const user = ref({});
const model = ref([
    {
        label: 'Home',
        items: [{ label: 'Dashboard', icon: 'pi pi-fw pi-home', to: '/' }]
    },
    {
        label: 'Cadastro',
        items: [
            { label: 'Categoria', icon: 'pi pi-fw pi-home', to: '/category' },
            { label: 'Conta bancaria', icon: 'pi pi-fw pi-home', to: '/bankAccount' },
            { label: 'Cartão de credito', icon: 'pi pi-fw pi-home', to: '/creditCard' }
        ]
    },
    {
        label: 'Financeiro',
        items: [
            { label: 'Recebidos', icon: 'pi pi-fw pi-home', to: '/accountReceivable' },
            { label: 'Pagos', icon: 'pi pi-fw pi-home', to: '/accountPayment' },
            { label: 'Transferencia', icon: 'pi pi-fw pi-home', to: '/accountTransfer' },
            {
                label: 'Cartão de Credito',
                icon: 'pi pi-fw pi-id-card',
                items: [
                    {
                        label: 'Transação',
                        icon: 'pi pi-fw pi-list',
                        to: '/creditCard/transactions'
                    },
                    {
                        label: 'Fatura',
                        icon: 'pi pi-fw pi-file',
                        to: '/creditCard/invoice'

                    }
                ]
            },
        ]
    },

]);
onBeforeMount(async () => {
    await store.dispatch('userModuleStore/findUserById', localStorage.getItem('userId'));
    user.value = store.state.userModuleStore.user;
});
</script>

<template>
    <ul class="layout-menu">
        <button v-ripple
            class="relative overflow-hidden w-full p-link flex align-items-center p-2 pl-3 text-color hover:surface-200 border-noround">
            <Avatar icon="pi pi-user" class="mr-2" size="xlarge" shape="circle" />
            <span class="inline-flex flex-column">
                <span class="font-bold">{{ user.name }}</span>
                <span class="text-sm">Admin</span>
            </span>
        </button>
        <Divider />
        <template v-for="(item, i) in model" :key="item">
            <app-menu-item v-if="!item.separator" :item="item" :index="i"></app-menu-item>
            <li v-if="item.separator" class="menu-separator"></li>
        </template>
    </ul>
</template>

<style lang="scss" scoped></style>
