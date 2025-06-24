const API_BASE = "http://localhost:8080/api"; 
let inspecciones;
let propietarios;
let vehiculos;
let inspectores;

async function cargarAutos() {
  const filtro = document.getElementById("filtroEstado").value;
  const tbody = document.querySelector("#tablaAutos tbody");
  tbody.innerHTML = "";

  try {
    const res = await fetch(`${API_BASE}/vehiculos`);
    vehiculos = await res.json();
    cargarPropietarios()
    vehiculos.forEach((vehiculo) => {
      if (filtro && vehiculo.estado !== filtro) return;

      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${vehiculo.patente}</td>
        <td>${vehiculo.marca}</td>
        <td>${vehiculo.modelo}</td>
        <td>${vehiculo.vencimientoVtv}</td>
        <td><span class="badge bg-${getColorEstado(vehiculo.estado)}">${vehiculo.estado}</span></td>
        <td><button class="btn btn-sm btn-info" onclick="mostrarAccionesVehiculo(${vehiculo.id})">Ver</button></td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error("Error al cargar los vehiculos", err);
  }
}

async function cargarInspectores() {
  const tbody = document.querySelector("#tablaInspectores tbody");
  tbody.innerHTML = "";
  try {
    const res = await fetch(`${API_BASE}/inspectores`);
    inspectores = await res.json();
    inspectores.forEach((inspector) => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${inspector.id}</td>
        <td>${inspector.nombre} ${inspector.apellido}</td>
        <td><button class="btn btn-sm btn-info" onclick="verInspeccion(${inspector.id})">Ver</button></td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error("Error al cargar inspecciones", err);
  }
}

async function cargarInspecciones() {
  const filtro = document.getElementById("filtroEstado").value;
  const tbody = document.querySelector("#tablaInspecciones tbody");
  tbody.innerHTML = "";
  try {
    const res = await fetch(`${API_BASE}/inspecciones`);
    inspecciones = await res.json();
    inspecciones.forEach((inspeccion) => {
      if (filtro && inspeccion.resultadoFinal !== filtro) return;

      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${inspeccion.id}</td>
        <td>${inspeccion.fechaInspeccion}</td>
        <td><span class="badge bg-${getColorEstado(inspeccion.resultadoFinal)}">${inspeccion.resultadoFinal}</span></td>
        <td><button class="btn btn-sm btn-info" onclick="verDetalleInspector(${inspeccion.inspectorId})">Inspector</button></td>
        <td><button class="btn btn-sm btn-primary" onclick="verDetalleAuto(${inspeccion.vehiculoId})">Auto</button></td>
        <td><button class="btn btn-sm btn-success" onclick="verDetalleMediciones(${inspeccion.id})">Mediciones</button></td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error("Error al cargar inspecciones", err);
  }
}

async function cargarPropietarios(){
  try{
    const res = await fetch(`${API_BASE}/propietarios`);
    propietarios = await res.json();
    const tbody = document.querySelector("#tablaPropietarios tbody");
    tbody.innerHTML = "";
    propietarios.forEach((propietario) => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${propietario.id}</td>
        <td>${propietario.nombre} ${propietario.apellido}</td>
        <td>${propietario.esExento ? "Si" : "No"}</td>
        <td><button class="btn btn-sm btn-primary" onclick="verDetalleAuto(${propietario.vehiculoId})">Ver</button></td>
      `;
      tbody.appendChild(tr);
    });

  }catch(e){
    console.log("Error al cargar propietarios", e)
  }
}

function getColorEstado(estado) {
  switch (estado) {
    case "APTO": return "success";
    case "CONDICIONAL": return "warning";
    case "RECHAZADO": return "danger";
    default: return "secondary";
  }
}

async function verInspeccion(id) {
  try {
    const res = await fetch(`${API_BASE}/inspecciones/id/${id}`);
    const inspeccion = await res.json();
    alert(`Inspección #${inspeccion.id}\nFecha: ${inspeccion.fecha_inspeccion}\nEstado: ${inspeccion.resultadoFinal}`);
  } catch (err) {
    alert("Error al obtener detalle de inspección");
  }
}

window.addEventListener("DOMContentLoaded", () => {
  cargarAutos();
  document.getElementById("filtroEstado").addEventListener("change", cargarAutos);

  const autosTab = document.querySelector("#autos");
  const inspeccionesTab = document.querySelector("#inspecciones");
  const inspectoresTab = document.querySelector("#inspectores");
  const propietariosTab = document.querySelector("#propietarios");

  const btnVehiculo = document.createElement("button");
  btnVehiculo.className = "btn btn-primary mb-3";
  btnVehiculo.textContent = "+ Vehículo";
  btnVehiculo.onclick = crearVehiculo;
  autosTab.prepend(btnVehiculo);

  const btnInspeccion = document.createElement("button");
  btnInspeccion.className = "btn btn-success mb-3";
  btnInspeccion.textContent = "+ Inspección";
  btnInspeccion.onclick = crearInspeccion;
  inspeccionesTab.prepend(btnInspeccion);

  const btnPropietario = document.createElement("button");
  btnPropietario.className = "btn btn-secondary mb-3";
  btnPropietario.textContent = "+ Propietario";
  btnPropietario.onclick = crearVehiculo;
  propietariosTab.prepend(btnPropietario);

  const btnInspector = document.createElement("button");
  btnInspector.className = "btn btn-warning mb-3";
  btnInspector.textContent = "+ Inspector";
  btnInspector.onclick = crearInspector;
  inspectoresTab.prepend(btnInspector);
});


let selectPropietarios;

async function crearVehiculo() {
    try {
        const res = await fetch(`${API_BASE}/propietarios`);
        const propietarios = await res.json();
        selectPropietarios = `<select class="form-control" id="swal-propietario"> `;
            propietarios.forEach(propietario => {
                selectPropietarios += `<option value="${propietario.id}"> ${propietario.nombre} ${propietario.apellido} - N° ${propietario.id} </option>`
        })
        console.log(selectPropietarios)
        selectPropietarios += '</select>';
    } catch (err) {
        console.log("Error al obtener los propietarios. ", err);
    }

  
  const { value: formValues } = await Swal.fire({
    title: 'Crear Vehículo',
    html:
      '<input id="swal-patente" class="form-control" placeholder="Patente">' +
      '<input id="swal-marca" class="form-control" placeholder="Marca">' +
      '<input id="swal-modelo" class="form-control" placeholder="Modelo">' +
      selectPropietarios,
    focusConfirm: false,
    preConfirm: () => {
      return {
        patente: document.getElementById('swal-patente').value,
        marca: document.getElementById('swal-marca').value,
        modelo: document.getElementById('swal-modelo').value,
        propietarioDTOId: parseInt(document.getElementById('swal-propietario').value)   
      }
    }
  });

  if (formValues) {
    try {
      await fetch(`${API_BASE}/vehiculos`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formValues)
      });
      Swal.fire('Creado!', 'Vehículo creado correctamente.', 'success');
      cargarAutos();
    } catch (err) {
      Swal.fire('Error', 'No se pudo crear el vehículo.', 'error');
    }
  }
}

async function obtenerVehiculos() {
  const res = await fetch(`${API_BASE}/vehiculos`);
  return await res.json();
}

async function obtenerInspectores() {
  const res = await fetch(`${API_BASE}/inspectores`);
  return await res.json();
}

async function crearInspeccion() {
  const vehiculos = await obtenerVehiculos();
  const inspectores = await obtenerInspectores();
  const estados = ['APTO', 'CONDICIONAL', 'RECHAZADO'];
const selectHtml = (id, label, options) => `
    <div style="margin-bottom: 10px;">
      <label for="${id}" style="display: block; font-weight: bold;">${label}</label>
      <select id="${id}" class="swal2-select" style="width: 100%;">
        ${options.map(opt => `<option value="${opt.value}">${opt.text}</option>`).join('')}
      </select>
    </div>`;

  const selectVehiculo = selectHtml('vehiculo-select', 'Vehículo', vehiculos.map(v => ({
    value: v.id,
    text: `${v.patente} - ${v.marca} ${v.modelo}`
  })));

  const selectInspector = selectHtml('inspector-select', 'Inspector', inspectores.map(i => ({
    value: i.id,
    text: `${i.nombre} ${i.apellido}`
  })));

  const medicionesVisuales = ['luz', 'patente', 'espejos', 'chasis', 'vidrios', 'seguridadYemergencia']
    .map(campo => selectHtml(campo, campo.charAt(0).toUpperCase() + campo.slice(1), estados.map(e => ({ value: e, text: e }))))
    .join('');

  const medicionesMaquina = ['suspension', 'direccion', 'trenDelantero']
    .map(campo => selectHtml(campo, campo.charAt(0).toUpperCase() + campo.slice(1), estados.map(e => ({ value: e, text: e }))))
    .join('');

  const { value: confirm } = await Swal.fire({
    title: 'Nueva Inspección',
    html: `
      ${selectVehiculo}
      ${selectInspector}
      <hr>
      <h5 style="text-align:center;">Mediciones Visuales</h5>
      ${medicionesVisuales}
      <hr>
      <h5 style="text-align:center;">Mediciones de Máquina</h5>
      ${medicionesMaquina}
    `,
    focusConfirm: false,
    showCancelButton: true,
    preConfirm: () => {
      const getValor = id => document.getElementById(id).value;
      return {
        vehiculoId: getValor('vehiculo-select'),
        inspectorId: getValor('inspector-select'),
        medicionVisual: {
          luz: getValor('luz'),
          patente: getValor('patente'),
          espejos: getValor('espejos'),
          chasis: getValor('chasis'),
          vidrios: getValor('vidrios'),
          seguridadYemergencia: getValor('seguridadYemergencia')
        },
        medicionMaquina: {
          suspension: getValor('suspension'),
          direccion: getValor('direccion'),
          trenDelantero: getValor('trenDelantero')
        }
      }
    }
  });

  if (confirm) {
    try {
      await fetch(`${API_BASE}/inspecciones`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(confirm)
      });
      Swal.fire('Éxito', 'Inspección creada correctamente', 'success');
      cargarInspecciones();
    } catch (err) {
      Swal.fire('Error', 'No se pudo crear la inspección.', 'error');
    }
  }
}

// Crear inspector
async function crearInspector() {
  const { value: nombre } = await Swal.fire({
    title: 'Crear Inspector',
    input: 'text',
    inputLabel: 'Nombre del inspector',
    showCancelButton: true
  });

  if (nombre) {
    try {
      await fetch(`${API_BASE}/inspectores`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nombre })
      });
      Swal.fire('Creado!', 'Inspector creado correctamente.', 'success');
    } catch (err) {
      Swal.fire('Error', 'No se pudo crear el inspector.', 'error');
    }
  }
}

async function verDetalleInspector(inspectorId) {
  try {
    const res = await fetch(`${API_BASE}/inspectores/${inspectorId}`);
    const inspector = await res.json();

    await Swal.fire({
      title: 'Inspector',
      html: `
        <p><strong>Nombre:</strong> ${inspector.nombre}</p>
        <p><strong>Apellido:</strong> ${inspector.apellido}</p>
        <p><strong>Legajo:</strong> ${inspector.legajo}</p>
      `
    });
  } catch (err) {
    Swal.fire('Error', 'No se pudo cargar el inspector.', 'error');
  }
}

async function verDetalleAuto(autoId) {
  try {
    const res = await fetch(`${API_BASE}/vehiculos/id/${autoId}`);
    const auto = await res.json();

    const fechaVto = new Date(auto.vencimiento);
    const hoy = new Date();
    const vencida = fechaVto < hoy;

    await Swal.fire({
      title: 'Vehículo',
      html: `
        <p><strong>Patente:</strong> ${auto.patente}</p>
        <p><strong>Marca:</strong> ${auto.marca}</p>
        <p><strong>Modelo:</strong> ${auto.modelo}</p>
        <p><strong>Año:</strong> ${auto.anio}</p>
        <p><strong>Vencimiento VTV:</strong> ${auto.vencimientoVtv}</p>
      `
    });
  } catch (err) {
    Swal.fire('Error', 'No se pudo cargar el vehículo.', 'error');
  }
}

async function verDetalleMediciones(inspeccionId) {
  try {
    const res = await fetch(`${API_BASE}/inspecciones/id/${inspeccionId}`);
    const inspeccion = await res.json();

    const mv = inspeccion.medicionVisual;
    const mm = inspeccion.medicionMaquina;

    const getItem = (nombre, estado) => `<p><span class="badge bg-${getColorEstado(estado)}">${nombre}: ${estado}</span></p>`;

    await Swal.fire({
      title: 'Mediciones',
      html: `
        <h5>Visuales:</h5>
        ${getItem('Luz', mv.luz)}
        ${getItem('Patente', mv.patente)}
        ${getItem('Espejos', mv.espejos)}
        ${getItem('Chasis', mv.chasis)}
        ${getItem('Vidrios', mv.vidrios)}
        ${getItem('Seguridad y Emergencia', mv.seguridadYemergencia)}
        <hr/>
        <h5>Máquina:</h5>
        ${getItem('Suspensión', mm.suspension)}
        ${getItem('Dirección', mm.direccion)}
        ${getItem('Tren delantero', mm.trenDelantero)}
      `
    });
  } catch (err) {
    Swal.fire('Error', 'No se pudo cargar la inspección.', 'error');
  }
}



async function mostrarAccionesVehiculo(vehiculoId) {
  const { value: opcion } = await Swal.fire({
    title: 'Acciones',
    input: 'select',
    inputOptions: {
      detalle: 'Ver detalle',
      editar: 'Editar',
      eliminar: 'Eliminar'
    },
    inputPlaceholder: 'Selecciona una acción',
    showCancelButton: true
  });

  if (opcion === 'detalle') {
    verDetalleAuto(vehiculoId);
  } else if (opcion === 'editar') {
    editarVehiculo(vehiculoId);
  } else if (opcion === 'eliminar') {
    eliminarVehiculo(vehiculoId);
  }
}

async function editarVehiculo(vehiculoId) {
  try {
    const res = await fetch(`${API_BASE}/vehiculos/id/${vehiculoId}`);
    const vehiculo = await res.json();

    const { value: form } = await Swal.fire({
      title: 'Editar Vehículo',
      html:
        `<input id="dominio" class="swal2-input" placeholder="Patente" value="${vehiculo.patente}">` +
        `<input id="marca" class="swal2-input" placeholder="Marca" value="${vehiculo.marca}">` +
        `<input id="modelo" class="swal2-input" placeholder="Modelo" value="${vehiculo.modelo}">` +
        `<input id="anio" class="swal2-input" placeholder="Año" value="${vehiculo.anio}">`,
      focusConfirm: false,
      showCancelButton: true,
      preConfirm: () => {
        return {
          patente: document.getElementById('dominio').value,
          marca: document.getElementById('marca').value,
          modelo: document.getElementById('modelo').value,
          anio: document.getElementById('anio').value
        };
      }
    });

    if (form) {
      await fetch(`${API_BASE}/vehiculos/${vehiculoId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form)
      });
      Swal.fire('Actualizado', 'Vehículo actualizado correctamente', 'success');
      cargarAutos();
    }
  } catch (err) {
    Swal.fire('Error', 'No se pudo editar el vehículo.', 'error');
  }
}

async function eliminarVehiculo(vehiculoId) {
  const confirm = await Swal.fire({
    title: '¿Eliminar vehículo?',
    text: 'Esta acción no se puede deshacer.',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Sí, eliminar'
  });

  if (confirm.isConfirmed) {
    try {
      await fetch(`${API_BASE}/vehiculos/${vehiculoId}`, {
        method: 'DELETE'
      });
      Swal.fire('Eliminado', 'Vehículo eliminado correctamente', 'success');
      cargarAutos();
    } catch (err) {
      Swal.fire('Error', 'No se pudo eliminar el vehículo.', 'error');
    }
  }
}
