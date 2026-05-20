const API_MATERIAS =
    "http://localhost:8080/api/materias";

const API_ESTUDIANTES =
    "http://localhost:8080/api/estudiantes";

let editandoId = null;

document.addEventListener("DOMContentLoaded", () => {

    cargarMaterias();

    document.getElementById("formMateria")
        .addEventListener("submit", guardarMateria);
});

async function cargarMaterias(){

    try{

        const response =
            await fetch(API_MATERIAS);

        const materias =
            await response.json();

        const tabla =
            document.getElementById("tablaMaterias");

        tabla.innerHTML = "";

        materias.forEach(materia => {

            tabla.innerHTML += `
                <tr>

                    <td>${materia.id}</td>

                    <td>${materia.nombre}</td>

                    <td>${materia.creditos}</td>

                    <td>

                        <button
                            class="btn-editar"
                            onclick="editarMateria(
                                ${materia.id},
                                '${materia.nombre}',
                                ${materia.creditos}
                            )">

                            Editar

                        </button>

                        <button
                            class="btn-eliminar"
                            onclick="eliminarMateria(${materia.id})">

                            Eliminar

                        </button>

                        <button
                            class="btn-ver"
                            onclick="verEstudiantes(${materia.id})">

                            Ver Estudiantes

                        </button>

                    </td>

                </tr>
            `;
        });

    } catch(error){

        console.error(error);

        alert("Error cargando materias");
    }
}

async function guardarMateria(event){

    event.preventDefault();

    const materia = {

        nombre:
            document.getElementById("nombre").value,

        creditos: parseInt(
            document.getElementById("creditos").value
        )
    };

    try{

        let response;

        if(editandoId){

            response = await fetch(
                `${API_MATERIAS}/${editandoId}`,
                {
                    method: "PUT",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify(materia)
                }
            );

        } else {

            response = await fetch(API_MATERIAS, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(materia)
            });
        }

        if(response.ok){

            alert(editandoId
                ? "Materia actualizada"
                : "Materia guardada");

            document.getElementById("formMateria")
                .reset();

            editandoId = null;

            cargarMaterias();

        } else {

            const error =
                await response.json();

            alert(
                error.mensaje ||
                "Error guardando materia"
            );
        }

    } catch(error){

        console.error(error);

        alert("Error en petición");
    }
}

function editarMateria(id, nombre, creditos){

    editandoId = id;

    document.getElementById("nombre").value =
        nombre;

    document.getElementById("creditos").value =
        creditos;
}

async function eliminarMateria(id){

    if(!confirm("¿Eliminar materia?")){
        return;
    }

    try{

        const response =
            await fetch(`${API_MATERIAS}/${id}`, {

                method: "DELETE"
            });

        if(response.ok){

            alert("Materia eliminada");

            cargarMaterias();

        } else {

            alert("Error eliminando materia");
        }

    } catch(error){

        console.error(error);

        alert("Error eliminando");
    }
}

async function verEstudiantes(materiaId){

    try{

        const response =
            await fetch(
                `${API_MATERIAS}/${materiaId}/estudiantes`
            );

        const estudiantesIds =
            await response.json();

        const lista =
            document.getElementById("listaEstudiantes");

        lista.innerHTML = "";

        for(const id of estudiantesIds){

            const responseEstudiante =
                await fetch(
                    `${API_ESTUDIANTES}/${id}`
                );

            const estudiante =
                await responseEstudiante.json();

            lista.innerHTML += `
                <li>
                    ${estudiante.nombre}
                    ${estudiante.apellido}
                    (${estudiante.correo})
                </li>
            `;
        }

    } catch(error){

        console.error(error);

        alert("Error cargando estudiantes");
    }
}

function volver(){

    window.location.href = "index.html";
}