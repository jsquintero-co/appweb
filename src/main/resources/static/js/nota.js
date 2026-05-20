const API_ESTUDIANTES =
    "http://localhost:8080/api/estudiantes";

const API_NOTAS =
    "http://localhost:8080/api/notas";

const API_MATERIAS =
    "http://localhost:8080/api/materias";

const params =
    new URLSearchParams(window.location.search);

const estudianteId =
    params.get("estudianteId");

document.addEventListener("DOMContentLoaded", () => {

    cargarEstudiante();

    cargarMaterias();

    cargarNotas();

    document.getElementById("formNota")
        .addEventListener("submit", guardarNota);

    document.getElementById("materia")
        .addEventListener("change", cargarNotas);
});

async function cargarEstudiante(){

    try{

        const response =
            await fetch(
                `${API_ESTUDIANTES}/${estudianteId}`
            );

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

async function cargarMaterias(){

    try{

        const response =
            await fetch(API_MATERIAS);

        const materias =
            await response.json();

        const select =
            document.getElementById("materia");

        select.innerHTML = `
            <option value="">
                Seleccione una materia
            </option>
        `;

        materias.forEach(materia => {

            select.innerHTML += `
                <option value="${materia.id}">
                    ${materia.nombre}
                </option>
            `;
        });

    } catch(error){

        console.error(error);

        alert("Error cargando materias");
    }
}

async function cargarNotas(){

    try{

        const materiaId =
            document.getElementById("materia").value;

        if(!materiaId){

            document.getElementById("tablaNotas")
                .innerHTML = "";

            actualizarResumen([]);

            return;
        }

        const response =
            await fetch(
                `${API_NOTAS}/estudiante/${estudianteId}`
            );

        const notas =
            await response.json();

        const notasFiltradas =
            notas.filter(nota =>
                nota.materiaId == materiaId
            );

        const tabla =
            document.getElementById("tablaNotas");

        tabla.innerHTML = "";

        notasFiltradas.forEach(nota => {

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

        actualizarResumen(notasFiltradas);

    } catch(error){

        console.error(error);

        alert("Error cargando notas");
    }
}

async function guardarNota(event){

    event.preventDefault();

    const materiaId =
        parseInt(
            document.getElementById("materia").value
        );

    if(!materiaId){

        alert("Seleccione una materia");

        return;
    }

    const nota = {

        estudianteId: parseInt(estudianteId),

        materiaId: materiaId,

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

            document.getElementById("materia").value =
                materiaId;

            cargarNotas();

        } else {

            const error =
                await response.json();

            alert(
                error.mensaje ||
                "Error guardando nota"
            );
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
            await fetch(
                `${API_NOTAS}/${id}`,
                {
                    method: "DELETE"
                }
            );

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

        const materiaId =
            parseInt(
                document.getElementById("materia").value
            );

        if(!materiaId){

            alert("Seleccione una materia");

            return;
        }

        const response =
            await fetch(

                `${API_NOTAS}/final/${estudianteId}/${materiaId}`
            );

        const notaFinal =
            await response.json();

        alert(
            `Nota final: ${notaFinal.toFixed(2)}`
        );

    } catch(error){

        console.error(error);

        alert("Error calculando nota final");
    }
}

function actualizarResumen(notas){

    let porcentaje = 0;

    let acumulado = 0;

    notas.forEach(nota => {

        porcentaje += nota.porcentaje;

        acumulado +=
            (nota.valor * nota.porcentaje) / 100;
    });

    let estado = "Sin calcular";

    if(porcentaje > 0){

        estado =
            acumulado >= 3
                ? "Aprobado"
                : "Reprobado";
    }

    document.getElementById("resumen").innerHTML = `

        <h3>Resumen Académico</h3>

        <p>
            Porcentaje acumulado:
            ${porcentaje}%
        </p>

        <p>
            Nota acumulada:
            ${acumulado.toFixed(2)}
        </p>

        <p>
            Estado:
            ${estado}
        </p>
    `;
}

function volver(){

    window.location.href =
        "index.html";
}