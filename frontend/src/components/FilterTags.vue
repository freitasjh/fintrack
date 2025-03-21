<script setup>

import { XIcon } from "lucide-vue-next"; // Ícone de fechar

const props = defineProps({
    filters: Array
});

const emit = defineEmits(["remove"]); // Evento emitido ao remover um filtro


const formatInformation = (filterInformation) => {
    // if (filterInformation.filterType == "dateProcess") {
    //     return formatDate(filterInformation.keyword)
    // }

    return filterInformation.keyword;
};

const formatDate = (value) => {
    const formatter = new Intl.DateTimeFormat('pt-BR', { dateStyle: 'short' });
    let date = new Date(value);
    return formatter.format(date);
};

</script>

<template>
    <div v-if="filters.length" class="flex flex-wrap gap-2">
        <Tag v-for="(filter, index) in filters" :key="index" v-tooltip="filter.filterName"
            class="flex items-center gap-2 px-3 py-1 rounded-lg bg-gray-200 text-gray-800">
            <XIcon class="cursor-pointer hover:text-red-700" :size="13" @click="emit('remove', index)" />
            {{ formatInformation(filter) }}
        </Tag>
    </div>
</template>