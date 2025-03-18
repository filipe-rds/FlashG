document.addEventListener("DOMContentLoaded", function () {
    // Configuração de eventos para comentários
    setupCommentEvents();

    // Configuração de eventos para envio de novos comentários
    setupCommentSubmission();
});

// Configuração de todos os eventos relacionados a comentários
function setupCommentEvents() {
    // Delegação de eventos para edição e exclusão (funcionará para elementos existentes e adicionados dinamicamente)
    document.addEventListener("click", function (event) {
        // Botão de excluir
        if (event.target.classList.contains("delete-btn") ||
            (event.target.closest(".delete-btn") && event.target.tagName === "IMG")) {
            const commentId = event.target.closest(".delete-btn").getAttribute("data-id");
            enableDelete(commentId);
        }

        // Botão de cancelar exclusão
        if (event.target.classList.contains("cancel-delete-btn")) {
            const commentId = event.target.getAttribute("data-id");
            cancelDelete(commentId);
        }

        // Botão de editar
        if (event.target.classList.contains("edit-btn") ||
            (event.target.closest(".edit-btn") && event.target.tagName === "IMG")) {
            const commentId = event.target.closest(".edit-btn").getAttribute("data-id");
            enableEdit(commentId);
        }

        // Botão de cancelar edição
        if (event.target.classList.contains("cancel-edit-btn")) {
            const commentId = event.target.getAttribute("data-id");
            cancelEdit(commentId);
        }
    });

    // Delegação de eventos para formulários
    document.addEventListener("submit", function (event) {
        // Formulário de exclusão
        if (event.target.classList.contains("delete-form")) {
            event.preventDefault();
            const commentId = event.target.getAttribute("data-id");
            deleteComment(commentId);
        }

        // Formulário de edição
        if (event.target.classList.contains("edit-form")) {
            event.preventDefault();
            const commentId = event.target.getAttribute("data-id");
            submitEdit(event, commentId);
        }
    });
}

// Configuração do envio de novos comentários
function setupCommentSubmission() {
    // Usando delegação de eventos para capturar todos os formulários, incluindo os adicionados dinamicamente
    document.addEventListener("submit", function(event) {
        if (event.target.id && event.target.id.startsWith("form-")) {
            event.preventDefault();

            const formId = event.target.id;
            const photoId = formId.split('-')[1];
            const form = event.target;

            // Cria um objeto com os dados do formulário
            const formData = new FormData(form);

            // Configuração da data no fuso horário de Brasília
            const currentDate = new Date();
            const timeZoneOffset = -180; // Brasília UTC-3
            const adjustedDate = new Date(currentDate.getTime() + (timeZoneOffset * 60 * 1000));
            const createdAt = adjustedDate.toISOString();

            // Prepara os dados para envio
            const data = {
                commentText: formData.get("commentText"),
                createdAt: createdAt,
                photographer: formData.get("photographer"),
                photo: formData.get("photo")
            };

            // Envia os dados usando Fetch API (mais moderna que jQuery)
            fetch('/FlashG/addComment', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(response => response.json())
                .then(responseData => {
                    atualizarHTMLPosComentario(responseData, photoId);
                })
                .catch(error => {
                    console.error('Erro ao enviar o comentário:', error);
                });
        }
    });
}

// Funções para gerenciar a exclusão de comentários
function enableDelete(commentId) {
    document.getElementById('deleteForm-' + commentId).classList.remove('hidden');
}

function cancelDelete(commentId) {
    document.getElementById('deleteForm-' + commentId).classList.add('hidden');
}

function deleteComment(commentId) {
    fetch('/FlashG/comment/delete', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ commentId })
    })
        .then(response => {
            if (response.ok) {
                // Remover o comentário do DOM
                const commentElement = document.getElementById('comment-' + commentId);
                if (commentElement) {
                    // Atualizar contador de comentários
                    const photoId = commentElement.closest('[id^="listComment-"]')?.id.split('-')[1];
                    if (photoId) {
                        const countElement = document.getElementById('sizeComment-' + photoId);
                        if (countElement) {
                            const currentCount = parseInt(countElement.textContent, 10);
                            countElement.textContent = Math.max(0, currentCount - 1);
                        }
                    }
                    commentElement.remove();
                }
            } else {
                console.error('Erro ao excluir comentário:', response.statusText);
            }
        })
        .catch(error => console.error('Erro ao excluir comentário:', error));
}

// Funções para gerenciar a edição de comentários
function enableEdit(commentId) {
    document.getElementById('commentText-' + commentId).classList.add('hidden');
    document.getElementById('editForm-' + commentId).classList.remove('hidden');
}

function cancelEdit(commentId) {
    document.getElementById('commentText-' + commentId).classList.remove('hidden');
    document.getElementById('editForm-' + commentId).classList.add('hidden');
}

function submitEdit(event, commentId) {
    const newCommentText = document.getElementById('editTextArea-' + commentId).value.trim();

    if (newCommentText === "") {
        deleteComment(commentId);
        return;
    }

    fetch('/FlashG/comment/edit', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ commentId, newCommentText })
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('commentText-' + commentId).innerText = data.updatedText;
            document.getElementById('commentText-' + commentId).classList.remove('hidden');
            document.getElementById('editForm-' + commentId).classList.add('hidden');
        })
        .catch(error => console.error('Erro ao atualizar comentário:', error));
}

// Função para atualizar o HTML após adicionar um novo comentário
function atualizarHTMLPosComentario(data, photoId) {
    // Atualizar contador de comentários
    const commentCountElement = document.getElementById('sizeComment-' + photoId);
    if (commentCountElement) {
        commentCountElement.textContent = data.numberOfComments;
    }

    // Limpar o textarea
    const textAreaElement = document.getElementById('textArea-' + photoId);
    if (textAreaElement) {
        textAreaElement.value = '';
    }

    // Criar o novo elemento de comentário
    const commentListElement = document.getElementById('listComment-' + photoId);
    if (commentListElement) {
        // Criar um elemento temporário para inserir o HTML
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = `
            <div id="comment-${data.id}" class="flex items-start gap-3 mb-4 max-w-[294px]">
                <img src="${data.imageUrl}" alt="perfil" class="h-8 w-8 rounded-full object-cover">
                <div class="w-full">
                    <div class="flex flex-row justify-between items-center">
                        <p class="text-sm font-medium text-slate-800">${data.photographerName}</p>
                        <p class="text-xs text-gray-500">${data.createdAt}</p>
                    </div>
                    <p id="commentText-${data.id}" class="text-sm text-slate-600 max-w-[248px] break-words">${escapeHTML(data.commentText)}</p>

                    <div class="flex gap-2 mt-2">
                        <button class="edit-btn text-blue-600 text-sm hover:underline" data-id="${data.id}">
                            <img src="/FlashG/assets/icons/pen.svg" alt="Editar" class="w-[18px] h-[18px]">
                        </button>
                        <button class="delete-btn text-red-600 text-sm hover:underline" data-id="${data.id}">
                            <img src="/FlashG/assets/icons/trash.svg" alt="Excluir" class="w-[18px] h-[18px]">
                        </button>
                    </div>

                    <!-- Formulário de edição (oculto por padrão) -->
                    <form id="editForm-${data.id}" class="edit-form hidden mt-2" data-id="${data.id}">
                        <textarea id="editTextArea-${data.id}" class="w-full p-2 rounded-md border text-sm" maxlength="512">${escapeHTML(data.commentText)}</textarea>
                        <div class="flex gap-2 mt-2">
                            <button type="submit" class="px-3 py-1 bg-green-600 text-white rounded-md hover:bg-green-800">Salvar</button>
                            <button type="button" class="cancel-edit-btn px-3 py-1 bg-gray-400 text-white rounded-md hover:bg-gray-600" data-id="${data.id}">Cancelar</button>
                        </div>
                    </form>

                    <!-- Formulário de exclusão (oculto por padrão) -->
                    <form id="deleteForm-${data.id}" class="delete-form hidden mt-2" data-id="${data.id}">
                        <div class="flex gap-2 mt-2">
                            <button type="submit" class="px-3 py-1 bg-red-600 text-white rounded-md hover:bg-red-800">Excluir</button>
                            <button type="button" class="cancel-delete-btn px-3 py-1 bg-gray-400 text-white rounded-md hover:bg-gray-600" data-id="${data.id}">Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
        `;

        // Adicionar o novo comentário à lista
        commentListElement.appendChild(tempDiv.firstElementChild);
    }
}

// Função auxiliar para escapar HTML (prevenção de XSS)
function escapeHTML(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}