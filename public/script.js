$(document).ready(function(){
  $("#iniciar").hide();
  $("#registro").hide();
  $("#tienda").hide();
  $("#editar-usuario").hide();
  $("#editar-contrasena").hide();
  $("#borrar-usuario").hide();
  $("#edit_buton").hide();
  $("#info_buton").hide();
  $("#borrar_buton").hide();


   function cambiarBotonDesplegable() {
       $("#inicio_buton").hide();
       $("#regis_buton").hide();
       $("#edit_buton").show();
       $("#info_buton").show();
       $("#borrar_buton").show();

       var username=sessionStorage.getItem('username');
       $("#myNavbar .dropdown-toggle").html('MI PERFIL <br>' + username +' <span class="caret"></span>');
   }

   function showIniciarSection() {
      $("#inicio").hide();
      $("#test").hide();
      $("#iniciar").show();
      $("#registro").hide();
      $("#tienda").hide();
    }

   $("#inicio_buton").click(function(){
      showIniciarSection();
   });

  $("#tienda_button").click(function(){
      $("#inicio").hide();
      $("#test").hide();
      $("#registro").hide();
      $("#iniciar").hide();
      $("#tienda").show();
      $("#borrar-usuario").hide();
      $("#editar-usuario").hide();
      $("#editar-contrasena").hide();
    });

    $("#regis_buton").click(function(){
      $("#inicio").hide();
      $("#test").hide();
      $("#registro").show();
      $("#iniciar").hide();
      $("#tienda").hide();
      $("#borrar-usuario").hide();
      $("#editar-usuario").hide();
      $("#editar-contrasena").hide();
    });

  $("#edit_buton").click(function(){
        $("#inicio").hide();
        $("#test").hide();
        $("#registro").hide();
        $("#iniciar").hide();
        $("#tienda").hide();
        $("#borrar-usuario").hide();
        $("#editar-usuario").show();
        $("#editar-contrasena").show();
      });

  $("#borrar_buton").click(function(){
        $("#inicio").hide();
        $("#test").hide();
        $("#registro").hide();
        $("#iniciar").hide();
        $("#tienda").hide();
        $("#editar-usuario").hide();
        $("#editar-contrasena").hide();
        $("#borrar-usuario").show();
      });


  $("#info_buton").click(function(){
        $("#inicio").hide();
        $("#test").hide();
        $("#registro").hide();
        $("#iniciar").hide();
        $("#tienda").hide();

      });



    $("#menu").click(function(){
      $("#iniciar").hide();
      $("#registro").hide();
      $("#inicio").show();
      $("#test").show();
      $("#tienda").hide();
    });


    $("#btnRegister").click(function(){

      var username=$("#usr_reg").val();
      var email=$("#email_reg").val();
      var password=$("#pwd_reg").val();

      var userData={
        username:username,
        email:email,
        password:password
      };

      fetch('http://localhost:8080/dsaApp/jugadores/register',{
        method:'POST',
        headers: {
          'Content-Type':'application/json'
        },
      body: JSON.stringify(userData)
      })
      .then(response=> response.json())
      .then(data => {
              console.log(data);
              if (data.success) {
                sessionStorage.setItem('username', username);
                mostrarAlerta('success', 'Registro successful!');
                showIniciarSection();
              } else {
                mostrarAlerta('danger', data.message);
              }
            })
            .catch(error => {
              console.error('Error', error);
              mostrarAlerta('danger', 'An error occurred. Please try again later.');
            });
    });

  $("#btnLogin").click(function(){
    var username=$("#usr_ini").val();
    var password=$("#pwd_ini").val();

    var userData={
      username:username,
      password:password
    };

    fetch('http://localhost:8080/dsaApp/jugadores/login',{
      method:'POST',
      headers:{
        'Content-Type':'application/json'
      },
      body:JSON.stringify(userData)
    })
    .then(response=> response.json())
    .then(data => {
        console.log(data);
        if (data.success) {
          sessionStorage.setItem('username', username);
          cambiarBotonDesplegable();
          mostrarAlerta('success', 'Login successful!');
          $("#iniciar").hide();
          $("#inicio").show();
          $("#test").show()
        } else {
          mostrarAlerta('danger', data.message);
        }
      })
      .catch(error => {
        console.error('Error', error);
        mostrarAlerta('danger', 'An error occurred. Please try again later.');
      });
  });

  $("#btnEditUser").click(function(){

    var usernameJugadorRegistrado=sessionStorage.getItem('username');
    var newUser = $("#new_usr").val();
    var password = $("#pwd_edit").val();

    var userData = {
       username:usernameJugadorRegistrado,
       newUser: newUser,
       password: password
    };
    console.log(userData);
    fetch('http://localhost:8080/dsaApp/jugadores/updateUsername',{
          method:'PUT',
          headers:{
            'Content-Type':'application/json'
          },
          body:JSON.stringify(userData)
        })
        .then(response=> response.json())
        .then(data => {
            console.log(data);
            if (data.success) {
              sessionStorage.setItem('username', newUser);
              mostrarAlerta('success', 'Update successful!');
              var username = sessionStorage.getItem('username');
              $("#myNavbar .dropdown-toggle").html('MI PERFIL <br>' + username +' <span class="caret"></span>');
            } else {
              mostrarAlerta('danger', data.message);
            }
          })
          .catch(error => {
            console.error('Error', error);
            mostrarAlerta('danger', 'An error occurred. Please try again later.');
          });
     });


  $("#btnEditPassword").click(function(){
      var usernameJugadorRegistrado = sessionStorage.getItem('username');
      var password = $("#current_pwd").val();
      var newPassword = $("#new_pwd").val();

      var userData = {
         username: usernameJugadorRegistrado,
         password: password,
         newPassword: newPassword
      };

      fetch('http://localhost:8080/dsaApp/jugadores/updatePassword',{
          method:'PUT',
          headers:{
              'Content-Type':'application/json'
          },
          body:JSON.stringify(userData)
      })
      .then(response => response.json())
      .then(data => {
          console.log(data);
          if (data.success) {
              mostrarAlerta('success', 'Contraseña actualizada correctamente.');
          } else {
              mostrarAlerta('danger', data.message);
          }
      })
      .catch(error => {
          console.error('Error', error);
          mostrarAlerta('danger', 'Se produjo un error. Por favor, inténtelo de nuevo más tarde.');
      });
  });

  $("#btnDeleteUser").click(function(){
      var usernameJugadorRegistrado = sessionStorage.getItem('username');
      var password = $("#pwd_delete").val();

      var userData = {
         username: usernameJugadorRegistrado,
         password: password
      };

      fetch('http://localhost:8080/dsaApp/jugadores/deleteUser', {
          method: 'DELETE',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(userData)
      })
      .then(response => response.json())
      .then(data => {
          console.log(data);
          if (data.success) {
              // Borrar la información de sesión después de borrar el usuario
              sessionStorage.removeItem('username');
              mostrarAlerta('success', 'Usuario borrado correctamente.');
              $("#myNavbar .dropdown-toggle").html('MI PERFIL <span class="caret"></span>');
          } else {
              mostrarAlerta('danger', data.message);
          }
      })
      .catch(error => {
          console.error('Error', error);
          mostrarAlerta('danger', 'Se produjo un error. Por favor, inténtelo de nuevo más tarde.');
      });
  });

   function mostrarAlerta(tipo, contenido) {
      $('#miAlerta').html(contenido);
      $('#miAlerta').removeClass().addClass('alert ' + 'alert-' + tipo);
      $('#miAlerta').fadeIn().delay(2000).fadeOut();
   }
  var datosProductos=[
    {nombre:"Producto 1", descripcion:"+20 de daño", imagen:"espinete.jpg",precio:100},
    {nombre:"Producto 2", descripcion:"+20 de vida", imagen:"espinete.jpg",precio:100},
    {nombre:"Producto 3", descripcion:"+20 de velocidad", imagen:"espinete.jpg",precio:100},
    {nombre:"Unobtanium", descripcion:"Invisibilidad durante 30s", imagen:"espinete.jpg",precio:210},
    {nombre:"Producto 5", descripcion:"Escopeta", imagen:"espinete.jpg",precio:200},
    {nombre:"Producto 6", descripcion:"Espada", imagen:"espinete.jpg",precio:150},
    ];

    function increaseDamage(index){

      var usernameJugadorRegistrado=sessionStorage.getItem('username');
      var data={
      username:usernameJugadorRegistrado
      };

      fetch('http://localhost:8080/dsaApp/tienda/increaseDamage',{
      method:'POST',
      headers:{
      'Content-Type':'application/json'
      },
      body:JSON.stringify(data)
      })
      .then(response=> response.json())
      .then(data=>{
      console.log(data);
      })
      .catch(error=>{
      console.error('Error',error);
      });
   };

   function increaseHealth(index){

    var usernameJugadorRegistrado=sessionStorage.getItem('username');
    var data={
    username:usernameJugadorRegistrado
    };

    fetch('http://localhost:8080/dsaApp/tienda/increaseHealth',{
    method:'POST',
    headers:{
    'Content-Type':'application/json'
    },
    body:JSON.stringify(data)
    })
    .then(response=> response.json())
    .then(data=>{
    console.log(data);
    })
    .catch(error=>{
    console.error('Error',error);
    });
    };

    function increaseSpeed(index){
      var usernameJugadorRegistrado=sessionStorage.getItem('username');
      var dat={
      username:usernameJugadorRegistrado
      };
      fetch('http://localhost:8080/dsaApp/tienda/increaseSpeed',{
      method:'POST',
      headers:{
      'Content-Type':'application/json'
      },
      body:JSON.stringify(data)
      })
      .then(response=> response.json())
      .then(data=>{
      console.log(data);
      })
      .catch(error=>{
      console.error('Error',error);
      });
      };

     function invisibility(index){
      var usernameJugadorRegistrado=sessionStorage.getItem('username');
      var data={
      username:usernameJugadorRegistrado
      };
      fetch('http://localhost:8080/dsaApp/tienda/invisibility',{
      method:'POST',
      headers:{
      "Content-Type":'application/json'
      },
      body:JSON.stringify(data)
      })
      .then(response=>response.json())
      .then(data=>{
      console.log(data);
      })
      .catch(error=>{
      console.error('Error',error);
      });
      };

        function generarProductoWEB(producto,index){
          return `
          <div class="col-md-4 custom-row-margin">
            <div class="card">
               <img src="${producto.imagen}" class="card-img-top" alt="${producto.nombre}" width="40" height="40">
               <div class="card-body">
                 <h5 class="card-title">${producto.nombre} </h5>
                 <p class="card-text">${producto.descripcion}</p>
                 <a href="#" class="btn btn-primary btmComprar"data-index="${index}">Comprar</a>
             </div>
            </div>
          </div>
          `;
        }
        $(document).ready(function(){
          var productosContainer=$('#productos');
          datosProductos.forEach(function(producto,index) {
            var productoWeb=generarProductoWEB(producto,index);
            productosContainer.append(productoWeb);

            $(".btnComprar[data-index='"+index+"']").click(function(){

            switch(index){
            case 0:
             increaseDamage(currentIndex);
             break;
            case 1:
             increaseHealth(currentIndex);
             break;
            case 2:
             increaseSpeed(currentIndex);
             break;
             case 3:
             invisibility(currentIndex);
             break;
            }
          });
          });
        });
});