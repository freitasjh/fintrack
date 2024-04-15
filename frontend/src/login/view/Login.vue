<script setup>
import { ref } from 'vue';
import LoginModel from '@/login/model/LoginModel';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const login = ref(new LoginModel());
const store = useStore();
const router = useRouter();

const authenticate = async () => {
    try {
        await store.dispatch('authenticate', login.value);
        await store.dispatch('userModuleStore/findUserById', store.getters.getUserId);
        router.push('/');
    } catch (error) {
        window.alert('Erro ao tentar fazer o login');
    }
};
</script>
<!-- eslint-disable prettier/prettier -->
<template>
    <div
        class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden"
    >
        <div class="flex flex-column align-items-center justify-content-center">
            <!-- <img :src="logoUrl" alt="Sakai logo" class="mb-5 w-6rem flex-shrink-0" /> -->
            <div
                style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%)"
            >
                <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 53px">
                    <div class="text-center mb-5">
                        <!-- <img
                            src="/demo/images/login/avatar.png"
                            alt="Image"
                            height="50"
                            class="mb-3"
                        />-->
                        <div class="text-900 text-3xl font-medium mb-3">{{ $t('welcome') }}</div>
                        <span class="text-600 font-medium">{{ $t('personalFinancialControl') }}</span>
                    </div>

                    <div>
                        <label
                            for="email1"
                            class="block text-900 text-xl font-medium mb-2"
                        >{{ $t('loginUsername') }}</label>
                        <InputText
                            id="email1"
                            type="text"
                            :placeholder="$t('emailAddress')"
                            class="w-full md:w-30rem mb-5"
                            style="padding: 1rem"
                            v-model="login.username"
                        />

                        <label
                            for="password1"
                            class="block text-900 font-medium text-xl mb-2"
                        >{{ $t('password') }}</label>
                        <Password
                            id="password1"
                            v-model="login.password"
                            :placeholder="$t('password')"
                            :toggleMask="true"
                            class="w-full mb-3"
                            inputClass="w-full"
                            :inputStyle="{ padding: '1rem' }"
                        ></Password>

                        <div class="flex align-items-center justify-content-between mb-5 gap-5">
                            <div class="flex align-items-center">
                                <a
                                    @click="router.push('/newAccount')"
                                    class="font-medium no-underline ml-2 text-right cursor-pointer"
                                    style="color: var(--primary-color)"
                                >{{ $t('newAccount')}}</a>
                            </div>
                            <a
                                class="font-medium no-underline ml-2 text-right cursor-pointer"
                                style="color: var(--primary-color)"
                            >{{ $t('forgotPassword')}}</a>
                        </div>
                        <Button
                            :label="$t('signIn')"
                            class="w-full p-3 text-xl"
                            @click="authenticate"
                        ></Button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.pi-eye {
    transform: scale(1.6);
    margin-right: 1rem;
}

.pi-eye-slash {
    transform: scale(1.6);
    margin-right: 1rem;
}
</style>
