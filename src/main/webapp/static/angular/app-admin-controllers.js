angular.module('app-admin-controllers', ['ngRoute', 'ngFileUpload'])
.config(function($routeProvider){
    $routeProvider.when('/admin/companies', {
        templateUrl: 'static/html/companies.html', 
        controller:'companiesController' 
    });
    $routeProvider.when('/admin/accounts', {
    	templateUrl: 'static/html/accounts.html',
        controller:'accountsController' 
    });
    $routeProvider.when('/admin/statistics', {
    	templateUrl: 'static/html/statistics.html',
        controller:'statisticsController' 
    });
})
.controller('companiesController', ['$scope', 'adminService', '$location', '$route', function($scope, adminService, $location, $route){
	if ($location.search().page == null) {
		$scope.currentPage = 0;
	}
	else {
		$scope.currentPage = $location.search().page;
	}
	$scope.path = $location.path() + "?";
	$scope.companiesPage = adminService.listCompanies($scope.currentPage);
	
	$scope.reset = function() {
		$scope.isNewCompany = true;
		$scope.currentCompany = {};
	};
	$scope.reset();

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
	
	$scope.addCompany = function () {
		adminService.addCompany($scope.currentCompany, function() {
			alert('Company was added');
			$route.reload();
		}, function() {
			alert('Company was not added');
		})
	};
	
	$scope.saveCompany = function() {
		adminService.updateCompany($scope.currentCompany, function() {
			alert('Company was updated');
			$route.reload();
		}, function() {
			alert('Company was not updated');
		})
	};
	
	$scope.deleteCompany = function(id){
		if (confirm('Are you sure you want to delete company')){
			adminService.deleteCompany(id, function() {
				alert('Company was deleted');
				$route.reload();
			}, function() {
			alert('Company was not deleted');
			})
		}
	};
}
])
.controller('accountsController', ['$scope', 'adminService', '$location', '$route', 'DefaultAvatar',
                                   function($scope, adminService, $location, $route, DefaultAvatar){
	if ($location.search().page == null) {
		$scope.currentPage = 0;
	}
	else {
		$scope.currentPage = $location.search().page;
	}
	$scope.path = $location.path() + "?";
	$scope.accountsPage = adminService.listAccounts($scope.currentPage);
	$scope.companies = adminService.listAllCompanies();
	
	$scope.roles = ['User', 'Admin'];
	
	$scope.reset = function() {
		$scope.isNewUser = true;
		$scope.currentUser = {};
	};
	$scope.reset();
	
	$scope.editUser = function(id) {
		for (i=0; i < $scope.accountsPage.content.length; i++)
		{
			if ($scope.accountsPage.content[i].id == id)
			{
				$scope.currentUser = $scope.accountsPage.content[i];
				$scope.isNewUser = false;
				break;
			}
		}
		$scope.repeatPassword = '';
	};
	
	$scope.uploadAvatar = function() {
		if ($scope.currentUser.email){
			var postData = {email: $scope.currentUser.email};
			var service = adminService.uploadAvatar();
			service.upload({}, postData, 
					function(response) {
						$scope.currentUser.avatar = response.avatarUrl;
					},
					function(response) {
						alert("Upload avatar error");
					}
			);
		}
	};
	
	$scope.addUser = function () {
		var service = adminService.addUser();
		service.add({}, $scope.currentUser, 
	    		function(response) {
			alert("User added successfully");
			$route.reload();
		},
		function() {
			alert("User was not added");
		}
	);
	};
	
	$scope.saveUser = function() {
		var service = adminService.updateUser();
		service.update({}, $scope.currentUser, 
		function(response) {
			alert("User saved successfully");
			$route.reload();
		},
		function() {
			alert("User was not saved");
		})
	};
	
	$scope.deleteUser = function(id){
		if (confirm('Are you sure you want to delete company')){
			adminService.deleteUser(id, function() {
				alert("Deleted successfully");
				$route.reload();
			},
			function() {
				alert("User was not deleted");
			})
		}
	}
}])
.controller("statisticsController", ['$scope', 'adminService', 
                                     function($scope, adminService){
	$scope.statistics = adminService.statistics();
}]);
