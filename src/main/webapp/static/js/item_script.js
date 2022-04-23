if (document.getElementById('comment_form') !== null) {
    const commentForm = document.getElementById('comment_form');
    const userCommentInput = document.getElementById('user_comment_input');
    const isUserCommentValid = (userComment) => {
        const re = /^([\w\s:!?.,_'-]{1,500})$/
        return re.test(userComment)
    }
    userCommentInput.isValid = () => isUserCommentValid(userCommentInput.value);
    const userCommentInputFields = [userCommentInput];
    let shouldValidateUserComment = false;
    let isUserCommentFormValid = false;
    const validateUserCommentInputs = () => {
        if (!shouldValidateUserComment) return;
        isUserCommentFormValid = true;
        userCommentInputFields.forEach((input) => {
            input.nextElementSibling.classList.add("custom_hidden");
            if (!input.isValid()) {
                isUserCommentFormValid = false;
                input.nextElementSibling.classList.remove("custom_hidden");
            }
        });
    };
    commentForm.addEventListener('submit', (e) => {
        shouldValidateUserComment = true;
        validateUserCommentInputs();
        if (!isUserCommentFormValid) {
            e.preventDefault();
        }
    });
    userCommentInputFields.forEach((input) => input.addEventListener("input", validateUserCommentInputs));
}
