'use strict';
app.controller('AgenteController', [ '$window', '$http', '$scope', '$route', '$rootScope', '$location', 'AgenteService', 'DataService', 'NAV_DATA', 'GameService',
function($window, $http, $scope, $route, $rootScope, $location, AgenteService, DataService, NAV_DATA, GameService ) {
	
	$scope.agente = {};

    var modal = document.getElementById('myModal');

    //editor actionBefore
    var editorActionBefore = ace.edit("editorActionBefore");
    editorActionBefore.setTheme("ace/theme/eclipse");
    editorActionBefore.session.setMode("ace/mode/java");     
    editorActionBefore.$blockScrolling = Infinity;

    var editorActionAfter = ace.edit("editorActionAfter");
    editorActionAfter.setTheme("ace/theme/eclipse");
    editorActionAfter.session.setMode("ace/mode/java");     
    editorActionAfter.$blockScrolling = Infinity;

    var editorRequestHelp = ace.edit("editorRequestHelp");
    editorRequestHelp.setTheme("ace/theme/eclipse");
    editorRequestHelp.session.setMode("ace/mode/java");     
    editorRequestHelp.$blockScrolling = Infinity;

    var editorResponseHelp = ace.edit("editorResponseHelp");
    editorResponseHelp.setTheme("ace/theme/eclipse");
    editorResponseHelp.session.setMode("ace/mode/java");     
    editorResponseHelp.$blockScrolling = Infinity;


    

    




	
	
	$scope.new = function () {	
        modal.style.display = "block";	
		$scope.agente = {};
	};

    $scope.close = function () {  
        modal.style.display = "none";  
    };
	
	$scope.save = function () {	
        $scope.agente.actionBefore = editorActionBefore.getValue();	
        $scope.agente.actionAfter = editorActionAfter.getValue(); 
        $scope.agente.requestHelp = editorRequestHelp.getValue(); 
        $scope.agente.responseHelp = editorResponseHelp.getValue(); 
        

                
		AgenteService.save($scope.agente).then(
			function (data) {
				$scope.new();
				$scope.refreshGrid();
			},
			function (error) {
				console.log(error);					
			}
		);
	};	

    $scope.load = function (id) {
        modal.style.display = "block";
        AgenteService.get(id).then(
                function (data) {
                    $scope.agente = data;
                    editorActionBefore.setValue($scope.agente.actionBefore);
                    editorActionBefore.gotoLine(0);                    

                    editorActionAfter.setValue($scope.agente.actionAfter);
                    editorActionAfter.gotoLine(0);

                    editorRequestHelp.setValue($scope.agente.requestHelp);
                    editorRequestHelp.gotoLine(0);

                    editorResponseHelp.setValue($scope.agente.responseHelp);
                    editorResponseHelp.gotoLine(0);                    

                },
                function (error) {
                    console.log(error);                 
                }
            );
    };  
	
	
	$scope.refreshGrid = function () {	
         setTimeout(function () {
            modal.style.display = "none";
        	 $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
         }, 1000);
	}
	
	$scope.delete = function () {	
		AgenteService.delete($scope.agente.id).then(
				function (data) {
					$scope.refreshGrid();
				},
				function (error) {
					console.log(error);					
				}
			);			
	};	
	

	
	$scope.changeTab = function(tab) {
	    $scope.view_tab = tab;
	}
	
	if (!$scope.view_tab){
		$scope.changeTab('tab1');
	}
	
    $scope.filterOptions = {
            filterText: "",
            useExternalFilter: true
        }; 
        $scope.totalServerItems = 0;
        $scope.pagingOptions = {
            pageSizes: [5,10],
            pageSize: 5,
            currentPage: 1
        };	
        $scope.setPagingData = function(data, page, pageSize){	
            var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
            $scope.myData = pagedData;
            $scope.totalServerItems = data.length;
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        };
        $scope.getPagedDataAsync = function (pageSize, page, searchText) {
            setTimeout(function () {
                var data;
                if (searchText) {
                    var ft = searchText.toLowerCase();
                    $http.get('/api/agentes').success(function (largeLoad) {		
                        data = largeLoad.filter(function(item) {
                            return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                        });
                        $scope.setPagingData(data,page,pageSize);
                    });            
                } else {
                    $http(
                    		{
                    			method: 'GET',
                    			url: '/api/agentes'
                    		}
                    ).success(function (largeLoad) {
                        $scope.setPagingData(largeLoad,page,pageSize);
                    });
                }
            }, 100);
        };
    	
        $scope.refreshGrid();
    	
        $scope.$watch('pagingOptions', function (newVal, oldVal) {
            if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
              $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
            }
        }, true);
        $scope.$watch('filterOptions', function (newVal, oldVal) {
            if (newVal !== oldVal) {
              $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
            }
        }, true);

         $scope.labelBoolean = function(value){
            if (value){
                return 'sim';
            }
            return 'não';

         }


        
        $scope.gridOptions = {
            data: 'myData',
            columnDefs: [
                         {
                        	 field: 'name', 
                        	 displayName: 'Agente', 
                        	 cellTemplate: '<div  ng-click="load(row.entity.id)" ng-bind="row.getProperty(col.field) "></div>',
                        	 width: "80%"
                        },
                        {
                            field: 'enabled', 
                            displayName: 'Ativo', 
                            cellTemplate: '<div  ng-click="load(row.entity.id)" ng-bind="labelBoolean(row.getProperty(col.field))"></div>',
                            width: "10%"
                        },
     	                {
                        	field: 'clones', 
                        	displayName: 'Clones', 
                        	cellTemplate: '<div  ng-click="load(row.entity.id)" ng-bind="row.getProperty(col.field)"></div>',
                        	width: "10%"
                        }
                        ],	                
            enablePaging: true,
    		showFooter: true,
    		enableColumnResize: true,
    		showFilter: true,
        
            totalServerItems: 'totalServerItems',
            pagingOptions: $scope.pagingOptions,
            filterOptions: $scope.filterOptions,
            multiSelect: false
        };
	
}]);