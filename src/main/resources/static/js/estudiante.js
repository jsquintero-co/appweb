 const API_URL = "http://localhost:8080/api/estudiantes";

let editandoId = null;

document.addEventListener("DOMContentLoaded", () => {

    cargarEstudiantes();

    document.getElementById("formEstudiante")
        .addEventListener("submit", guardarEstudiante);
});

async function cargarEstudiantes() {

    try {

        const response = await fetch(API_URL);

        const estudiantes = await response.json();

        const tabla = document.getElementById("tablaEstudiantes");

        tabla.innerHTML = "";

        estudiantes.forEach(estudiante => {

    tabla.innerHTML += `
      <tr>
          <td>${estudiante.id}</td>
          <td>${estudiante.nombre}</td>
          <td>${estudiante.apellido}</td>
         <td>${estudiante.correo}</td>

         <td>

              <button
                 class="btn-editar"
                 onclick="editarEstudiante(
                        ${estudiante.id},
                        '${estudiante.nombre}',
                        '${estudiante.apellido}',
                        '${estudiante.correo}'
                   )">
                    Editar
             </button>

              <button
                    class="btn-eliminar"
                    onclick="eliminarEstudiante(${estudiante.id})">
                    Eliminar
             </button>

                <button
                   class="btn-notas"
                   onclick="verNotas(${estudiante.id})">
                   Ver Notas
             </button>

            </td>

        </tr>
    `;
        });

    } catch(error) {

        console.error(error);

        alert("Error cargando estudiantes");
    }
}

async function guardarEstudiante(event) {

    event.preventDefault();

    const estudiante = {

        nombre: document.getElementById("nombre").value,

        apellido: document.getElementById("apellido").value,

        correo: document.getElementById("correo").value
    };

    try {

        let response;

        if(editandoId){

            response = await fetch(`${API_URL}/${editandoId}`, {

                method: "PUT",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(estudiante)
            });

        } else {

            response = await fetch(API_URL, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(estudiante)
            });
        }

        if(response.ok){

            alert(editandoId
                ? "Estudiante actualizado"
                : "Estudiante guardado");

            document.getElementById("formEstudiante").reset();

            editandoId = null;

            cargarEstudiantes();

        } else {
            const error = await response.json();
            alert(error.mensaje || "Error guardando estudiante");
        }

    } catch(error){

        console.error(error);

        alert("Error en la petición");
    }
}

function editarEstudiante(id, nombre, apellido, correo){

    editandoId = id;

    document.getElementById("nombre").value = nombre;

    document.getElementById("apellido").value = apellido;

    document.getElementById("correo").value = correo;
}

async function eliminarEstudiante(id){

    if(!confirm("¿Eliminar estudiante?")){
        return;
    }

    try {

        const response = await fetch(`${API_URL}/${id}`, {

            method: "DELETE"
        });

        if(response.ok){

            alert("Estudiante eliminado");

            cargarEstudiantes();

        } else {

            alert("Error eliminando estudiante");
        }

    } catch(error){

        console.error(error);

        alert("Error eliminando");
    }

}

    function verNotas(id){

    window.location.href =
        `nota.html?estudianteId=${id}`;
    }