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

export const handlerError = (response, swal) => {
    swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: response.data.msg
    });
};
