<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
import { useLayout } from '@/layout/composables/layout';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import { onBeforeMount } from 'vue';
import NotificationService from '../notification/service/notificationService';
import sockjs from "sockjs-client/dist/sockjs"
import { Client } from '@stomp/stompjs';

const { layoutConfig, onMenuToggle } = useLayout();

const outsideClickListener = ref(null);
const topbarMenuActive = ref(false);
const router = useRouter();
const store = useStore();
const profile = ref();
const user = ref({})
const totalNotification = ref(1);
let stompClient = null;


onMounted(() => {
    bindOutsideClickListener();
    connect();
});

onBeforeMount(async () => {
    await findTotalNotificationNotVisualized();
});

onBeforeUnmount(() => {
    unbindOutsideClickListener();
});

const connect = () => {
    const socket = new sockjs('/api/notification');
    stompClient = new Client({
        webSocketFactory: () => socket,
        debug: (str) => {
            console.log(str);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
    });
    stompClient.onConnect = () => {
        console.log(store.state.userModuleStore.user);
        stompClient.subscribe(`/user/${store.state.userModuleStore.user.email}/queue/notification`, async (message) => {
            // await findTotalNotificationNotVisualized();

            console.log('Recebeu mensagem: ', message.body);
        });
    };

    stompClient.activate();
}

const logoUrl = computed(() => {
    return `/layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`;
});
const items = ref([
    {
        separator: true
    },

    {
        label: 'Profile',
        items: [
            {
                label: 'Settings',
                icon: 'pi pi-cog',
            },
            {
                label: 'Logout',
                icon: 'pi pi-sign-out',
                shortcut: '⌘+Q',
                command: () => onLogout()
            }
        ]
    },
    {
        separator: true
    }
]);

const onTopBarMenuButton = () => {
    topbarMenuActive.value = !topbarMenuActive.value;
};

const topbarMenuClasses = computed(() => {
    return {
        'layout-topbar-menu-mobile-active': topbarMenuActive.value
    };
});
const toggleProfile = (event) => {
    profile.value.toggle(event);
}

const bindOutsideClickListener = () => {
    if (!outsideClickListener.value) {
        outsideClickListener.value = (event) => {
            if (isOutsideClicked(event)) {
                topbarMenuActive.value = false;
            }
        };
        document.addEventListener('click', outsideClickListener.value);
    }
};
const unbindOutsideClickListener = () => {
    if (outsideClickListener.value) {
        document.removeEventListener('click', outsideClickListener);
        outsideClickListener.value = null;
    }
};
const isOutsideClicked = (event) => {
    if (!topbarMenuActive.value) return;

    const sidebarEl = document.querySelector('.layout-topbar-menu');
    const topbarEl = document.querySelector('.layout-topbar-menu-button');

    return !(sidebarEl.isSameNode(event.target) || sidebarEl.contains(event.target) || topbarEl.isSameNode(event.target) || topbarEl.contains(event.target));
};
const onLogout = () => {
    store.dispatch("logout");
    window.location.reload(true);
}

const findTotalNotificationNotVisualized = async () => {
    try {
        const service = new NotificationService();
        const response = await service.findTotalNotificationNotVisualized(store.getters.getUserId);
        const totalNotificationResponse = response.data;
        totalNotification.value = response.data;
        if (totalNotificationResponse > 5) {
            totalNotification.value = "5+";
        }

    } catch (error) {
        console.log(error);
    }
}
</script>

<template>
    <div class="layout-topbar">
        <button class="p-link layout-menu-button layout-topbar-button" @click="onMenuToggle()">
            <i class="pi pi-bars"></i>
        </button>
        <router-link to="/" class="layout-topbar-logo">
            <img :src="logoUrl" alt="logo" />
            <span></span>
        </router-link>

        <button class="p-link layout-topbar-menu-button layout-topbar-button" @click="onTopBarMenuButton()">
            <i class="pi pi-ellipsis-v"></i>
        </button>

        <div class="layout-topbar-menu" :class="topbarMenuClasses">
            <!-- <button @click="onTopBarMenuButton()" class="p-link layout-topbar-button">
                <i class="pi pi-calendar"></i>
                <span>Calendar</span>
            </button> -->
            <Button link class="layout-topbar-badge">
                <i v-badge.warning="totalNotification" class="pi pi-inbox" />
            </Button>
            <Button @click="toggleProfile" class="layout-topbar-button" link>
                <i class="pi pi-user"></i>
                <span>Profile</span>
            </Button>

            <OverlayPanel ref="profile">
                <Menu :model="items" class="w-full md:w-15rem">
                    <template #start>
                        <span class="inline-flex align-items-center gap-1 px-2 py-2">
                            <svg width="35" height="40" viewBox="0 0 35 40" fill="none"
                                xmlns="http://www.w3.org/2000/svg" class="h-2rem">
                                <path d="..." fill="var(--primary-color)" />
                                <path d="..." fill="var(--text-color)" />
                            </svg>
                            <span class="text-xl font-semibold">Financeiro <span class="text-primary">APP</span></span>
                        </span>
                    </template>
                    <template #submenuheader="{ item }">
                        <span class="text-primary font-bold">{{ item.label }}</span>
                    </template>
                    <template #item="{ item, props }">
                        <a v-ripple class="flex align-items-center" v-bind="props.action">
                            <span :class="item.icon" />
                            <span class="ml-2">{{ item.label }}</span>
                            <Badge v-if="item.badge" class="ml-auto" :value="item.badge" />

                        </a>
                    </template>
                </Menu>
            </OverlayPanel>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.layout-topbar-badge {
    display: inline-flex;
    justify-content: center;
    align-items: center;
    position: relative;
    color: var(--text-color-secondary);
    border-radius: 50%;
    width: 3rem;
    height: 3rem;
    cursor: pointer;

    &:hover {
        color: var(--text-color);
        background-color: var(--surface-hover);
    }

    // &:focus {
    //     @include focused();
    // }

    i {
        font-size: 1.3rem;
    }

    span {
        font-size: 1rem;
        display: none;
    }
}
</style>
