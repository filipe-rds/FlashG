document.addEventListener("DOMContentLoaded", function () {
    const tagInput = document.getElementById("tagInput");
    const suggestionsContainer = document.getElementById("tagSuggestions");
    const selectedTagsContainer = document.getElementById("selectedTagsContainer");
    const hiddenInput = document.getElementById("selectedTags");

    tagInput.addEventListener("input", async function () {
        let input = tagInput.value.trim();

        if (input.length < 1) {
            suggestionsContainer.innerHTML = "";
            suggestionsContainer.classList.add("hidden");
            return;
        }

        try {
            let response = await fetch(`/FlashG/tags/suggestions?name=${input}`);
            let tags = await response.json();

            suggestionsContainer.innerHTML = "";
            suggestionsContainer.classList.remove("hidden");

            // Exibir sugestões do banco de dados, caso existam
            tags.forEach(tag => {
                let tagElement = document.createElement("div");
                tagElement.textContent = tag;
                tagElement.classList.add("cursor-pointer", "p-2", "hover:bg-gray-200");
                tagElement.onclick = function () {
                    addTag(tag);
                    tagInput.value = "";
                    suggestionsContainer.classList.add("hidden");
                };
                suggestionsContainer.appendChild(tagElement);
            });

        } catch (error) {
            console.error("Erro ao buscar tags:", error);
        }
    });

    document.getElementById("addTagButton").addEventListener("click", function () {
        let input = tagInput.value.trim();
        if (input.length > 0) {
            addTag(input);
            tagInput.value = "";
            suggestionsContainer.classList.add("hidden");
        }
    });

    function addTag(tagName) {
        let existingTags = Array.from(selectedTagsContainer.children).map(tag =>
            tag.textContent.replace("x", "").trim()
        );

        if (existingTags.includes(tagName)) {
            return;
        }

        let tagElement = document.createElement("span");
        tagElement.textContent = tagName;
        tagElement.classList.add("bg-blue-500", "text-white", "px-3", "py-1", "rounded-full", "text-sm", "mr-2");

        let removeButton = document.createElement("button");
        removeButton.textContent = "x";
        removeButton.classList.add("ml-2", "text-xs", "text-white", "cursor-pointer");
        removeButton.onclick = function () {
            selectedTagsContainer.removeChild(tagElement);
            updateHiddenInput();
        };

        tagElement.appendChild(removeButton);
        selectedTagsContainer.appendChild(tagElement);

        updateHiddenInput();
    }

    function updateHiddenInput() {
        let selectedTags = Array.from(selectedTagsContainer.children).map(tag =>
            tag.textContent.replace("x", "").trim()
        );
        hiddenInput.value = selectedTags.join(",");
    }
});
