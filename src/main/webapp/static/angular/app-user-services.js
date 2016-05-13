angular.module('app-user-services', ['ngResource'])
.service("userService", ['$resource', function($resource) {
	return {
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
		}
	}
}]);