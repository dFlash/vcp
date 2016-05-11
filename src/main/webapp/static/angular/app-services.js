angular.module('app-services', ['ngResource'])
.service("videoService", ['$resource', function($resource) {
	return {
		listAll : function (pageNum){
			var url = '/video/all?page=' + pageNum;
			return $resource(url).get();
		},
		getVideo : function (videoId){
			var url = '/video/'+videoId;
			return $resource(url).get();
		},
		uploadVideo : function (){
			return $resource('/my-account/videos', {}, {upload: {
				method: 'POST',
	            transformRequest: angular.identity,
	            headers: {'Content-Type': undefined}
	        }});
		},
		updateVideo : function (id){
			var url = '/my-account/videos/' + id;
			return $resource(url, {}, {update: {
				method: 'PUT',
	            headers: {'Content-Type': 'application/json'}
	        }});
		},
		uploadThumbnail : function (){
			return $resource('/my-account/thumbnail', {}, {upload: {
				method: 'POST',
	            transformRequest: angular.identity,
	            headers: {'Content-Type': undefined}
	        }});
		},
		deleteVideo : function (id, success, error){
			var url = '/my-account/videos/' + id;
			return $resource(url).remove(success, error);
		},
		userListAll : function (pageNum){
			var url = '/my-account/videos?page=' + pageNum;
			return $resource(url).get();
		},
		listBySearchQuery : function(pageNum, query) {
			return $resource('/videos/search?query=:queryParam&page=:pageParam',{queryParam:query, pageParam:pageNum}).get();
		}
	}
}])
.service("loginService", ['$http', function($http) {
	return {
		login : function(config, success) {
			$http.post("/login", null, config).success(success);
		},
		logout : function(success) {
			$http.post("/logout").success(success);
		}
	}
	
}])
.service("adminService", ['$resource', '$http', function($resource, $http) {
	return {
		listAccounts : function (pageNum){
			var url = '/admin/accounts?page=' + pageNum;
			return $resource(url).get();
		},
		listCompanies : function (){
			var url = '/admin/companies/all';
			return $resource(url).get();
		},
		uploadAvatar : function (){
			return $resource('/admin/avatar', {}, {upload: {
				method: 'POST',
	            transformRequest: angular.identity,
	            headers: {'Content-Type': undefined}
	        }});
		},
		addUser : function (){
			var url = '/admin/accounts/';
			return $resource(url, {}, {add: {
				method: 'POST',
	            headers: {'Content-Type': 'application/json'}
	        }});
		},
		updateUser : function (id){
			var url = '/admin/accounts/' + id;
			return $resource(url, {}, {update: {
				method: 'PUT',
	            headers: {'Content-Type': 'application/json'}
	        }});
		},
		deleteUser : function (id, success, error){
			var url = '/admin/accounts/' + id;
			return $resource(url).remove(success, error);
		}
	}
	
}]);