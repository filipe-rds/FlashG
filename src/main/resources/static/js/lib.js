// Adicionamos o evento window.onload, que é disparado quando todos os recursos (HTML, CSS, imagens, scripts) estiverem carregados
window.onload = function () {
    // Lista de URLs dos scripts JavaScript
    const jsLibraries = [
        'https://unpkg.com/@material-tailwind/html@latest/scripts/dialog.js',
        'https://unpkg.com/@material-tailwind/html@latest/scripts/dialog.js',
        // Adicione mais URLs conforme necessário
    ];

    // Função para adicionar os scripts JavaScript ao documento
    function addScriptInOrder(url) {
        // Retorna uma Promise que é resolvida quando o script for carregado com sucesso, ou rejeitada se ocorrer um erro
        return new Promise((resolve, reject) => {
            const script = document.createElement('script');  // Cria um elemento <script> dinamicamente
            script.src = url;  // Define a URL do script que será carregado
            script.defer = true;  // O script será carregado de forma assíncrona
            script.onload = () => resolve(`Script carregado: ${url}`);  // Se o script for carregado com sucesso, resolve a Promise
            script.onerror = () => reject(`Erro ao carregar o script: ${url}`);  // Se ocorrer um erro ao carregar, rejeita a Promise
            document.body.appendChild(script);  // Adiciona o script no final do <body> da página
        });
    }

    // Usamos reduce para encadear as Promises de forma sequencial, garantindo que os scripts sejam carregados um por vez
    jsLibraries
        .reduce(
            (prevPromise, currentUrl) =>
                prevPromise.then(() => addScriptInOrder(currentUrl)),  // A Promise anterior deve ser resolvida antes de carregar o próximo script
            Promise.resolve() // Promise inicial resolvida, indicando que nada está bloqueando o processo
        )
        .then(() => {
            console.log('Todos os scripts foram carregados com sucesso!');  // Quando todos os scripts forem carregados, exibimos uma mensagem no console
        })
        .catch((error) => {
            console.error('Erro ao carregar os scripts:', error);  // Se ocorrer um erro ao carregar qualquer script, exibimos o erro no console
        });
};

