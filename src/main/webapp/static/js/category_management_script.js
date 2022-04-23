const addCategoryForm = document.getElementById('add_category_form');
const editCategoryForm = document.getElementById('edit_category_form');

const categoryTitleInput = document.getElementById('add_category_title');
const isCategoryTitleValid = (categoryTitle) => {
    const re = /^([\w\s:.'-]{1,30})$/
    return re.test(categoryTitle)
}
categoryTitleInput.isValid = () => isCategoryTitleValid(categoryTitleInput.value);
const editCategoryInputFields = [categoryTitleInput];
let shouldValidateEditCategory = false;
let isEditCategoryFormValid = false;

const validateEditCategoryInputs = () => {
    if (!shouldValidateEditCategory) return;
    isEditCategoryFormValid = true;
    editCategoryInputFields.forEach((input) => {
        input.nextElementSibling.classList.add("custom_hidden");
        if (!input.isValid()) {
            isEditCategoryFormValid = false;
            input.nextElementSibling.classList.remove("custom_hidden");
        }
    });
};
addCategoryForm.addEventListener('submit', (e) => {
    shouldValidateEditCategory = true;
    validateEditCategoryInputs();
    if (!isEditCategoryFormValid) {
        e.preventDefault();
    }
});
editCategoryForm.addEventListener('submit', (e) => {
    shouldValidateEditCategory = true;
    validateEditCategoryInputs();
    if (!isEditCategoryFormValid) {
        e.preventDefault();
    }
});
editCategoryInputFields.forEach((input) => input.addEventListener("input", validateEditCategoryInputs));

