'use strict';
	
angular.module("app").controller('SugestaoProgrideController', ['$location', 'toaster', 'SugestaoProgrideResource', 'sugestaoProgride', SugestaoProgrideController]);

function SugestaoProgrideController($location, toaster, SugestaoProgrideResource, sugestaoProgride) {
	
	var ctrl = this;
	if(sugestaoProgride){
		ctrl.sugestaoProgride = sugestaoProgride;	
	}
	
	ctrl.consultar = function () {
		SugestaoProgrideResource.query({mensagem: ctrl.mensagem, usuarioSugestao: ctrl.usuarioSugestao}, function(result) {				
			ctrl.sugestoesProgride = result;
	    })
    };
    
    ctrl.incluir = function () {
    	$location.path('/sugestaoProgride/0');
    };
    
    ctrl.editar = function (cliente) {
    	$location.path('/sugestaoProgride/'+cliente.id);
    };
    
    ctrl.salvar = function () {
    	SugestaoProgrideResource.save(ctrl.sugestaoProgride, function(data) {
    		toaster.pop('success', null, 'Sugestão Progride salvo com sucesso');
    		ctrl.sugestaoProgride.id = data.id;
    	});
    };
    
    ctrl.excluir = function () {
    	SugestaoProgrideResource.remove({id : ctrl.sugestaoProgride.id}, function(data) {
    		toaster.pop('success', null, 'Sugestão Progride excluída com sucesso');
    		ctrl.isExcluido = true;
    	});
    };
    
    ctrl.voltar = function () {
    	$location.path('/sugestaoProgride');
    };
}
