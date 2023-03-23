function submitForm() {

    const form = document.getElementById("search-form");
    if (!form) {
        throw new Error("Form not found");
    }

    let genres = "";
    const genreInputs = form.querySelectorAll("input[data-genre]:checked");

    genreInputs.forEach(function (input, index) {
        const genre = input.getAttribute("data-genre");
        if (genre === "") {
            console.error({"error": "Element data-genre attribute is empty", "element": input});
        }
        genres += genre;

        // Add comma if not last element
        if (index < genreInputs.length - 1) {
            genres += ",";
        }
    });

    const genreSubmit = document.createElement("input");
    genreSubmit.setAttribute("type", "hidden");
    genreSubmit.setAttribute("name", "genres");
    genreSubmit.setAttribute("value", genres);
    form.appendChild(genreSubmit);

    form.submit();

    form.removeChild(genreSubmit);
}

function decodeURI() {
    const url = window.location.href;
    const decoded = decodeURIComponent(url);
    window.history.replaceState({}, document.title, decoded);
}

decodeURI();
