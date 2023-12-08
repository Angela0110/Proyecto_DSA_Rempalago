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

   function mostrarAlerta(tipo, contenido) {
        $('#miAlerta').html(contenido);
        $('#miAlerta').removeClass().addClass('alert ' + 'alert-' + tipo);
        $('#miAlerta').fadeIn().delay(2000).fadeOut();
   }

   function generarProductoWEB(producto, index){
        return `
        <div class="col-md-4 custom-row-margin">
            <div class="card">
                // <img src="${producto.imagen}" class="card-img-top" alt="${producto.nombre}" width="40" height="40">
                <div class="card-body">
                    <h5 class="card-title">${producto.nombre}</h5>
                    <p class="card-text">${producto.description}</p>
                    <a href="#" class="btn btn-primary btnComprar" data-index="${index}">Comprar</a>
                </div>
            </div>
        </div>
    `;
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

      document.getElementById('btnRegister').addEventListener('click',function (){
      var username=document.getElementById('usr_reg').value;
      var email=document.getElementById('email_reg').value;
      var password=document.getElementById('pwd_reg').value;

      if(username==''){
        document.getElementBy('usernameReg-error').innerText='Please enter a username';
        return;
       }else{
        document.getElementById('usernameReg-error').innerText='';
       }
       if(email==''){
        document.getElementBy('emailReg-error').innerText='Please enter a email';
        return;
       }else{
        document.getElementById('emailReg-error').innerText='';
       }
       if(password==''){
        document.getElementBy('passwordReg-error').innerText='Please enter a password';
        return;
       }else{
        document.getElementById('passwordReg-error').innerText='';
       }
       alert ('Todo correcto, se puede enviar.')
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
    var newUsername = $("#new_usr").val();
    var password = $("#pwd_edit").val();

    var userData = {
       username:usernameJugadorRegistrado,
       newUsername: newUsername,
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
              sessionStorage.setItem('username', newUsername);
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

      var userData = {
         username: usernameJugadorRegistrado,
      };

      fetch('http://localhost:8080/dsaApp/jugadores/deleteUser/' + usernameJugadorRegistrado, {
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



  $('#tienda_button').click(function(){
          const listaProductosElement = document.getElementsByClassName('tienda_productos')[0];

          fetch('http://localhost:8080/dsaApp/tienda/todos')
              .then(response => response.json())
              .then(productos => {
                  productos.forEach((producto, index) => {
                  const productoWeb = generarProductoWEB(producto, index);

                      listaProductosElement.innerHTML += productoWeb;
                  });

                  $('.btnComprar').click(function(){
                      var index = $(this).data('index');
                      var username = $('#usr_ini').val();
                      fetch('http://localhost:8080/dsaApp/tienda/comprar/' + productos[index].nombre + '/' + username, {
                            method: 'GET',
                            headers:{
                                'Content-Type':'application/json'
                            },
                      })
                      .then(response => response.json())
                      .then(data => {
                            console.log(data);
                            if(data.success){
                                mostrarAlerta('Success','Objeto comprado');
                            }
                            else{
                                console.error('Error', error);
                                mostrarAlerta('Danger','No se ha podido comprar el objeto, no tienes eurillos suficientes');
                            }
                      })
                  });
              })
              .catch(error => console.error('Error al obtener la lista', error));
  });
});