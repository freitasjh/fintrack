import { inject } from 'vue';
import { useLoading } from 'vue-loading-overlay';

export function useLoader() {
    const $loading = useLoading({});
    let loader;
    let isLoading = false;

    const showLoading = () => {
        if (!isLoading) {
            loader = $loading.show();
            isLoading = true;
        }
    };

    const hideLoading = () => {
        if (isLoading) {
            console.log('Hide loading');
            loader.hide();
            isLoading = false;
        }
    };

    return { showLoading, hideLoading };
}

export function useHandlerMessage() {
    const swal = inject('$swal');

    const handlerErrorValidation = (error) => {
        let errorMessage = '';
        errorMessage = error.response.data.errors.map((error) => `<p><b>${error.message}</b></p>`).join('');

        // Exiba a mensagem de erro usando o Swal
        swal.fire({
            icon: 'error',
            title: error.response.data.msg,
            html: errorMessage
        });
    };

    const handlerError = (error) => {
        let message = '';
        if (error.response !== undefined && error.response.data !== null && error.response.data.msg !== undefined) {
            if (error.response.data.errors !== undefined && error.response.data.erros !== null) {
                handlerErrorValidation(error);
                return;
            } else {
                message = error.response.data.msg;
            }
        } else {
            message = error;
        }
        swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: message
        });
    };

    const handlerToastSuccess = (message) => {
        const Toast = swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.onmouseenter = swal.stopTimer;
                toast.onmouseleave = swal.resumeTimer;
            }
        });
        Toast.fire({
            icon: 'success',
            title: message
        });
    };

    const handlerModalSuccess = (message, swal) => {
        swal.fire({
            title: 'Success',
            text: message,
            icon: 'success'
        });
    };

    return { handlerError, handlerErrorValidation, handlerModalSuccess, handlerToastSuccess };
}

export function useCurrency() {
    const formatCurrency = (value) => {
        return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
    };

    return { formatCurrency };
}
