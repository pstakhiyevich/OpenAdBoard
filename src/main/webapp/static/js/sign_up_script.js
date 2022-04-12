if (document.getElementById('registration_form') != null) {
    const signUpForm = document.getElementById('registration_form');
    const userNameInput = document.getElementById('user_name_input');
    const userEmailInput = document.getElementById('user_email_input');
    const userPasswordInput = document.getElementById('user_password_input');
    const userRepeatedPasswordInput = document.getElementById('user_repeated_password_input');
    const isValidName = (userName) => {
        const re = /^([\w\s:.'-]{1,30})$/
        return re.test(userName)
    }
    const isValidEmail = (userEmail) => {
        const re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/
        return re.test(userEmail)
    }
    const isValidPassword = (userPassword) => {
        const re = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=\S+$).{6,50}$/
        return re.test(userPassword)
    }
    userNameInput.isValid = () => isValidName(userNameInput.value);
    userEmailInput.isValid = () => isValidEmail(userEmailInput.value);
    userPasswordInput.isValid = () => isValidPassword(userPasswordInput.value);
    userRepeatedPasswordInput.isValid = () => isValidPassword(userRepeatedPasswordInput.value) && (userPasswordInput.value === userRepeatedPasswordInput.value);
    const inputFields = [userNameInput, userEmailInput, userPasswordInput, userRepeatedPasswordInput];
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
    signUpForm.addEventListener('submit', (e) => {
        shouldValidate = true;
        validateSignUpInputs();
        if (!isFormValid) {
            e.preventDefault();
        }
    });
    inputFields.forEach((input) => input.addEventListener("input", validateSignUpInputs));
}