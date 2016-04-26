'use strict';
app.controller('AgenteController', [ '$window', '$http', '$scope', '$route', '$rootScope', '$location', 'AgenteService', 'DataService', 'NAV_DATA', 'GameService',
function($window, $http, $scope, $route, $rootScope, $location, AgenteService, DataService, NAV_DATA, GameService ) {
	
	$scope.agente = {};
	
	
	$scope.new = function () {		
		$scope.agente = {};
	};
	
	$scope.save = function () {		
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
	
	$scope.startGame = function () {		
		GameService.addGame($scope.agente).then(
				function (data) {
					
				},
				function (error) {
					console.log(error);					
				}
			);
	};	
	
	
	$scope.refreshGrid = function () {	
         setTimeout(function () {
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
	
	$scope.load = function (id) {
		AgenteService.get(id).then(
				function (data) {
					$scope.agente = data;
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

        
        $scope.gridOptions = {
            data: 'myData',
            columnDefs: [
                         {
                        	 field: 'name', 
                        	 displayName: 'Agente', 
                        	 cellTemplate: '<div  ng-click="load(row.entity.id)" ng-bind="row.getProperty(col.field)"></div>',
                        	 width: "90%"
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