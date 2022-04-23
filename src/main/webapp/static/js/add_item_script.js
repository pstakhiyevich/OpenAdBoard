if (document.getElementById('add_item_form') != null) {
    const addItemForm = document.getElementById('add_item_form');
    const itemTitleInput = document.getElementById('item_title_input');
    const itemPriceInput = document.getElementById('item_price_input');
    const itemDescriptionInput = document.getElementById('item_description_input');
    const itemContactInfoInput = document.getElementById('item_contact_info_input');
    const itemPictureInput = document.getElementById('file_input');

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
    const isValidPicture = (picture) => {
        const re = /([^\s]+(\.(jpg|jpeg|png))$)/
        return re.test(picture)
    }
    itemTitleInput.isValid = () => isValidItemTitle(itemTitleInput.value);
    itemPriceInput.isValid = () => isValidItemPrice(itemPriceInput.value);
    itemDescriptionInput.isValid = () => isValidItemDescription(itemDescriptionInput.value);
    itemContactInfoInput.isValid = () => isValidItemContactInfo(itemContactInfoInput.value);
    itemPictureInput.isValid = () => isValidPicture(itemPictureInput.value);
    console.log(itemPictureInput.value);
    const addItemInputFields = [itemTitleInput, itemPriceInput, itemDescriptionInput, itemContactInfoInput, itemPictureInput];
    let shouldValidate = false;
    let isFormValid = false;
    const validateAddItemInputs = () => {
        if (!shouldValidate) return;
        isFormValid = true;
        addItemInputFields.forEach((input) => {
            input.nextElementSibling.classList.add("custom_hidden");
            if (!input.isValid()) {
                isFormValid = false;
                input.nextElementSibling.classList.remove("custom_hidden");
            }
        });
    };
    addItemForm.addEventListener('submit', (e) => {
        shouldValidate = true;
        validateAddItemInputs();
        if (!isFormValid) {
            e.preventDefault();
        }
    });
    addItemInputFields.forEach((input) => input.addEventListener("input", validateAddItemInputs));
}


