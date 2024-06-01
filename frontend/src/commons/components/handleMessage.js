export const handlerToastSuccess = (message, swal) => {
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

export const handleErrorValidation = (response, swal) => {
    let errorMessage = '';
    errorMessage = response.data.errors.map((error) => `<p><b>${error.message}</b></p>`).join('');

    // Exiba a mensagem de erro usando o Swal
    swal.fire({
        icon: 'error',
        title: response.data.msg,
        html: errorMessage
    });
};

export const handlerError = (error, swal) => {
    let message = '';
    if (error.response !== undefined && error.response.data !== null && error.response.data.msg !== undefined) {
        message = error.response.data.msg;
    } else {
        message = error;
    }
    swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: message
    });
};

export const handlerModalSuccess = (message, swal) => {
    swal.fire({
        title: 'Success',
        text: message,
        icon: 'success'
    });
};
