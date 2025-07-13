const API_URL = ""; // leave empty since you're serving from Spring Boot

// Handle form submit
document.getElementById("userForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const name = document.getElementById("name").value.trim();
  const age = parseInt(document.getElementById("age").value.trim());
  const interestsInput = document.getElementById("interests").value.trim();

  const interests = interestsInput
    .split(",")
    .map(i => i.trim().toLowerCase())
    .filter(i => i.length > 0);

  const payload = { name, age, interests };

  const response = await fetch(`${API_URL}/create`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(payload)
  });

  const result = await response.json();
  alert(`User ${result.name} added.`);
  e.target.reset();
  fetchUsers();
});

// Fetch all users
async function fetchUsers() {
  const res = await fetch(`${API_URL}/getAll`);
  const users = await res.json();

  const list = document.getElementById("userList");
  list.innerHTML = "";

  if (users.length === 0) {
    list.innerHTML = "<p>No users found.</p>";
    return;
  }

  users.forEach(user => {
    const div = document.createElement("div");
    div.classList.add("user-card");
    div.innerHTML = `
      <strong>${user.name}</strong> (Age: ${user.age})<br>
      Interests: ${user.interests.join(", ")}<br>
      <button onclick="deleteUser('${user.name}')">Delete</button>
      <button onclick="matchUser('${user.name}')">Find Matches</button>
    `;
    list.appendChild(div);
  });
}

// Delete user by username
async function deleteUser(username) {
  const res = await fetch(`${API_URL}/delete/${username}`, { method: "DELETE" });
  const msg = await res.text();
  alert(msg);
  fetchUsers();
}

// Delete all users
async function deleteAll() {
  if (!confirm("Are you sure you want to delete all users?")) return;
  const res = await fetch(`${API_URL}/deleteAll`, { method: "DELETE" });
  const msg = await res.text();
  alert(msg);
  fetchUsers();
}

// Get matches for a user
async function matchUser(username) {
  const res = await fetch(`${API_URL}/getInterest/${username}`);
  const users = await res.json();

  if (users.length === 0) {
    alert(`No matches found for ${username}.`);
    return;
  }

  const matches = users.map(u =>
    `${u.name} (Age: ${u.age}) - ${u.interests.join(", ")}`
  ).join("\n");

  alert(`Matches for ${username}:\n\n${matches}`);
}
