const urlUser = 'http://localhost:8080/api/users/';
const urlRoles = 'http://localhost:8080/api/roles/';

let rolesArr = [];

// var editModal = new bootstrap.Modal(document.getElementById('editModal'));
// var delModal = new bootstrap.Modal(document.getElementById('delModal'));

window.addEventListener("DOMContentLoaded", () => {

    /*** Вывод всех пользователей из БД в таблицу через метод get ***/

    function showAllUsers() {
        getResource(urlUser)
            .then(data => createTable(data))
            .catch(err => console.error(err));
    }

    showAllUsers();

    async function getResource(url) {
        const res = await fetch(`${url}`);

        if (!res.ok) {
            throw new Error(`Could no fetch ${url}, status: ${res.status}`);
        }

        return await res.json();
    }

    function createTable(response) {
        response.forEach(user => {
            console.log(user);
            let table = document.createElement('tr');

            const datasetJSON = urlUser + user.id;

            table.innerHTML = `
                        <td>${user.id}</td>  
                        <td>${user.username}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.map(role => role.name.slice(5))}</td>
                        <td>
                            <button type="button" name="submit" id="${user.id}" class="btn btn-info"
                            data-toggle="modal" data-target="#editModal" data-json="${datasetJSON}">
                                Edit
                            </button>
                        </td>
                        <td>
                            <button type="button" name="submit" id="${user.id}"  class="btn btn-danger"
                            data-toggle="modal" data-target="#delModal" data-json="${datasetJSON}">
                                Delete
                            </button>
                        </td>
                        <td></td>
                    `;
            document.querySelector('.usersTbody').appendChild(table);
        });
    }

    /*** Вывод всех ролей из БД в таблицу через метод get ***/

    function showAllRoles() {
        getResource(urlRoles)
            .then(data => createOption(data))
            .then(roles => roles.forEach(role => rolesArr.push(role)))
            .catch(err => console.error(err));
    }

    showAllRoles();

    function createOption(response) {
        document.querySelectorAll('.role_select').forEach(selector => {
            response.forEach(role => {
                let option = document.createElement('option');
                option.value = role.id;
                option.text = role.name.slice(5);

                selector.appendChild(option);

            });
        });
    }


    /*** Добавление в бд нового пользователя через метод post ***/

    const formForNewUser = document.querySelector(".form_for_new_user");
    const newRoles =  document.getElementById("rolesNew");

    function addNewUser(e) {
        e.preventDefault();

        let rolesJ = [];

        const selectedOpts = [...newRoles.options]
            .filter(x => x.selected)
            .map(x => x.value);

        selectedOpts.forEach(
            role => {
                rolesJ.push(rolesArr[role - 1])
            }
        );


        let formData = new FormData(formForNewUser);

        let obj = {};
        formData.forEach((value, key) => {
            obj[key] = value;
        });
        obj.roles = rolesJ;
        console.log(obj.roles);

        postResource(urlUser, obj)
            .catch(err => console.error(err));
    }

    formForNewUser.addEventListener("submit", (e) => addNewUser(e));

    async function postResource(url, data) {
        const res = await fetch(`${url}`, {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(
                data)
        });

        if (!res.ok) {
            throw new Error(`Could no fetch ${url}, status: ${res.status}`);
        }

        return await res.json();
    }

    /*** Открытие модального окона ***/

    document.addEventListener('click', function (e) {
        if (e.target.dataset.json && e.target.dataset.target === "#delModal") {
            e.preventDefault();

            getResource(e.target.dataset.json)
                .then(data => updateDelModal(data))
                .catch(err => console.error(err));
        } else if (e.target.dataset.json && e.target.dataset.target === "#editModal") {
            e.preventDefault();

            getResource(e.target.dataset.json)
                .then(data => updateEditModal(data))
                .catch(err => console.error(err));
        }
    });

    function updateEditModal(user) {
        console.log(user);
        document.getElementById('idEdit').value = user.id;
        document.getElementById('nameEdit').value = user.username;
        document.getElementById('passwordEdit').value = user.password;
        document.getElementById('ageEdit').value = user.age;
        document.getElementById('emailEdit').value = user.email;
    }


    function updateDelModal(user) {
        console.log(user);
        document.getElementById('idDel').value = user.id;
        document.getElementById('usernameDel').value = user.username;
        document.getElementById('passwordDel').value = user.password;
        document.getElementById('ageDel').value = user.age;
        document.getElementById('emailDel').value = user.email;
    }


    /*** Удаление пользователя через метод DELETE ***/

    const formForDelUser = document.querySelector(".form_for_delete_user");

    function delUser(e) {
        e.preventDefault();

        let formData = new FormData(formForDelUser);

        let obj = {};
        formData.forEach((value, key) => {
            if (key == "roles") {
                getResource(urlRoles + value)
                    .then(data => value = data)
                    .catch(err => console.error(err))
            }
            obj[key] = value;
        });
        console.log(obj.roles);

        delResource(urlUser + obj.id, obj)
            .catch(err => console.error(err));
    }

    formForDelUser.addEventListener("submit", (e) => delUser(e), {"once": true});

    async function delResource(url, data) {
        const res = await fetch(`${url}`, {
            method: "DELETE",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (!res.ok) {
            throw new Error(`Could no fetch ${url}, status: ${res.status}`);
        }

        return await res.json();
    }


    /*** Изминение пользователя через метод PATCH ***/

    const formForUpdateUser = document.querySelector(".form_for_update_user");

    function updateUser(e) {
        e.preventDefault();

        let formData = new FormData(formForUpdateUser);

        let obj = {};
        formData.forEach((value, key) => {
            obj[key] = value;
        });
        console.log(obj);

        patchResource(urlUser, obj)
            .catch(err => console.error(err));
    }

    formForUpdateUser.addEventListener("submit", (e) => updateUser(e));

    async function patchResource(url, data) {
        const res = await fetch(`${url}`, {
            method: "PATCH",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (!res.ok) {
            throw new Error(`Could no fetch ${url}, status: ${res.status}`);
        }

        return await res.json();
    }

})
