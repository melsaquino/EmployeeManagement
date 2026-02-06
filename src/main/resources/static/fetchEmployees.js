const csrfToken = document.querySelector('meta[name="_csrf"]').content;
fetchAll();

function fetchBasedOnEndPoints(url, method) {
    //const container = document.getElementById("books-container");
    //const userId = container.dataset.userId;

    fetch(url, {
        method: method,// Specify the HTTP method
        credentials: "include"

    }).then(response => response.json())
        .then(employees => {
            const tbody = document.getElementById('employees-list');
            tbody.innerHTML = ''; // clear existing rows if any
            employees.forEach(employee => {
                const row = document.createElement('tr');
                const csrfInput = document.createElement("input");
                csrfInput.type = "hidden";
                csrfInput.name = "_csrf";
                csrfInput.value = csrfToken;
                row.innerHTML = `
                    <td>${employee.employeeId}</td>
                    <td>${employee.name}</td>
                    <td>${employee.dateOfBirth}</td>
                    <td>${employee.department}</td>
                    <td>${employee.salary}</td>
                    <td><button type="button" 
                    class="btn btn-outline-success" 
                        data-bs-toggle="modal" 
                        data-employee-id="${employee.employeeId}"
                        data-employee-name ="${employee.name}"
                        data-employee-dob ="${employee.dateOfBirth}"
                        data-employee-department="${employee.department}"
                        data-employee-salary="${employee.salary}"

                        data-bs-target="#editEmployeeModal">
                      Edit
                    </button></td>
                    <td><button class="btn btn-outline-danger" onClick="deleteItem(${employee.employeeId})">Delete</button></td>

                    `;

                const td = document.createElement('td');
                //td.appendChild(buttonEdit);
                row.appendChild(td);
                tbody.appendChild(row);

            });
        })
        .catch(error => {
            console.error('Error loading books:', error);
        });
}
function deleteItem(employeeId) {
    if (confirm("Are you sure you want to delete this item?")) {
        fetch(`/api/employees/${employeeId}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    // Handle successful deletion (e.g., remove the item from the UI, show a message)
                    console.log(`User ${employeeId} deleted successfully.`);
                    // Optionally, redirect or refresh the page
                    // window.location.reload();
                } else {
                    // Handle errors (e.g., item not found, server error)
                    console.error('Failed to delete the item.');
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    }
}
const modal = document.getElementById("editEmployeeModal");

modal.addEventListener("show.bs.modal", event => {
    const btn = event.relatedTarget;
    console.log(btn.dataset);
    document.getElementById('edit-id').value = btn.dataset.employeeId;
    document.getElementById('edit-name').value = btn.dataset.employeeName;
    document.getElementById('edit-dateOfBirth').value = btn.dataset.employeeDob;
    document.getElementById('edit-department').value = btn.dataset.employeeDepartment;
    document.getElementById('edit-salary').value = btn.dataset.employeeSalary;
});
function fetchAll(page = 0) {
    currentPage = page;
    fetchBasedOnEndPoints(`/api/employees`, "GET");
}
/*
document.getElementById("nextBtn").addEventListener("click", () => {
    fetchAll(currentPage + 1);
});
document.getElementById("prevBtn").addEventListener("click", () => {
    if (currentPage > 0)
        fetchAll(currentPage - 1);
});*/