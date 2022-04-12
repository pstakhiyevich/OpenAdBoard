if (document.getElementById('sign_in_form') != null) {
    const signInForm = document.getElementById('sign_in_form');
    const userEmailInput = document.getElementById('user_email_input');
    const userPasswordInput = document.getElementById('user_password_input');
    const isValidEmail = (userEmail) => {
        const re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/
        return re.test(userEmail)
    }
    const isValidPassword = (userPassword) => {
        const re = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=\S+$).{6,50}$/
        return re.test(userPassword)
    }
    userEmailInput.isValid = () => isValidEmail(userEmailInput.value);
    userPasswordInput.isValid = () => isValidPassword(userPasswordInput.value);
    const inputFields = [userEmailInput, userPasswordInput];
    let shouldValidate = false;
    let isFormValid = false;
    const validateSignUpInputs = () => {
        if (!shouldValidate) return;
        isFormValid = true;
        inputFields.forEach((input) => {
            input.nextElementSibling.classList.add("custom_hidden");
            if (!input.isValid()) {
                isFormValid = false;
                input.nextElementSibling.classList.remove("custom_hidden");
            }
        });
    };
    signInForm.addEventListener('submit', (e) => {
        shouldValidate = true;
        validateSignUpInputs();
        if (!isFormValid) {
            e.preventDefault();
        }
    });
    inputFields.forEach((input) => input.addEventListener("input", validateSignUpInputs));
}