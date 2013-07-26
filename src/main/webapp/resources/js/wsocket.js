/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var connection = new WebSocket('ws://localhost:8080/process/status');

connection.onopen = function() {
    connection.send("[Client] Conncetion Established");
};

connection.onmessage = function(event) {
    console.log("Message from the Socket is received");
    var socketMsg = document.getElementById("sockMsg");
    socketMsg.innerHTML = socketMsg.innerHTML + "<br/><label id='data'>" + event.data + "</label>";
};

connection.onerror = function(error) {
    console.log('WebSocket Error ' + error);
};

connection.onclose = function() {
    console.log("Connection closed");
};

window.onbeforeunload = function(){
  if(connection){
      connection.close();
  }  
};