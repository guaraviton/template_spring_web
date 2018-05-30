'use strict';

require.config({
  paths: {
	  'jQuery' : 'components/jquery/jquery-2.2.4.min',
	  'jQueryMaskPlugin' : 'components/jQuery-Mask-Plugin/jquery.mask.min',
      'angular' : 'components/angular/angular.min',
      'angularResource' : 'components/angular/angular-resource.min',
      'angularUiRouter' : 'components/angular-ui-router/angular-ui-router.min',
      'angularJqueryMask' : 'components/angular-jquery-mask/angular-jquery-mask',
      'angular-sanitize' : 'components/angular/angular-sanitize.min',
      'ngProgress': 'components/ngProgress/js/ngprogress.min',
      'domReady': 'components/domReady/domReady',
      'bootStrap' : 'components/bootstrap/js/bootstrap.min',
      'angularUiBootstrap' : 'components/angular-ui-bootstrap/js/ui-bootstrap-2.5.0.min',
      'ocLazyLoad' : 'components/ocLazyLoad/ocLazyLoad.min',
      'ngFileUpload' : 'components/ng-file-upload/angular-file-upload.min',
      'dataTables' : 'components/dataTables/js/datatables.min',
      'angularDataTables' : 'components/angular-datatables/js/angular-datatables.min',
      'toaster' : 'components/toaster/js/toaster.min',
      'ngBusy' : 'components/ng-busy/js/angular-busy.min',
      'angularBootstrapConfirm' : 'components/angular-bootstrap-confirm/angular-bootstrap-confirm.min',
      'appNgProgress' : 'components/appNgProgress/module'
  },
  shim: {
	  jQueryMaskPlugin: {
          deps: ['jQuery']
      },
      angular: {
    	  deps: ['jQuery'],
    	  exports: 'angular'
      },
      angularResource: {
          deps: ['angular']          
      },
      angularUiRouter: {
          deps: ['angular']
      },
      angularJqueryMask: {
          deps: ['angular']
      },
      'angular-sanitize': {
          deps: ['angular']
      },
      ngFileUpload: {
          deps: ['angular']          
      },
      ngProgress: {
          deps: ['angular']          
      },
      domReady: {
          deps: ['angular']          
      },
      bootStrap: {
    	  deps: ['jQuery']          
      },
      angularUiBootstrap: {
    	  deps: ['angular']     
      },
      ocLazyLoad: {
    	  deps: ['angular']          
      },
      toaster: {
    	  deps: ['angular']          
      },
      ngBusy: {
    	  deps: ['angular']          
      },
      angularBootstrapConfirm: {
    	  deps: ['angular', 'bootStrap']
      },
      appNgProgress: {
    	  deps: ['ngProgress']                    
      },
      dataTables: {
    	  deps: ['jQuery']                    
      },
      angularDataTables: {
    	  deps: ['angular', 'dataTables']                    
      },
      'app/main/module': {
    	  deps: ['jQueryMaskPlugin', 'angularResource', 'angularUiRouter', 'angularJqueryMask', 'ngFileUpload', 'domReady', 'angularUiBootstrap', 'ocLazyLoad', 'appNgProgress', 'angularDataTables', 'toaster', 'ngBusy', 'angularBootstrapConfirm']          
      },
      'app/main/interceptor': {
    	  deps: ['app/main/module']          
      },
      'app/main/resources': {
    	  deps: ['app/main/module']          
      },
      'app/main/services': {
    	  deps: ['app/main/module']          
      },
      'app/main/rotas': {
    	  deps: ['app/main/module']          
      },
      'app/main/mainController': {
    	  deps: ['app/main/services', 'app/main/resources']          
      },
      'app/main/start': {
    	  deps: ['app/main/rotas', 'app/main/mainController', 'app/main/interceptor']          
      }
  }
  //baseUrl: 'js',
  //,urlArgs: 'v=' +  (new Date()).getTime()
});

require(['domReady!'], function () {
	console.info('start');
	require(['app/main/start']);
});