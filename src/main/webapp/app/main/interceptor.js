'use strict';

angular.module('app')
    .config(['$provide', '$httpProvider', function($provide, $httpProvider) {

        $provide.factory('interceptor',

            ['$q', '$rootScope', '$window', 'toaster', '$injector', '$location',

                function($q, $rootScope, $window, toaster, $injector, $location) {

                    function popErrorMessage(rejection) {
                    	if (rejection.data && rejection.data.fieldErrors) {
                            for (var i = 0; i < rejection.data.fieldErrors.length; i++) {
                                var error = rejection.data.fieldErrors[i];
                                toaster.pop('error', null, error.message);
                            }
                        } else {
                        	toaster.pop('error', 'Erro ao realizar a sua requisição', 'Favor contactar o administrador do sistema.');
                        }
                    }

                    return {                        
                        request: function(config) {     
                            return config || $q.when(config);
                        },
                        requestError: function(rejection) {     
                            return $q.reject(rejection);
                        },
                        response: function(response) {                                   
                            if (response.data.mensagemRetorno){
                                toaster.pop(response.data.mensagemRetorno.tipo, response.data.mensagemRetorno.titulo,response.data.mensagemRetorno.mensagem);   
                            }
                            
                            return response || $q.when(response);
                        },
                        responseError: function(rejection) {         
                            switch (rejection.status) {
                                default:
                                    popErrorMessage(rejection);
                            }
                            return $q.reject(rejection);
                        }
                    };
                }
            ]);

        $httpProvider.interceptors.push('interceptor');
    }]);