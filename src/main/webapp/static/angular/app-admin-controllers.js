angular.module('app-admin-controllers', ['ngRoute'])
.config(function($routeProvider){
    $routeProvider.when('/admin/companies', {
        templateUrl: 'static/html/companies.html', 
        controller:'companiesController' 
    });
})
.controller('companiesController', ['$scope', 'companiesService', '$location', function($scope, companiesService, $location){
	if ($location.search().page == null) {
		$scope.currentPage = 0;
	}
	else {
		$scope.currentPage = $location.search().page;
	}
	$scope.path = $location.path() + "?";
	$scope.companiesPage = companiesService.listCompanies($scope.currentPage);
	
	//
	$scope.isNewCompany = true;
	$scope.currentCompany = {};
	
	$scope.currentCompany.name = '';
	$scope.currentCompany.address = '';
	$scope.currentCompany.contactEmail = '';
	$scope.currentCompany.phone = '';
	
	$scope.editCompany = function(id) {
		for (i=0; i < $scope.companiesPage.content.length; i++)
		{
			if ($scope.companiesPage.content[i].id == id)
			{
				$scope.currentCompany = $scope.companiesPage.content[i];
				$scope.isNewCompany = false;
				break;
			}
		}
	};
	$scope.reset = function() {
		//
		$scope.isNewCompany = true;
		$scope.currentCompany = {};
		
		$scope.currentCompany.name = '';
		$scope.currentCompany.address = '';
		$scope.currentCompany.contactEmail = '';
		$scope.currentCompany.phone = '';
	};
	
	$scope.addUser = function () {
		console.log('add')
	};
	
	$scope.saveUser = function() {
		console.log('save')
	};
	
	$scope.deleteUser = function(id){
		console.log('delete')
	};
}
]);
