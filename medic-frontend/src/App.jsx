import { useEffect, useMemo, useState } from "react";
import {
  getPatients,
  createPatient,
  getAppointments,
  createAppointment,
  getPrescriptions,
  createPrescription
} from "./services/api";
import "./styles/global.css";

const emptyPatient = {
  nom: "",
  prenom: "",
  groupeSanguin: "A_POSITIF",
  telephone: ""
};

const emptyAppointment = {
  date: new Date().toISOString().slice(0, 10),
  motif: "CONSULTATION_GENERALE",
  medecin: "",
  patientId: "",
  remarques: ""
};

function formatDate(date) {
  if (!date) return "—";
  const parsed = new Date(date + "T00:00:00");
  return Number.isNaN(parsed.getTime())
    ? date
    : parsed.toLocaleDateString("fr-FR", {
        day: "2-digit",
        month: "short",
        year: "numeric"
      });
}

function getPatientName(patient) {
  if (!patient) return "Patient inconnu";
  return `${patient.prenom ?? ""} ${patient.nom ?? ""}`.trim() || "Patient inconnu";
}

function initials(person) {
  return `${person?.prenom?.[0] ?? ""}${person?.nom?.[0] ?? ""}` || "?";
}

function Sidebar({ page, setPage }) {
  const links = [
    { id: "dashboard", icon: "⌂", label: "Overview", number: "01" },
    { id: "patients", icon: "♙", label: "Patients", number: "02" },
    { id: "appointments", icon: "▤", label: "Appointments", number: "03" }
  ];

  return (
    <aside className="sidebar">
      <a className="brand" href="#" onClick={(e) => { e.preventDefault(); setPage("dashboard"); }}>
        <span className="brand-symbol"><span>+</span></span>
        <span className="brand-copy">
          <strong>MediCare<span>.</span></strong>
          <small>CLINIC MANAGEMENT</small>
        </span>
      </a>

      <div className="sidebar-section-label">WORKSPACE</div>
      <nav className="nav-links">
        {links.map((link) => (
          <button
            key={link.id}
            className={`nav-link ${page === link.id ? "active" : ""}`}
            onClick={() => setPage(link.id)}
          >
            <span className="nav-number">{link.number}</span>
            <span className="nav-link-icon">{link.icon}</span>
            <span>{link.label}</span>
            {page === link.id && <span className="nav-dot" />}
          </button>
        ))}
      </nav>

      <div className="sidebar-note">
        <div className="note-art"><span>✳</span><i>+</i></div>
        <p className="note-label">A little reminder</p>
        <p className="note-text">Good care starts with good organization.</p>
      </div>

      <div className="sidebar-footer">
        <span className="online-dot" />
        <span>Clinic workspace</span>
        <span className="footer-mark">MC</span>
      </div>
    </aside>
  );
}

function Topbar({ page }) {
  const titles = {
    dashboard: ["Your workspace", "A clearer view of your clinic."],
    patients: ["Patient directory", "Every patient, thoughtfully organized."],
    appointments: ["Appointments", "The people and plans on your calendar."]
  };

  const [title, subtitle] = titles[page];

  return (
    <header className="topbar">
      <div className="mobile-brand"><span>+</span> MediCare.</div>
      <div className="page-heading">
        <div className="breadcrumb">MEDICARE <span>/</span> {page.toUpperCase()}</div>
        <h1>{title}</h1>
        <p>{subtitle}</p>
      </div>
      <div className="topbar-right">
        <div className="today-label">
          <span className="online-dot" />
          All systems connected
        </div>
        <div className="avatar">M</div>
      </div>
    </header>
  );
}

function StatCard({ label, value, icon, color, detail, index }) {
  return (
    <article className={`stat-card stat-${color}`}>
      <div className="stat-card-top">
        <span className="stat-index">0{index}</span>
        <span className="stat-icon">{icon}</span>
      </div>
      <p className="stat-label">{label}</p>
      <div className="stat-value">{value}</div>
      <p className="stat-detail">{detail}</p>
      <span className="stat-accent" />
    </article>
  );
}

function SectionHeading({ eyebrow, title, description, action }) {
  return (
    <div className="section-heading">
      <div>
        {eyebrow && <span className="section-eyebrow">{eyebrow}</span>}
        <h2>{title}</h2>
        {description && <p>{description}</p>}
      </div>
      {action}
    </div>
  );
}

function Dashboard({ patients, appointments, loading, onNavigate }) {
  const recent = useMemo(
    () =>
      [...appointments]
        .sort((a, b) => String(b.date ?? "").localeCompare(String(a.date ?? "")))
        .slice(0, 5),
    [appointments]
  );

  const today = new Date().toISOString().slice(0, 10);
  const todayCount = appointments.filter((a) => a.date === today).length;

  return (
    <>
      <section className="welcome-banner">
        <div className="welcome-copy">
          <span className="eyebrow"><span className="eyebrow-line" /> YOUR CLINIC, IN FOCUS</span>
          <h2>Care, made<br /><em>a little simpler.</em></h2>
          <p>Your day-to-day clinic operations, gathered in one thoughtful space.</p>
          <button className="btn btn-dark" onClick={() => onNavigate("appointments")}>
            Explore appointments <span>↗</span>
          </button>
        </div>
        <div className="welcome-art" aria-hidden="true">
          <div className="art-orbit orbit-one" />
          <div className="art-orbit orbit-two" />
          <div className="art-sun" />
          <div className="art-cross">+</div>
          <div className="art-card">
            <span className="art-card-label">TODAY'S OVERVIEW</span>
            <span className="art-card-number">{todayCount.toString().padStart(2, "0")}</span>
            <span className="art-card-caption">appointments today</span>
            <div className="art-card-line"><span /></div>
          </div>
          <span className="art-spark spark-one">✳</span>
          <span className="art-spark spark-two">✳</span>
        </div>
        <span className="banner-index">MC — 001</span>
      </section>

      <div className="section-intro">
        <div>
          <span className="section-eyebrow">THE BIG PICTURE</span>
          <h2>Your clinic at a glance<span className="peach-period">.</span></h2>
        </div>
        <span className="updated-label"><span className="online-dot" /> Live overview</span>
      </div>

      <section className="stats-grid">
        <StatCard label="Registered patients" value={loading ? "—" : patients.length} icon="♙" color="blue" detail="People in your directory" index={1} />
        <StatCard label="Total appointments" value={loading ? "—" : appointments.length} icon="↗" color="peach" detail="Visits recorded in the system" index={2} />
        <StatCard label="Today's appointments" value={loading ? "—" : todayCount} icon="◷" color="green" detail="Scheduled for today" index={3} />
      </section>

      <section className="content-card recent-card">
        <SectionHeading
          eyebrow="RECENT ACTIVITY"
          title="The latest visits"
          description="A quick look at what's on the books."
          action={<button className="text-link" onClick={() => onNavigate("appointments")}>All appointments <span>↗</span></button>}
        />

        {loading ? (
          <div className="empty-state">Gathering your clinic data…</div>
        ) : recent.length === 0 ? (
          <div className="empty-state">
            <div className="empty-art">▤</div>
            <h3>A fresh start.</h3>
            <p>Your appointments will find their place here once you add the first one.</p>
            <button className="btn btn-dark" onClick={() => onNavigate("appointments")}>Add an appointment <span>↗</span></button>
          </div>
        ) : <AppointmentTable appointments={recent} />}
      </section>

      <section className="quick-actions">
        <button className="quick-action" onClick={() => onNavigate("patients")}>
          <span className="quick-art quick-blue">♙</span>
          <span className="quick-copy"><small>01 / DIRECTORY</small><strong>Meet your patients</strong><span>View records or welcome someone new.</span></span>
          <span className="quick-arrow">↗</span>
        </button>
        <button className="quick-action" onClick={() => onNavigate("appointments")}>
          <span className="quick-art quick-peach">▤</span>
          <span className="quick-copy"><small>02 / SCHEDULING</small><strong>Plan a visit</strong><span>Schedule appointments and manage care.</span></span>
          <span className="quick-arrow">↗</span>
        </button>
      </section>
    </>
  );
}

function Patients({ patients, loading, onAddPatient }) {
  const [search, setSearch] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState(emptyPatient);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  const filtered = patients.filter((patient) =>
    `${patient.nom ?? ""} ${patient.prenom ?? ""} ${patient.telephone ?? ""}`
      .toLowerCase().includes(search.toLowerCase())
  );

  async function handleSubmit(event) {
    event.preventDefault();
    setSaving(true);
    setError("");
    try {
      await onAddPatient(form);
      setForm(emptyPatient);
      setShowForm(false);
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  }

  return (
    <section className="content-card directory-card">
      <SectionHeading
        eyebrow="YOUR PEOPLE"
        title="Patient directory"
        description={`${patients.length} registered patient${patients.length !== 1 ? "s" : ""}. Every record in one place.`}
        action={<button className="btn btn-dark" onClick={() => setShowForm(!showForm)}>{showForm ? "Cancel" : <><span>+</span> Add patient</>}</button>}
      />

      {showForm && (
        <form className="form-panel" onSubmit={handleSubmit}>
          <div className="form-title"><span>NEW RECORD / 01</span><h3>Let's add a patient.</h3></div>
          <div className="form-grid">
            <label>First name<input required value={form.prenom} onChange={(e) => setForm({ ...form, prenom: e.target.value })} /></label>
            <label>Last name<input required value={form.nom} onChange={(e) => setForm({ ...form, nom: e.target.value })} /></label>
            <label>Phone number<input required value={form.telephone} onChange={(e) => setForm({ ...form, telephone: e.target.value })} /></label>
            <label>Blood group
              <select value={form.groupeSanguin} onChange={(e) => setForm({ ...form, groupeSanguin: e.target.value })}>
                {["A_POSITIF", "A_NEGATIF", "B_POSITIF", "B_NEGATIF", "AB_POSITIF", "AB_NEGATIF", "O_POSITIF", "O_NEGATIF"].map((group) => <option key={group} value={group}>{group.replace("_", " ")}</option>)}
              </select>
            </label>
          </div>
          {error && <p className="form-error">{error}</p>}
          <button className="btn btn-dark" disabled={saving}>{saving ? "Saving…" : "Save patient ↗"}</button>
        </form>
      )}

      <div className="table-toolbar">
        <div className="table-count"><span className="online-dot" /> PATIENT REGISTER</div>
        <input className="search-input" placeholder="Search a name or phone…" value={search} onChange={(e) => setSearch(e.target.value)} />
      </div>

      {loading ? <div className="empty-state">Gathering patient records…</div> : filtered.length === 0 ? (
        <div className="empty-state"><div className="empty-art">♙</div><h3>{search ? "No match just yet." : "An empty page, for now."}</h3><p>{search ? "Try another name or phone number." : "Your patient records will appear here."}</p></div>
      ) : (
        <div className="table-wrap"><table>
          <thead><tr><th>Patient</th><th>Phone</th><th>Blood group</th><th>Record</th></tr></thead>
          <tbody>{filtered.map((patient, index) => (
            <tr key={patient.id ?? index}>
              <td><div className="patient-cell"><div className={`patient-avatar avatar-tone-${index % 4}`}>{initials(patient)}</div><span><strong>{getPatientName(patient)}</strong><small>Patient record</small></span></div></td>
              <td>{patient.telephone || "—"}</td>
              <td><span className="soft-badge">{patient.groupeSanguin?.replace("_", " ") || "—"}</span></td>
              <td><span className="record-id">MC-{String(patient.id ?? index + 1).padStart(3, "0")}</span></td>
            </tr>
          ))}</tbody>
        </table></div>
      )}
    </section>
  );
}

function AppointmentTable({ appointments }) {
  return (
    <div className="table-wrap"><table>
      <thead><tr><th>Patient</th><th>Date</th><th>Practitioner</th><th>Visit type</th><th>Record</th></tr></thead>
      <tbody>{appointments.map((appointment, index) => (
        <tr key={appointment.id ?? index}>
          <td><div className="patient-cell"><div className={`patient-avatar avatar-tone-${index % 4}`}>{initials(appointment.patient)}</div><span><strong>{getPatientName(appointment.patient)}</strong><small>Patient visit</small></span></div></td>
          <td><span className="date-main">{formatDate(appointment.date)}</span></td>
          <td>{appointment.medecin || "—"}</td>
          <td><span className="soft-badge">{appointment.motif?.replaceAll("_", " ") || "—"}</span></td>
          <td><span className="record-id">AP-{String(appointment.id ?? index + 1).padStart(3, "0")}</span></td>
        </tr>
      ))}</tbody>
    </table></div>
  );
}

function Appointments({ appointments, patients, loading, onAddAppointment }) {
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState(emptyAppointment);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [prescriptionFor, setPrescriptionFor] = useState(null);
  const [medicaments, setMedicaments] = useState("");
  const [prescriptions, setPrescriptions] = useState([]);
  const [prescriptionError, setPrescriptionError] = useState("");
  const [prescriptionLoading, setPrescriptionLoading] = useState(false);

  async function handleSubmit(event) {
    event.preventDefault();
    setSaving(true);
    setError("");
    try {
      await onAddAppointment({ ...form, patientId: Number(form.patientId) });
      setForm(emptyAppointment);
      setShowForm(false);
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  }

  async function openPrescriptions(appointment) {
    setPrescriptionFor(appointment);
    setMedicaments("");
    setPrescriptionError("");
    setPrescriptionLoading(true);
    try {
      const result = await getPrescriptions(appointment.id);
      setPrescriptions(Array.isArray(result) ? result : result ? [result] : []);
    } catch (err) {
      setPrescriptions([]);
      setPrescriptionError(err.message);
    } finally {
      setPrescriptionLoading(false);
    }
  }

  async function handlePrescription(event) {
    event.preventDefault();
    if (!prescriptionFor || !medicaments.trim()) return;
    setPrescriptionLoading(true);
    setPrescriptionError("");
    try {
      await createPrescription({
        appointmentId: prescriptionFor.id,
        medicaments: medicaments.split(/\n|,/).map((item) => item.trim()).filter(Boolean)
      });
      setMedicaments("");
      await openPrescriptions(prescriptionFor);
    } catch (err) {
      setPrescriptionError(err.message);
      setPrescriptionLoading(false);
    }
  }

  return (
    <>
      <section className="content-card directory-card">
        <SectionHeading
          eyebrow="THE CLINIC CALENDAR"
          title="Appointments"
          description={`${appointments.length} recorded visit${appointments.length !== 1 ? "s" : ""}. A little more structure to every day.`}
          action={<button className="btn btn-dark" onClick={() => setShowForm(!showForm)}>{showForm ? "Cancel" : <><span>+</span> Schedule visit</>}</button>}
        />

        {showForm && (
          <form className="form-panel" onSubmit={handleSubmit}>
            <div className="form-title"><span>NEW VISIT / 01</span><h3>Make room for a visit.</h3></div>
            {patients.length === 0 && <p className="form-error">Register a patient before creating an appointment.</p>}
            <div className="form-grid">
              <label>Patient
                <select required value={form.patientId} onChange={(e) => setForm({ ...form, patientId: e.target.value })}>
                  <option value="">Choose a patient</option>
                  {patients.map((patient) => <option key={patient.id} value={patient.id}>{getPatientName(patient)} (#{patient.id})</option>)}
                </select>
              </label>
              <label>Date<input type="date" required value={form.date} onChange={(e) => setForm({ ...form, date: e.target.value })} /></label>
              <label>Practitioner<input required placeholder="Dr. Bennani" value={form.medecin} onChange={(e) => setForm({ ...form, medecin: e.target.value })} /></label>
              <label>Visit type
                <select value={form.motif} onChange={(e) => setForm({ ...form, motif: e.target.value })}>
                  {["CONSULTATION_GENERALE", "SUIVI", "URGENCE", "CONTROLE"].map((reason) => <option key={reason} value={reason}>{reason.replaceAll("_", " ")}</option>)}
                </select>
              </label>
              <label className="full-width">Notes<textarea rows="2" value={form.remarques} onChange={(e) => setForm({ ...form, remarques: e.target.value })} /></label>
            </div>
            {error && <p className="form-error">{error}</p>}
            <button className="btn btn-dark" disabled={saving || patients.length === 0}>{saving ? "Saving…" : "Save appointment ↗"}</button>
          </form>
        )}

        <div className="table-toolbar"><div className="table-count"><span className="online-dot" /> APPOINTMENT REGISTER</div><span className="muted">Select a record to manage its prescription.</span></div>

        {loading ? <div className="empty-state">Gathering appointments…</div> : appointments.length === 0 ? (
          <div className="empty-state"><div className="empty-art">▤</div><h3>Nothing on the calendar yet.</h3><p>Schedule a visit and it will appear here.</p></div>
        ) : (
          <>
            <AppointmentTable appointments={appointments} />
            <div className="prescription-actions">
              <span className="section-eyebrow">PRESCRIPTION RECORDS</span>
              <div className="appointment-actions">{appointments.map((appointment) => (
                <button className="btn btn-outline" key={appointment.id} onClick={() => openPrescriptions(appointment)}>Appointment #{appointment.id} <span>↗</span></button>
              ))}</div>
            </div>
          </>
        )}
      </section>

      {prescriptionFor && (
        <section className="content-card prescription-card">
          <SectionHeading eyebrow="CARE NOTES" title={`Prescription · #${prescriptionFor.id}`} description={getPatientName(prescriptionFor.patient)} action={<button className="text-link" onClick={() => setPrescriptionFor(null)}>Close ×</button>} />
          {prescriptionLoading ? <p className="muted">Loading prescriptions…</p> : prescriptionError ? <p className="form-error">{prescriptionError}</p> : prescriptions.length === 0 ? <p className="muted">No prescription data returned for this appointment yet.</p> : (
            <div className="prescription-list">{prescriptions.map((prescription, index) => (
              <div className="prescription-item" key={prescription.id ?? index}><strong>Prescription {prescription.id ? `#${prescription.id}` : `#${index + 1}`}</strong><ul>{(prescription.medicaments ?? []).map((medicine, i) => <li key={i}>{medicine}</li>)}</ul></div>
            ))}</div>
          )}
          <form className="form-panel" onSubmit={handlePrescription}>
            <div className="form-title"><span>NEW PRESCRIPTION</span><h3>Record the medication.</h3></div>
            <label>Medications<textarea rows="3" required placeholder={"One medication per line, for example:\nParacétamol 1g\nVitamine C"} value={medicaments} onChange={(e) => setMedicaments(e.target.value)} /></label>
            <p className="muted small">Separate medications with new lines or commas.</p>
            {prescriptionError && <p className="form-error">{prescriptionError}</p>}
            <button className="btn btn-dark" disabled={prescriptionLoading}>Save prescription ↗</button>
          </form>
        </section>
      )}
    </>
  );
}

export default function App() {
  const [page, setPage] = useState("dashboard");
  const [patients, setPatients] = useState([]);
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  async function loadData() {
    setLoading(true);
    setError("");
    try {
      const [patientData, appointmentData] = await Promise.all([getPatients(), getAppointments()]);
      setPatients(Array.isArray(patientData) ? patientData : []);
      setAppointments(Array.isArray(appointmentData) ? appointmentData : []);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => { loadData(); }, []);

  async function handleAddPatient(patient) {
    await createPatient(patient);
    const result = await getPatients();
    setPatients(Array.isArray(result) ? result : []);
  }

  async function handleAddAppointment(appointment) {
    await createAppointment(appointment);
    const result = await getAppointments();
    setAppointments(Array.isArray(result) ? result : []);
  }

  return (
    <div className="app-shell">
      <Sidebar page={page} setPage={setPage} />
      <main className="main-area">
        <Topbar page={page} />
        <div className="page-content">
          {error && <div className="error-banner"><div><strong>We couldn't reach the clinic data.</strong><p>{error}</p><small>Check that the backend services and gateway on port 8888 are running.</small></div><button className="btn btn-outline" onClick={loadData}>Try again ↗</button></div>}
          {page === "dashboard" && <Dashboard patients={patients} appointments={appointments} loading={loading} onNavigate={setPage} />}
          {page === "patients" && <Patients patients={patients} loading={loading} onAddPatient={handleAddPatient} />}
          {page === "appointments" && <Appointments appointments={appointments} patients={patients} loading={loading} onAddAppointment={handleAddAppointment} />}
          <footer className="footer"><span>© {new Date().getFullYear()} MediCare</span><span>Made with care <span className="peach-period">♥</span></span><span>CLINIC MANAGEMENT / 001</span></footer>
        </div>
      </main>
    </div>
  );
}
