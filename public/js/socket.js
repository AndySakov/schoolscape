// (esversion: 6)
var socket = io(`myip:port`);

socket.on('connect', function() {
    console.log( "Connected..." );
});

socket.on( 'disconnect', function() {
    console.log( "Disconnected..." );
} );


socket.on( 'notify', function(data) {
    pushNotify( data.title, data.msg );
});