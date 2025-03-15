document.addEventListener("DOMContentLoaded", function () {
    const tagInput = document.getElementById("tagInput");
    const suggestionsContainer = document.getElementById("tagSuggestions");
    const selectedTagsContainer = document.getElementById("selectedTagsContainer");
    const hiddenInput = document.getElementById("selectedTags");

    // Posicionamento correto do dropdown
    tagInput.addEventListener("focus", () => {
        suggestionsContainer.classList.remove("hidden");
    });

    tagInput.addEventListener("blur", () => {
        setTimeout(() => {
            suggestionsContainer.classList.add("hidden");
        }, 200); // Pequeno delay para permitir o clique nas sugestões
    });

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
            tag.textContent.trim()
        );

        if (existingTags.length >= 4) {
            alert("Você pode adicionar no máximo 4 tags.");
            return;
        }

        if (existingTags.includes(tagName)) {
            return;
        }

        let tagElement = document.createElement("button"); // Alterado para um botão
        tagElement.textContent = tagName;
        tagElement.classList.add(
            "bg-blue-500", "text-white", "px-3", "py-1", "rounded-full",
            "text-sm", "mr-2", "w-1/4", "truncate", "text-center",
            "hover:bg-red-500", "transition-colors", "duration-200"
        );

        // Tornando a tag inteira clicável para remover
        tagElement.onclick = function () {
            selectedTagsContainer.removeChild(tagElement);
            updateHiddenInput();
        };

        selectedTagsContainer.appendChild(tagElement);
        updateHiddenInput();
    }

    function updateHiddenInput() {
        let selectedTags = Array.from(selectedTagsContainer.children).map(tag =>
            tag.textContent.trim()
        );
        hiddenInput.value = selectedTags.join(",");
    }
});
