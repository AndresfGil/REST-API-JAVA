window.addEventListener('load', function () {
    // Obtener el formulario de actualización de turno
    const formulario = document.querySelector('#update_turno_form');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        // Crear el JSON con los datos del turno
        const formData = {
            id: document.querySelector('#turno_id').value,
            paciente: {
                id: document.querySelector('#paciente_id').value,
                nombre: document.querySelector('#paciente_nombre').value,
                apellido: document.querySelector('#paciente_apellido').value,
                cedula: document.querySelector('#paciente_cedula').value,
                fechaIngreso: document.querySelector('#paciente_fechaIngreso').value,
                domicilio: {
                    id: document.querySelector('#paciente_domicilio_id').value,
                    calle: document.querySelector('#paciente_calle').value,
                    numero: document.querySelector('#paciente_numero').value,
                    localidad: document.querySelector('#paciente_localidad').value,
                    provincia: document.querySelector('#paciente_provincia').value
                },
                email: document.querySelector('#paciente_email').value
            },
            odontologo: {
                id: document.querySelector('#odontologo_id').value,
                nombre: document.querySelector('#odontologo_nombre').value,
                apellido: document.querySelector('#odontologo_apellido').value,
                matricula: document.querySelector('#odontologo_matricula').value
            },
            fecha: document.querySelector('#turno_fecha').value
        };

        // Configurar la petición fetch para actualizar el turno
        const url = '/turnos';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        };

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                console.log('Turno actualizado:', data);
                location.reload();
            }).catch(error => {
                console.error('Error al actualizar el turno:', error);
            });
    });
});

// Función para buscar y llenar el formulario con los datos del turno
function findBy(id) {
    const url = '/turnos/' + id;
    const settings = {
        method: 'GET'
    };

    fetch(url, settings)
        .then(response => response.json())
        .then(data => {
            let turno = data;
            document.querySelector('#turno_id').value = turno.id;
            document.querySelector('#paciente_id').value = turno.paciente.id;
            document.querySelector('#paciente_nombre').value = turno.paciente.nombre;
            document.querySelector('#paciente_apellido').value = turno.paciente.apellido;
            document.querySelector('#paciente_cedula').value = turno.paciente.cedula;
            document.querySelector('#paciente_fechaIngreso').value = turno.paciente.fechaIngreso;
            document.querySelector('#paciente_domicilio_id').value = turno.paciente.domicilio.id;
            document.querySelector('#paciente_calle').value = turno.paciente.domicilio.calle;
            document.querySelector('#paciente_numero').value = turno.paciente.domicilio.numero;
            document.querySelector('#paciente_localidad').value = turno.paciente.domicilio.localidad;
            document.querySelector('#paciente_provincia').value = turno.paciente.domicilio.provincia;
            document.querySelector('#paciente_email').value = turno.paciente.email;

            document.querySelector('#odontologo_id').value = turno.odontologo.id;
            document.querySelector('#odontologo_nombre').value = turno.odontologo.nombre;
            document.querySelector('#odontologo_apellido').value = turno.odontologo.apellido;
            document.querySelector('#odontologo_matricula').value = turno.odontologo.matricula;

            document.querySelector('#turno_fecha').value = turno.fecha;

            // Mostrar el formulario de actualización
            document.querySelector('#div_turno_updating').style.display = "block";
        }).catch(error => {
            console.error('Error al buscar el turno:', error);
        });
}