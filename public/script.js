$(document).ready(function(){
$("#iniciar").hide();
$("#registro").hide();
$("#tienda").hide();


$("#inicio_buton").click(function(){
    $("#inicio").hide();
    $("#test").hide();
    $("#iniciar").show();
    $("#registro").hide();
    $("#tienda").hide();

  });
$("#tienda_button").click(function(){
    $("#inicio").hide();
    $("#test").hide();
    $("#registro").hide();
    $("#iniciar").hide();
    $("#tienda").show();

});

  $("#regis_buton").click(function(){
    $("#inicio").hide();
    $("#test").hide();
    $("#registro").show();
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
    .then(data=> {
      sessionStorage.setItem('username',username);
      console.log(data);
    })
     .catch(error=> {
      console.error('Error',error);
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
  .then(data=>{
  sessionStorage.setItem('username',username);
  console.log(data);
    })
    .catch(error=>{
      console.error('Error',error);
    });
});

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
username=usernameJugadorRegistrado
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
}

var datosProductos=[
{nombre:"Producto 1", descripcion:"+20 de daño", imagen:"espinete.jpg",precio:100},
{nombre:"Producto 2", descripcion:"+20 de vida", imagen:"espinete.jpg",precio:100},
{nombre:"Producto 3", descripcion:"+20 de velocidad", imagen:"espinete.jpg",precio:100},
{nombre:"Unobtanium", descripcion:"Invisibilidad durante 30s", imagen:"espinete.jpg",precio:210},
{nombre:"Producto 5", descripcion:"Escopeta", imagen:"espinete.jpg",precio:200},
{nombre:"Producto 6", descripcion:"Espada", imagen:"espinete.jpg",precio:150},
];
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

    $(".btnComprar").click(function(){

    var index = $(this).data("index");
    switch(index){
    case 0:
      aumentarDanio(index);
    break;

    }
  });
});
});


});