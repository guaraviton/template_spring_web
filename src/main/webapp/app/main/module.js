'use strict';

angular.module('app', ['ngResource', 'ui.router', 'oc.lazyLoad', 'fend.progressbar.loading', 'ngSanitize', 'angularFileUpload', 'datatables', 'ui.bootstrap', 'angular-jquery-mask', 'toaster', 'ngBusy', 'mwl.confirm']);

angular.module('app').config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
	$ocLazyLoadProvider.config({
		debug: true,
		jsLoader: requirejs
	});
}]);

angular.module('app').run(['DTDefaultOptions',function(DTDefaultOptions) {
    DTDefaultOptions.setLanguageSource('components/angular-datatables/Portuguese-Brasil.json');
    DTDefaultOptions.setDisplayLength(10);
    DTDefaultOptions.setOption('info', true);
    DTDefaultOptions.setOption('lengthChange',false);
}]);

angular.module('app').run(['uibDatepickerPopupConfig', function(datepickerPopupConfig) {
    datepickerPopupConfig.appendToBody = true;
    datepickerPopupConfig.closeText = "Fechar";
    datepickerPopupConfig.currentText = "Hoje";
    datepickerPopupConfig.clearText = "Limpar";	
    datepickerPopupConfig.showButtonBar = false;
}]);

angular.module('app').run(['uibDatepickerConfig', function(uibDatepickerConfig) {
	uibDatepickerConfig.showWeeks = false;
	uibDatepickerConfig.startingDay = 0;
}]);