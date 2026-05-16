const API_ESTUDIANTES =
    "http://localhost:8080/api/estudiantes";

const API_NOTAS =
    "http://localhost:8080/api/notas";

const params =
    new URLSearchParams(window.location.search);

const estudianteId =
    params.get("estudianteId");

document.addEventListener("DOMContentLoaded", () => {

    cargarEstudiante();

    cargarNotas();

    document.getElementById("formNota")
        .addEventListener("submit", guardarNota);
});

async function cargarEstudiante(){

    try{

        const response =
            await fetch(`${API_ESTUDIANTES}/${estudianteId}`);

        const estudiante =
            await response.json();

        document.getElementById("tituloEstudiante")
            .innerText =
            `Estudiante: ${estudiante.nombre} ${estudiante.apellido}`;

    } catch(error){

        console.error(error);

        alert("Error cargando estudiante");
    }
}

async function cargarNotas(){

    try{

        const response =
            await fetch(`${API_NOTAS}/estudiante/${estudianteId}`);

        const notas =
            await response.json();

        const tabla =
            document.getElementById("tablaNotas");

        tabla.innerHTML = "";

        notas.forEach(nota => {

            tabla.innerHTML += `
                <tr>

                    <td>${nota.id}</td>

                    <td>${nota.materiaNombre}</td>

                    <td>${nota.valor}</td>

                    <td>${nota.porcentaje}%</td>

                    <td>${nota.observacion || ""}</td>

                    <td>

                        <button
                            class="btn-eliminar"
                            onclick="eliminarNota(${nota.id})">

                            Eliminar

                        </button>

                    </td>

                </tr>
            `;
        });

    } catch(error){

        console.error(error);

        alert("Error cargando notas");
    }
}

async function guardarNota(event){

    event.preventDefault();

const nota = {

    estudianteId: parseInt(estudianteId),

    materiaNombre:
        document.getElementById("materia").value,

    valor: parseFloat(
        document.getElementById("valor").value
    ),

    porcentaje: parseFloat(
        document.getElementById("porcentaje").value
    ),

    observacion:
        document.getElementById("observacion").value
};

    try{

        const response =
            await fetch(API_NOTAS, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(nota)
            });

        if(response.ok){

            alert("Nota guardada");

            document.getElementById("formNota").reset();

            cargarNotas();

        } else {

            const error = await response.json();

            alert(error.mensaje || "Error guardando nota");
        }

    } catch(error){

        console.error(error);

        alert("Error en petición");
    }
}

async function eliminarNota(id){

    if(!confirm("¿Eliminar nota?")){
        return;
    }

    try{

        const response =
            await fetch(`${API_NOTAS}/${id}`, {

                method: "DELETE"
            });

        if(response.ok){

            alert("Nota eliminada");

            cargarNotas();

        } else {

            alert("Error eliminando nota");
        }

    } catch(error){

        console.error(error);

        alert("Error eliminando");
    }
}

async function calcularNotaFinal(){

    try{

        const response =
            await fetch(
                `${API_ESTUDIANTES}/${estudianteId}/nota-final`
            );

        const data =
            await response.json();

        alert(
            `Nota final: ${data.notaFinal}`
        );

    } catch(error){

        console.error(error);

        alert("Error calculando nota final");
    }
}

function volver(){

    window.location.href = "index.html";
}