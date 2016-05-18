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
		listBySearchQuery : function(pageNum, query) {
			return $resource('/videos/search?query=:queryParam&page=:pageParam',{queryParam:query, pageParam:pageNum}).get();
		}
	}
}])
.service("loginService", ['$http', '$resource', function($http, $resource) {
	return {
		login : function(config, success) {
			$http.post("/login", null, config).success(success);
		},
		logout : function(success) {
			$http.post("/logout").success(success);
		},
		sendMail : function(postData, success, error) {
			var url = '/send-mail';
			$resource(url).save({},postData, success, error);
		}
	}
	
}])
.service("changePasswordService", ['$resource', function($resource) {
	return {
		sendNewPassword : function (postData, success, error){
			var url = '/change-password';
			$resource(url).save({},postData, success, error);
		}
	}
}]);