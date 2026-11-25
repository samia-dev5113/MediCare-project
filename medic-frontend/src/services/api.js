const API_URL = "http://localhost:8888";

async function request(path, options = {}) {
  let response;

  try {
    response = await fetch(`${API_URL}${path}`, {
      ...options,
      headers: {
        ...(options.body ? { "Content-Type": "application/json" } : {}),
        ...options.headers
      }
    });
  } catch {
    throw new Error(
      "Cannot connect to the backend. Check that the gateway is running on port 8888."
    );
  }

  if (!response.ok) {
    const details = await response.text().catch(() => "");
    throw new Error(
      details || `Request failed (${response.status} ${response.statusText})`
    );
  }

  if (response.status === 204) return null;

  const text = await response.text();
  return text ? JSON.parse(text) : null;
}

export function getPatients() {
  return request("/patient-service/patients");
}

export function createPatient(patient) {
  return request("/patient-service/patients", {
    method: "POST",
    body: JSON.stringify(patient)
  });
}

export function getAppointments() {
  return request("/appointment-service/appointments");
}

export function createAppointment(appointment) {
  return request("/appointment-service/appointments", {
    method: "POST",
    body: JSON.stringify(appointment)
  });
}

export function getPrescriptions(appointmentId) {
  return request(`/appointment-service/ordonnances/appointment/${appointmentId}`);
}

export function createPrescription(prescription) {
  return request("/appointment-service/ordonnances", {
    method: "POST",
    body: JSON.stringify(prescription)
  });
}
