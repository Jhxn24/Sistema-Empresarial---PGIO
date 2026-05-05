function abrirModal() {
  document.getElementById("modalEmpleado").style.display = "block";
}

function cerrarModal() {
  document.getElementById("modalEmpleado").style.display = "none";
}

// Cerrar si el usuario hace clic fuera de la caja blanca
window.onclick = function (event) {
  let modal = document.getElementById("modalEmpleado");
  if (event.target == modal) {
    cerrarModal();
  }
};
