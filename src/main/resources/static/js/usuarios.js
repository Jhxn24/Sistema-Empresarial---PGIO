// ── Abrir modal CREAR usuario ─────────────────────────────────────────────────
function abrirModalNuevo() {
  document.getElementById("modalTitulo").textContent = "Crear Nuevo Usuario";
  document.getElementById("modalCrear").style.display = "block";
}

function abrirModalEditarRol(id) {
  fetch("/admin/usuarios/" + id + "/datos")
    .then(function (res) {
      if (!res.ok) throw new Error("Error " + res.status);
      return res.json();
    })
    .then(function (u) {
      document.getElementById("editId").value = u.id;
      document.getElementById("editUsername").textContent = u.username;
      document.getElementById("editRol").value = u.rol;
      document.getElementById("modalEditarRol").style.display = "block";
    })
    .catch(function (err) {
      alert("Error al cargar datos: " + err.message);
    });
}
function abrirModalPassword(id, username) {
  document.getElementById("pwdId").value = id;
  document.getElementById("pwdUsername").textContent = username;
  document.getElementById("nuevaPassword").value = "";
  document.getElementById("modalPassword").style.display = "block";
}

function confirmarEliminar(id, username) {
  if (
    !confirm(
      '¿Eliminar al usuario "' +
        username +
        '"?\nEsta acción no se puede deshacer.',
    )
  ) {
    return;
  }
  var form = document.getElementById("formEliminar");
  form.action = "/admin/usuarios/" + id + "/eliminar";
  form.submit();
}

function cerrarModal(modalId) {
  document.getElementById(modalId).style.display = "none";
}

window.onclick = function (event) {
  ["modalCrear", "modalEditarRol", "modalPassword"].forEach(function (id) {
    var modal = document.getElementById(id);
    if (modal && event.target === modal) {
      modal.style.display = "none";
    }
  });
};
