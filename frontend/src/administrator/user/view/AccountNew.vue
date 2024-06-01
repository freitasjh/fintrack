<script setup>
import { inject, ref } from 'vue';
import UserModel from '../model/UserModel';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { handleErrorValidation, handlerError, handlerModalSuccess } from '@/commons/components/handleMessage';

const user = ref(new UserModel());
const store = useStore();
const router = useRouter();
const swal = inject('$swal');

const saveNewAccount = async () => {
    try {
        user.value.gender = 'M';
        await store.dispatch('userModuleStore/saveNewAccount', user.value);
        handlerModalSuccess("Nova conta cadastrada com sucesso", swal);
    } catch (error) {
        console.log(error);
        if (error.response.status === 406) {
            handleErrorValidation(error.response, swal);
        } else {
            handlerError(error.response, swal);
        }
    }
};
</script>
<!-- eslint-disable prettier/prettier -->
<template>
    <div
        class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
        <div class="flex flex-column align-items-center justify-content-center">
            <div
                style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%)">
                <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 53px">
                    <div class="text-center mb-5">
                        <div class="text-900 text-3xl font-medium mb-3">{{ $t('createNewAccount') }}</div>
                    </div>
                    <div class="flex align-items-center justify-content-center mb-5 gap-5">
                        <a class="font-medium no-underline ml-2 text-center cursor-pointer"
                            style="color: var(--primary-color)" @click="router.push({ name: 'login' })">{{
                                $t('returnLoginPage') }}</a>
                    </div>
                    <div>
                        <label for="user-name" class="block text-900 text-xl font-medium mb-2">{{ $t('userName')
                            }}</label>
                        <InputText id="user-name" type="text" class="w-full md:w-30rem mb-5" style="padding: 1rem"
                            v-model="user.name" />
                        <label for="user-federalId" class="block text-900 text-xl font-medium mb-2">{{
                            $t('userFederalId') }}</label>
                        <InputText id="user-federalId" type="text" class="w-full md:w-30rem mb-5" style="padding: 1rem"
                            v-model="user.federalId" />
                        <label for="user-phone" class="block text-900 text-xl font-medium mb-2">{{ $t('userPhone')
                            }}</label>
                        <InputText id="user-phone" type="text" class="w-full md:w-30rem mb-5" style="padding: 1rem"
                            v-model="user.phone" />

                        <label for="user-cellphone" class="block text-900 text-xl font-medium mb-2">{{
                            $t('userCellPhone') }}</label>
                        <InputText id="user-cellphone" type="text" class="w-full md:w-30rem mb-5" style="padding: 1rem"
                            v-model="user.cellPhone" />

                        <label for="user-dateOfBirth" class="block text-900 text-xl font-medium mb-2">{{
                            $t('userDateOfBirth') }}</label>
                        <InputText id="user-dateOfBirth" type="text" class="w-full md:w-30rem mb-5"
                            style="padding: 1rem" v-model="user.dateOfBirth" />

                        <label for="user-email" class="block text-900 text-xl font-medium mb-2">{{ $t('userEmail')
                            }}</label>
                        <InputText id="user-email" type="text" :placeholder="$t('emailAddress')"
                            class="w-full md:w-30rem mb-5" style="padding: 1rem" v-model="user.email" />

                        <label for="user-username" class="block text-900 text-xl font-medium mb-2">{{
                            $t('loginUsername')
                        }}</label>
                        <InputText id="user-username" type="text" :placeholder="$t('emailAddress')"
                            class="w-full md:w-30rem mb-5" style="padding: 1rem" v-model="user.username" />

                        <label for="user-password" class="block text-900 font-medium text-xl mb-2">{{ $t('password')
                            }}</label>
                        <Password id="user-password" v-model="user.password" :placeholder="$t('password')"
                            :toggleMask="true" class="w-full mb-3" inputClass="w-full"
                            :inputStyle="{ padding: '1rem' }"></Password>
                        <Button :label="$t('register')" class="w-full p-3 text-xl" @click="saveNewAccount"></Button>
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
