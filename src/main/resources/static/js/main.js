angular.module('main', [])
  .controller('controller-div', function($scope) {

    var pathArray = window.location.pathname.split( '/' );
    var secondLevelLocation = pathArray[0];
    console.log(secondLevelLocation);

    $.get( "/summoner/name/ripolly",
        function(data){
            console.log(data)
        });
    console.log("hello");
    $scope.greeting = {id: 'xxx', content: 'Hello World!'}
})