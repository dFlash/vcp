angular.module('app-admin-services', ['ngResource'])
.service("companiesService", ['$resource', function($resource) {
	return {
		listCompanies : function (pageNum){
			var url = '/admin/companies?page=' + pageNum;
			return $resource(url).get();
		}
	}
}]);