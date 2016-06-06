<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE HTML>
<html ng-app="app">
	<head>
		<title>Video Content Portal</title>
		<link rel="shortcut icon" type="image/x-icon" href="static/images/pageicon.png" />
		<link ng-if="production=='true'" href="static/css/style.min.css" rel="stylesheet" type="text/css"  media="all" />
		<link ng-if="production=='true'" href="static/css/bootstrap.min.css" rel="stylesheet" type="text/css"  media="all" />
		<link ng-if="production=='true'" href="static/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"  media="all" />
		<link ng-if="production=='false'" href="static/css/style.css" rel="stylesheet" type="text/css"  media="all" />
		<link ng-if="production=='false'" href="static/css/bootstrap.css" rel="stylesheet" type="text/css"  media="all" />
		<link ng-if="production=='false'" href="static/css/bootstrap-theme.css" rel="stylesheet" type="text/css"  media="all" />
		<meta name="keywords" content="Video, portal" />
	</head>
	<body>
	<!----start-wrap---->
	<div class="wrap">
		<!----start-Header---->
		<div class="header">
			<!----start-Logo---->
			<div class="logo">
				<a href="index.jsp"><img src="static/images/logo.png" title="logo" /></a>
			</div>
			<!----End-Logo---->
			<!----start-top-nav---->
			<div class="top-nav" ng-controller="menuController">
				<ul>
					<a ng-href="#/videos/?page=0"><li><p>Home</p></li></a>
					<a ng-href="#/upload" ng-show="principal.role==user"><li><p>Upload</p></li></a>
					<a ng-href="#/user-videos?page=0" ng-show="principal.role==user"><li><p>My videos</p></li></a>
					<a ng-href="#/admin/accounts?page=0" ng-show="principal.role==admin"><li><p>Accounts</p></li></a>
					<a ng-href="#/admin/companies?page=0" ng-show="principal.role==admin"><li><p>Companies</p></li></a>
					<a ng-href="#/admin/statistics" ng-show="principal.role==admin"><li><p>Statistics</p></li></a>
					<a ng-href="#/login" ng-show="!principal.auth"><li><p>Sign in</p></li></a>
					<a ng-href="#/logout" ng-show="principal.auth"><li><p>Logout</p></li></a>
				</ul>
			</div>
			<div class="clear"> </div>
			<!----End-top-nav---->
		</div>
		<!----End-Header---->
		<div class="clear"> </div>
		<div class="content">
			<div class="left-content">
				<div class="searchbar">
					<div class="search-left">
						<p>Video Content Portal</p>
					</div>
					<div class="search-right" ng-controller="searchController">
						<input type="text" id="searchQuery" ng-model="query" ng-keypress="onEnter($event)">
						<input type="submit" value="" ng-click="find()" />
					</div>
					<div class="clear"> </div>
				</div>
			<ng-view ></ng-view>
		</div>
		<div class="clear"> </div>
		</div>
		</div>
			<div class="clear"> </div>
			<div class="copy-right">
			<p>&#169 2013 All rights Reserved | Design by &nbsp;<a href="http://w3layouts.com">W3Layouts</a></p>
		</div>
	</div>
	<!----End-wrap---->
	<div ng-if="production=='true'">
		<script type="text/javascript" src="/static/js/angular.min.js"></script>
		<script type="text/javascript" src="/static/js/angular-route.min.js"></script>
		<script type="text/javascript" src="/static/js/angular-resource.min.js"></script>
		<script type="text/javascript" src="/static/js/file-upload.min.js"></script>
		<script type="text/javascript" src="/static/angular/script.min.js"></script>
	</div>
	<div ng-if="production=='false'">
		<script type="text/javascript" src="/static/js/angular.js"></script>
		<script type="text/javascript" src="/static/js/angular-route.js"></script>
		<script type="text/javascript" src="/static/js/angular-resource.js"></script>
		<script type="text/javascript" src="/static/js/ng-file-upload-shim.js"></script>
		<script type="text/javascript" src="/static/js/ng-file-upload.js"></script>
		<script type="text/javascript" src="/static/angular/app-controllers.js"></script>
		<script type="text/javascript" src="/static/angular/app-admin-controllers.js"></script>
		<script type="text/javascript" src="/static/angular/app-user-controllers.js"></script>
		<script type="text/javascript" src="/static/angular/app-services.js"></script>
		<script type="text/javascript" src="/static/angular/app-admin-services.js"></script>
		<script type="text/javascript" src="/static/angular/app-user-services.js"></script>
		<script type="text/javascript" src="/static/angular/app-filters.js"></script>
		<script type="text/javascript" src="/static/angular/app-interceptors.js"></script>
		<script type="text/javascript" src="/static/angular/app-constants.js"></script>
		<script type="text/javascript" src="/static/angular/app.js"></script>
	</div>
	</body>
</html>

