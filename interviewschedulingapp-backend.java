app.post("/api/bookings", async (req, res) => {
  const { slotId, candidate } = req.body;
  try {
    const slot = await Slot.findByPk(slotId);
    if (!slot || slot.status !== "open")
      return res.status(400).json({ message: "Slot unavailable" });

    await Booking.create({
      slotId,
      candidateName: candidate.name,
      candidateEmail: candidate.email,
      status: "confirmed"
    });

    slot.status = "booked";
    await slot.save();

    sendEmail(candidate.email, "Interview Scheduled", "Your interview is confirmed!");
    res.json({ message: "Booking successful" });
  } catch (err) {
    res.status(500).json({ message: "Server error" });
  }
});
