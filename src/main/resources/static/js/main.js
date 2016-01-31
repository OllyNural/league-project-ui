angular.module('main', [])
  .controller('controller-div', function($scope) {
    $.get( "http://localhost:8080/league-project/summoner/ripolly",
        function(data){
            console.log(data)
        },
        "JSONP");
    console.log("hello");
    $scope.greeting = {id: 'xxx', content: 'Hello World!'}
})