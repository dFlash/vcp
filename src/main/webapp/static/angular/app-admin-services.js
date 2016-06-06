angular.module('app-admin-services', ['ngResource'])
.service("adminService", ['$resource', function($resource) {
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
		updateCompany : function (putData, success, error){
			var url = '/admin/companies';
			var service = $resource(url, null, {
		        'update': { method:'PUT' }
		    });
			service.update({}, putData, success, error);
		},
		listAccounts : function (pageNum){
			var url = '/admin/accounts?page=' + pageNum;
			return $resource(url).get();
		},
		listAllCompanies : function (){
			var url = '/admin/companies/all';
			return $resource(url).get();
		},
		uploadAvatar : function (){
			return $resource('/admin/avatar', {}, {upload: {
				method: 'POST'
	        }});
		},
		addUser : function (){
			var url = '/admin/accounts/';
			return $resource(url, {}, {add: {
				method: 'POST',
	            headers: {'Content-Type': 'application/json'}
	        }});
		},
		updateUser : function (){
			var url = '/admin/accounts';
			return $resource(url, {}, {update: {
				method: 'PUT',
	            headers: {'Content-Type': 'application/json'}
	        }});
		},
		deleteUser : function (id, success, error){
			var url = '/admin/accounts/' + id;
			return $resource(url).remove(success, error);
		},
		statistics : function (pageNum){
			var url = '/admin/statistics?page=' + pageNum;
			return $resource(url).get();
		}
	}
}]);