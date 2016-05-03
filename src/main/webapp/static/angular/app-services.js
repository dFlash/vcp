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
			return $resource('/my-account/upload', {}, {upload: {
				method: 'POST',
	            transformRequest: angular.identity,
	            headers: {'Content-Type': undefined}
	        }});
		},
		userListAll : function (pageNum){
			var url = '/my-account/user-video/all?page=' + pageNum;
			return $resource(url).get();
		},
		listBySearchQuery : function(pageNum, query) {
			return $resource('/videos/search?query=:queryParam&page=:pageParam',{queryParam:query, pageParam:pageNum}).get(
					function(response){
						var a = response;
					}
			);
		}
	}
}]);