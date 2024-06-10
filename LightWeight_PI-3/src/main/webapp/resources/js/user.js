document.addEventListener('DOMContentLoaded', function() {

    document.getElementById('button_user_4').addEventListener('click', function() {
        loadProduct();
    });

    function loadProduct() {
        fetch('../funcionario/produto.xhtml')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.text();
            })
            .then(data => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(data, 'application/xhtml+xml');
                const loginContent = doc.getElementById('manifest_user');

                if (loginContent) {
                    document.getElementById('spawn').innerHTML = '';
                    document.getElementById('spawn').appendChild(loginContent);
                    attachCloseEvent();
                    attachCreateProductEvent();
                } else {
                    console.error('Failed to find login content in the fetched XHTML.');
                }
            })
            .catch(error => console.error('There was a problem with the fetch operation:', error));
    }

    function loadCreatProduct() {
        fetch('../funcionario/criarProduto.xhtml')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.text();
            })
            .then(data => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(data, 'application/xhtml+xml');
                const createContent = doc.getElementById('container_product_creator');
    
                if (createContent) {
                    document.getElementById('spawn').innerHTML = '';
                    document.getElementById('spawn').appendChild(createContent);
                    attachCloseEvent();
                } else {
                    console.error('Failed to find create content in the fetched XHTML.');
                }
            })
            .catch(error => console.error('There was a problem with the fetch operation:', error));
    }

    function attachCreateProductEvent() {
        const createBtn = document.getElementById('button_creat_product');
        if (createBtn) {
            createBtn.addEventListener('click', function() {
                loadCreatProduct();
            });
        }
    }

    function attachCloseEvent() {
        const closeBtn = document.getElementById('close');
        if (closeBtn) {
            closeBtn.addEventListener('click', function() {
                document.getElementById('spawn').innerHTML = '';
            });
        }
    }
});