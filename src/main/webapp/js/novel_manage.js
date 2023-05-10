function submitForm(event) {
    const form = event.target
    const genreInput = addCheckboxesData(form, 'genre', 'genres');
    if (!genreInput) {
        alert("Chọn ít nhất 1 thể loại");
        return;
    }
    form.appendChild(genreInput);
    form.submit();
}

const image_preview = document.getElementById("image-preview");
const image_input = document.getElementById("image-input");

bindImagePreview(image_input, image_preview);