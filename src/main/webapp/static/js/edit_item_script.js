if (document.getElementById('edit_item_form') != null) {
    const editItemForm = document.getElementById('edit_item_form');
    const editItemTitleInput = document.getElementById('item_title_input');
    const editItemPriceInput = document.getElementById('item_price_input');
    const editItemDescriptionInput = document.getElementById('item_description_input');
    const editItemContactInfoInput = document.getElementById('item_contact_info_input');

    const isValidItemTitle = (itemTitle) => {
        const re = /^([\w\s:.,'-]{1,50})$/
        return re.test(itemTitle)
    }
    const isValidItemPrice = (itemPrice) => {
        const re = /^\d{1,10}$/
        return re.test(itemPrice)
    }
    const isValidItemDescription = (itemDescription) => {
        const re = /^([\w\s:!?.,_'-]{1,300})$/
        return re.test(itemDescription)
    }
    const isValidItemContactInfo = (itemContactInfo) => {
        const re = /^([\w:.@'()+-]{1,30})$/
        return re.test(itemContactInfo)
    }
    editItemTitleInput.isValid = () => isValidItemTitle(editItemTitleInput.value);
    editItemPriceInput.isValid = () => isValidItemPrice(editItemPriceInput.value);
    editItemDescriptionInput.isValid = () => isValidItemDescription(editItemDescriptionInput.value);
    editItemContactInfoInput.isValid = () => isValidItemContactInfo(editItemContactInfoInput.value);
    const editItemInputFields = [editItemTitleInput, editItemPriceInput, editItemDescriptionInput, editItemContactInfoInput];
    let shouldValidateEdit = false;
    let isEditItemFormValid = false;
    const validateEditItemInputs = () => {
        if (!shouldValidateEdit) return;
        isEditItemFormValid = true;
        editItemInputFields.forEach((input) => {
            input.nextElementSibling.classList.add("custom_hidden");
            if (!input.isValid()) {
                isEditItemFormValid = false;
                input.nextElementSibling.classList.remove("custom_hidden");
            }
        });
    };
    editItemForm.addEventListener('submit', (e) => {
        shouldValidateEdit = true;
        validateEditItemInputs();
        if (!isEditItemFormValid) {
            e.preventDefault();
        }
    });
    editItemInputFields.forEach((input) => input.addEventListener("input", validateEditItemInputs));
}