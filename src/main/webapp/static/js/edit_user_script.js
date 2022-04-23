const navEditTab = document.getElementById('nav-edit-tab');
const navPasswordTab = document.getElementById('nav-password-tab');

if (document.getElementById('edit_user_form') != null) {
    const editUserForm = document.getElementById('edit_user_form');
    const userNameInput = document.getElementById('user_name_input');
    const userEmailInput = document.getElementById('user_email_input');
    const isValidUserName = (userName) => {
        const re = /^([\w\s:.'-]{1,30})$/
        return re.test(userName)
    }
    const isValidUserEmail = (userEmail) => {
        const re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/
        return re.test(userEmail)
    }
    userNameInput.isValid = () => isValidUserName(userNameInput.value);
    userEmailInput.isValid = () => isValidUserEmail(userEmailInput.value);
    const inputFieldsEditUserForm = [userNameInput, userEmailInput];
    let shouldValidateEditUserForm = false;
    let isEditUserFormValid = false;
    const validateEditUserFormInputs = () => {
        if (!shouldValidateEditUserForm) return;
        isEditUserFormValid = true;
        inputFieldsEditUserForm.forEach((input) => {
            input.nextElementSibling.classList.add("custom_hidden");
            if (!input.isValid()) {
                isEditUserFormValid = false;
                input.nextElementSibling.classList.remove("custom_hidden");
            }
        });
    };
    editUserForm.addEventListener('submit', (e) => {
        shouldValidateEditUserForm = true;
        validateEditUserFormInputs();
        if (!isEditUserFormValid) {
            e.preventDefault();
        }
        navEditTab.classList.add('active');
        navPasswordTab.classList.remove('active');
    });
    inputFieldsEditUserForm.forEach((input) => input.addEventListener("input", validateEditUserFormInputs));
}

if (document.getElementById('change_password_form') != null) {
    const changePasswordForm = document.getElementById('change_password_form');
    const oldPasswordInput = document.getElementById('old_password_input');
    const newPasswordInput = document.getElementById('new_password_input');
    const repeatNewPasswordInput = document.getElementById('repeat_new_password_input');
    const isValidUserPassword = (userPassword) => {
        const re = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=\S+$).{6,50}$/
        return re.test(userPassword)
    }
    oldPasswordInput.isValid = () => isValidUserPassword(oldPasswordInput.value);
    newPasswordInput.isValid = () => isValidUserPassword(newPasswordInput.value);
    repeatNewPasswordInput.isValid = () => isValidUserPassword(repeatNewPasswordInput.value) && (newPasswordInput.value === repeatNewPasswordInput.value);
    const inputFieldsChangePasswordForm = [oldPasswordInput, newPasswordInput, repeatNewPasswordInput];
    let shouldValidateChangePasswordForm = false;
    let isChangePasswordFormValid = false;
    const validateChangePasswordFormInputs = () => {
        if (!shouldValidateChangePasswordForm) return;
        isChangePasswordFormValid = true;
        console.log(oldPasswordInput.value);
        console.log(inputFieldsChangePasswordForm);
        inputFieldsChangePasswordForm.forEach((input) => {
            input.nextElementSibling.classList.add("custom_hidden");
            if (!input.isValid()) {
                isChangePasswordFormValid = false;
                input.nextElementSibling.classList.remove("custom_hidden");
            }
        });
    };
    changePasswordForm.addEventListener('submit', (e) => {
        shouldValidateChangePasswordForm = true;
        validateChangePasswordFormInputs();
        if (!isChangePasswordFormValid) {
            e.preventDefault();
        }
        navEditTab.classList.remove('active');
        navPasswordTab.classList.add('active');
    });
    inputFieldsChangePasswordForm.forEach((input) => input.addEventListener("input", validateChangePasswordFormInputs));
}