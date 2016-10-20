(function () {
  'use strict';

  angular
    .module('app')
    .controller('BoardCtrl', ['$scope', '$http', '$filter', BoardCtrl]);

  function BoardCtrl($scope, $http, $filter) {

    var ctrl = this;

    ctrl.board = {};
    ctrl.boards = [];

    ctrl.create = create;
    ctrl.findAll = findAll;

    init();

    function create() {
      return $http.post('/api/boards', null)
        .success(function (response) {
          if (response) {
            ctrl.board = response;
          }
        }).error(function (response, status) {
          console.log('Request error ' + response + ', status code: ' + status);
        });
    }

    function findAll() {
      return $http.get('/api/boards')
        .success(function (response, status) {
          if (response) {
            ctrl.boards = [];
            angular.forEach(response, function (board) {
              ctrl.boards.push(board);
            });
          }
        }).error(function (response, status) {
          console.log('Request error ' + response + ', status code: ' + status);
        });
    }

    function init() {
      findAll();
    }
  }
})();