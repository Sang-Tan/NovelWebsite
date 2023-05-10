function registerSubmit() {
    const form = event.target;
    const request = new XMLHttpRequest();
    request.open("POST", "/register");

    request.onload = () => {
        if (request.status === 404) {
            alert("Đăng ký thất bại");
            return;
        }

        const data = JSON.parse(request.responseText);

        if (data.status === "success") {
            alert("Đăng ký thành công, vui lòng đăng nhập")
            window.location.reload();
        } else {
            showFormError(form, data);
        }
    }
    const formData = new FormData(form);
    request.send(formData);
}

function loginSubmit() {
    const form = event.target;
    const request = new XMLHttpRequest();
    request.open("POST", "/login");

    request.onload = () => {
        if (request.status === 404) {
            alert("Đăng nhập thất bại");
            return;
        }

        const data = JSON.parse(request.responseText);
        if (data.status === "success") {
            window.location.reload();
        } else {
            showFormError(form, data);
        }
    }

    const formData = new FormData(form);
    request.send(formData);
}

function showFormError(form, errorData) {
    const errors = errorData.errors;
    Object.entries(errors).forEach(([key, value]) => {
        const invalidInput = form.elements[key];
        const errorText = getErrorTextElement(invalidInput);

        invalidInput.classList.add("error");
        showErrorText(errorText, value);

//add event listener to remove error class
        invalidInput.addEventListener("focus", () => {
            invalidInput.classList.remove("error");
            hideErrorText(errorText);
        });
    });
}

function getErrorTextElement(inputElement) {
    return inputElement.parentNode.querySelector(".error-text");
}

function showErrorText(errorTextElement, text) {
    if (errorTextElement) {
        errorTextElement.classList.remove("hidden");
        errorTextElement.innerText = text;
    }
}

function hideErrorText(errorTextElement) {
    if (errorTextElement) {
        errorTextElement.classList.add("hidden");
    }
}

function redirectToSearch(event) {
    event.preventDefault();
    const novelName = document.getElementById('novelName').value;
    location.assign("/tim-kiem-truyen?novel=" + novelName);
}