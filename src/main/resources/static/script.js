angular.module("projetoTecnico", [ 'ui.router']);
angular.module("projetoTecnico").config(function($stateProvider, $urlRouterProvider, $locationProvider) {
    $urlRouterProvider.otherwise('/');
    $stateProvider
        .state('home', {
            url: '/clientes',
            templateUrl: 'paginas/cadastroClientes.html',
            controller: 'cadastroController'
        })
        .state('login', {
            url: '/login',
            templateUrl: 'paginas/login.html',
            controller: 'loginController'
        });

});
angular.module("projetoTecnico").controller("cadastroController",['$window', '$scope','ClienteService', function($window, $scope, ClienteService){
    $scope.title = "Cadastro de Clientes";
    $scope.cliente={telefones:[{}], enderecos:[] };
    $scope.clientes= [];
    $scope.endereco= {};

    $scope.isClienteSelecionado = function(clientes){
        return clientes.some(function (cliente){
            return cliente.selecionado;
        });
    };
    $scope.addCliente = function () {
        if ($scope.cliente != null && $scope.cliente.nome) {
            $scope.cliente.telefones[0].principal = true;
            ClienteService.addCliente($scope.cliente)
                .then (function success(response){
                        $scope.listaClientes();
                        $scope.message = 'User added!';
                        $scope.errorMessage = '';
                        delete $scope.cliente;
                    },
                    function error(response){
                        $window.alert(response.data.message);
                        $scope.errorMessage = 'Error adding user!';
                        $scope.message = '';
                    })
        }
        else {
            $scope.errorMessage = 'Please enter a name!';
            $scope.message = '';
        }

    }

    $scope.updateUser = function () {
        ClienteService.updateUser($scope.cliente.id,
            $scope.cliente.name, $scope.cliente.cpfCnpj)
            .then(function success(response) {
                    $scope.message = 'Cliente atualizado!';
                    $scope.errorMessage = '';
                },
                function error(response) {
                    $scope.errorMessage = 'Erro ao atualizar cadastro do cliente!';
                    $scope.message = '';
                });
    }

    $scope.deleteCliente = function (clientes) {
        var selecionados = clientes.filter(function(cliente){
            return cliente.selecionado;
        });
        selecionados.forEach(function(data) {

            ClienteService.deleteCliente(data)
                .then (function success(response) {
                        $scope.message = 'Cliente Deletado!';
                        $scope.User = null;
                        $scope.errorMessage='';
                        $scope.listaClientes();
                    },
                    function error(response) {
                        $scope.errorMessage = 'Erro ao deletar!';
                        $scope.message='';
                    });
        });
    }
    $scope.listaClientes = function (){
        ClienteService.listaClientes()
            .then(function success(response) {
                    $scope.clientes = response.data;
                    $scope.message='';
                    $scope.errorMessage = '';
                },
                function error (response) {
                    $scope.message='';
                    $scope.errorMessage = 'Erro ao listar os clientes!';
                });
    }
    $scope.addEndereco = function (endereco){

        $scope.cliente.enderecos.push(endereco);
        $scope.endereco={};
    }

    $scope.getTelefone = function (cliente) {
        if (!cliente.telefone || cliente.telefone.length <= 0) {
            return
        }
        var telefonePrincipal = cliente.telefone.filter(function (data) {
            return data.principal;
        });

        if (telefonePrincipal && telefonePrincipal.length>0) {
            return telefonePrincipal[0].telefone;
        }
    }
    $scope.getEndereco = function (cliente){
        if (!cliente.endereco || cliente.endereco.length <= 0) {
            return
        }
        var enderecoPrincipal = cliente.endereco.filter(function (data) {
            return data.principal;
        });

        if (enderecoPrincipal && enderecoPrincipal.length>0) {
            return (enderecoPrincipal[0].logradouro + ", " + enderecoPrincipal[0].numero + ", " +
                    enderecoPrincipal[0].bairro + ", "+ enderecoPrincipal[0].cidade + "-" + enderecoPrincipal[0].uf);
        }
    }
    $scope.listaClientes();

}]);

angular.module("projetoTecnico").service('ClienteService', [ '$http', function($http) {
    this.addCliente = function addCliente(cliente) {
        return $http({
            method : 'POST',
            url : '/clientes',
            data : {
                nome : cliente.nome,
                cpfCnpj: cliente.cpfCnpj,
                telefone: cliente.telefones,
                endereco: cliente.enderecos,
            }
        });
    }
    this.listaClientes = function listaClientes() {
        return $http({
            method : 'GET',
            url : '/clientes'
        });
    }
    this.deleteCliente = function deleteCliente(cliente) {
        return $http({
            method : 'DELETE',
            url : '/clientes/ ' + cliente.id
        })
    }
} ]);



