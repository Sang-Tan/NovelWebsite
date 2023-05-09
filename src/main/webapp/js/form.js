/**
 * Create and return a hidden input with the value of the selected checkboxes as a comma separated string
 * @param form form to get checkboxes from
 * @param dataName data attribute name to search. Example : if dataName = "genre" then attribute name will be "data-genre"
 * @param submitName name of the input to create. Example : if submitName = "genres" then input name will be "genres"
 * @returns {HTMLInputElement,null} input with the value of the selected checkboxes as a comma separated string or null if no checkbox is selected
 */
function addCheckboxesData(form, dataName, submitName) {

    let submitData = "";
    const checkboxes = form.querySelectorAll(`input[data-${dataName}]:checked`);

    if (checkboxes.length === 0) {
        return null;
    }

    const selectAttr = `data-${dataName}`;

    checkboxes.forEach(function (input, index) {
        const data = input.getAttribute(selectAttr);
        if (data === "") {
            console.error({"error": `Element ${selectAttr} attribute is empty`, "element": input});
        }
        submitData += data;

        // Add comma if not last element
        if (index < checkboxes.length - 1) {
            submitData += ",";
        }
    });

    const dataSubmit = document.createElement("input");
    dataSubmit.setAttribute("type", "hidden");
    dataSubmit.setAttribute("name", submitName);
    dataSubmit.setAttribute("value", submitData);

    return dataSubmit;
}

function bindImagePreview(input, preview) {
    input.addEventListener("change", function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.addEventListener("load", function () {
                preview.setAttribute("style", `background-image: url(${this.result})`);
            });
            reader.readAsDataURL(file);
        }
    });
}

/**
 * @param form {HTMLFormElement}
 * @returns {Promise<any>}
 */
async function submitFormAjax(form) {
    const formData = new FormData(form);
    const enctype = form.enctype;
    let reqBody;
    if (enctype === "multipart/form-data") {
        reqBody = formData;
    } else if (enctype === "application/x-www-form-urlencoded") {
        let dataList = [];
        for (let pair of formData.entries()) {
            dataList.push(`${pair[0]}=${pair[1]}`);
        }
        reqBody = dataList.join("&");
    }
    return fetch(form.action, {
        method: form.method,
        body: reqBody,
        headers: {
            'Content-Type': form.enctype
        }
    }).then(function (response) {
        if (response.ok) {
            return response.json();
        } else {
            return Promise.reject();
        }
    });
}

