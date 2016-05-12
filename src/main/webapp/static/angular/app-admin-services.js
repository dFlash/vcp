angular.module('app-admin-services', ['ngResource'])
.service("companiesService", ['$resource', function($resource) {
	return {
		listCompanies : function (pageNum){
			var url = '/admin/companies?page=' + pageNum;
			return $resource(url).get();
		},
		addCompany : function (postData, success, error){
			var url = '/admin/companies';
			$resource(url).save({},postData, success, error);
		},
		deleteCompany : function (id, success, error){
			var url = '/admin/companies/' + id;
			return $resource(url).remove(success, error);
		},
		updateCompany : function (id, putData, success, error){
			var url = '/admin/companies/' + id;
			var service = $resource(url, null, {
		        'update': { method:'PUT' }
		    });
			service.update({}, putData, success, error);
		}
	}
}]);