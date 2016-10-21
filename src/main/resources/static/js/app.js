var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
  $routeProvider
    .when('/boards',{
      templateUrl: '/views/boards.html',
      controller: 'BoardCtrl',
      controllerAs: 'ctrl'
    })
    .otherwise(
    { redirectTo: '/'}
  );
});