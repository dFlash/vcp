angular.module('app-admin-controllers', ['ngRoute'])
.config(function($routeProvider){
    $routeProvider.when('/admin/companies', {
        templateUrl: 'static/html/companies.html', 
        controller:'companiesController' 
    });
})
.controller('companiesController', ['$scope', 'companiesService', '$location', '$route', function($scope, companiesService, $location, $route){
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
	
	$scope.addCompany = function () {
		companiesService.addCompany($scope.currentCompany, function() {
			alert('Company was added');
			$route.reload();
		}, function() {
			alert('Company was not added');
		})
	};
	
	$scope.saveCompany = function() {
		companiesService.updateCompany($scope.currentCompany.id, $scope.currentCompany, function() {
			alert('Company was updated');
			$route.reload();
		}, function() {
			alert('Company was not updated');
		})
	};
	
	$scope.deleteCompany = function(id){
		companiesService.deleteCompany(id, function() {
			alert('Company was deleted');
			$route.reload();
		}, function() {
			alert('Company was not deleted');
		})
	};
}
]);
