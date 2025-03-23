<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from "vue";
import { useLayout } from "@/layout/composables/layout";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { onBeforeMount } from "vue";
import NotificationService from "../notification/service/notificationService";
import sockjs from "sockjs-client/dist/sockjs";
import { Client } from "@stomp/stompjs";
import { useHandlerMessage } from "../composables/commons";


const { layoutConfig, onMenuToggle } = useLayout();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const outsideClickListener = ref(null);
const topbarMenuActive = ref(false);
const router = useRouter();
const store = useStore();
const profile = ref();
const user = ref({});
const notification = ref();
const totalNotification = ref(1);
let stompClient = null;

const listNotification = ref([]);

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
    const socket = new sockjs("/api/notification");
    stompClient = new Client({
        webSocketFactory: () => socket,
        // debug: (str) => {
        //     console.log(str);
        // },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
    });
    stompClient.onConnect = () => {
        console.log(store.state.userModuleStore.user);
        stompClient.subscribe(
            `/user/${store.state.userModuleStore.user.email}/queue/notification`,
            async (message) => {
                await findTotalNotificationNotVisualized();

                console.log("Recebeu mensagem: ", message.body);
            }
        );
    };

    stompClient.activate();
};

const logoUrl = computed(() => {
    return `/layout/images/${layoutConfig.darkTheme.value ? "logo-white" : "logo-dark"
        }.svg`;
});
const items = ref([
    {
        separator: true,
    },

    {
        label: "Profile",
        items: [
            {
                label: "Settings",
                icon: "pi pi-cog",
            },
            {
                label: "Logout",
                icon: "pi pi-sign-out",
                shortcut: "⌘+Q",
                command: () => onLogout(),
            },
        ],
    },
    {
        separator: true,
    },
]);

const onTopBarMenuButton = () => {
    topbarMenuActive.value = !topbarMenuActive.value;
};

const topbarMenuClasses = computed(() => {
    return {
        "layout-topbar-menu-mobile-active": topbarMenuActive.value,
    };
});
const toggleProfile = (event) => {
    profile.value.toggle(event);
};

const toggleNotifications = async (event) => {
    notification.value.toggle(event);
    await findNotificationNotVisualized();
};

const bindOutsideClickListener = () => {
    if (!outsideClickListener.value) {
        outsideClickListener.value = (event) => {
            if (isOutsideClicked(event)) {
                topbarMenuActive.value = false;
            }
        };
        document.addEventListener("click", outsideClickListener.value);
    }
};
const unbindOutsideClickListener = () => {
    if (outsideClickListener.value) {
        document.removeEventListener("click", outsideClickListener);
        outsideClickListener.value = null;
    }
};
const isOutsideClicked = (event) => {
    if (!topbarMenuActive.value) return;

    const sidebarEl = document.querySelector(".layout-topbar-menu");
    const topbarEl = document.querySelector(".layout-topbar-menu-button");

    return !(
        sidebarEl.isSameNode(event.target) ||
        sidebarEl.contains(event.target) ||
        topbarEl.isSameNode(event.target) ||
        topbarEl.contains(event.target)
    );
};
const onLogout = () => {
    store.dispatch("logout");
    window.location.reload(true);
};

const findTotalNotificationNotVisualized = async () => {
    try {
        const service = new NotificationService();
        const response = await service.findTotalNotificationNotVisualized(
            store.getters.getUserId
        );
        const totalNotificationResponse = response.data;
        totalNotification.value = response.data;
        if (totalNotificationResponse > 5) {
            totalNotification.value = "5+";
        }
    } catch (error) {
        console.log(error);
    }
};

const findNotificationNotVisualized = async () => {
    try {
        const service = new NotificationService();
        const response = await service.findNotificationByUser(store.getters.getUserId);
        listNotification.value = response.data;
    } catch (error) {
        handlerError(error)
    }
};

const updateAllNotification = async () => {
    try {
        console.log("Atualizando todas as notificações");
        const service = new NotificationService();
        await service.updateAllVisualized(store.getters.getUserId);
        await findTotalNotificationNotVisualized();
        await findNotificationNotVisualized();
    } catch (error) {
        handlerError(error)
    }
};
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
            <Button link class="layout-topbar-badge" @click="toggleNotifications">
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

            <OverlayPanel ref="notification">
                <div class=" md:w-25rem">
                    <div class="flex justify-content-between">
                        <div class="m-2 font-semibold">
                            <span>Notificação</span>
                        </div>
                        <div class="font-semibold mb-1" v-if="listNotification.length > 0">
                            <Button label="Marcar como lido" icon="pi pi-check" raised rounded
                                @click="updateAllNotification" />
                        </div>
                    </div>
                    <DataView :value="listNotification">
                        <template #list="slotProps">
                            <ScrollPanel style="width: 100%; height: 450px">
                                <div v-for="(item, index) in slotProps.items" :key="index">
                                    <div class="card border-blue m-3" style="border-left-width: 10px;">
                                        <div class="col-span-12">
                                            <div class="text-blue-600 font-medium">
                                                Mensagem
                                            </div>
                                            <div class="font-sm text-surface-700 dark:text-surface-100 leading-normal">
                                                {{ item.message }}
                                            </div>
                                            <div class="flex justify-content-between">
                                                <div
                                                    class="text-xs flex items-center text-surface-700 dark:text-surface-100 mt-4">
                                                    <i class="pi pi-calendar mr-1 text-xs"></i><span>{{ new
                                                        Date(item.dateCreated).toLocaleString() }}</span>
                                                </div>

                                                <div class="mt-3" v-if="item.visualized === false">
                                                    <Tag value="New"></Tag>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </ScrollPanel>
                        </template>
                        <template #empty>
                            <div class="p-4 text-center">
                                <span>Nenhuma notificação</span>
                            </div>
                        </template>
                    </DataView>
                </div>
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

    i {
        font-size: 1.3rem;
    }

    span {
        font-size: 1rem;
        display: none;
    }
}

.border-blue {
    border-color: #3f57df;
    /* Substitua pela cor desejada */
}
</style>