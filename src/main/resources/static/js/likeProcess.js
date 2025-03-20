$("[id^='formLike-']").on("submit", function(event) {
    event.preventDefault();  // Evita o envio do formulário

    // Cria um objeto FormData a partir do formulário
    const formId = $(this).attr("id");  // Obtém o ID do formulário
    const idPart = formId.split('-')[1];
    //const formData = new FormData(this);  // 'this' refere-se ao formulário
    const formData = new FormData(this);  // 'this' refere-se ao formulário
    // Preenche o campo de data com a data e hora atual
    // Cria uma data atual
    //const currentDate = new Date();
    // Obtém o deslocamento de fuso horário do horário de Brasília (em minutos)
    //const timeZoneOffset = -180; // Brasília está a -3 horas UTC
    // Ajusta a data para o fuso horário de Brasília
    //const adjustedDate = new Date(currentDate.getTime() + (timeZoneOffset * 60 * 1000));
    // Converte a data ajustada para uma string ISO
    //const createdAt = adjustedDate.toISOString();
    //console.log(createdAt);

    //formData.set("createdAt", createdAt);  // Adiciona ou atualiza o campo createdAt

    // Obtém os dados do comentário
    //const photo = formData.get("commentText");
    //const commentDate = new Date().toLocaleString();  // Data do comentário
    const photographerId = formData.get("photographer");  // ID do fotógrafo
    const photoId = formData.get("photo");  // ID da foto

    // Prepara os dados para o envio via AJAX
    const data = {
        photographer: photographerId,  // Envia o ID do fotógrafo
        photo: photoId,  // Envia o ID da foto
    };


    $.ajax({
        url: '/FlashG/likeAction',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),


        success: function(response) {
            console.log('Comentário enviado com sucesso:', response);

            atualizarHTMLPosCurtida(response,idPart);


        },


        error: function(xhr, status, error) {
            console.log('Resposta do servidor:', xhr.responseText);
            console.log(error);
            console.error('Erro ao enviar o comentário:', error);
        }
    });


  });

  function atualizarHTMLPosCurtida(data, id) {

    const likeCountElement = $('#sizeLikes-' + id);
    likeCountElement.text('');
    likeCountElement.text(data.likeCount);

    const buttonLikeElement = $('#buttonLike-' + id );
    buttonLikeElement.text('');
    buttonLikeElement.text(data.response);
 
}




