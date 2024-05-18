<script setup>
import { FilterMatchMode } from 'primevue/api';
import { onBeforeMount, ref } from 'vue';

const bankAccount = ref(null);
const listOfBankAccount = ref(null);
const listOfBank = ref(null);
const filters = ref({});

const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};
onBeforeMount(() => {
    initFilters();
});
</script>
<!-- eslint-disable prettier/prettier -->
<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <Toolbar class="mb-4">
                    <template v-slot:start>
                        <div class="my-2">
                            <Button label="New" icon="pi pi-plus" class="mr-2" severity="success" />
                            <Button
                                label="Delete"
                                icon="pi pi-trash"
                                severity="danger"
                                :disabled="!selectedProducts || !selectedProducts.length"
                            />
                        </div>
                    </template>
                </Toolbar>
            </div>
        </div>
    </div>
</template>
