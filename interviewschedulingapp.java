import React, { useState, useEffect } from "react";
import axios from "axios";
export default function Booking({ jobId }) {
  const [slots, setSlots] = useState([]);
  const [selected, setSelected] = useState(null);
  const [candidate, setCandidate] = useState({ name: "", email: "" });
  useEffect(() => {
    axios.get(`/api/jobs/${jobId}/slots`).then(res => setSlots(res.data));
  }, [jobId]);
  const bookSlot = () => {
    if (!selected) return alert("Please select a slot");
    axios.post("/api/bookings", { slotId: selected.id, candidate })
      .then(() => alert("Booking confirmed!"))
      .catch(() => alert("Booking failed, please try again."));
  };
  return (
    <div>
      <h2>Available Slots</h2>
      {slots.map(slot => (
        <label key={slot.id}>
          <input type="radio" name="slot" onChange={() => setSelected(slot)} />
          {new Date(slot.start).toLocaleString()} ({slot.duration} mins)
        </label>
      ))}
      <input placeholder="Name" onChange={e => setCandidate({...candidate, name: e.target.value})} />
      <input placeholder="Email" onChange={e => setCandidate({...candidate, email: e.target.value})} />
      <button onClick={bookSlot}>Confirm Booking</button>
    </div>
  );
