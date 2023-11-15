$(document).ready(function(){
$("#iniciar").hide();
$("#registro").hide();

  $("#inicio_buton").click(function(){
    $("#inicio").hide();
    $("#test").hide();
    $("#iniciar").show();
    $("#registro").hide();
  });

  $("#regis_buton").click(function(){
    $("#inicio").hide();
    $("#test").hide();
    $("#registro").show();
    $("#iniciar").hide();
  });

  $("#btnRegister").click(function(){

    var username=$("#user_reg").val();
    var email=$("#email_reg").val();
    var password=$("#pwd_reg").val();

    var userData={
      username:username,
      email:email,
      password:password
    };

    fetch('http://localhost:8080/jugadores/register',{
      method:'POST',
      headers: {
        'Content-Type':'application/json'
    },
    body: JSON.stringify(userData)
    })
    .then(response=> response.json())
    .then(data=> {
      console.log(data);
    })
     .catch(error=> {
      console.error('Error',error);
    });
  });

$("#btnLogin").click(function(){
  var username=$("usr_ini").val();
  var password=$("#pwd_ini").val();

  var userData={
    username:username,
    password:password
  };
  fetch('http://localhost:8080/jugadores/login',{
    method:'POST',
    headers:{
      'Content-Type':'application/json'
    },
    body:JSON.stringify(userData)
  })
  .then(response=> response.json())
  .then(data=>{
  console.log(data);
    })
    .catch(error=>{
      console.error('Error',error);
    });
});

  $("#menu").click(function(){
    $("#iniciar").hide();
    $("#registro").hide();
    $("#inicio").show();
    $("#test").show();
  });

  $("#tiendabtn").click(function(){
    $("#test").hide();
    //$.get("http://localhost:8080/dsaApp/juegos", function(data, status){
    //alert("Data: " + JSON.stringify(data) + "\nStatus: " + status);
    fetch('http://localhost:8080/dsaApp/tracks')
    .then(response => response.json())
    .then(data => {

     var tabla = document.createElement('table');

     // por cada dato se crea una fila
     for (const fila of data){
     let tr = document.createElement('tr');

     // otro bucle para recorrer los datos de cada objeto
     for (const atributo of Object.values(fila)) {

     var celda = document.createElement('td');
     celda.textContent = atributo;
     celda.style.border = '1px solid';
     tr.appendChild(celda);
     }

     tr.appendChild(celda);

     tabla.appendChild(tr);
     }
     document.body.appendChild(tabla);
    })
  });


});