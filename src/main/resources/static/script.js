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
angular.module("projetoTecnico").controller("cadastroController",['$window', '$scope','UserCRUDService', function($window, $scope, UserCRUDService){
    $scope.title = "Cadastro de Clientes";
    $scope.cliente={telefones:[{}], enderecos:[] };
    $scope.clientes= [];
    $scope.endereco= {};

    $scope.apagarClientes = function (clientes){
        $scope.cliente = clientes.filter(function(cliente){
            if (!cliente.selecionado) return cliente;
        });
    };
    $scope.isClienteSelecionado = function(clientes){
        return clientes.some(function (cliente){
            return cliente.selecionado;
        });
    };
    $scope.addCliente = function () {
        if ($scope.cliente != null && $scope.cliente.nome) {
            $scope.cliente.telefones[0].principal = true;
            UserCRUDService.addCliente($scope.cliente)
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
        UserCRUDService.updateUser($scope.cliente.id,
            $scope.cliente.name, $scope.cliente.cpfCnpj)
            .then(function success(response) {
                    $scope.message = 'User data updated!';
                    $scope.errorMessage = '';
                },
                function error(response) {
                    $scope.errorMessage = 'Error updating user!';
                    $scope.message = '';
                });
    }

    $scope.deleteCliente = function (clientes) {
        var selecionados = clientes.filter(function(cliente){
            return cliente.selecionado;
        });
        selecionados.forEach(function(data) {

            UserCRUDService.deleteCliente(data)
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
        UserCRUDService.listaClientes()
            .then(function success(response) {
                    $scope.clientes = response.data;
                    $scope.message='';
                    $scope.errorMessage = '';
                },
                function error (response) {
                    $scope.message='';
                    $scope.errorMessage = 'Error getting users!';
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

angular.module("projetoTecnico").controller("loginController", function ($scope){
    $scope.message = "funcionou !!!!"
});

angular.module("projetoTecnico").service('UserCRUDService', [ '$http', function($http) {
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

    this.loginUsuario = function loginUsuario(usuario) {
        return $http({
            method : 'POST',
            url : '/oauth/token',

        })
    }
} ]);

angular.module("projetoTecnico").controller('loginController', ['$scope','UserCRUDService',
    function ($scope,UserCRUDService) {
        $scope.getUser = function () {
            UserCRUDService.getUser($scope.user.id)
                .then(function success(response) {
                        $scope.clientes = response.data;
                        $scope.clientes.id = id;
                        $scope.message='';
                        $scope.errorMessage = '';
                    },
                    function error (response) {
                        $scope.message = '';
                        if (response.status === 404){
                            $scope.errorMessage = 'User not found!';
                        }
                        else {
                            $scope.errorMessage = "Error getting user!";
                        }
                    });
        };
    }]);



